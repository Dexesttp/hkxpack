package com.dexesttp.hkxpack.hkxwriter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Optional;

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
	/**
	 * Stores the default capacity for an outputted hkx file (currently {@value #DEFAULT_BUFFER_CAPACITY})
	 * <p>
	 * 10Mo default capacity should be ok, as the buffer will only use what it needs. The biggest found FO4 file was 1.4 Mo.
	 */
	public static final int DEFAULT_BUFFER_CAPACITY = 10000000;
	
	private final transient HKXEnumResolver enumResolver;
	private final transient Optional<File> outputFile;
	private final transient ByteBuffer outputBB;

	/**
	 * Creates a {@link HKXWriter}, with a default capacity of {@value #DEFAULT_BUFFER_CAPACITY}
	 * @param outputFile the {@link File} to output data into.
	 * @param enumResolver the {@link HKXEnumResolver} to use.
	 */
	public HKXWriter(final File outputFile, final HKXEnumResolver enumResolver)
	{
		this(outputFile, enumResolver, DEFAULT_BUFFER_CAPACITY);
	}

	/**
	 * Creates a {@link HKXWriter}.
	 * @param outputFile the {@link File} to output data into.
	 * @param enumResolver the {@link HKXEnumResolver} to use.
	 * @param bufferCapacity the capacity of the buffer to use, in bytes
	 */
	public HKXWriter(final File outputFile, final HKXEnumResolver enumResolver, final int bufferCapacity)
	{
		this.enumResolver = enumResolver;
		this.outputFile = Optional.of(outputFile);
		this.outputBB = ByteBuffer.allocateDirect(bufferCapacity);
	}

	/**
	 * Creates a {@link HKXWriter}.
	 * @param outputFile the {@link File} to output data into.
	 * @param enumResolver the {@link HKXEnumResolver} to use.
	 */
	public HKXWriter(final ByteBuffer outputBB, final HKXEnumResolver enumResolver)
	{
		this.outputBB = outputBB;
		this.outputFile = Optional.empty();
		this.enumResolver = enumResolver;
	}

	/**
	 * Writes a {@link HKXFile}'s data into this {@link HKXReader}'s {@Link ByteBuffer}
	 * and then optionally transfers that data to an outputFile {@link File} (if specified in the constructor).
	 * @param file the {@link HKXFile} to take data from.
	 * @throws IOException 
	 * @throws UnsupportedVersionError 
	 */
	public void write(final HKXFile file) throws IOException, UnsupportedVersionError
	{
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
			
			// Flips the ByteBuffer now, to set its limit to the end of the file.
			outputBB.flip();
			
			// Write the data section to the file.
			connector.writeSection(header, HKXSectionHandler.DATA, data);
		}
		catch (ClassCastException e)
		{
			LoggerUtil.add(new WrongInputCastException(String.format(SBundle.getString("error.hkx.write.cast")), e));
		}
		
		// Prepare the output ByteBuffer for use.
		outputBB.position(0);
		
		// If needed, write the output ByteBuffer back to the file.
		if (outputFile.isPresent())
		{
			try (RandomAccessFile out = new RandomAccessFile(outputFile.get(), "rw"))
			{
				byte[] bytes = new byte[outputBB.limit()];
				outputBB.get(bytes);
				out.write(bytes);
				out.close();
			}
		}
	}
}
