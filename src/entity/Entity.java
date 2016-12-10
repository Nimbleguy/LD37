package entity;

import java.awt.Image;
import java.util.ArrayList;

public class Entity {
	private int spriteIndex;
	private ArrayList<Image> sprites;
	double x;
	double y;

	public Entity(ArrayList<Image> sprites, double x, double y){
		this.sprites = sprites;
		this.spriteIndex = 0;
		this.x = x;
		this.y = y;
	}
	
	public ArrayList<Image> getSprites(){
		return sprites;
	}
	
	public int getSpriteIndex(){
		return spriteIndex;
	}
	
	public void setSpriteIndex(int spriteIndex){
		this.spriteIndex = spriteIndex;
	}
	
	public void setSprites(ArrayList<Image> sprites){
		this.spriteIndex = 0;
		this.sprites = sprites;
	}

	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}

	public int getDrawX(){
		return (int)Math.round(x);
	}
	public int getDrawY(){
		return (int)Math.round(y);
	}

	public Image getSprite(){
		return sprites.get(spriteIndex);
	}
	
	public void update(){
		x+=1;
		y+=0.5;
	}
}
