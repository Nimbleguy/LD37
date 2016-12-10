package core.gl;

public interface IRenderTask{
	public int priority();
	public boolean remove();
	public void init();
	public void run(long window);
	public void destroy();
}
