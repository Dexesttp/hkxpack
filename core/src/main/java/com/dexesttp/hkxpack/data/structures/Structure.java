package com.dexesttp.hkxpack.data.structures;

public class Structure {
	int size;
	
	public void setSize(int size) {
		this.size = size;
		resolvePadding();
	}
	
	public int getSize() {
		return size;
	}
	
	public void resolvePadding() {
		if(size > 8)
			if(size % 8 != 0)
				size = ((size + 8) / 8) * 8;
	}
}
