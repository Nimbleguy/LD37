package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.util.HitboxGenerator;
import entity.Hitbox;
import entity.Player;
import entity.Walls;
import entity.Button;

public class Play{

	private static Player player;
	private static Walls[] walls = new Walls[16];
	private static Button[] buttons = new Button[4];

	public void init(){
		try{
			player = new Player(77, 77);
		}catch(IOException e){
			e.printStackTrace();
		}
		Game.getInstance().addEntity(player);
		Game.getInstance().addPaint(player);
		try{
			for (int i = 0; i<4; i++){
				for (int ii = 0; ii<4; ii++){
					BufferedImage sprite1 = ImageIO.read(new File("assets/wall-" + String.valueOf((4*i)+ii) + "-0.png"));
					Hitbox hitbox1 = HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/whit-" + String.valueOf((4*i)+ii) + "-0.png")));
					BufferedImage sprite2 = ImageIO.read(new File("assets/wall-" + String.valueOf((4*i)+ii) + "-1.png"));
					Hitbox hitbox2 = HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/whit-" + String.valueOf((4*i)+ii) + "-1.png")));
					
					ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
					ArrayList<Hitbox> hitboxes = new ArrayList<Hitbox>();
					
					sprites.add(sprite1);
					sprites.add(sprite2);
					hitboxes.add(hitbox1);
					hitboxes.add(hitbox2);
					
					Walls w = new Walls(sprites, hitboxes,(double)ii*(800/4),(double)i*(800/4));
					
					walls[(4*i)+ii] = w;
					
					Game.getInstance().addEntity(w);
					Game.getInstance().addPaint(w);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static Player getPlayer(){
		return player;
	}
	public static Walls[] getWalls(){
		return walls;
	}
	public static Button[] getButtons(){
		return buttons;
	}
}
