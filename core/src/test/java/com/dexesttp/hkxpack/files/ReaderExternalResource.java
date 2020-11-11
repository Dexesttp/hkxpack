package com.dexesttp.hkxpack.files;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.rules.ExternalResource;
import org.xml.sax.SAXException;

import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

/**
 * General purpose resource for a ReaderExternalResource
 */
public abstract class ReaderExternalResource extends ExternalResource {
	/**
	 * The loaded {@link HKXFile}.
	 */
	public HKXFile file;

	/**
	 * Override by the loader for {@link #file}
	 * 
	 * @throws InvalidPositionException
	 */
	@Override
	public abstract void before() throws IOException, ParserConfigurationException, SAXException,
			InvalidTagXMLException, InvalidPositionException;
}
