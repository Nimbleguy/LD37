package core.gl;

import static org.lwjgl.opengl.GL11.*;

public class RenderTaskClear implements IRenderTask{
	public int priority(){
		return 10;
	}

	public boolean remove(){
		return false;
	}

	public void init(){
		glClearColor(102f/255f, 0, 255f/255f, 0);
	}

	public void run(long window){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void destroy(){}
}
