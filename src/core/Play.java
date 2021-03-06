package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import core.util.HitboxGenerator;
import entity.Goal;
import entity.Hitbox;
import entity.Player;
import entity.Walls;
import entity.Button;

public class Play{

	private static Player player;
	private static Walls[] walls = new Walls[16];
	private static Button[] buttons = new Button[4];
	private static Clip clip;

	public void init(){		
		try{
			@SuppressWarnings("unchecked")
			ArrayList<Walls>[] bwal = new ArrayList[4];
			for(int i = 0; i < 4; i++){
				bwal[i] = new ArrayList<Walls>();
			}
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
					int tmp = (4*i)+ii;
					if(tmp < 4){
						bwal[0].add(w);
					}
					else if(tmp == 5 || tmp == 6 || tmp == 9 || tmp == 10){
						bwal[1].add(w);
					}
					else if(tmp == 4 || tmp == 8 || tmp == 12 || tmp == 13){
						bwal[2].add(w);
					}
					else{
						bwal[3].add(w);
					}
					Game.getInstance().addEntity(w);
					Game.getInstance().addPaint(w);
				}
			}
			BufferedImage spr = ImageIO.read(new File("assets/ButtonSprite1.png"));
			Hitbox hit = HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/ButtonHitmap1.png")));
			ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
			ArrayList<Hitbox> hitboxes = new ArrayList<Hitbox>();
			sprites.add(spr);
			hitboxes.add(hit);
			Game.getInstance().addEntity(buttons[0] = new Button(bwal[0], sprites, hitboxes, 300, 300));
			Game.getInstance().addPaint(buttons[0]);
			Game.getInstance().addEntity(buttons[1] = new Button(bwal[1], sprites, hitboxes, 500, 300));
			Game.getInstance().addPaint(buttons[1]);
			Game.getInstance().addEntity(buttons[2] = new Button(bwal[2], sprites, hitboxes, 300, 500));
			Game.getInstance().addPaint(buttons[2]);
			Game.getInstance().addEntity(buttons[3] = new Button(bwal[3], sprites, hitboxes, 500, 500));
			Game.getInstance().addPaint(buttons[3]);
			
			
			ArrayList<BufferedImage> sprites2 = new ArrayList<BufferedImage>();
			ArrayList<Hitbox> hitboxes2 = new ArrayList<Hitbox>();
			sprites2.add(ImageIO.read(new File("assets/goalSprite.png")));
			hitboxes2.add(HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/goalMap.png"))));
			
			Goal g = new Goal(sprites2,hitboxes2,675,675);
			Game.getInstance().addEntity(g);
			Game.getInstance().addPaint(g);
			
			try{//play is last so they are foregroundmost
				player = new Player(77, 77);
			}catch(IOException e){
				e.printStackTrace();
			}
			Game.getInstance().addEntity(player);
			Game.getInstance().addPaint(player);
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File("assets/music.wav"));
			clip = AudioSystem.getClip();
			clip.open(sound);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.setFramePosition(0);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
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
	
	public static void stopSoundLoop(){
		clip.stop();
	}
}
