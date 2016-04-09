package com.dexesttp.hkxpack.cli.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.dexesttp.hkxpack.cli.ConsoleView;

/**
 * Help to walk through a directory to retrieve files.
 */
public class DirWalker {
	private static final Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());
	private final transient String[] extensions;

	/**
	 * Creates a directory walker.
	 * @param extensions the extensions to detect
	 */
	public DirWalker(final String... extensions) {
		this.extensions = extensions;
	}
	
	/**
	 * Walk through a directory.
	 * @param directory the directory to walk through, as a {@link File}.
	 * @return a list of {@link Entry} detected as suitable files.
	 */
	public List<Entry> walk(final File directory) {
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
	private void walk(final File directory, final String accumulatedPath, final List<Entry> outputFiles) {
		try {
			DirectoryStream<Path> files = Files.newDirectoryStream(directory.toPath());
			for(Path directoryComponent : files) {
				File element = createFile(directoryComponent);
				if(element.isFile()) {
					Stream<String> extensionStream = Arrays.stream(extensions);
					if(extensionStream.anyMatch(
							(ext) -> {
								return directoryComponent.getFileName().toString().endsWith(ext);
							})) {
						outputFiles.add(createEntry(accumulatedPath, element));
					}
					extensionStream.close();
				}
				else if(element.isDirectory()) {
					walk(element, accumulatedPath + "/" + element.getName(), outputFiles);
				}
			}
		} catch (IOException e) {
			LOGGER.throwing(this.getClass().getName(), "walk", e);
		}
	}
	
	private Entry createEntry(final String accumulatedPath, final File element) {
		return new Entry(accumulatedPath, element.getName());
	}

	private File createFile(final Path directoryComponent) {
		return new File(directoryComponent.toUri());
	}

	/**
	 * Represents a file.
	 */
	public class Entry {
		private final transient String fileName;
		private final transient String pathName;
		protected Entry(final String pathName, final String fileName) {
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
		public String getPath(final String rootPath) {
			StringBuilder result = new StringBuilder();
			result.append(rootPath);
			if(result.length() != 0 && !pathName.isEmpty()) {
				result.append('/');
			}
			return result.append(pathName).toString();
		}

		/**
		 * Retrieves the {@link Entry} full name. <br />
		 * @return the full name, meaning the name plus the default path.
		 */
		public String getFullName() {
			StringBuilder result = new StringBuilder();
			result.append(pathName);
			if(result.length() != 0) {
				result.append('/');
			}
			return result.append(fileName).toString();
		}
	}
}
