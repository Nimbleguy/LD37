package entity;

import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Rectangle2D;

public class CollisionBox{//a bunch of rectangles which make up a hitbox
	List<RelRectangle2D> rectangles = new ArrayList<RelRectangle2D>();
	Entity entity;
	public CollisionBox(Entity e, List<RelRectangle2D> boxes){
		entity = e;
		rectangles = boxes;
	}

	public boolean intersects(CollisionBox other){
		for (RelRectangle2D rec1 : rectangles){
			Rectangle2D r = rec1.getRectangle();
			r.setRect(rec1.getX()+entity.getX(), rec1.getY()+entity.getY(), r.getWidth(), r.getHeight());
			for (RelRectangle2D rec2 : other.getRectangles()){
				if (rec1.getRectangle().intersects(rec2.getRectangle()))
					return true;
			}
		}
		return false;
	}

	private List<RelRectangle2D> getRectangles(){
		return rectangles;
	}
	
	class RelRectangle2D{
		double x;//relative x compared to center
		double y;//relative y
		Rectangle2D rec;
		public RelRectangle2D(Rectangle2D rec, double x, double y){
			this.rec = rec;
			this.x = x;
			this.y = y;
		}

		public double getX(){
			return x;
		}
		public double getY(){
			return y;
		}

		public Rectangle2D getRectangle(){
			return rec;
		}
	}
}