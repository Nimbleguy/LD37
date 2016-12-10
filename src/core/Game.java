package core;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

import entity.Entity;

public class Game {
	public static void main(String[] args){
		instance = new Game();
		instance.run();
	}

	private static final DisplayMode[] modes1 = {
		new DisplayMode(800,600,32,0),//first two are resolution, third is bitdepth, fourth is refreshrate
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),

		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0),
	};

	private static Game instance;

	private ScreenManager sc;
	private boolean running;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> toPaint;
	private JFrame frame;

	public void run(){//so there cant be multiple running at once
		sc = new ScreenManager();
		frame = new JFrame();
		toPaint = new ArrayList<Entity>();
		entities = new ArrayList<Entity>();
		running  = true;

		frame.setBackground(Color.BLACK);
		frame.setForeground(Color.WHITE);
		frame.setFont(new Font("Arial", Font.PLAIN, 24));

		sc = new ScreenManager();
		try{
			DisplayMode dm = sc.findFirstCompatibleMode(modes1);
			sc.setToFullScreen(frame, dm);
			long ticks = 0;
			try{
				while(running){
					Graphics2D g = sc.getGraphics();
					drawEntities(g);
					g.dispose();
					sc.update();
					Thread.sleep(10);
					ticks++;
					if (ticks>=500){
						running=false;
					}
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

	public static Game getInstance(){
		return instance;
	}
}