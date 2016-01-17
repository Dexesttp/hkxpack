package com.dexesttp.hkxpack.hkx.logic;

import java.io.IOException;
import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.DataReader;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.ClassXML;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ClassXMLMember;

public class DataLogic {

	private int value = 3;
	
	public DataLogic() {
	}

	public void resolve(HKXHandler handler) throws UninitializedHKXException, IOException, UnresolvedMemberException {
		final LinkedList<ClassXML> instances = handler.getInstanceList();
		final DataReader reader = handler.getDataReader();
		final Document document = handler.getDocument();
		final Node docRoot = document.getChildNodes().item(0);
		final Element root = document.createElement("hksection");
		root.setAttribute("name", "__data__");
		ClassXML instance;
		while((instance = instances.poll()) != null) {
			Element classElement = document.createElement("hkobject");
			classElement.setAttribute("name", "#" + value++);
			classElement.setAttribute("class", instance.getClassName());
			classElement.setAttribute("signature", ""+instance.getClassID());
			for(ClassXMLMember member : instance.getMembers()) {
				Resolver<Node> resolver = member.getResolver(handler);
				if(resolver != null) {
					reader.setNext(resolver);
					Node node = reader.read();
					classElement.appendChild(node);
				}
			}
			root.appendChild(classElement);
		}
		docRoot.appendChild(root);
	}
}
