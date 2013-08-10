package com.example.crisis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class phage {
	float xPos,yPos;
	protected Bitmap phage;
	protected RectF myRect = new RectF();
	
	int spriteWidth;
	int spriteHeight;
	
	
	private Rect sourceRect;
	private int frameNo;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;
	
	
	boolean Collision = false; 
	
	phage(int x, int y, Bitmap test)
	{
		phage = test;
		currentFrame = 0;
		frameNo = 4;
		spriteWidth = phage.getWidth() / frameNo;
		spriteHeight = phage.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / 10;
		frameTicker = 0l;
		
		xPos = x;
		yPos = y;
	}
	
	public boolean checkPos(int x, int y)
	{
		if((x >= xPos - phage.getWidth() / 2 ) && (x <= xPos + phage.getWidth() / 2) && (y <= yPos + phage.getHeight() / 2) && (y >= yPos - phage.getHeight() / 2))
		{
			return true;
		}
		return false;
	}
	
	public void Move(float x, float y) {
		
		xPos = x; 
		yPos = y;
		
		/*xPos = (int) ((xPos * 0.9) + (x * 0.1));
		yPos = (int) ((yPos * 0.9) + (y * 0.1));*/
		
	}
	
	public RectF getRect(){
		return this.myRect;
	}
	
	public void UpdateRect(){
		this.myRect.set(xPos - 25, yPos - 25, xPos + 25, yPos + 25);
	}
	
	public void Draw(Canvas g){
		UpdateRect();
		Rect destRect = new Rect((int)xPos - spriteWidth/2,(int) yPos - spriteHeight/2, (int)xPos + spriteWidth/2,(int) yPos + spriteHeight/2);
		//g.drawBitmap(cell,xPos,yPos, null);
		g.drawBitmap(phage, sourceRect, destRect, null);
	}
	
	public void spriteUpdate(long currentTimeMillis) {
		{
		if(currentTimeMillis > frameTicker + framePeriod){
			frameTicker = currentTimeMillis;
			currentFrame++;
			if(currentFrame >= frameNo) {
				currentFrame = 0;
			}
			
		}
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
		}
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
