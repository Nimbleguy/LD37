package core.util;

public class Vector {
	private double degrees;
	private double power;
	private double traction;
	public Vector(double degrees, double power, double traction){
		degrees%=360;
		if (degrees<0){//makes it positive
			degrees+=180;
			if (degrees<0)
				degrees+=180;
		}
		this.degrees=degrees;
		this.power=power;
		this.traction=traction;
	}

	public double[] calcMove(double x, double y, boolean useTraction){
		if (useTraction)
			power-=traction;
		power=Math.max(power, 0);
		return new double[]{
				x+(power*Math.cos(Math.toRadians(degrees))),
				y+(power*Math.sin(Math.toRadians(degrees)))
		};
	}

	public void addDegrees(double amount){
		degrees+=amount;
		degrees%=amount;
	}

	public double getDegrees(){
		return degrees;
	}

	public void addPower(double amount){
		power+=amount;
	}

	public void add(Vector another){
		double x = power*Math.cos(Math.toRadians(degrees));
		double y = power*Math.sin(Math.toRadians(degrees));
		x += another.power*Math.cos(Math.toRadians(another.degrees));
		y += another.power*Math.sin(Math.toRadians(another.degrees));
		power = Math.sqrt((x*x)+(y*y));
		degrees = Math.toDegrees(Math.atan(y/x));
		if (degrees<0){//makes it positive
			degrees+=180;
			if (degrees<0)
				degrees+=180;
		}
	}
	
	public double getPower(){
		return power;
	}
}
