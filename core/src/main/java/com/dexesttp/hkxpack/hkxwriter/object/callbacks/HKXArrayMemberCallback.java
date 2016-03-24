package com.dexesttp.hkxpack.hkxwriter.object.callbacks;

import java.io.IOException;
import java.util.List;

public interface HKXArrayMemberCallback {
	long process(List<HKXMemberCallback> memberCallbacks, long position) throws IOException;
}
