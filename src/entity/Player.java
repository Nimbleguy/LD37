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
			this.add(ImageIO.read(new File("src/assets/playerSprite.png")));
		}},new ArrayList<Hitbox>(){{
			this.add(HitboxGenerator.generateHitbox(ImageIO.read(new File("src/assets/playerBox.png"))));
		}},x,y);
	}
}
