package com.dexesttp.hkxpack.hkxreader.member.arrays;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;
import com.dexesttp.hkxpack.l10n.SBundle;

public class HKXArrayContentsReaderFactory {
	private HKXReaderConnector connector;
	private HKXDescriptorFactory descriptorFactory;
	private HKXObjectReader objectReader;
	private PointerNameGenerator pointerNameGenerator;

	public HKXArrayContentsReaderFactory(HKXReaderConnector connector, HKXDescriptorFactory descriptorFactory, HKXObjectReader objectReader, PointerNameGenerator pointerNameGenerator) {
		this.connector = connector;
		this.descriptorFactory = descriptorFactory;
		this.objectReader = objectReader;
		this.pointerNameGenerator = pointerNameGenerator;
	}
	
	public HKXArrayContentsReader get(HKXMemberTemplate template) throws ClassFileReadError {
		switch(template.vsubtype.getFamily()) {
			case DIRECT:
			case COMPLEX:
				return new HKXDirectArrayContentsReader(connector, template.vsubtype);
			case STRING:
				return new HKXStringArrayContentsReader(connector, template.vsubtype);
			case OBJECT:
				HKXDescriptor descriptor = descriptorFactory.get(template.target);
				return new HKXObjectArrayContentsReader(objectReader, descriptorFactory, descriptor);
			case POINTER:
				return new HKXPointerArrayContentsReader(connector, pointerNameGenerator);
			default:
				throw new IllegalArgumentException(SBundle.getString("error.hkx.read.subtype") + template.vsubtype);
		}
	}
}
