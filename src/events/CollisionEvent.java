package events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import core.Game;

public class CollisionEvent implements Event{
	
	@Retention(value=RetentionPolicy.RUNTIME)
	@Target(value=ElementType.METHOD)
	@interface Listener{
		
	}
	
	@Override
	public boolean trigger(){
		
		for (Listener l : Game.getInstance().getListeners()){
			Method m = l.getClass().getMethod("listen", Event.class);
			if (){
				
			}
		}
	}
}
