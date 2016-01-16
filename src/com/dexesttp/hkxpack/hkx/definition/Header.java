package com.dexesttp.hkxpack.hkx.definition;

import com.dexesttp.hkxpack.hkx.handler.IHeader;
import com.dexesttp.hkxpack.resources.ByteUtils;

public class Header implements IHeader {
	public byte[] magicCode = new byte[]{87, -32, -32, 87, 16, -64, -64, 16, 0, 0, 0, 0};
	public byte[] version = new byte[4];
	public byte[] extras = new byte[4];
	public byte[] constants = new byte[20];
	public byte[] verName = new byte[16];
	public byte[] padding = new byte[4];
	public byte[] extras_new = new byte[2];
	public byte[] padding_new = new byte[2];
	
	public HeaderComponent[] components = new HeaderComponent[3];
	{
		for(int i = 0; i < components.length; i++)
			components[i] = new HeaderComponent();
	}
	
	// Interface implementation
	
	@Override
	public int getVersion() {
		return ByteUtils.getInt(version);
	}

	@Override
	public String getVersionName() {
		return new String(verName);
	}
	
	@Override
	public String getRegionName(int region) {
		return new String(components[region].sectionName);
	}
	
	@Override
	public long getRegionOffset(int region) {
		return ByteUtils.getInt(components[region].offset);
	}
	
	@Override
	public long getRegionDataOffset(int region, int dataID) {
		byte[] data = null;
		switch(dataID) {
			case 1 : data = components[region].part1; break;
			case 2 : data = components[region].part2; break;
			case 3 : data = components[region].part3; break;
			case 4 : data = components[region].part4; break;
			case 5 : data = components[region].part5; break;
			case 6 : data = components[region].part6; break;
			default: data = null; break;
		}
		return ByteUtils.getInt(data);
	}
}
