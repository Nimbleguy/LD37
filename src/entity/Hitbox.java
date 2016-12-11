package entity;

import java.awt.geom.Rectangle2D;
import java.util.List;

public class Hitbox {
	List<Rectangle2D> boxes;
	
	public Hitbox(List<Rectangle2D> boxes){
		this.boxes = boxes;
	}
	
	public List<Rectangle2D> getBoxes(){
		return boxes;
	}
}
