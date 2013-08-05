package com.example.crisis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class BlockerCell {
	int xPos,yPos;
	protected Bitmap wall;
	protected RectF myRect = new RectF();
	
	int spriteWidth;
	int spriteHeight;
	
	
	boolean Collision = false; 
	
	BlockerCell(int x, int y, Bitmap test)
	{
		
		wall = test;
		spriteWidth = wall.getWidth();
		spriteHeight = wall.getHeight();
		xPos = x;
		yPos = y;
	}
	
	public RectF getRect(){
		return this.myRect;
	}
	
	public void UpdateRect(){
		this.myRect.set(xPos - 25, yPos - 25, xPos + 25, yPos + 25);
	}
	
	public void Draw(Canvas g){
		UpdateRect();
		g.drawBitmap(wall, xPos - (wall.getWidth() / 2),
				yPos - (wall.getHeight() / 2), null);
	}
	
	
	public boolean checkCollision(Cells cell) {
		if (RectF.intersects(myRect, cell.getRect())) {
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
