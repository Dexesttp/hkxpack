package com.dexesttp.hkxpack.hkx;

/**
 * Contain utils for the HKX classes.
 */
public final class HKXUtils {
	private static final long HALF_LINE = 0x08;
	private static final long FULL_LINE = 0x10;
	
	private HKXUtils() {
		// NO OP
	}
	
	/**
	 * Snap the object's position to the relevant byte.
	 * @param currentPos
	 * @return
	 */
	public static long snapObject(final long currentPos) {
		if(currentPos % FULL_LINE == 0)
			return currentPos;
		return (1 + currentPos / FULL_LINE) * FULL_LINE;
	}
	
	/**
	 * Snap the given position to the next line.
	 * @param currentPos
	 * @return
	 */
	public static long snapLine(final long currentPos) {
		if(currentPos % FULL_LINE == 0)
			return currentPos;
		return (1 + currentPos / FULL_LINE) * FULL_LINE;
	}

	/**
	 * Snap the string's size to the relevant size.
	 * @param currentSize the string size.
	 * @return the snapped size.
	 */
	public static long snapString(final long currentSize) {
		if(currentSize < HALF_LINE )
			return HALF_LINE;
		if(currentSize % FULL_LINE == 0)
			return currentSize;
		return (1 + currentSize / FULL_LINE ) * FULL_LINE;
	}

	/**
	 * Snap a size to the given snap and offset to the given offset.
	 * @param offset the offset to snap
	 * @param snap the snap.
	 * @return
	 */
	public static long snapSize(final long offset, final long snap) {
		if(offset % snap == 0)
			return offset;
		return (1 + offset / snap) * snap;
	}
}
