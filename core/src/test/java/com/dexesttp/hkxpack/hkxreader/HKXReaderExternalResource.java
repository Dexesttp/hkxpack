package com.dexesttp.hkxpack.hkxreader;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.files.ReaderExternalResource;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.utils.FileUtils;

/**
 * The external resource to handle the HKX file.
 */
public class HKXReaderExternalResource extends ReaderExternalResource {
	private final transient String baseFileResourceName;

	/**
	 * Creates the HKXReader external resource
	 * @param baseFileResourceName
	 */
	public HKXReaderExternalResource(final String baseFileResourceName) {
		super();
		this.baseFileResourceName = baseFileResourceName;
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public void before() throws IOException, InvalidPositionException {
		ByteBuffer buffer = FileUtils.resourceToHKXByteBuffer(baseFileResourceName + ".hkx");
		HKXEnumResolver enumResolver = new HKXEnumResolver();
		HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
		HKXReader reader = new HKXReader(buffer, descriptorFactory, enumResolver);
		file = reader.read();
	}
}
