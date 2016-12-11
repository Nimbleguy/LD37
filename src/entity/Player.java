package entity;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;

import core.util.Convert;

public class Player extends Entity{
	public Player(double x, double y){
		super(new ArrayList<Image>(Arrays.asList(new Image[]{new ImageIcon("assets/player0.png").getImage(),
				new ImageIcon("assets/player1.png").getImage(),
				new ImageIcon("assets/player2.png").getImage(),
				new ImageIcon("assets/player3.png").getImage(),
				new ImageIcon("assets/player0.png").getImage()})), new Hitbox(new ArrayList<Rectangle2D.Double>(Arrays.asList(
						new Rectangle2D.Double(x, y, Convert.perWidth(new ImageIcon("assets/sprites/player.png").getImage().getWidth(null)), Convert.perHeight(new ImageIcon("assets/sprites/player.png").getImage().getHeight(null)))))), x, y);
		this.setSpriteIndex(0);
	}
}
