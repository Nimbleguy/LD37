package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import core.Game;
import events.CollisionEvent;
import events.Event;
import events.Listener;

public class Button extends Entity implements Listener{
	private List<Walls> w;
	private static boolean init;
	private static List<Button> buttons = new ArrayList<Button>();
	
	public Button(List<Walls> walls, ArrayList<BufferedImage> sprites, ArrayList<Hitbox> hitbox, double x, double y) {
		super(sprites, hitbox, x, y);
		w = walls;
		if (!init){
			init();
		}
	}

	private static void init(){
		init = true;
		Game.getInstance().registerListener(new Button(null,null,null,-1,-1));
	}
	
	public void press(){
		for (Walls wall : w){
			wall.setSpriteIndex((wall.getSpriteIndex()+1)%wall.getSprites().size());
		}
	}
	
	@CollisionEvent.Listen
	public void listen(Event e){
		for (Button button : buttons){
			button.press();
		}
	}
	
	@Override
	public void update(){
		//no need to update
	}
}
