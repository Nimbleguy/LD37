package objects.maps;

import java.awt.image.BufferedImage;

public class MapInterpreter {
	byte[][] dirMap;//x,y
	//-1 is null, 0=N,1=NE,2=E,3=SE,4=S,5=SW,6=W,7=NW
	public MapInterpreter(BufferedImage map){
		for(int w=0; w<map.getWidth(); w++){
			for(int h=0; h<map.getHeight(); h++){
				switch(map.getRGB(w, h)){
				case 1864135*0://black (-1)(#000000)
					dirMap[w][h] = -1;
					break;
				case 1864135*1://sky blue (0) (#1C71C7)
					dirMap[w][h] = 0;
					break;
				case 1864135*2://light blue-green (1) (#38E38E)
					dirMap[w][h] = 1;
					break;
				case 1864135*3://grey (2) (#555555)
					dirMap[w][h] = 2;
					break;
				case 1864135*4://lime green (3) (#71c71c)
					dirMap[w][h] = 3;
					break;
				case 1864135*5://purple (4) (#8E38E3)
					dirMap[w][h] = 4;
					break;
				case 1864135*6://light grey (5) (#AAAAAA)
					dirMap[w][h] = 5;
					break;
				case 1864135*7://magenta (6) (#C71C71)
					dirMap[w][h] = 6;
					break;
				case 1864135*8://orange (7) (#E38E38)
					dirMap[w][h] = 7;
					break;
				}
			}
		}
	}
	
	public byte getDir(int x, int y){//NOTE: x,y are the x,y of the map image.
		return dirMap[x][y];
	}
}
