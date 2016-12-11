package entity;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

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
	private List<Entity> touching;
	
	public Entity(ArrayList<Image> sprites, Hitbox hitbox, double x, double y){
		this.sprites = sprites;
		this.spriteIndex = 0;
		this.x = x;
		this.y = y;
		this.hitbox = hitbox;
		touching = new ArrayList<Entity>();
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
		double prevX = this.x;
		this.x = x;
		boolean doMove = true;
		if (this.x!=prevX){//it moved
			List<Entity> nowTouching = new ArrayList<Entity>();
			for (Entity other : Game.getInstance().getEntities()){
				if (other != this){
					if (isTouching(other)){
						nowTouching.add(other);
						doMove = doMove && new CollisionEvent(this,other,!touching.contains(other)).trigger();
					}
				}
			}
			if (doMove){
				touching = nowTouching;
			}
		}
		if (!doMove){
			this.x=prevX;
		}
	}

	public void setY(double y){
		double prevY = this.y;
		this.y = y;
		boolean doMove = true;
		if (this.y!=prevY){//it moved
			List<Entity> nowTouching = new ArrayList<Entity>();
			for (Entity other : Game.getInstance().getEntities()){
				if (other != this){
					if (isTouching(other)){
						nowTouching.add(other);
						doMove = doMove && new CollisionEvent(this,other,!touching.contains(other)).trigger();
					}
				}
			}
			if (doMove){
				touching = nowTouching;
			}
		}
		if (!doMove){
			this.y=prevY;
		}
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
	
	public List<Entity> getTouching(){
		return touching;
	}

	public boolean isTouching(Entity other){//TODO check if works
		for (Rectangle2D.Double boxxx : hitbox.getBoxes()){
			for (Rectangle2D.Double otherBoxxx : other.hitbox.getBoxes()){
				Rectangle hellothedarknessmyoldfriend = new Rectangle(Convert.pixWidth(boxxx.getX() + getX()), Convert.pixHeight(boxxx.getY() + getY()), Convert.pixWidth(boxxx.getWidth()), Convert.pixHeight(boxxx.getHeight()));
				Rectangle ivecometotalktoyouagain = new Rectangle(Convert.pixWidth(otherBoxxx.getX() + other.getX()), Convert.pixHeight(otherBoxxx.getY() + other.getY()), Convert.pixWidth(otherBoxxx.getWidth()), Convert.pixHeight(otherBoxxx.getHeight()));
				Rectangle2D hell = hellothedarknessmyoldfriend.createIntersection(ivecometotalktoyouagain);
				if (!hell.isEmpty()){
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
		boolean doMove = true;
		x = loc[0];
		y = loc[1];
		if (!(x==prevX && y==prevY)){//it moved
			List<Entity> nowTouching = new ArrayList<Entity>();
			for (Entity other : Game.getInstance().getEntities()){
				if (other != this){
					if (isTouching(other)){
						nowTouching.add(other);
						doMove = doMove && new CollisionEvent(this,other,!touching.contains(other)).trigger();
					}
				}
			}
			if (doMove){
				touching = nowTouching;
			}
		}
		if (!doMove){
			x=prevX;
			y=prevY;
		}
	}
}
