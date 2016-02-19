package com.dexesttp.hkxpack.data;

import java.util.ArrayList;
import java.util.Collection;

public class HKXFile {
	private final String contentsversion;
	private final int classversion;
	private final Collection<HKXObject> content;

	public HKXFile(String contentsversion, int classversion) {
		content = new ArrayList<>();
		this.contentsversion = contentsversion;
		this.classversion = classversion;
	}

	public String getContentsVersion() {
		return contentsversion;
	}

	public int getClassVersion() {
		return classversion;
	}

	public Collection<HKXObject> content() {
		return content;
	}

	public void add(HKXObject object) {
		content.add(object);
	}

	public void addAll(HKXObject... hkxObjects) {
		for(HKXObject object : hkxObjects) {
			add(object);
		}
	}
}
