package core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import events.Event;
import events.Listener;
import events.MouseEvent;
import events.MouseEvent.Priority;

public class MouseHandler extends GLFWCursorPosCallback{

	static double x = -1;
	static double y = -1;
	private static HashMap<Integer,ArrayList<Listener>> listeners = new HashMap<Integer,ArrayList<Listener>>();// HashMap<key, HashMap<Priority, Listener> >

	@Override
	public void invoke(long window, double xpos, double ypos) {
		new MouseEvent(window,xpos,ypos,x,y).trigger();
		x=xpos;
		y=ypos;
	}

	public static void registerMouseListener(Listener l){
		try{
			Method m = l.getClass().getMethod("listen", Event.class);
			int val = 0;
			if (m.isAnnotationPresent(Priority.class)){
				val = m.getAnnotation(Priority.class).value().getPriority();
			}
			ArrayList<Listener> existing = new ArrayList<Listener>();
			if (listeners.containsKey(val)){
				existing = listeners.get(val);
			}
			existing.add(l);
			listeners.put(val, existing);
		}catch (NoSuchMethodException | SecurityException e){e.printStackTrace();}//not possible?
	}
	public static void unregisterMouseListener(Listener l){
		try{
			Method m = l.getClass().getMethod("listen", Event.class);
			int val = 0;
			if (m.isAnnotationPresent(Priority.class)){
				val = m.getAnnotation(Priority.class).value().getPriority();
			}
			if (listeners.containsKey(val)){
				if (listeners.get(val).contains(l)){
					ArrayList<Listener> existing = new ArrayList<Listener>();
					existing = listeners.get(val);
					existing.remove(l);
					listeners.put(val, existing);
				}
			}
		}catch (NoSuchMethodException | SecurityException e){e.printStackTrace();}//not possible?
	}

	public static HashMap<Integer,ArrayList<Listener>> getListeners(){
		return listeners;
	}
}
