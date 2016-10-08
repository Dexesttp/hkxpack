package com.dexesttp.hkxpack.descriptor.reader;

import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadException;

/**
 * Creates a {@link ClassXMLReader}  from a {@link com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory}.
 * <p>
 * It is expected for the HKXDescriptorFactory to have the ClassXMLReader as internal reader. <br />
 * That means you must be doing something very technical to have to use a ClassXMLReaderFactory yourself.
 */
public class ClassXMLReaderFactory {
	private final transient HKXEnumResolver enumResolver;

	/**
	 * Create a new ClassXMLReaderFactory.
	 * @param enumResolver
	 * @see ClassXMLReaderFactory
	 */
	public ClassXMLReaderFactory(final HKXEnumResolver enumResolver) {
		this.enumResolver = enumResolver;
	}
	
	/**
	 * Create a {@link ClassXMLReader}, linked to a {@link HKXDescriptorFactory}.
	 * @param descriptor the {@link HKXDescriptorFactory} to use.
	 * @return a new {@link ClassXMLReader}
	 * @throws ClassListReadException
	 */
	public ClassXMLReader create(final HKXDescriptorFactory descriptor) throws ClassListReadException {
		ClassXMLList list = new ClassXMLList();
		return new ClassXMLReader(descriptor, list, enumResolver);
	}
}
