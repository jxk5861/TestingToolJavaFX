package testing.dynamiclinkage;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class Utility {
	/**
	 * Try to load a class from its file path. This method assumes that the class is
	 * located in a directory corresponding to its class-path.
	 */
	// TODO: Try to read the class path from the .class file (also unreliable)
	public static Class<?> tryLoadClassFromFile(File file) {
		String name = file.getName();
		name = name.substring(0, name.lastIndexOf('.'));
		StringBuilder classFullyQualifiedName = new StringBuilder(name);

		while (file.getParentFile() != null) {
			file = file.getParentFile();
			if (!file.getName().equals("")) {
				try {
					URL url = file.toURI().toURL();
					URL[] urls = { url };
					ClassLoader loader = URLClassLoader.newInstance(urls);

					return Class.forName(classFullyQualifiedName.toString(), true, loader);
				} catch (Exception | Error e) {

				}

				classFullyQualifiedName.insert(0, '.');
				classFullyQualifiedName.insert(0, file.getName());
			}
		}
		return null;
	}
}
