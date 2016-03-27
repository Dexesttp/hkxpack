package com.dexesttp.hkxpack.cli.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Help to walk through a directory to retrieve files.
 */
public class DirWalker {
	private final String[] extensions;

	/**
	 * Creates a directory walker.
	 * @param extensions the extensions to detect
	 */
	public DirWalker(String... extensions) {
		this.extensions = extensions;
	}
	
	/**
	 * Walk through a directory.
	 * @param directory the directory to walk through, as a {@link File}.
	 * @return a list of {@link Entry} detected as suitable files.
	 */
	public List<Entry> walk(File directory) {
		List<Entry> res = new ArrayList<>();
		walk(directory, directory.getName(), res);
		return res;
	}
	
	/**
	 * Recursive walk function
	 * @param directory the current directory {@link File} 
	 * @param accumulatedPath the accumulated path of the directory
	 * @param outputFiles the files detected by the walk
	 */
	private void walk(File directory, String accumulatedPath, List<Entry> outputFiles) {
		try {
			DirectoryStream<Path> files = Files.newDirectoryStream(directory.toPath());
			for(Path directoryComponent : files) {
				File element = new File(directoryComponent.toUri());
				if(element.isFile()) {
					Stream<String> extensionStream = Arrays.stream(extensions);
					if(extensionStream.anyMatch(
							(ext) -> {
								return directoryComponent.getFileName().toString().endsWith(ext);
							}))
						outputFiles.add(new Entry(accumulatedPath, element.getName()));
					extensionStream.close();
				} else if(element.isDirectory())
					walk(element, accumulatedPath + "/" + element.getName(), outputFiles);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Represents a file.
	 */
	public class Entry {
		private final String fileName;
		private final String pathName;
		protected Entry(String pathName, String fileName) {
			this.pathName = pathName;
			this.fileName = fileName;
		}
		
		/**
		 * Retrieves the {@link Entry} name.
		 * @return the {@link Entry} name.
		 */
		public String getName() {
			return fileName;
		}
		
		/**
		 * Retrieves the {@link Entry} path from the given root.<br />
		 * Note : the given path doesn't end with a {@code /}.
		 * @param rootPath the root to start the path from.
		 * @return the {@link Entry} path.
		 */
		public String getPath(String rootPath) {
			String res = rootPath;
			if(!res.isEmpty() && !pathName.isEmpty())
				res += "/";
			res += pathName;
			return res;
		}

		/**
		 * Retrieves the {@link Entry} full name. <br />
		 * @return the full name, meaning the name plus the default path.
		 */
		public String getFullName() {
			String res = pathName;
			if(!res.isEmpty())
				res += "/";
			res += fileName;
			return res;
		}
	}
}
