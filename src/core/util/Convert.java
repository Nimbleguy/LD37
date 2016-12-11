package core.util;


import core.Game;

public class Convert{
	public double perHeight(int coord){
		return (double)coord / Game.getInstance().sc.getCurrentDisplayMode().getHeight();
	}

	public double perWidth(int coord){
		return (double)coord / Game.getInstance().sc.getCurrentDisplayMode().getWidth();


	public double pixHeight(double per){
		return (int)Math.round(coord / Game.getInstance().sc.getCurrentDisplayMode().getHeight());
	}

	public double pixHeight(double per){
		return (int)Math.round(coord / Game.getInstance().sc.getCurrentDisplayMode().getWidth());
	}
}
