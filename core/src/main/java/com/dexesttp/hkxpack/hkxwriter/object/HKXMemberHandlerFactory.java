package com.dexesttp.hkxpack.hkxwriter.object;

import java.io.File;
import java.util.List;

import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.descriptor.members.HKXMemberTemplate;
import com.dexesttp.hkxpack.hkx.data.DataExternal;
import com.dexesttp.hkxpack.hkx.data.DataInternal;

public class HKXMemberHandlerFactory {
	private final File outFile;
	private final HKXEnumResolver enumResolver;
	private final List<DataInternal> data1List;
	private final List<DataExternal> data2List;

	/**
	 * Creates a {@link HKXMemberHandlerFactory}.
	 * @param outFile the {@link File} to write into.
	 * @param enumResolver the {@link HKXEnumResolver} to use to resolve enums.
	 * @param data1List the list of {@link DataInternal} to fill while solving a 
	 * @param data2List
	 */
	public HKXMemberHandlerFactory(File outFile, HKXEnumResolver enumResolver, List<DataInternal> data1List, List<DataExternal> data2List) {
		this.outFile = outFile;
		this.enumResolver = enumResolver;
		this.data1List = data1List;
		this.data2List = data2List;
	}

	/**
	 * Creates the {@link HKXMemberHandler} rekevant to the given {@link HKXMemberTemplate}.
	 * @param memberTemplate the {@link HKXMemberTemplate} to base the {@link HKXMemberHandler} on.
	 * @return the relevant {@link HKXMemberHandler}.
	 */
	public HKXMemberHandler create(HKXMemberTemplate memberTemplate) {
		return new HKXMemberHandler() {
			@Override
			public HKXMemberCallback write(HKXMember member, long currentPos) {
				return new HKXMemberCallback() {
					@Override
					public long process(List<HKXMemberCallback> memberCallbacks, long position) {
						return 0;
					}
				};
			}
		};
	}

}
