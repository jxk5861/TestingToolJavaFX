package testing.dynamiclinkage;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class Utility {
	public static Class<?> tryLoadClassFromFile(File file) {
        String name = file.getName();
        name = name.substring(0, name.lastIndexOf('.'));

//        System.out.println("Name: " + name);

        StringBuilder classFullyQualifiedName = new StringBuilder(name);

        while(file.getParentFile() != null) {
            file = file.getParentFile();
            if(!file.getName().equals("")) {
//                System.out.println("Trying to load file: " + file.getAbsolutePath() + " " + classFullyQualifiedName.toString());

                try {
                    URL url = file.toURI().toURL();
//                    System.out.println("URL for " + classFullyQualifiedName + ": " + url);
                    URL[] urls = {url};
//                    System.out.println(classFullyQualifiedName);
                    ClassLoader loader = URLClassLoader.newInstance(urls);

                    return Class.forName(classFullyQualifiedName.toString(), true, loader);
                }catch(Exception e) {
//					e.printStackTrace();
                }catch(Error e){

                }

                classFullyQualifiedName.insert(0, '.');
                classFullyQualifiedName.insert(0, file.getName());
            }
        }
        return null;
    }
}
