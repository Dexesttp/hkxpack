package com.dexesttp.hkxpack.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassListReadError;
import com.dexesttp.hkxpack.descriptor.reader.ClassXMLReader;
import com.dexesttp.hkxpack.descriptor.reader.ClassXMLReaderFactory;

/**
 * A HKXDescriptorFactory uses lazy instanciation to retrieve a non-unique HKXDescriptor.
 * Data may be reread when a different factory is used.
 */
public class HKXDescriptorFactory {
	private final ClassXMLReader reader;
	private Map<String, HKXDescriptor> contents = new HashMap<>();
	
	/**
	 * Retrieves a new HKXDescriptorFactory.
	 * @throws ClassListReadError if there was an error while reading the Class List.
	 */
	public HKXDescriptorFactory() throws ClassListReadError {
		ClassXMLReaderFactory factory = new ClassXMLReaderFactory();
		reader = factory.create(this);
	}
	
	/**
	 * Retrieves a HKXDescriptor from the class name.
	 * @param name the HKXDescriptor's name
	 * @return the HKXDescriptor
	 * @throws ClassFileReadError if there was an error while reading the Class File.
	 */
	public synchronized HKXDescriptor get(String name) throws ClassFileReadError {
		if(contents.containsKey(name))
			return contents.get(name);
		HKXDescriptor descriptor = reader.get(name);
		contents.put(name, descriptor);
		return descriptor;
	}
}
