package com.dexesttp.hkxpack.hkxreader;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.utils.FileUtils;


public class HKXReaderExternalResource extends ReaderExternalResource {
	private String baseFileResourceName;

	public HKXReaderExternalResource(String baseFileResourceName) {
		this.baseFileResourceName = baseFileResourceName;
	}
	
	@Override
	public void before() throws Exception {
		ByteBuffer buffer = FileUtils.resourceToHKXByteBuffer(baseFileResourceName + ".hkx");
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		HKXReader reader = new HKXReader(buffer, descriptorFactory, enumResolver);
		file = reader.read();
	}
}
