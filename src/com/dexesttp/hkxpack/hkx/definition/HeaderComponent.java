package com.dexesttp.hkxpack.hkx.definition;

public class HeaderComponent {
	public byte[] sectionName = new byte[16];
	public byte[] magic = new byte[]{0, 0, 0, -1};
	public byte[] offset = new byte[4];
	public byte[] part1 = new byte[4];
	public byte[] part2 = new byte[4];
	public byte[] part3 = new byte[4];
	public byte[] part4 = new byte[4];
	public byte[] part5 = new byte[4];
	public byte[] part6 = new byte[4];
}
