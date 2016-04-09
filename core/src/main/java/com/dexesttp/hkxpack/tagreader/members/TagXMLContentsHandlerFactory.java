package com.dexesttp.hkxpack.tagreader.members;

import com.dexesttp.hkxpack.data.members.HKXEnumMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;

/**
 * Allows creating {@link TagXMLContentsHandler}s.
 */
public class TagXMLContentsHandlerFactory {
	private final transient TagXMLNodeHandler nodeHandler;

	/**
	 * Creates a {@link TagXMLContentsHandlerFactory}.
	 * @param nodeHandler the {@link TagXMLNodeHandler} to use while resolving objects.
	 */
	public TagXMLContentsHandlerFactory(final TagXMLNodeHandler nodeHandler) {
		this.nodeHandler = nodeHandler;
	}
	
	/**
	 * Returns a {@link TagXMLContentsHandler} from a {@link HKXType}.
	 * @param type the {@link HKXType} to handle.
	 * @return the {@link TagXMLContentsHandler} that can ahndle the given {@link HKXType}.
	 */
	public TagXMLContentsHandler getHandler(final HKXType type) {
		switch(type.getFamily()) {
			case DIRECT:
				return new TagXMLDirectHandler();
			case ENUM:
				return (member, memberTemplate) -> {
						HKXEnumMember enumMember = new HKXEnumMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype, memberTemplate.target);
						enumMember.set(member.getTextContent());
						return enumMember;
					};
			case STRING:
				return (member, memberTemplate) -> {
						HKXStringMember stringMember = new HKXStringMember(memberTemplate.name, memberTemplate.vtype);
						stringMember.set(member.getTextContent());
						return stringMember;
					};
			case POINTER:
				return (member, memberTemplate) -> {
						HKXPointerMember pointerMember = new HKXPointerMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype, memberTemplate.target);
						pointerMember.set(member.getTextContent());
						return pointerMember;
					};
			case COMPLEX:
				return new TagXMLComplexHandler();
			case ARRAY:
				return new TagXMLArrayHandler(nodeHandler);
			case OBJECT:
				return new TagXMLEmbeddedObjectHandler(nodeHandler);
			default :
				throw new IllegalArgumentException(SBundle.getString("error.tag.read.type.unknown"));
		}
	}
}
