package com.dexesttp.hkxpack.hkx;

/**
 * Contain utils for the HKX classes.
 */
public class HKXUtils {

	/**
	 * Snap to the next 0x04 factor if needed.
	 * @param l the value to snap.
	 * @return the snapped size.
	 */
	// TODO maybe improve SnapSize if it happens it isn't good enough.
	public static long snapSize(long l) {
		long smallSize = l / 4;
		smallSize *= 4;
		if(l == smallSize)
			return l;
		else
			return smallSize + 4;
	}
	
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
}
