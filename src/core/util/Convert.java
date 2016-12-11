package core.util;

import core.Game;

public class Convert{
	public static double perHeight(int coord){
		return (double)coord / Game.getInstance().getScreen().getCurrentDisplayMode().getHeight();
	}

	public static double perWidth(int coord){
		return (double)coord / Game.getInstance().getScreen().getCurrentDisplayMode().getWidth();
	}

	public static int pixHeight(double per){
		return (int)Math.round(per * Game.getInstance().getScreen().getCurrentDisplayMode().getHeight());
	}

	public static int pixWidth(double per){
		return (int)Math.round(per * Game.getInstance().getScreen().getCurrentDisplayMode().getWidth());
	}
}
