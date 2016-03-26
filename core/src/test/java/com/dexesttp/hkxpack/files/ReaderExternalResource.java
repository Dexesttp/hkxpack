package com.dexesttp.hkxpack.files;

import org.junit.rules.ExternalResource;

import com.dexesttp.hkxpack.data.HKXFile;

public abstract class ReaderExternalResource extends ExternalResource {
	public HKXFile file;
}
