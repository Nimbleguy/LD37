package entity;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import core.Game;
import core.util.Vector;
import core.util.Convert;
import events.CollisionEvent;

public class Entity {
	private int spriteIndex;
	private ArrayList<Image> sprites;
	private double x;
	private double y;
	private Vector vel;
	private Hitbox hitbox;

	public Entity(ArrayList<Image> sprites, Hitbox hitbox, double x, double y){
		this.sprites = sprites;
		this.spriteIndex = 0;
		this.x = x;
		this.y = y;
		this.hitbox = hitbox;
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

	public boolean isTouching(Entity other){//TODO check if works
		for (Rectangle2D.Double boxxx : hitbox.getBoxes()){
			for (Rectangle2D.Double otherBoxxx : other.hitbox.getBoxes()){
				if (boxxx.intersects(otherBoxxx)){
					return true;
				}
			}
		}
		return false;
	}

	public void update(){
		double prevX = x;
		double prevY = y;
		double[] loc = vel.calcMove(x, y, true);
		x = loc[0];
		y = loc[1];
		if (!(x==prevX && y==prevY)){//it moved
			for (Entity other : Game.getInstance().getEntities()){
				if (other != this){
					if (isTouching(other)){
						new CollisionEvent(this,other).trigger();
					}
				}
			}
		}
	}
}
