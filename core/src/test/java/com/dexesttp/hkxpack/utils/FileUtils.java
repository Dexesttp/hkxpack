package com.dexesttp.hkxpack.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtils {
	/**
	 * Convert a given resource to a temporary {@link File}.
	 * @param resourceName
	 * @return
	 * @throws Exception
	 */
	public static File resourceToTemporaryFile(String resourceName) throws Exception {
		InputStream inputStream = FileUtils.class.getResourceAsStream(resourceName);
		File tempFile = File.createTempFile(resourceName, ".tmp");
	    Files.copy(inputStream, tempFile.toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
	    inputStream.close();
	    return tempFile;
	}

	/**
	 * Convert a given resource to a {@link ByteBuffer}.
	 * @param resourceName
	 * @return
	 * @throws Exception
	 */
	public static ByteBuffer resourceToHKXByteBuffer(String resourceName) throws Exception {
		InputStream inputStream = FileUtils.class.getResourceAsStream(resourceName);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] tmp = new byte[1000];
		int l;
		while((l = inputStream.read(tmp)) != -1)
			byteArrayOutputStream.write(tmp, 0, l);
		ByteBuffer toRead = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
		inputStream.close();
		return toRead;
	}
}
