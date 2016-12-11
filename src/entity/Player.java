package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.util.HitboxGenerator;

public class Player extends Entity{
	
	ArrayList<Hitbox> hitboxes;
	
	@SuppressWarnings("serial")
	public Player(double x, double y) throws IOException{
		super(new ArrayList<BufferedImage>(){{
				this.add(ImageIO.read(new File("assets/player0.png")));
				this.add(ImageIO.read(new File("assets/player1.png")));
				this.add(ImageIO.read(new File("assets/player2.png")));
				this.add(ImageIO.read(new File("assets/player3.png")));
				}},HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/player0.png"))),x,y);
		this.setSpriteIndex(0);
		hitboxes = new ArrayList<Hitbox>();
		hitboxes.add(HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/player0.png"))));
		hitboxes.add(HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/player1.png"))));
		hitboxes.add(HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/player2.png"))));
		hitboxes.add(HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/player3.png"))));
	}
	
	@Override
	public void setSpriteIndex(int i){
		super.setHitbox(hitboxes.get(i));
		super.setSpriteIndex(i);
	}
}
