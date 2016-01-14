package com.dexesttp.hkxpack.hkx.reader;

import java.io.IOException;

import com.dexesttp.hkxpack.commons.parser.FixedReader;
import com.dexesttp.hkxpack.hkx.definition.TripleLink;

public class TripleLinkReader extends FixedReader<TripleLink> {

	@Override
	protected TripleLink readData() throws IOException {
		TripleLink res = new TripleLink();
		file.read(res.from);
		file.read(res.value);
		file.read(res.to);
		return res;
	}

	@Override
	protected long getEntitySize() {
		return 12;
	}

}
