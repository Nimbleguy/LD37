package core;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import core.util.HitboxGenerator;
import entity.Entity;
import entity.Hitbox;
import entity.Walls;
import events.Event;
import events.Listener;
import events.CollisionEvent;

public class Test implements Listener{
	public static Entity e1;
	public static Entity e2;

	public void init(){
		ArrayList<BufferedImage> sprite = new ArrayList<BufferedImage>();
		try {
			sprite.add(ImageIO.read(new File("assets/player.png")));

			BufferedImage i = ImageIO.read(new File("assets/TestWall"));
			ArrayList<Rectangle2D.Double> hitbox = new ArrayList<Rectangle2D.Double>();
			hitbox.add(new Rectangle2D.Double(0,0,100,100));

			e1 = new Entity(sprite,(ArrayList<Hitbox>) Arrays.asList(new Hitbox[]{new Hitbox(hitbox)}),0,0);
			e2 = new Walls((ArrayList<BufferedImage>) Arrays.asList(new BufferedImage[]{i}),(ArrayList<Hitbox>) Arrays.asList(HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/TestMap.png")))),300,300);
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		((CollisionEvent)e).setCancelled(true);
	}
}
