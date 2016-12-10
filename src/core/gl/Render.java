package core.gl;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import core.Main;

public class Render{
	private TreeMap<Integer, List<IRenderTask>> tasks = new TreeMap<Integer, List<IRenderTask>>();
	private List<IRenderTask> ntask = new ArrayList<IRenderTask>();

	public void render(long win){
		glfwSwapBuffers(win);//this and below were added recently
		glfwPollEvents();//without this, the window won't respond (i.e. no closing, no keypressing, etc.)
		List<IRenderTask> rmn = new ArrayList<IRenderTask>();
		for(IRenderTask t : ntask){
			t.init();
			List<IRenderTask> l = tasks.get(t.priority());
			if(l == null){
				l = new ArrayList<IRenderTask>();
			}
			l.add(t);
			tasks.put(t.priority(), l);
			rmn.add(t);
		}
		for(IRenderTask t : rmn){
			ntask.remove(t);
		}
		HashMap<Integer, IRenderTask> rem = new HashMap<Integer, IRenderTask>();
		for(Integer i : tasks.descendingKeySet()){
			for(IRenderTask t : tasks.get(i)){
				t.run(win);
				if(t.remove()){
					t.destroy();
					rem.put(i, t);
				}
			}
		}
		for(Integer i : rem.keySet()){
			List<IRenderTask> l = tasks.get(i);
			l.remove(rem.get(i));
			tasks.put(i, l);
		}
	}

	public void add(IRenderTask task){
		ntask.add(task);
	}
}
