package com.dexesttp.hkxpack.xml.classxml.definition.members.resolver;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.function.Function;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxpack.commons.resolver.Resolver;
import com.dexesttp.hkxpack.hkx.definition.DoubleLink;
import com.dexesttp.hkxpack.hkx.handler.HKXHandler;
import com.dexesttp.hkxpack.hkx.reader.InternalLinkReader;
import com.dexesttp.hkxpack.resources.ByteUtils;
import com.dexesttp.hkxpack.resources.exceptions.UninitializedHKXException;
import com.dexesttp.hkxpack.xml.classxml.definition.members.ResolvedMember;
import com.dexesttp.hkxpack.xml.classxml.definition.members.resolved.DirectMember;

public enum DirectMemberResolver {
	TYPE_VOID(0, (value) -> {return "";}),
	TYPE_BOOL(1, (value) -> {return ByteUtils.getInt(value) == 0 ? "false" : "true";}),
	TYPE_CHAR(1, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_INT8(8, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT8(8, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_INT16(16, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT16(16, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_INT32(32, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT32(32, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_INT64(64, (value) -> {return ""+ByteUtils.getSInt(value);}),
	TYPE_UINT64(64, (value) -> {return ""+ByteUtils.getInt(value);}),
	// TODO change these ones
	TYPE_REAL(32, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_VECTOR4(128, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_MATRIX4(128, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_QUATERNION(128, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_ROTATION(96, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_TRANSFORM(96, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_QSTRANSFORM(128, (value) -> {return ""+ByteUtils.getInt(value);}),
	TYPE_CSTRING((file) -> {
		try {
			return ByteUtils.readString(file);
		} catch(Exception e) {
			return "";
		}
	});
	

	
	private final int size;
	private final Function<RandomAccessFile, String> action;
	
	private DirectMemberResolver(int size, Function<byte[], String> action) {
		this.size = size;
		this.action = (file) ->  {
			final Function<byte[], String> byteAction = action;
			final byte[] bytes = new byte[size];
			try {
				file.read(bytes);
			} catch (Exception e) {
				return "";
			}
			return byteAction.apply(bytes);
		};
	}
	
	private DirectMemberResolver(Function<RandomAccessFile, String> action) {
		this.size = 0;
		this.action = action;
	}
	
	public ResolvedMember resolve(String name, String classname) {
		return new DirectMember(name, classname) {
			@Override
			public long getSize() {
				return size;
			}

			@Override
			public Resolver<Node> getResolver(HKXHandler handler) throws IOException, UninitializedHKXException {
				InternalLinkReader links = handler.getInternalLinkReader();
				DoubleLink link = links.read();
				if(link == null)
					return null;
				System.out.println(link.dump());
				return new Resolver<Node>() {
					private final Document document = handler.getDocument();
					private final long pos = ByteUtils.getLong(link.from);
					@Override
					public long getPos() {
						return pos;
					}

					@Override
					public Node solve(RandomAccessFile file) throws IOException {
						String value = action.apply(file);
						Element node = document.createElement("hkparam");
						node.setAttribute("name", name);
						node.appendChild(document.createTextNode(value));
						return node;
					}
				};
			}
		};
	}
}
