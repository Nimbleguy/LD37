package events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import core.Game;
import entity.Entity;

public class CollisionEvent implements Event{

	private boolean cancelled;
	private Entity e1;
	private Entity e2;
	public CollisionEvent(Entity agressor, Entity defender){
		e1 = agressor;
		e2 = defender;
		cancelled = false;
	}
	
	
	public void setCancelled(boolean cancel){
		cancelled = cancel;
	}
	
	public boolean isCancelled(){
		return cancelled;
	}
	
	public Entity getAgressor(){
		return e1;
	}
	
	public Entity getDefender(){
		return e2;
	}
	
	@Retention(value=RetentionPolicy.RUNTIME)
	@Target(value=ElementType.METHOD)
	public @interface Listen{}

	@Override
	public boolean trigger(){
		try{
			for (Listener l : Game.getInstance().getListeners()){
				Method m = l.getClass().getMethod("listen", Event.class);
				if (m.isAnnotationPresent(Listen.class)){
					l.listen(this);
				}
			}
		}catch (NoSuchMethodException | SecurityException e){
			e.printStackTrace();
		}
		return !cancelled;
	}
}
