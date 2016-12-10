package core;

public enum PriorityValue {
	LOWEST		(-3),
	LOW			(-2),
	MEDIUM_LOW	(-1),
	MEDIUM		(0),
	MEDIUM_HIGH	(1),
	HIGH		(2),
	HIGHEST		(3);

	private final int val;
	PriorityValue(int priority){
		this.val = priority;
	}
	public int getPriority(){
		return val;
	}
}
