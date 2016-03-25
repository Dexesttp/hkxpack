package com.dexesttp.hkxpack.hkxreader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.classnames.Classname;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.data.Data3Interface;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkxreader.member.HKXMemberReaderFactory;
import com.dexesttp.hkxpack.resources.LoggerUtil;

/**
 * Reads the content of a {@link File}, containing information in the hkx format, into a DOM-like {@link HKXFile}.
 */
public class HKXReader {
	private File hkxFile;
	private ByteBuffer hkxBB;
	private final HKXDescriptorFactory descriptorFactory;
	private final HKXEnumResolver enumResolver;

	/**
	 * Creates a {@link HKXReader}.
	 * @param hkxFile the {@link File} to read data from.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use to solve the {@link File}'s classes.
	 * @param enumResolver the {@link HKXEnumResolver} to store enumerations into.
	 */
	public HKXReader(File hkxFile, HKXDescriptorFactory descriptorFactory, HKXEnumResolver enumResolver) {
		this.hkxFile = hkxFile;
		this.descriptorFactory = descriptorFactory;
		this.enumResolver = enumResolver;
	}
	
	public HKXReader(ByteBuffer hkxBB, HKXDescriptorFactory descriptorFactory, HKXEnumResolver enumResolver) {
		this.hkxBB = hkxBB;
		this.descriptorFactory = descriptorFactory;
		this.enumResolver = enumResolver;
	}
	
	/**
	 * Read data from this {@link HKXReader}'s {@link File}.
	 * @return the read {@link HKXFile}
	 * @throws IOException if there was a problem accessing the file.
	 * @throws InvalidPositionException if there was a positionning problem while reading the file.
	 */
	public HKXFile read() throws IOException, InvalidPositionException {
	
		if(hkxFile != null)
		{
			RandomAccessFile raf =new RandomAccessFile(hkxFile, "rw" );
			this.hkxBB = raf.getChannel().map(MapMode.READ_WRITE, 0, hkxFile.length());
			raf.close();
		}
		
		// Connect the connector to the file.
		HKXReaderConnector connector= new HKXReaderConnector(hkxBB);
		
		// Get a file reader and a pointer name generator
		PointerNameGenerator generator = new PointerNameGenerator();
		HKXMemberReaderFactory memberFactory = new HKXMemberReaderFactory(descriptorFactory, connector, generator, enumResolver);
		HKXObjectReader creator = new HKXObjectReader(memberFactory);
		memberFactory.connectObjectCreator(creator);
		HKXDescriptorReader fileReader = new HKXDescriptorReader(creator, generator);
		
		// Retrieve useful data and interfaces from the header
		HeaderData header = connector.header;
		ClassnamesData classConverter = connector.classnamesdata;
		Data3Interface data3 = connector.data3;
		
		// Create the return object
		HKXFile content = new HKXFile(header.versionName, header.version);
		

		// Create all default names for hkobjects
		int pos = 0;
		try {
			while(true) {
				// Get the next data3 class.
				DataExternal currentClass = data3.read(pos++);
				// Asks for its name, resulting in creating it.
				generator.get(currentClass.from);
			}
		} catch (InvalidPositionException e) {
			// NO OP
		}
		// Reset position to the beginning of data3.
		pos = 0;
		
		// Retrieve the actual data
		try {
			while(true) {
				// Get the next data3 object
				DataExternal currentClass = data3.read(pos++);
				
				// Resolve the object's class into a HKXDescriptor
				Classname classObj = classConverter.get(currentClass.to);
				if(classObj != null) {
					String className = classObj.name;
					HKXDescriptor descriptor = descriptorFactory.get(className);
					
					// Read the HKXDescriptor into an HKXClass
					HKXObject result = fileReader.read(currentClass.from, descriptor);
					
					// Store the resulting class
					content.add(result);
				} else {
					LoggerUtil.add(new Exception("Illegal linked Classname position (" + currentClass.from + "//" + currentClass.to + "). Ignoring."));
				}
			}
		} catch (InvalidPositionException e) {
			if(!e.getSection().equals("DATA_3"))
				throw e;
		}
		
		return content;
	 
	}
	
}
