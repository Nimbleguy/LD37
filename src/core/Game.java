package core;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;

import core.util.Vector;
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
	private DisplayMode dm;
	private KeyboardHandler kh;

	public void run(){
		System.out.println("Initializing");
		sc = new ScreenManager();
		toPaint = new ArrayList<Entity>();
		entities = new ArrayList<Entity>();
		listeners = new ArrayList<Listener>();
		running  = true;

		sc = new ScreenManager();
		try{
			dm = sc.getBestDisplayMode(sc.getCompatibleDisplayModes());

			frame = new JFrame("Game");

			frame.setBackground(Color.BLACK);
			frame.setForeground(Color.WHITE);
			frame.setFont(new Font("Arial", Font.PLAIN, 24));

			sc.setToFullScreen(frame, dm);
			kh = new KeyboardHandler(frame);//registers the keyboard handler
			new Play().init();
			try{
				while(running){
					long t = System.currentTimeMillis();
					Graphics2D g = sc.getGraphics();
					sc.update();
					drawEntities(g);
					g.dispose();
					doKeys();
					try{
						Thread.sleep(Math.max(Math.round((1000d / 60d) - (System.currentTimeMillis() - t)),0));
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
			if (e == null)
				continue;
			g.drawImage(e.getSprite(), e.getDrawX(), e.getDrawY(), null);
		}
	}

	public void doKeys(){
		Iterator<Integer> iter = new CopyOnWriteArrayList<Integer>(kh.getPressed()).iterator();
		while(iter.hasNext()){
			Integer i = iter.next();
			switch(i){

			case KeyEvent.VK_ESCAPE:
				stop();
				break;

			case KeyEvent.VK_UP:
				Play.getPlayer().setY(Play.getPlayer().getY() - 2);
				Play.getPlayer().setSpriteIndex(0);
				break;

			case KeyEvent.VK_DOWN:
				Play.getPlayer().setY(Play.getPlayer().getY() + 2);
				Play.getPlayer().setSpriteIndex(1);
				break;

			case KeyEvent.VK_RIGHT:
				Play.getPlayer().setX(Play.getPlayer().getX() + 2);
				Play.getPlayer().setSpriteIndex(2);
				break;

			case KeyEvent.VK_LEFT:
				Play.getPlayer().setX(Play.getPlayer().getX() - 2);
				Play.getPlayer().setSpriteIndex(2);
				break;

			default:
				break;
			}
		}
	}


	///////////////setters and getters/////////////////


	public void addEntity(Entity e){
		entities.add(e);
	}

	public void removeEntity(Entity e){
		entities.remove(e);
	}

	public boolean entitiesContains(Entity e){
		return entities.contains(e);
	}

	public boolean paintContains(Entity e){
		return toPaint.contains(e);
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
		if (instance == null){
			System.err.println("Game.getInstance() returned null!");
		}
		return instance;
	}

	public JFrame getFrame(){
		return frame;
	}

	public ScreenManager getScreen(){
		return sc;
	}

	public void addPaint(Entity e){
		toPaint.add(e);
	}

	public void removePaint(Entity e){
		toPaint.remove(e);
	}
	public DisplayMode getMode(){
		return dm;
	}
}
