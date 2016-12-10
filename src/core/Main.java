package core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import core.gl.Render;
import core.gl.RenderTaskClear;
import core.gl.RenderSprite;
import events.Event;
import events.Listener;

public class Main{
	private final long win;
	private static Main main;
	private Render rend;

	private String title = "the gaem title";

	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback mouseCallback;

	private static List<Listener> listeners = new ArrayList<Listener>();

	private Main(){
		if(!glfwInit()){ // Start.
			System.err.println("Error initializing glfw.");
			System.exit(1);
		}
		setHints();
		win = glfwCreateWindow(800, 600, title, NULL, NULL);
		if(win == NULL){
			System.err.println("Error with window init.");
			System.exit(1);
		}

		glfwMakeContextCurrent(win);
		GL.createCapabilities(true);
		glfwSwapInterval(1);
		System.out.println(glGetString(GL_VERSION));
		start();

		glfwDestroyWindow(win);
		glfwTerminate(); // Exit.
	}

	private void setHints(){
		glfwWindowHint(GLFW_FLOATING, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	}

	private void start(){
		glfwSetKeyCallback(win, keyCallback = new KeyboardHandler());
		glfwSetCursorPosCallback(win, mouseCallback = new MouseHandler());
		rend = new Render();
		rend.add(new RenderTaskClear());
		//rend.add(new RenderSprite(0, ""));
		while(!glfwWindowShouldClose(win)){
			rend.render(win); // Render loop.
			glfwSwapBuffers(win);
		}
	}

	public static List<Listener> getListeners(){
		return listeners;
	}

	public static void registerListener(Listener listener){
		listeners.add(listener);
	}
	public static void unregisterListner(Listener listener){
		listeners.remove(listener);
	}

	public static void main(String[] args){
		KeyboardHandler.registerKeyListener(GLFW_KEY_W,new events.Listener(){//keylistener example
			@Override
			public void listen(Event e) {
				System.out.println(((events.KeyEvent)e).isPressed());
			}
		});
		KeyboardHandler.registerKeyListener(GLFW_KEY_ESCAPE,new events.Listener(){//keylistener example
			@Override
			public void listen(Event e) {
				if (((events.KeyEvent)e).isPressed()){
					System.out.println("Terminating");
					glfwSetWindowShouldClose(Main.main.win,true);
				}
			}
		});
		MouseHandler.registerMouseListener(new events.Listener(){
			@Override
			public void listen(Event e) {
				events.MouseEvent ev = (events.MouseEvent) e;
				System.out.printf("(%f,%f)\n",ev.getX(),ev.getY());
			}
		});
		main = new Main();
	}
}
