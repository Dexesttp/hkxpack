package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkxreader.HKXReader;
import com.dexesttp.hkxpack.hkxwriter.classnames.HKXClassnamesHandler;
import com.dexesttp.hkxpack.hkxwriter.exceptions.WrongInputCastException;
import com.dexesttp.hkxpack.hkxwriter.header.HKXHeaderFactory;
import com.dexesttp.hkxpack.hkxwriter.header.HKXSectionHandler;
import com.dexesttp.hkxpack.hkxwriter.utils.PointerResolver;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.resources.LoggerUtil;

/**
 * Handles writing a {@link HKXFile} into a {@link File}, using the binary hkx notation.
 */
public class HKXWriter
{
	private HKXEnumResolver enumResolver;
	private File outputFile;
	private ByteBuffer outputBB;

	/**
	 * Creates a {@link HKXWriter}.
	 * @param outputFile the {@link File} to output data into.
	 * @param enumResolver the {@link HKXEnumResolver} to use.
	 */
	public HKXWriter(File outputFile, HKXEnumResolver enumResolver)
	{
		this.enumResolver = enumResolver;
		this.outputFile = outputFile;
	}

	public HKXWriter(HKXEnumResolver enumResolver)
	{
		this.enumResolver = enumResolver;
	}

	/**
	 * Use this in order to control the temporary write buffer
	 * For example to set a smaller or larger capacity, or to use a HeapBuffer
	 * Calling this is not required under normal usage
	 * @param outputBB the {@link ByteBuffer} to fill on write
	 */
	public void setByteBuffer(ByteBuffer outputBB)
	{
		this.outputBB = outputBB;
	}

	/**
	 * After the write method is called, the return {@link ByteBuffer} will be filled with 
	 * the written data ready for use.
	 * This is useful if an outputFile {@link File} is not specified in the constructor. 
	 * @return A {@link ByteBuffer} filled with {@link HKXFile} data, as would be written to file.
	 */
	public ByteBuffer getByteBuffer()
	{
		return outputBB;
	}

	/**
	 * Writes a {@link HKXFile}'s data into this {@link HKXReader}'s {@Link ByteBuffer}
	 * and then optionally transfers that data to an outputFile {@link File} (if specified in the constructor).
	 * @param file the {@link HKXFile} to take data from.
	 * @throws IOException 
	 * @throws UnsupportedVersionError 
	 */
	public void write(HKXFile file) throws IOException, UnsupportedVersionError
	{
		// this should be the normal case
		if (outputBB == null)
		{
			//10 meg capacity should be ok, it'll only use what it needs
			//1.4 Mb is the biggest Fallout 4 hkx file I can find
			outputBB = ByteBuffer.allocateDirect(10000000);
		}

		// Connect to the file.
		HKXWriterConnector connector = new HKXWriterConnector(outputBB);

		// Create the header.
		HeaderData header = new HKXHeaderFactory().create(file);

		// Create the file's section data.
		HKXSectionHandler sectionHandler = new HKXSectionHandler(header);
		SectionData classnames = new SectionData();
		SectionData types = new SectionData();
		SectionData data = new SectionData();
		sectionHandler.init(HKXSectionHandler.CLASSNAME, classnames);

		// Create the ClassNames data.
		HKXClassnamesHandler cnameHandler = new HKXClassnamesHandler();
		ClassnamesData cnameData = cnameHandler.getClassnames(file);

		// Write ClassNames data to the file.
		long classnamesEnd = connector.writeClassnames(classnames, cnameData);
		sectionHandler.fillCName(classnames, classnamesEnd);
		connector.writeHeader(header);
		connector.writeSection(header, HKXSectionHandler.CLASSNAME, classnames);

		// Update things to prepare for Data writing.
		sectionHandler.init(HKXSectionHandler.TYPES, types);
		connector.writeSection(header, HKXSectionHandler.TYPES, types);
		sectionHandler.init(HKXSectionHandler.DATA, data);

		// Write data in the file and store data1/data2/data3 values.
		PointerResolver resolver = new PointerResolver();
		HKXDataHandler dataHandler = new HKXDataHandler(outputBB, cnameData, enumResolver);
		try
		{
			long endData = dataHandler.fillFile(data, file, resolver) - data.offset;
			data.data1 = endData % 0x10 == 0 ? endData : (1 + endData / 0x10) * 0x10;
			dataHandler.fillPointers(data, resolver);
			outputBB.flip();

			// Write the data section to the file.
			connector.writeSection(header, HKXSectionHandler.DATA, data);
		}
		catch (ClassCastException e)
		{
			LoggerUtil.add(new WrongInputCastException(String.format(SBundle.getString("error.hkx.write.cast")), e));
		}
		
		//prep for use
		outputBB.position(0);
		
		if (outputFile != null)
		{
			try (RandomAccessFile out = new RandomAccessFile(outputFile, "rw"))
			{				
				byte[] bytes = new byte[outputBB.limit()];
				outputBB.get(bytes);
				out.write(bytes);
				out.close();
			}
		}

	}
}
