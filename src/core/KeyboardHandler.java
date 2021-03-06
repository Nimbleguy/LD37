package core;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyboardHandler implements KeyListener{
	public KeyboardHandler(Window w){
		w.setFocusTraversalKeysEnabled(false);//things like tab act as buttons not functions
		w.addKeyListener(this);
	}

	List<Integer> pressed = new ArrayList<Integer>();

	@Override
	public void keyPressed(KeyEvent e){
		if (!pressed.contains(e.getKeyCode())){
			pressed.add(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e){
		if (pressed.contains(e.getKeyCode())){//stops this from being repeatedly called... (somehow?) (w/e)
			pressed.remove((Integer)e.getKeyCode());
		}
	}

	@Override
	public void keyTyped(KeyEvent e){
		//pretty much useless
	}
	
	public List<Integer> getPressed(){
		return pressed;
	}
}
