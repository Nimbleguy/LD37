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
import core.Play;
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
			try{
				Play.stopSoundLoop();
				try{
					Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=1Bix44C1EzY").toURI());
				}
				catch(Exception exexexexexex3etsfruweygfhuofyegrtyu4geruyfgeruosd0ghruogoergh9er7gfuo3q4g7934gt7934fry7t34g79634gt79340gty743gt3487tg7943grt79436fr934gf4t34ft63469tf6349ft69473ft7934ft7346ftiy43vf4793g7yc3597vy79340vf034gyfgu934vf934gf9437ygfu9gru923guy92cg49ygdndg934g2379g79n796fnistb9723DOTEXEDOTCOMDOTUKDOTTKDOTZOMBODOTCOMDOTAPP){
					System.out.println("Your platform doesn't support Desktop.getDesktop().browse, apparently.");
				}
				Game.getInstance().clearPaint();
				Game.getInstance().addPaint(new Entity(new ArrayList<BufferedImage>(){{
					this.add(ImageIO.read(new File("src/assets/congrats.png")));
				}},new ArrayList<Hitbox>(),0,0));
			}catch (IOException /*| URISyntaxException*/ e1){
				e1.printStackTrace();
			}
		}
	}
}
