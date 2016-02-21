package com.dexesttp.hkxpack.tagreader;

import org.w3c.dom.Node;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;

public class TagXMLObjectHandler {
	private final HKXFile hkxFile;
	private final HKXDescriptorFactory descriptorFactory;

	public TagXMLObjectHandler(HKXFile hkxFile, HKXDescriptorFactory descriptorFactory) {
		this.hkxFile = hkxFile;
		this.descriptorFactory = descriptorFactory;
	}

	public void handleObject(Node objectNode) {
		// TODO handle a Tag object
	}
}
