package events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;

import core.KeyboardHandler;
import core.PriorityValue;

public class KeyEvent implements Event{

	int key;
	boolean isDown;

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Priority{
		PriorityValue value() default PriorityValue.MEDIUM;
	}

	public KeyEvent(int key, boolean isPressed){
		if (KeyboardHandler.getKeyListeners().containsKey(key)){
			this.key = key;
			isDown = isPressed;
			HashMap<Integer,ArrayList<Listener>> listeners = KeyboardHandler.getKeyListeners(key);
			int iter = Integer.MIN_VALUE;
			for (int x : listeners.keySet()){
				if (x > iter){
					iter = x;
				}
			}
			
			iter++;
			for (int c = 0; c < listeners.size();){
				do{
					iter--;
				}while(!listeners.containsKey(iter));
				for (Listener listener : listeners.get(iter)){
					listener.listen(this);
					c++;
				}
			}
		}
	}

	public boolean isPressed(){
		return isDown;
	}

	public boolean trigger(){
		return true;
	}
}
