package events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;

import core.MouseHandler;
import core.PriorityValue;

import static org.lwjgl.glfw.GLFW.*;

public class MouseEvent implements Event{

	long window;
	double x;
	double y;
	double prevX;
	double prevY;

	boolean cancel;

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Priority{
		PriorityValue value() default PriorityValue.MEDIUM;
	}

	public MouseEvent(long window, double x, double y, double prevX, double prevY){
		this.window = window;
		this.x = x;
		this.y = y;
		this.prevX = prevX;
		this.prevY = prevY;
	}

	@Override
	public boolean trigger(){
		double startX = x;
		double startY = y;
		HashMap<Integer,ArrayList<Listener>> listeners = MouseHandler.getListeners();
		if (listeners.size() > 0){
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
		if ((x != startX || y != startY) && !cancel){
			glfwSetCursorPos(window,x,y);
		}
		if (cancel){
			glfwSetCursorPos(window,startX,startY);
		}
		return !cancel;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setPrevX(double x){
		this.prevX = x;
	}

	public void setPrevY(double y){
		this.prevX = y;
	}

	public void setCancelled(boolean c){
		cancel = c;
	}


	public boolean isCancelled(){
		return cancel;
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getPrevX(){
		return prevX;
	}

	public double getPrevY(){
		return prevY;
	}

	public long getWindow(){
		return window;
	}
}
