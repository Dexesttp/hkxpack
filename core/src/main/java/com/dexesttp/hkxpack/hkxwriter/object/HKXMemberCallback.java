package com.dexesttp.hkxpack.hkxwriter.object;

import java.util.List;

public interface HKXMemberCallback {
	public long process(List<HKXMemberCallback> memberCallbacks, long position);
}
