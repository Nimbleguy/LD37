package events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import core.Main;
import entity.Entity;

public class EntityMoveEvent implements Event{

	Entity entity;
	boolean cancel;
	double toX;
	double toY;

	public EntityMoveEvent(Entity e, double toX, double toY){
		entity = e;
		this.toX = toX;
		this.toY = toY;
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Listener{
	}

	public boolean isCancelled(){
		return cancel;
	}

	@Override
	public boolean trigger(){
		for (events.Listener l : Main.getListeners()){
			try{
				if (l.getClass().getMethod("listen", Event.class).isAnnotationPresent(Listener.class)){
					l.listen(this);
				}
			}catch (NoSuchMethodException e){
				e.printStackTrace();
			}catch (SecurityException e){
				e.printStackTrace();
			}
		}
		return !cancel;
	}
}
