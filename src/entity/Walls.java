package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Walls extends Entity{
	public Walls(ArrayList<BufferedImage> sprites, ArrayList<Hitbox> hitbox, double x, double y){
		super(sprites, hitbox, x, y);
	}
}
