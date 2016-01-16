package com.dexesttp.hkxpack.hkx.handler;

public interface IHeader {

	/**
	 * Retrieves the HKX version.
	 * @return version
	 */
	public int getVersion();

	/**
	 * Retrieves the HKX version name.
	 * @return name
	 */
	public String getVersionName();
	
	/**
	 * Retrieves a region name. Comportement is undefined if the region doesn't exist.
	 * @param region
	 * @return offset
	 */
	public String getRegionName(int region);
	
	/**
	 * Retrieves a region offset. Comportement is undefined if the region doesn't exist.
	 * @param region
	 * @return offset
	 */
	public long getRegionOffset(int region);
	
	/**
	 * Retrieves the offset from a region's data. Comportement is undefined if the region or data doesn't exist.
	 * @param region
	 * @param dataID
	 * @return offset
	 */
	public long getRegionDataOffset(int region, int dataID);
}
