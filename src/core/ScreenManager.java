package core;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import entity.Entity;

public class ScreenManager {

	private GraphicsDevice vc;
	JFrame frame;

	public ScreenManager(){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = env.getDefaultScreenDevice();
	}

	public DisplayMode[] getCompatibleDisplayModes(){
		return vc.getDisplayModes();
	}

	public DisplayMode getBestDisplayMode(DisplayMode[] dms){
		DisplayMode returnDM = null;
		int highestPix = -1;
		for (DisplayMode dm : dms){
			if (dm.getHeight()*dm.getWidth() > highestPix){
				returnDM = dm;
				highestPix = dm.getHeight()*dm.getWidth();
			}
		}
		return returnDM;
	}
	
	public DisplayMode getCurrentDisplayMode(){
		return vc.getDisplayMode();
	}

	public boolean displayModesMatch(DisplayMode m1, DisplayMode m2){
		if (m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight())
			return false;
		if (m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth())
			return false;
		if (m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate())
			return false;
		return true;
	}

	public void setToFullScreen(JFrame frame, DisplayMode dm){
		this.frame = frame;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(false);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		frame.setSize(800, 800);
		frame.setVisible(true);
		frame.setLocation(vc.getDisplayMode().getWidth()/2, vc.getDisplayMode().getHeight()/2);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

		if (dm != null&& vc.isDisplayChangeSupported()){
			try{
				vc.setDisplayMode(dm);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		frame.createBufferStrategy(2);//2 buffers
	}

	public Graphics2D getGraphics(){
		
		if (frame != null){
			BufferStrategy strategy = frame.getBufferStrategy();
			return (Graphics2D)strategy.getDrawGraphics();
		}else{
			return null;
		}
	}

	public void update(){
		
		if (frame != null){
			BufferStrategy strategy = frame.getBufferStrategy();
			if (!strategy.contentsLost()){
				strategy.show();
			}
			for (Entity e : Game.getInstance().getEntities()){
				if (e == null)
					continue;
				e.update();
			}
		}
	}

	public Window getWindow(){
		if (frame == null){
			System.err.println("Returned null in a getWindow()!");
		}
		return frame;
	}

	public int getWidth(){
		if (frame != null){
			return vc.getFullScreenWindow().getWidth();
		}else{
			return -1;
		}
	}

	public int getHeight(){
		if (frame != null){
			return vc.getFullScreenWindow().getHeight();
		}else{
			return -1;
		}
	}
	
	public void restoreScreen(){
		if (frame != null){
			frame.dispose();
		}
		vc.setFullScreenWindow(null);
	}
	
	public BufferedImage createCompatibleImage(int w, int h, int transparency){
		if (frame != null){
			GraphicsConfiguration gc = frame.getGraphicsConfiguration();
			return gc.createCompatibleImage(w, h, transparency);
		}
		return null;
	}
}
