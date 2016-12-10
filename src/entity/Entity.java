package entity;

import java.awt.image.BufferedImage;
import java.util.List;

import events.EntityCollisionEvent;
import events.EntityMoveEvent;

public class Entity {
	private static List<Entity> entities;

	private double[] loc = new double[2];
	BufferedImage sprite;
	CollisionBox hitbox;


	public Entity(double x, double y, BufferedImage image, CollisionBox box){
		loc = new double[]{x,y};
		hitbox = box;
		sprite = image;
		hitbox = box;
	}
	public Entity(double[] center, BufferedImage image, CollisionBox box){
		loc = center;
		hitbox = box;
		sprite = image;
		hitbox = box;
	}

	public boolean teleport(double x, double y, boolean ignoreCancelled){
		boolean returnVal = new EntityMoveEvent(this,x,y).trigger();
		if (returnVal){
			for (Entity e : entities){
				if (hitbox.intersects(e.getHitbox())){
					returnVal = returnVal && new EntityCollisionEvent(this, e).trigger();
				}
			}
		}
		if (returnVal){
			loc = new double[]{x,y};
		}
		return returnVal;
	}

	public double[] getLoc(){
		return loc;
	}
	public double getX(){
		return loc[0];
	}
	public double getY(){
		return loc[1];
	}
	public CollisionBox getHitbox(){
		return hitbox;
	}
}
