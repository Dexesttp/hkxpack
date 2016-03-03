package com.dexesttp.hkxpack.tagreader.members;

import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXPointerMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.Flag;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.l10n.SBundle;
import com.dexesttp.hkxpack.tagreader.TagXMLNodeHandler;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * Allows creating {@link TagXMLContentsHandler}s.
 */
public class TagXMLContentsHandlerFactory {
	private final TagXMLNodeHandler nodeHandler;

	/**
	 * Creates a {@link TagXMLContentsHandlerFactory}.
	 * @param nodeHandler the {@link TagXMLNodeHandler} to use while resolving objects.
	 */
	public TagXMLContentsHandlerFactory(TagXMLNodeHandler nodeHandler) {
		this.nodeHandler = nodeHandler;
	}
	
	/**
	 * Returns a {@link TagXMLContentsHandler} from a {@link HKXType}.
	 * @param type the {@link HKXType} to handle.
	 * @return the {@link TagXMLContentsHandler} that can ahndle the given {@link HKXType}.
	 */
	public TagXMLContentsHandler getHandler(HKXType type) {
		switch(type.getFamily()) {
			case DIRECT:
				return new TagXMLDirectHandler();
			case ENUM:
				return (member, memberTemplate) -> {
						HKXDirectMember<String> directMember = new HKXDirectMember<>(memberTemplate.name, memberTemplate.vtype);
						if(member == null) {
							if(memberTemplate.flag == Flag.SERIALIZE_IGNORED)
								return directMember;
							throw new InvalidTagXMLException(SBundle.getString("error.tag.read.member") + memberTemplate.name);
						}
						directMember.set(member.getTextContent());
						return directMember;
					};
			case STRING:
				return (member, memberTemplate) -> {
						HKXStringMember stringMember = new HKXStringMember(memberTemplate.name, memberTemplate.vtype);
						if(member == null) {
							if(memberTemplate.flag == Flag.SERIALIZE_IGNORED)
								return stringMember;
							throw new InvalidTagXMLException(SBundle.getString("error.tag.read.member") + memberTemplate.name);
						}
						stringMember.set(member.getTextContent());
						return stringMember;
					};
			case POINTER:
				return (member, memberTemplate) -> {
						HKXPointerMember pointerMember = new HKXPointerMember(memberTemplate.name, memberTemplate.vtype, memberTemplate.vsubtype, memberTemplate.target);
						if(member == null) {
							if(memberTemplate.flag == Flag.SERIALIZE_IGNORED)
								return pointerMember;
							throw new InvalidTagXMLException(SBundle.getString("error.tag.read.member") + memberTemplate.name);
						}
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
