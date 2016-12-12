package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Button extends Entity{
	private List<Walls> w;
	public Button(List<Walls> walls, ArrayList<BufferedImage> sprites, ArrayList<Hitbox> hitbox, double x, double y) {
		super(sprites, hitbox, x, y);
		w = walls;
	}

	public void press(){
		for (Walls wall : w){
			wall.setSpriteIndex((wall.getSpriteIndex()+1)%wall.getSprites().size());
		}
	}
}
