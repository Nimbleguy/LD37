package entity;

import java.awt.Image;
import java.util.ArrayList;

public class Walls extends Entity{
	@SuppressWarnings("serial")
	public Walls(Image image, Hitbox hitbox, double x, double y) {
		super(new ArrayList<Image>(){{
			this.add(image);
		}}, hitbox, x, y);
	}
}
