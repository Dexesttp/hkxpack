package com.dexesttp.hkxpack.hkx.handler;

public class HKXReaderFactory {
	public HKXReader build() {
		return new HKXHandlerImpl();
	}
}
