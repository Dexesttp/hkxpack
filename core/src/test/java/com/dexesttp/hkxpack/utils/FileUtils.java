package com.dexesttp.hkxpack.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Helper methods to handle a {@link File}
 */
public final class FileUtils {
	private FileUtils() {
		// NO OP
	}

	/**
	 * Convert a given resource to a temporary {@link File}.
	 * 
	 * @param resourceName the resource to convert
	 * @return the {@link File} object pointing to the temporary File.
	 * @throws Exception
	 */
	public static File resourceToTemporaryFile(final String resourceName) throws IOException {
		InputStream inputStream = FileUtils.class.getResourceAsStream(resourceName);
		File tempFile = File.createTempFile(resourceName, ".tmp");
		Files.copy(inputStream, tempFile.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
		inputStream.close();
		return tempFile;
	}

	/**
	 * Convert a given resource to a {@link ByteBuffer}.
	 * 
	 * @param resourceName the resource to convert
	 * @return the ByteBuffer filled with the resource's data.
	 * @throws Exception
	 */
	public static ByteBuffer resourceToHKXByteBuffer(final String resourceName) throws IOException {
		InputStream inputStream = FileUtils.class.getResourceAsStream(resourceName);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] tmp = new byte[1000];
		int length = inputStream.read(tmp);
		while (length != -1) {
			byteArrayOutputStream.write(tmp, 0, length);
			length = inputStream.read(tmp);
		}
		ByteBuffer toRead = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
		inputStream.close();
		return toRead;
	}

	/**
	 * Convert a given resource to a {@link byte} array.
	 * 
	 * @param resourceName the resource to convert
	 * @return a {@link byte} array
	 * @throws Exception
	 */
	public static byte[] resourceToByteArray(final String resourceName) throws IOException {
		InputStream inputStream = FileUtils.class.getResourceAsStream(resourceName);
		byte[] byteArray = new byte[inputStream.available()];
		DataInputStream dataIS = new DataInputStream(inputStream);
		dataIS.readFully(byteArray);
		dataIS.close();
		inputStream.close();
		return byteArray;
	}
}
