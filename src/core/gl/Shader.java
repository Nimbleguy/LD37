package core.gl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

import org.apache.commons.io.IOUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader{
	private static HashMap<String, Integer> vertexl = new HashMap<String, Integer>();
	private static HashMap<String, Integer> fragmentl = new HashMap<String, Integer>();
	private static HashMap<String[], Integer> programl = new HashMap<String[], Integer>();

	private static HashMap<Integer, Integer> usev = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> usef = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Integer> usep = new HashMap<Integer, Integer>();

	private final int vert;
	private final int frag;
	private final int prog;

	public Shader(String vertex, String fragment){
		if(vertexl.get(vertex) == null || fragmentl.get(fragment) == null || programl.get(new String[] {vertex, fragment}) == null){
			int pid = glCreateProgram();
			int vid = -1;
			int fid = -1;
			if(vertexl.get(vertex) == null){
				try{
					InputStream in = new FileInputStream(new File(vertex));
					String v = IOUtils.toString(in, "utf-8");
					in.close();
					vid = glCreateShader(GL_VERTEX_SHADER);
					glShaderSource(vid, v);
					vertexl.put(vertex, vid);
					assert glGetShaderi(vid, GL_COMPILE_STATUS) != GL_FALSE;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else{
				vid = vertexl.get(vertex);
			}
			glAttachShader(pid, vid);
			if(usev.get(vid) == null){
				usev.put(vid, 0);
			}
			usev.put(vid, usev.get(vid) + 1);
			vert = vid;
			if(fragmentl.get(fragment) == null){
				try{
					InputStream in = new FileInputStream(new File(fragment));
					String v = IOUtils.toString(in, "utf-8");
					in.close();
					fid = glCreateShader(GL_VERTEX_SHADER);
					glShaderSource(fid, v);
					fragmentl.put(fragment, fid);
					assert glGetShaderi(fid, GL_COMPILE_STATUS) != GL_FALSE;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			else{
				fid = fragmentl.get(fragment);
			}
			glAttachShader(pid, fid);
			if(usef.get(fid) == null){
				usef.put(fid, 0);
			}
			usef.put(fid, usef.get(fid) + 1);
			frag = fid;
			try{
				glLinkProgram(pid);
				programl.put(new String[] {vertex, fragment}, pid);
				assert glGetProgrami(pid, GL_LINK_STATUS) != GL_FALSE;
			}
			catch(Exception e){
				e.printStackTrace();
			}
			prog = pid;
		}
		else{
			vert = vertexl.get(vertex);
			frag = fragmentl.get(fragment);
			prog = programl.get(new String[] {vertex, fragment});
		}
		if(usep.get(prog) == null){
			usep.put(prog, 0);
		}
		usep.put(prog, usep.get(prog) + 1);
	}

	public int getProgram(){
		return prog;
	}

	public int getVertex(){
		return vert;
	}

	public int getFragment(){
		return frag;
	}

	public void destroy(){
		usev.put(vert, usev.get(vert) - 1);
		usef.put(frag, usef.get(frag) - 1);
		usep.put(prog, usep.get(prog) - 1);
		if(usep.get(prog) <= 0){
			glDetachShader(prog, vert);
			glDetachShader(prog, frag);
			glDeleteProgram(prog);
		}
		if(usev.get(vert) <= 0){
			glDeleteShader(vert);
		}
		if(usef.get(frag) <= 0){
			glDeleteShader(frag);
		}
	}
}
