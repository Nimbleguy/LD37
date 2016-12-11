package core;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import entity.Entity;
import entity.Hitbox;
import events.Event;
import events.Listener;
import events.CollisionEvent;

public class Test implements Listener{
	public static Entity e1;
	public static Entity e2;

	public void init(){
		ArrayList<Image> sprite = new ArrayList<Image>();
		sprite.add(new ImageIcon("assets/Untitled.png").getImage());
		ArrayList<Rectangle2D.Double> hitbox = new ArrayList<Rectangle2D.Double>();
		hitbox.add(new Rectangle2D.Double(0,0,100,100));

		e1 = new Entity(sprite,new Hitbox(hitbox),0,0);
		e2 = new Entity(sprite,new Hitbox(hitbox),300,300);

		Game.getInstance().addEntity(e1);
		Game.getInstance().addEntity(e2);
		Game.getInstance().addPaint(e1);
		Game.getInstance().addPaint(e2);
		
		Game.getInstance().registerListener(this);
	}

	public Entity getE1(){
		return e1;
	}

	public Entity getE2(){
		return e2;
	}

	@Override
	@CollisionEvent.Listen
	public void listen(Event e){
		Game.stop();
	}
}
