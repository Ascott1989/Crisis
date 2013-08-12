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
	
	protected boolean sickled = false; 
	
	
	private boolean collided;
	private int rand;
	private int type; 
	private Rect sourceRect;
	private int frameNo;
	private int currentFrame;
	private long frameTicker;
	private int framePeriod;
	Random randomGenerator = new Random();
	private int spriteWidth;
	private int spriteHeight;
	boolean created = true;

	public Cells(int x, int y,Bitmap cellSprite, int type) {
		RandomNumber();
		xPos = x;
		yPos = y;
		cell = cellSprite;
		sickled = false; 
		this.type = type;
		collided = false; 
		currentFrame = rand;
		frameNo = 4;
		spriteWidth = cell.getWidth() / frameNo;
		spriteHeight = cell.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / 10;
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
		this.myRect.set(xPos - 25, yPos - 25, xPos + 25, yPos + 25);
	}

	public RectF getRect() {
		return this.myRect;
	}
	
	public boolean getSickled()
	{
		return this.sickled; 
	}
	
	public void collided(boolean col)
	{
		collided = col; 
	}
	
	public boolean checkcol()
	{
		return collided; 
	}
	
	public void setPos(int x, int y)
	{
		
		xPos = x;
		yPos = y;
	}
	
	public int getType(){
		return this.type;
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
	
	
	
	public boolean checkSickled()
	{
		return sickled;
	}
	
	public void setCollision(Bitmap sprite)
	{
		sickled = true; 
		cell = sprite;
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
		
		/*xPos = x; 
		yPos = y;*/
		
		xPos = (int) ((xPos * 0.9) + (x * 0.1));
		yPos = (int) ((yPos * 0.9) + (y * 0.1));
		
	}



}
