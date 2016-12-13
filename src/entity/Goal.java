package entity;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.Game;
import events.CollisionEvent;
import events.Event;
import events.Listener;

public class Goal extends Entity implements Listener{

	public Goal(ArrayList<BufferedImage> arrayList, ArrayList<Hitbox> hitbox, double x, double y) {
		super(arrayList, hitbox, x, y);
		Game.getInstance().registerListener(this);
	}
	
	@SuppressWarnings("serial")
	@CollisionEvent.Listen
	public void listen(Event e){
		if (((CollisionEvent)e).isNew() &&((CollisionEvent)e).getDefender() == this){
			try {
				Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=1Bix44C1EzY").toURI());
				Game.getInstance().clearPaint();
				Game.getInstance().addPaint(new Entity(new ArrayList<BufferedImage>(){{
					this.add(ImageIO.read(new File("assets/congrats.png")));
				}},new ArrayList<Hitbox>(),0,0));
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
	}
}
