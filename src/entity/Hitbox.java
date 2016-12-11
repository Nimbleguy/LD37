package entity;

import java.awt.geom.Rectangle2D;
import java.util.List;

public class Hitbox {
	private List<Rectangle2D.Double> boxes;

	public Hitbox(List<Rectangle2D.Double> boxes){
		this.boxes = boxes;
	}

	public List<Rectangle2D.Double> getBoxes(){
		return boxes;
	}
}
