package core;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import entity.Entity;

public class ScreenManager {

	private GraphicsDevice vc;

	public ScreenManager(){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = env.getDefaultScreenDevice();
	}

	public DisplayMode[] getCompatibleDisplayModes(){
		return vc.getDisplayModes();
	}

	public DisplayMode findFirstCompatibleMode(DisplayMode[] modes){
		DisplayMode[] goodModes = vc.getDisplayModes();
		for (int x=0;x<modes.length;x++){
			for (int y=0;y<goodModes.length;y++){
				if (displayModesMatch(modes[x],goodModes[y])){
					return modes[x];
				}
			}
		}
		return null;
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
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		vc.setFullScreenWindow(frame);

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
		Window w = vc.getFullScreenWindow();
		if (w != null){
			BufferStrategy strategy = w.getBufferStrategy();
			return (Graphics2D)strategy.getDrawGraphics();
		}else{
			return null;
		}
	}

	public void update(){
		Window w = vc.getFullScreenWindow();
		if (w != null){
			BufferStrategy strategy = w.getBufferStrategy();
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

	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}

	public int getWidth(){
		if (vc.getFullScreenWindow() != null){
			return vc.getFullScreenWindow().getWidth();
		}else{
			return -1;
		}
	}

	public int getHeight(){
		if (vc.getFullScreenWindow() != null){
			return vc.getFullScreenWindow().getHeight();
		}else{
			return -1;
		}
	}
	
	public void restoreScreen(){
		Window w = vc.getFullScreenWindow();
		if (w != null){
			w.dispose();
		}
		vc.setFullScreenWindow(null);
	}
	
	public BufferedImage createCompatibleImage(int w, int h, int transparency){
		Window wi = vc.getFullScreenWindow();
		if (wi != null){
			GraphicsConfiguration gc = wi.getGraphicsConfiguration();
			return gc.createCompatibleImage(w, h, transparency);
		}
		return null;
	}
}
