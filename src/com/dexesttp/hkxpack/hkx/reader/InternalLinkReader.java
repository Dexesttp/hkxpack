package com.dexesttp.hkxpack.hkx.reader;

import java.io.IOException;

import com.dexesttp.hkxpack.commons.parser.FixedReader;
import com.dexesttp.hkxpack.hkx.definition.DoubleLink;

public class InternalLinkReader extends FixedReader<DoubleLink> {

	@Override
	protected DoubleLink readData() throws IOException {
		DoubleLink res = new DoubleLink();
		file.read(res.from);
		file.read(res.to);
		return res;
	}

	@Override
	protected long getEntitySize() {
		return 8;
	}
	
	public long getCurrentPosition() {
		return this.position + this.arrayPos * getEntitySize();
	}

}
