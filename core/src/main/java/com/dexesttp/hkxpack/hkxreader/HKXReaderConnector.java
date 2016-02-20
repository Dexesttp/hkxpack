package com.dexesttp.hkxpack.hkxreader;

import java.io.File;
import java.io.IOException;

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

public class HKXReaderConnector {
	public final HeaderData header;
	public final SectionData classnamesHead;
	public final SectionData dataHead;
	public final ClassnamesData classnamesdata;
	public final DataInterface data;
	public final Data1Interface data1;
	public final Data2Interface data2;
	public final Data3Interface data3;

	HKXReaderConnector(File file) throws IOException {
		// Extract the header
		HeaderInterface headInt = new HeaderInterface();
		headInt.connect(file);
		header = headInt.extract();
		headInt.close();
		
		// Extract the section interfaces
		SectionInterface sectInt = new SectionInterface();
		sectInt.connect(file, header);
		classnamesHead = sectInt.extract(0);
		dataHead = sectInt.extract(2);
		sectInt.close();
		
		// Extract the classnames
		ClassnamesInterface cnamesInt = new ClassnamesInterface();
		cnamesInt.connect(file, classnamesHead);
		classnamesdata = cnamesInt.extract();
		cnamesInt.close();

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
