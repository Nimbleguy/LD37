package core.gl;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;

import java.io.File;

import core.Utilities;

public class RenderSprite implements IRenderTask{

	private float[] vertex = new float[] {-1f, 1f, 0f, 0f, -1f, -1f, 0f, 0f, 0.5f, -0.5f, 0f, 0f, 0.5f, 0.5f, 0f, 0f};
	private float[] colors = new float[] {1f, 0f, 0f, 1f, 0f, 1f, 0f, 1f, 0f, 0f, 1f, 1f, 1f, 1f, 1f, 1f};
	private final byte[] indicies = new byte[] {0, 1, 2, 2, 3, 0};

	private final int prior;
	//private final ByteBuffer image;
	private int vao = -1;
	private int vboV = -1;
	private int vboI = -1;
	private int vboC = -1;

	private Shader shader;

	public RenderSprite(int p, String path){
		prior = p;
		shader = new Shader(Utilities.getAsset("glsl/generic.vert"), Utilities.getAsset("glsl/generic.frag"));
	}

	public RenderSprite offset(float x, float y, float z, float w){
		for(int i = 0; i < vertex.length; i++){
			if((i + 1) % 4 == 0){
				vertex[i] += w;
			}
			else if((i + 1) % 3 == 0){
				vertex[i] += z;
			}
			else if((i + 1) % 2 == 0){
				vertex[i] += y;
			}
			else{
				vertex[i] += x;
			}
		}
		return this;
	}

	public int priority(){
		return prior;
	}

	public boolean remove(){
		return false;
	}

	public void init(){
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vboV = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboV);
		FloatBuffer buf = BufferUtils.createFloatBuffer(vertex.length);
		buf.put(vertex).flip();
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		vboC = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboC);
		FloatBuffer cbuf = BufferUtils.createFloatBuffer(colors.length);
		cbuf.put(colors).flip();
		glBufferData(GL_ARRAY_BUFFER, cbuf, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		vboI = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboI);
		ByteBuffer ibuf = BufferUtils.createByteBuffer(indicies.length);
		ibuf.put(indicies).flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibuf, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void run(long window){
		glClear(GL_COLOR_BUFFER_BIT);
		glUseProgram(shader.getProgram());
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboI);
		glDrawElements(GL_TRIANGLES, indicies.length, GL_UNSIGNED_BYTE, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	public void destroy(){
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboV);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboI);
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
		shader.destroy();
	}
}
