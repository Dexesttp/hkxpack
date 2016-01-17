package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.resources.exceptions.UnresolvedMemberException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.DirectMember;

public enum DirectMemberResolver {
	TYPE_VOID(0, (value) -> {return null;}),
	TYPE_BOOL(1, (value) -> {return null;}),
	TYPE_CHAR(1, (value) -> {return null;}),
	TYPE_INT8(8, (value) -> {return null;}),
	TYPE_UINT8(8, (value) -> {return null;}),
	TYPE_INT16(16, (value) -> {return null;}),
	TYPE_UINT16(16, (value) -> {return null;}),
	TYPE_INT32(32, (value) -> {return null;}),
	TYPE_UINT32(32, (value) -> {return null;}),
	TYPE_INT64(64, (value) -> {return null;}),
	TYPE_UINT64(64, (value) -> {return null;}),
	TYPE_REAL(32, (value) -> {return null;}),
	TYPE_VECTOR4(128, (value) -> {return null;}),
	TYPE_MATRIX4(128, (value) -> {return null;}),
	TYPE_QUATERNION(128, (value) -> {return null;}),
	TYPE_ROTATION(96, (value) -> {return null;}),
	TYPE_TRANSFORM(96, (value) -> {return null;});
	

	
	private final int size;
	private final BiFunction<RandomAccessFile, Node, String> action;
	
	private DirectMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = (file, node) -> {
			byte[] byteArray = new byte[size];
			try {
				file.read(byteArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return action.apply(byteArray);
		}; 
	}
	
	public ResolvedMember resolve(String name) {
		return new DirectMember(name) {
			@Override
			public long getSize() {
				return size;
			}
			
			public Node apply(RandomAccessFile file, long position, Document document) throws IOException {
				Node node = document.createElement(name);
				file.seek(position);
				Text text = document.createTextNode(action.apply(file, node));
				node.appendChild(text);
				return node;
			}

			@Override
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UnresolvedMemberException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
