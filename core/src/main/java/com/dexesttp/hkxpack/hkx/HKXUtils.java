package com.dexesttp.hkxpack.hkx;

/**
 * Contain utils for the HKX classes.
 */
public class HKXUtils {
	
	/**
	 * Snap the object's position to the relevant byte.
	 * @param currentPos
	 * @return
	 */
	public static long snapObject(long currentPos) {
		if(currentPos % 0x10 == 0)
			return currentPos;
		return (1 + currentPos / 0x10) * 0x10;
	}
	
	/**
	 * Snap the given position to the next line.
	 * @param currentPos
	 * @return
	 */
	public static long snapLine(long currentPos) {
		if(currentPos % 0x10 == 0)
			return currentPos;
		return (1 + currentPos / 0x10) * 0x10;
	}

	/**
	 * Snap the string's size to the relevant size.
	 * @param currentSize the stirng size.
	 * @return the snapped size.
	 */
	public static long snapString(long currentSize) {
		if(currentSize < 0x08)
			return 0x08;
		if(currentSize % 0x10 == 0)
			return currentSize;
		return (1 + currentSize / 0x10) * 0x10;
	}

	/**
	 * Snap a size to the given snap and offset to the given offset.
	 * @param offset the offset to snap
	 * @param snap the snap.
	 * @return
	 */
	public static long snapSize(long offset, long snap) {
		if(offset % snap == 0)
			return offset;
		return (1 + offset / snap) * snap;
	}
}
