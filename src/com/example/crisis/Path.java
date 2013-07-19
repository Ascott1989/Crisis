package com.example.crisis;

import java.util.ArrayList;

public class Path {
	private ArrayList steps = new ArrayList();
	
	public Path(){
		
	}
	
	public int getLength(){
		return steps.size();
	}
	
	public Step getStep(int index){
		return (Step) steps.get(index);
	}
	
	public int getX(int index){
		return getStep(index).x;
	}
	
	public int getY(int index){
		return getStep(index).y;
	}
	
	public void appendStep(int x, int y){
		steps.add(new Step(x,y));
	}
	
	public void prependStep(int x, int y){
		steps.add(0, new Step(x,y));
	}
	
	public boolean contains(int x, int y){
		return steps.contains(new Step(x,y));
	}
	
	/* A step in the path */ 
	
	public class Step {
		/** The x coordinate at the given step */
		private int x;
		/** The y coordinate at the given step */
		private int y;
		
		/**
		 * Create a new step
		 * 
		 * @param x The x coordinate of the new step
		 * @param y The y coordinate of the new step
		 */
		public Step(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Get the x coordinate of the new step
		 * 
		 * @return The x coodindate of the new step
		 */
		public int getX() {
			return x;
		}

		/**
		 * Get the y coordinate of the new step
		 * 
		 * @return The y coodindate of the new step
		 */
		public int getY() {
			return y;
		}
		
		/**
		 * @see Object#hashCode()
		 */
		public int hashCode() {
			return x*y;
		}

		/**
		 * @see Object#equals(Object)
		 */
		public boolean equals(Object other) {
			if (other instanceof Step) {
				Step o = (Step) other;
				
				return (o.x == x) && (o.y == y);
			}
			
			return false;
		}
	}
}
