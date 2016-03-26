package com.dexesttp.hkxpack.tagreader;

import java.io.File;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.utils.FileUtils;

public class TagXMLReaderExternalResource extends ReaderExternalResource {
	private String baseFileResourceName;

	public TagXMLReaderExternalResource(String baseFileResourceName) {
		this.baseFileResourceName = baseFileResourceName;
	}
	
	@Override
	public void before() throws Exception {
		File baseFile = FileUtils.resourceToTemporaryFile(baseFileResourceName + ".xml");
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		TagXMLReader reader = new TagXMLReader(baseFile, descriptorFactory);
		file = reader.read();
	}
}
