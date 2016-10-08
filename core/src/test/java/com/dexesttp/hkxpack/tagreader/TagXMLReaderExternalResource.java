package com.dexesttp.hkxpack.tagreader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;
import com.dexesttp.hkxpack.utils.FileUtils;

/**
 * External resource handler for the TagXML file.
 */
public class TagXMLReaderExternalResource extends ReaderExternalResource {
	private final transient String baseFileResourceName;

	/**
	 * Creates a {@link TagXMLReaderExternalResource}
	 * @param baseFileResourceName the resource file's name, without its extension
	 */
	public TagXMLReaderExternalResource(final String baseFileResourceName) {
		super();
		this.baseFileResourceName = baseFileResourceName;
	}
	
	/**
	 * Set up the resource as the {@link #file} member
	 */
	@Override
	public void before() throws IOException, ParserConfigurationException, SAXException, InvalidTagXMLException {
		File baseFile = FileUtils.resourceToTemporaryFile(baseFileResourceName + ".xml");
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		TagXMLReader reader = new TagXMLReader(baseFile, descriptorFactory);
		file = reader.read();
	}
}
