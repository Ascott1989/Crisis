package com.example.crisis;



import android.graphics.RectF;

public class Cellexit {
		int xPos, yPos;
		
		protected RectF myRect = new RectF();
		
		boolean Collision = false; 
		
		public Cellexit(int xPos, int yPos, int Colour){
			this.xPos = xPos;
			this.yPos = yPos;
			this.UpdateRect();
		}
		
		public RectF getRect() {
			return this.myRect;
		}
		
		
		public void UpdateRect() {
			this.myRect.set(xPos - 80, yPos - 80, xPos + 80, yPos + 80);
		}
		
		public void Collision(){
			
		}
		
		
		public boolean checkCollision(Cells cell) {
			if (RectF.intersects(this.myRect, cell.getRect())) {
				this.Collision = true;

			}
			if (Collision == true) {
				this.Collision = false;
				return true;
				
			} else {
				return false;
			}
		}
}
