package core;

import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Assets{
	public static String getAsset(String name){
		String root = System.getProperty("user.dir");
		if(root.endsWith(File.separator)){
			root = root.substring(0, root.lastIndexOf(File.separator));
		}
		return System.getProperty("user.dir") + File.separator + "assets" + File.separator + name.replaceAll("/", File.separator);
	}

	public static BufferedImage getImage(String name){
		try{
			return ImageIO.read(new File(getAsset(name)));
		}
		catch(IOException e){
			System.out.println("Error in reading asset " + name + ".");
			e.printStackTrace();
			return null;
		}
	}
}
