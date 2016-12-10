package core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.glfw.GLFWKeyCallback;

import events.Event;
import events.KeyEvent;
import events.Listener;
import events.KeyEvent.Priority;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback{

	public static boolean[] keys = new boolean[65536];
	private static HashMap<Integer,HashMap<Integer,ArrayList<Listener>>> listeners = new HashMap<Integer,HashMap<Integer,ArrayList<Listener>>>();// HashMap<key, HashMap<Priority, Listener> >

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (keys[key] != (action!=GLFW_RELEASE)){//if key changed
			new KeyEvent(key,action != GLFW_RELEASE).trigger();
		}
		keys[key] = action != GLFW_RELEASE;
	}

	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}

	public static void registerKeyListener(int keyNum, Listener l){
		try{
			Method m = l.getClass().getMethod("listen", Event.class);
			ArrayList<Listener> existing = new ArrayList<Listener>();
			int val;
			if (m.isAnnotationPresent(Priority.class)){
				val = m.getAnnotation(Priority.class).value().getPriority();
			}else{
				val = 0;
			}
			if (listeners.containsKey(keyNum)){
				if (listeners.get(keyNum).containsKey(val)){
					existing = listeners.get(keyNum).get(val);
				}
			}
			existing.add(l);
			if (!listeners.containsKey(keyNum)){
				listeners.put(keyNum, new HashMap<Integer,ArrayList<Listener>>());
			}
			listeners.get(keyNum).put(val, existing);
		}catch(NoSuchMethodException | SecurityException e){e.printStackTrace();}//not possible?
	}
	public static void unregisterKeyListener(int keyNum, Listener l){
		try{
			Method m = l.getClass().getMethod("listen", Event.class);
			ArrayList<Listener> existing = new ArrayList<Listener>();
			int val;
			if (m.isAnnotationPresent(Priority.class)){
				val = m.getAnnotation(Priority.class).value().getPriority();
			}else{
				val = 0;
			}
			if (listeners.containsKey(keyNum)){
				if (listeners.get(keyNum).containsKey(val)){
					existing = listeners.get(keyNum).get(val);
					existing.remove(l);
					listeners.get(keyNum).put(val, existing);
				}
			}
		}catch(NoSuchMethodException | SecurityException e){e.printStackTrace();}//also not possible?
	}

	public static HashMap<Integer,HashMap<Integer,ArrayList<Listener>>> getKeyListeners(){
		return listeners;
	}

	public static HashMap<Integer,ArrayList<Listener>> getKeyListeners(int key){
		return listeners.get(key);
	}
}
