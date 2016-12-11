package entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.util.HitboxGenerator;

public class Player extends Entity{
	
	@SuppressWarnings("serial")
	public Player(double x, double y) throws IOException{
		super(new ArrayList<BufferedImage>(){{
				this.add(ImageIO.read(new File("assets/player0")));
				this.add(ImageIO.read(new File("assets/player1")));
				this.add(ImageIO.read(new File("assets/player2")));
				this.add(ImageIO.read(new File("assets/player3")));
				}},HitboxGenerator.generateHitbox(ImageIO.read(new File("assets/player0"))),x,y);
		this.setSpriteIndex(0);
	}
}
