package com.example.crisis;



import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Cellexit {
		int xPos, yPos;
		
		private Rect sourceRect;
		private int frameNo;
		private int currentFrame;
		private long frameTicker;
		private int framePeriod;
		Random randomGenerator = new Random();
		private int spriteWidth;
		private int spriteHeight;
		
		Bitmap cellExit;
		protected RectF myRect = new RectF();
		
		boolean Collision = false; 
		
		public Cellexit(int xPos, int yPos, Bitmap exit){
			this.xPos = xPos;
			this.yPos = yPos;
			
			cellExit = exit;
			currentFrame = 0;
			frameNo = 4;
			spriteWidth = cellExit.getWidth() / frameNo;
			spriteHeight = cellExit.getHeight();
			sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
			framePeriod = 1000 / 60;
			frameTicker = 0l;
			
			
			
			this.UpdateRect();
		}
		
		public RectF getRect() {
			return this.myRect;
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
		
		
		public void UpdateRect() {
			this.myRect.set(xPos - 80, yPos - 80, xPos + 80, yPos + 80);
		}
		
		public void Collision(){
			
		}
		
		public void Draw(Canvas g) {
			UpdateRect();
			Rect destRect = new Rect((int)xPos - spriteWidth/2,(int) yPos - spriteHeight/2, (int)xPos + spriteWidth/2,(int) yPos + spriteHeight/2);
			//g.drawBitmap(cell,xPos,yPos, null);
			g.drawBitmap(cellExit, sourceRect, destRect, null);
			
			
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
