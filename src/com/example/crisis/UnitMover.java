package com.example.crisis;

public class UnitMover implements Mover {
	/** The unit ID moving */
	private Cells type;
	
	/**
	 * Create a new mover to be used while path finder
	 * 
	 * @param type The ID of the unit moving
	 */
	//public UnitMover(Cells type) {
		//this.type = type;
	//}
	
	/**
	 * Get the ID of the unit moving
	 * 
	 * @return The ID of the unit moving
	 */
	public Cells getType() {
		return type;
	}
}
