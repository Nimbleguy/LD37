package core;

import java.io.File;

public class Utilities{
	public static String getAsset(String name){
		String root = System.getProperty("user.dir");
		if(root.endsWith(File.separator)){
			root = root.substring(0, root.lastIndexOf(File.separator));
		}
		return System.getProperty("user.dir") + File.separator + "assets" + File.separator + name.replaceAll("/", File.separator);
	}
}
