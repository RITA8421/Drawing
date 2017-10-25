package Pack;

import java.util.Comparator;

public abstract class ToCompare implements Comparator<ToCompare>{
	
	protected int area;
	
	public int compare(ToCompare com1, ToCompare com2){
		int com1Area = com1.getArea(); 
		int com2Area = com2.getArea();
		if(com1Area > com2Area) return 1;
		if(com1Area < com2Area) return -1;
		return 0; }

	private int getArea() {
		// TODO Auto-generated method stub
		return 0;
	}
		
	}

