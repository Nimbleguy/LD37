package core.util;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Hitbox;

public class HitboxGenerator {
	public static Hitbox generateHitbox(BufferedImage map){//maps must be black and white
		ArrayList<ArrayList<int[]>> rows = new ArrayList<ArrayList<int[]>>();
		
		for (int y = 0; y<map.getWidth(); y++){
			boolean recording = false;
			ArrayList<int[]> row = new ArrayList<int[]>();
			int low = -1,high=-1;
			for (int x = 0; x<map.getWidth(); x++){
				int rgb = map.getRGB(x, y);
				if (rgb == -16777216){//black (part of the wall)
					if (recording){
						high=x;
					}else{
						low=x;
					}
					recording = true;
				}else{//done recording
					if (low != -1 && high != -1)//makes sure that there was a black line before it
						row.add(new int[]{low,high});
					recording = false;
					low = -1;//reset
					high = -1;//reset
				}
			}
			if (low != -1 && high != -1)//incase entire thing was walls
				row.add(new int[]{low,high});
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
								current.get(range2)[3]=y;//fix second y value to higher
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
			if (!used.contains(range)){//so repeat values arent used
				boxes.add(new Rectangle2D.Double(range[0], range[1], range[2]-range[0]+1, range[3]-range[1]+1));
			}
			used.add(range);
		}
		
		return new Hitbox(boxes);
	}
}
