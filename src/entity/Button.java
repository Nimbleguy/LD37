package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import core.util.HitboxGenerator;

public class Button extends Entity{
	private List<Walls> w;
	public Button(List<Walls> walls, ArrayList<BufferedImage> sprites, Hitbox hitbox, double x, double y) {
		super(sprites, hitbox, x, y);
		w = walls;
	}

	public void press(){
		for (Walls wall : w){
			wall.setSpriteIndex((wall.getSpriteIndex()+1)%wall.getSprites().size());
			wall.setHitbox(HitboxGenerator.generateHitbox(wall.getSprite()));
		}
	}
}
