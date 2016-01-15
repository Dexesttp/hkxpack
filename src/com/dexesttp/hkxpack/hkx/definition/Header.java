package com.dexesttp.hkxpack.hkx.definition;

public class Header {
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
}
