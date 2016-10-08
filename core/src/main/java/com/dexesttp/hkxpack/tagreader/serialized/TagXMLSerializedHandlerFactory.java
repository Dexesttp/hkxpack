package com.dexesttp.hkxpack.tagreader.serialized;

import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXEnumMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.tagreader.members.TagXMLContentsHandler;

/**
 * Creates a {@link TagXMLSerializedHandler} from a given {@link HKXType}.
 */
public class TagXMLSerializedHandlerFactory {
	private final transient HKXDescriptorFactory descriptorFactory;
	
	/**
	 * Creates a {@link TagXMLSerializedHandlerFactory}.
	 * @param descriptorFactory the {@link HKXDescriptorFactory} to use while solving classes.
	 */
	public TagXMLSerializedHandlerFactory(final HKXDescriptorFactory descriptorFactory) {
		this.descriptorFactory = descriptorFactory;
	}

	/**
	 * Returns a {@link TagXMLContentsHandler} to support serialized members from a {@link HKXType}.
	 * @param type the {@link HKXType} to handle.
	 * @return the {@link TagXMLContentsHandler} that can ahndle the given {@link HKXType}.
	 */
	public TagXMLSerializedHandler getSerializedHandler(final HKXType type) {
		switch(type.getFamily()) {
			case DIRECT:
				return new TagXMLDirectSerializedHandler();
			case ENUM:
				return (memberTemplate) -> {
						HKXEnumMember enumMember = new HKXEnumMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype, memberTemplate.target);
						enumMember.set("");
						return enumMember;
					};
			case STRING:
				return (memberTemplate) -> {
						HKXStringMember stringMember = new HKXStringMember(memberTemplate.name, memberTemplate.vtype);
						return stringMember;
					};
			case POINTER:
				return (memberTemplate) -> {
						HKXPointerMember pointerMember = new HKXPointerMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype, memberTemplate.target);
						pointerMember.set("");
						return pointerMember;
					};
			case COMPLEX:
				return new TagXMLComplexSerializedHandler();
			case ARRAY:
				return (memberTemplate) -> {
					return new HKXArrayMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype);
				};
			case OBJECT:
				return new TagXMLEmbeddedObjectSerializedHandler(this, descriptorFactory);
			default :
				throw new IllegalArgumentException(SBundle.getString("error.tag.read.type.unknown"));
		}
	}
}
