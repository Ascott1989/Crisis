package com.example.crisis;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;

public class Cells {

	protected Paint paint = new Paint();
	protected float xPos, yPos;
	protected RectF myRect = new RectF();
	protected Bitmap cell;
	
	
	private int rand;
	
	private Rect sourceRect;
	private int frameNo;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;
	Random randomGenerator = new Random();
	private int spriteWidth;
	private int spriteHeight;
	boolean created = true;

	public Cells(int x, int y,Bitmap cellSprite) {
		RandomNumber();
		xPos = x;
		yPos = y;
		cell = cellSprite;
		
		
		currentFrame = rand;
		frameNo = 4;
		spriteWidth = cell.getWidth() / frameNo;
		spriteHeight = cell.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / 60;
		frameTicker = 0l;
		//this.radius = radius;// set to input parameter values
	}
	
	
	 void setSprite(Bitmap sprite)
	{
		cell = sprite;
		spriteWidth = sprite.getWidth() / frameNo;
		spriteHeight = sprite.getHeight();
	}
	
	 public Bitmap returnSprite()
	 {
		 return cell;
	 }
	 
	 public void RandomNumber(){
		rand = randomGenerator.nextInt(5);
	 }

	public void spriteUpdate(long currentTimeMillis) {
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

	public void UpdateRect() {
		this.myRect.set(xPos - 50, yPos - 50, xPos + 50, yPos + 50);
	}

	public RectF getRect() {
		return this.myRect;
	}
	
	public void setPos(int x, int y)
	{
		
		xPos = x;
		yPos = y;
	}
	
	public boolean checkPos(int x, int y)
	{
		if((x >= xPos - cell.getWidth() / 2 ) && (x <= xPos + cell.getWidth() / 2) && (y <= yPos + cell.getHeight() / 2) && (y >= yPos - cell.getHeight() / 2))
		{
			return true;
		}
		return false;
	}
	
	public void update(){
	if(this.created)
	{
		this.xPos += 1;
	}
	}
	
	public void selected(boolean x)
	{
		this.created = x; 
	}

	public void Draw(Canvas g) {
		UpdateRect();
		Rect destRect = new Rect((int)xPos - spriteWidth/2,(int) yPos - spriteHeight/2, (int)xPos + spriteWidth/2,(int) yPos + spriteHeight/2);
		//g.drawBitmap(cell,xPos,yPos, null);
		g.drawBitmap(cell, sourceRect, destRect, null);
		
		
	}


	public void Move(float x, float y) {
		
		xPos = x; 
		yPos = y;
		
		/*xPos = (int) ((xPos * 0.9) + (x * 0.1));
		yPos = (int) ((yPos * 0.9) + (y * 0.1));*/
		
	}



}
