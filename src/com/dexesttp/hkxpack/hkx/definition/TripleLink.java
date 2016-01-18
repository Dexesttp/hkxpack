package com.dexesttp.hkxpack.hkx.definition;

import com.dexesttp.hkxpack.resources.ByteUtils;

public class TripleLink {
	public byte[] from = new byte[4];
	public byte[] value = new byte[4];
	public byte[] to = new byte[4];
	public String dump() {
		return "Triple : " + Integer.toHexString(ByteUtils.getInt(from)) + " // " + Integer.toHexString(ByteUtils.getInt(to));
	}
}
