package com.dexesttp.hkxpack.hkxreader;

import java.nio.ByteBuffer;

import com.dexesttp.hkxpack.hkx.classnames.ClassnamesData;
import com.dexesttp.hkxpack.hkx.classnames.ClassnamesInterface;
import com.dexesttp.hkxpack.hkx.data.Data1Interface;
import com.dexesttp.hkxpack.hkx.data.Data2Interface;
import com.dexesttp.hkxpack.hkx.data.Data3Interface;
import com.dexesttp.hkxpack.hkx.data.DataInterface;
import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.HeaderInterface;
import com.dexesttp.hkxpack.hkx.header.SectionData;
import com.dexesttp.hkxpack.hkx.header.SectionInterface;

/**
 * Handles connexion between a {@link ByteBuffer} and a {@link HKXReader}.
 * <p>
 * Created and managed by {@link HKXReader}.
 */
public class HKXReaderConnector {
	public final transient HeaderData header;
	public final transient SectionData classnamesHead;
	public final transient SectionData dataHead;
	public final transient ClassnamesData classnamesdata;
	public final transient DataInterface data;
	public final transient Data1Interface data1;
	public final transient Data2Interface data2;
	public final transient Data3Interface data3;

	HKXReaderConnector(final ByteBuffer file) {
		// Extract the header
		HeaderInterface headInt = new HeaderInterface();
		headInt.connect(file);
		header = headInt.extract();

		// Extract the section interfaces
		SectionInterface sectInt = new SectionInterface();
		sectInt.connect(file, header);
		classnamesHead = sectInt.extract(0);
		dataHead = sectInt.extract(2);

		// Extract the classnames
		ClassnamesInterface cnamesInt = new ClassnamesInterface();
		cnamesInt.connect(file, classnamesHead);
		classnamesdata = cnamesInt.extract();

		// Connect the interfaces
		data1 = new Data1Interface();
		data1.connect(file, dataHead);
		data2 = new Data2Interface();
		data2.connect(file, dataHead);
		data3 = new Data3Interface();
		data3.connect(file, dataHead);
		data = new DataInterface();
		data.connect(file, dataHead);
	}
}
