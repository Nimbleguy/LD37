package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Walls extends Entity{
	@SuppressWarnings("serial")
	public Walls(BufferedImage image, Hitbox hitbox, double x, double y) {
		super(new ArrayList<BufferedImage>(){{
			this.add(image);
		}}, hitbox, x, y);
	}
}
