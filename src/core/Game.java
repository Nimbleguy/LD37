package core;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

import javax.swing.JFrame;

import entity.Entity;
import events.Listener;

public class Game {
	public static void main(String[] args){
		instance = new Game();
		instance.run();
	}


	private static Game instance;
	private List<Listener> listeners;

	private ScreenManager sc;
	private boolean running;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> toPaint;
	private JFrame frame;

	public void run(){
		System.out.println("Initializing");
		sc = new ScreenManager();
		toPaint = new ArrayList<Entity>();
		entities = new ArrayList<Entity>();
		running  = true;

		sc = new ScreenManager();
		try{
			DisplayMode dm = sc.findFirstCompatibleMode(sc.getCompatibleDisplayModes());

			frame = new JFrame();

			frame.setBackground(Color.BLACK);
			frame.setForeground(Color.WHITE);
			frame.setFont(new Font("Arial", Font.PLAIN, 24));

			sc.setToFullScreen(frame, dm);
			new KeyboardHandler(frame);//registers the keyboard handler
			try{
				while(running){
					Graphics2D g = sc.getGraphics();
					drawEntities(g);
					g.dispose();
					sc.update();
					Thread.sleep(10);
					try{
						Thread.sleep(20L);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}finally{
			sc.restoreScreen();
		}
	}

	public void drawEntities(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 10000, 10000);
		for (Entity e : toPaint){
			e.update();
			g.drawImage(e.getSprite(), e.getDrawX(), e.getDrawY(), null);
		}
	}

	public void addEntity(Entity e){
		entities.add(e);
	}

	public void removeEntity(Entity e){
		entities.remove(e);
	}

	public void startPainting(Entity e){
		toPaint.remove(e);
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public void registerListener(Listener l){
		listeners.add(l);
	}
	
	public void unregisterListener(Listener l){
		listeners.remove(l);
	}
	
	public List<Listener> getListeners(){
		return listeners;
	}

	public static void stop(){
		System.out.println("Terminating");
		instance.running = false;
	}

	public static Game getInstance(){
		return instance;
	}

	public ScreenManager getScreen(){
		return sc;
	}

	public void addPaint(Entity e){
		toPaint.add(e);
	}

	public void remPaint(Entity e){
		toPaint.remove(e);
	}
}
