package com.dexesttp.hkxpack.cli.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DirWalker {
	private String[] extensions;

	public DirWalker(String... extensions) {
		this.extensions = extensions;
	}
	
	public List<Entry> walk(File directory) {
		List<Entry> res = new ArrayList<>();
		walk(directory, directory.getName(), res);
		return res;
	}
	
	private void walk(File directory, String accumulatedPath, List<Entry> res) {
		try {
			DirectoryStream<Path> files = Files.newDirectoryStream(directory.toPath());
			for(Path directoryComponent : files) {
				File element = new File(directoryComponent.toUri());
				if(element.isFile()) {
					boolean isValid = false;
					for(String ext : extensions) {
						if(directoryComponent.getFileName().toString().endsWith(ext))
							isValid = true;
					}
					if(isValid)
						res.add(new Entry(accumulatedPath, element.getName()));
				} else if(element.isDirectory())
					walk(element, accumulatedPath + "/" + element.getName(), res);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class Entry {
		private final String fileName;
		private final String pathName;
		protected Entry(String pathName, String fileName) {
			this.pathName = pathName;
			this.fileName = fileName;
		}
		
		public String getName() {
			return fileName;
		}
		
		public String getPath(String rootPath) {
			String res = rootPath;
			if(!res.isEmpty() && !pathName.isEmpty())
				res += "/";
			res += pathName;
			return res;
		}

		public String getFullName() {
			String res = pathName;
			if(!res.isEmpty())
				res += "/";
			res += fileName;
			return res;
		}
	}
}
