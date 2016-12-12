package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import core.Game;
import events.CollisionEvent;
import events.Event;
import events.Listener;

public class Walls extends Entity implements Listener{
	private static boolean init = false;
	public Walls(ArrayList<BufferedImage> sprites, ArrayList<Hitbox> hitbox, double x, double y){
		super(sprites, hitbox, x, y);
		if (!init){
			init();
		}
	}

	private static void init(){
		init = true;
		Game.getInstance().registerListener(new Walls(null,null,-1,-1));
	}

	@CollisionEvent.Listen
	public void listen(Event e){
		if (((CollisionEvent)e).getDefender() instanceof Walls || ((CollisionEvent)e).getAgressor() instanceof Walls){
			((CollisionEvent)e).setCancelled(true);
		}
	}

	@Override
	public void update(){
		//no need to update
	}
}
