package com.dexesttp.hkxpack.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadException;
import com.dexesttp.hkxpack.descriptor.reader.ClassXMLReader;
import com.dexesttp.hkxpack.descriptor.reader.ClassXMLReaderFactory;

/**
 * A HKXDescriptorFactory uses lazy instanciation to retrieve a non-unique HKXDescriptor.
 * Data may be reread when a different factory is used.
 */
public class HKXDescriptorFactory {
	private final transient ClassXMLReader reader;
	private final transient Map<String, HKXDescriptor> contents = new HashMap<>();
	
	/**
	 * Retrieves a new HKXDescriptorFactory.
	 * @param enumResolver the {@link HKXEnumResolver} to put the read enums into.
	 * @throws ClassListReadException if there was an error while reading the Class List.
	 */
	public HKXDescriptorFactory(final HKXEnumResolver enumResolver) throws ClassListReadException {
		ClassXMLReaderFactory factory = new ClassXMLReaderFactory(enumResolver);
		reader = factory.create(this);
	}
	
	/**
	 * Retrieves a HKXDescriptor from the class name.
	 * @param name the HKXDescriptor's name
	 * @return the HKXDescriptor
	 * @throws ClassFileReadException if there was an error while reading the Class File.
	 */
	public HKXDescriptor get(final String name) throws ClassFileReadException {
		synchronized(this) {
			if(contents.containsKey(name)) {
				return contents.get(name);
			}
			HKXDescriptor descriptor = reader.get(name);
			contents.put(name, descriptor);
			return descriptor;
		}
	}
}
