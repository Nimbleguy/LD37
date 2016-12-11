package entity;

import java.awt.Image;
import java.util.ArrayList;

import core.util.Vector;

public class Entity {
	private int spriteIndex;
	private ArrayList<Image> sprites;
	double x;
	double y;
	Vector vel;

	public Entity(ArrayList<Image> sprites, double x, double y){
		this.sprites = sprites;
		this.spriteIndex = 0;
		this.x = x;
		this.y = y;
		vel = new Vector(0,0,0);
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

	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
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
	
	public void setVelocity(Vector vec){
		vel = vec;
	}
	
	public void addVelocity(Vector vec){
		vel.add(vec);
	}
	
	public void update(){
		double[] loc = vel.calcMove(x, y, true);
		x = loc[0];
		y = loc[1];
	}
}
