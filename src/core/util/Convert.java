package core.util;

import core.Game;

public class Convert{
	public static double perHeight(int coord){
		return (double)coord / Game.getInstance().getMode().getHeight();
	}

	public static double perWidth(int coord){
		return (double)coord / Game.getInstance().getMode().getWidth();
	}

	public static int pixHeight(double per){
		return (int)Math.round(per * Game.getInstance().getMode().getHeight());
	}

	public static int pixWidth(double per){
		return (int)Math.round(per * Game.getInstance().getMode().getWidth());
	}
}
