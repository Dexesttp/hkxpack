package com.dexesttp.hkxpack.hkxreader.member;

import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadError;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkxreader.HKXObjectReader;
import com.dexesttp.hkxpack.hkxreader.HKXReaderConnector;
import com.dexesttp.hkxpack.hkxreader.PointerNameGenerator;
import com.dexesttp.hkxpack.l10n.SBundle;

public class HKXMemberReaderFactory {
	private final HKXDescriptorFactory descriptorFactory;
	private final HKXReaderConnector connector;
	private final PointerNameGenerator generator;
	private final HKXEnumResolver enumResolver;
	private HKXObjectReader objectCreator;

	public HKXMemberReaderFactory(HKXDescriptorFactory descriptorFactory, HKXReaderConnector connector, PointerNameGenerator generator, HKXEnumResolver enumResolver) {
		this.descriptorFactory = descriptorFactory;
		this.connector = connector;
		this.generator = generator;
		this.enumResolver = enumResolver;
	}

	public void connectObjectCreator(HKXObjectReader objectCreator) {
		this.objectCreator = objectCreator;
	}

	public HKXMemberReader getMemberReader(HKXMemberTemplate template) throws ClassFileReadError {
		switch(template.vtype.getFamily()) {
			case DIRECT:
			case COMPLEX:
				return new HKXDirectMemberReader(connector, template.name, template.vtype, template.offset);
			case ENUM:
				return new HKXEnumMemberReader(connector, enumResolver, template.name, template.vtype, template.vsubtype, template.target, template.offset);
				
			case ARRAY:
				switch(template.vsubtype.getFamily()) {
					case DIRECT:
					case COMPLEX:
						return new HKXDirectArrayMemberReader(connector, template.name, template.vsubtype, template.offset);
					case STRING:
						return new HKXStringArrayMemberReader(connector, template.name, template.vsubtype, template.offset);
					case OBJECT:
						HKXDescriptor descriptor = descriptorFactory.get(template.target);
						return new HKXObjectArrayMemberReader(connector, objectCreator, descriptorFactory, template.name, template.offset, descriptor);
					case POINTER:
						return new HKXPointerArrayMemberReader(connector, generator, template.name, template.vsubtype, template.offset);
					default:
						throw new IllegalArgumentException(SBundle.getString("error.hkx.read.subtype") + template.vsubtype);
				}
			case OBJECT:
				HKXDescriptor descriptor = descriptorFactory.get(template.target);
				return new HKXObjectMemberReader(objectCreator, template.name, template.offset, descriptor);
			case POINTER:
				return new HKXPointerMemberReader(connector, generator, template.name, template.vsubtype, template.offset);
			case STRING:
				return new HKXStringMemberReader(connector, template.name, template.vtype, template.offset);
			default:
				throw new IllegalArgumentException(SBundle.getString("error.hkx.read.type") + template.vtype);
		}
	}
}
