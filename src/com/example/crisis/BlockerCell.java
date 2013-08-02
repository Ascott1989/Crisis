package com.example.crisis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class BlockerCell {
	int xPos,yPos;
	protected Bitmap wall;
	protected RectF myRect = new RectF();
	
	boolean Collision = false; 
	
	BlockerCell(int x, int y, Bitmap test)
	{
	
		wall = test;
		xPos = x;
		yPos = y;
	}
	
	public RectF getRect(){
		return this.myRect;
	}
	
	public void UpdateRect(){
		this.myRect.set(xPos - 50, yPos - 50, xPos + 50, yPos + 50);
	}
	
	public void Draw(Canvas g){
		UpdateRect();
		g.drawBitmap(wall, xPos - (wall.getWidth() / 2),
				yPos - (wall.getHeight() / 2), null);
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
