package com.dexesttp.hkxpack.resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.dexesttp.hkxunpack.format.Format;
import com.dexesttp.hkxunpack.format.Format_hk_2010_2_0_r1;
import com.dexesttp.hkxunpack.format.Format_hk_2014_1_0_r1;

public class FormatUtils {
	@SuppressWarnings({ "serial", "rawtypes" })
	private static final Map<Integer, Class<Format>> formatMap = Collections.unmodifiableMap(new HashMap<Integer, Class>() {{
		put(11, Format_hk_2014_1_0_r1.class);
		put(8, Format_hk_2010_2_0_r1.class);
	}});
	
	public static Format getFormat(int version) throws InvalidFormatException {
		try {
			return formatMap.get(version).newInstance();
		} catch (Exception e) {
			throw new InvalidFormatException(version);
		}
	}
}
