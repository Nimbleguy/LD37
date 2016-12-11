package entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Walls extends Entity{
	@SuppressWarnings("serial")
	public Walls(Image image, Hitbox hitbox, double x, double y) {
		super(new ArrayList<Image>(){{
			this.add(image);
		}}, hitbox, x, y);
	}

	public static Hitbox generateHitbox(Image map){//maps must be black and white
		BufferedImage image = (BufferedImage)map;//TODO test this function (gen hitbox)


		ArrayList<ArrayList<int[]>> rows = new ArrayList<ArrayList<int[]>>();
		for (int y = 0; y<image.getWidth(); y++){
			boolean recording = false;
			ArrayList<int[]> row = new ArrayList<int[]>();
			int low = -1,high=-1;
			for (int x = 0; x<image.getWidth(); x++){
				int rgb = image.getRGB(x, y);
				if (rgb == 0){//black (part of the wall)
					if (recording){
						high=x;
					}else{
						low=x;
					}
					recording = true;
				}else{
					if (low != -1 && high != -1)
						row.add(new int[]{low,high});
					recording = false;
				}
			}
			rows.add(row);
		}

		ArrayList<int[]> previous = null;
		HashMap<int[],int[]> current = new HashMap<int[],int[]>();//values are the rectangles
		int y = 0;
		for (ArrayList<int[]> row : rows){
			if (previous != null){
				for (int[] range1 : row){
					for (int[] range2 : previous){
						if (range1 == range2){
							if (current.containsKey(range2)){
								current.get(range2)[3]=range1[1];//fix second y value to higher
							}else{
								current.put(range1, new int[]{range2[0],y-1,range1[0],y});
							}
						}else{
							current.put(range1, new int[]{range1[0],y,range1[1],y});
						}
					}
				}
			}
			previous = row;
			y++;
		}

		ArrayList<Rectangle2D.Double> boxes = new ArrayList<Rectangle2D.Double>();
		ArrayList<int[]> used = new ArrayList<int[]>();
		for (int[] range : current.values()){
			if (!used.contains(range)){
				boxes.add(new Rectangle2D.Double(range[0], range[1], range[2], range[3]));
			}
			used.add(range);
		}
		return new Hitbox(boxes);
	}
}
