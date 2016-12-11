package core;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import core.util.HitboxGenerator;
import entity.Player;
import entity.Walls;
import events.Listener;
import events.CollisionEvent;
import events.Event;

public class Play implements Listener{

	private static Player player;
	private static Walls[] walls = new Walls[16];

	public void init(){
		player = new Player(10, 10);
		Game.getInstance().addEntity(player);
		Game.getInstance().addPaint(player);
		try{
			for(int i = 0; i < 800; i += 200){
				for(int ii = 0; ii < 800; ii += 200){
					walls[(i / 50) + (ii / 200)] = new Walls(new ImageIcon("assets/wall-" + String.valueOf((i / 50) + (ii / 200)) + "-0.png").getImage(), HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/whit-" + String.valueOf((i / 50) + (ii / 200)) + "-0.png"))), i, ii);
					Game.getInstance().addEntity(walls[(i / 50) + (ii / 200)]);
					Game.getInstance().addPaint(walls[(i /	50) + (ii / 200)]);
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		Game.getInstance().registerListener(this);
	}

	public static Player getPlayer(){
		return player;
	}
	public static Walls[] getWalls(){
		return walls;
	}

	@Override
        @CollisionEvent.Listen
        public void listen(Event e){
		((CollisionEvent)e).setCancelled(true);
	}
}
