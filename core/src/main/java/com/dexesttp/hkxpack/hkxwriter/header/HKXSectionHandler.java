package com.dexesttp.hkxpack.hkxwriter.header;

import com.dexesttp.hkxpack.hkx.header.HeaderData;
import com.dexesttp.hkxpack.hkx.header.SectionData;

/**
 * Handles filling {@link SectionData} from a {@link HeaderData} and needed values
 */
public class HKXSectionHandler {
	/**
	 * The sectionID for the CLASSNAME section.
	 */
	public static final int CLASSNAME = 0;
	/**
	 * The sectionID for the TYPES section.
	 */
	public static final int TYPES = 1;
	/**
	 * The sectionID for the DATA section.
	 */
	public static final int DATA = 2;

	private final long header_base_size = 0x40;
	private final int section_size = 0x40;
	private final HeaderData header;
	private Long classnamesEnd = null;

	/**
	 * Creates a HKXSectionHandler to handle creation of section data.
	 * @param header
	 */
	public HKXSectionHandler(HeaderData header) {
		this.header = header;
	}

	/**
	 * Initialize the given {@link SectionData} as the given SectionID section.
	 * @param sectionID the sectionID, either one of {@link #CLASSNAME}, {@link #TYPES} and {@link #DATA}.
	 * @param data the {@link SectionData} to initialize.
	 */
	public void init(int sectionID, SectionData data) {
		switch(sectionID) {
			case CLASSNAME:
				init_classname(data);
				break;
			case TYPES:
				init_types(data);
				break;
			case DATA:
				init_data(data);
				break;
			default:
				throw new IllegalArgumentException("SectionID isn't a knwon exception ID");
		}
	}
	
	private void init_classname(SectionData data) {
		data.name = "__classnames__";
		data.offset = header_base_size + header.padding_after + 3 * section_size;
	}

	private void init_types(SectionData data) {
		if(classnamesEnd != null) {
			data.name = "__types__";
			data.offset = classnamesEnd;
		}
	}

	private void init_data(SectionData data) {
		if(classnamesEnd != null) {
			data.name = "__data__";
			data.offset = classnamesEnd;
		}
	}


	/**
	 * Fills the given {@link SectionData} as the ClassName data, with the given cnameEnd data as its offset.
	 * <p>
	 * The {@link SectionData} has to be initialized frst using {@link #init(int, SectionData)}.
	 * @param data the {@link SectionData} to fill.
	 * @param cnameEnd the end position of the ClassNames section, from the beginning of the file.
	 */
	public void fillCName(SectionData classnames, long cnameEnd) {
		long cnameData = cnameEnd - classnames.offset;
		classnames.data1 = cnameData;
		classnames.data2 = cnameData;
		classnames.data3 = cnameData;
		classnames.data4 = cnameData;
		classnames.data5 = cnameData;
		classnames.end = cnameData;
		this.classnamesEnd = cnameEnd;
	}

}
