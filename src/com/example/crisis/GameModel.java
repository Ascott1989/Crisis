package com.example.crisis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

//import android.R.color;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;


import com.example.crisis.Draw;
//import com.example.crisis.R;

public class GameModel {
	private Map myMap;
	protected Paint paint;
	protected float screenWidth, screenHeight;
	
	private PathFinder finder;
	private Path path;
	Resources resources;
	boolean cellSelected;
	boolean collision = false;
	protected ArrayList<Cells> myCells;
	protected ArrayList<Cellexit> myExits;
	Random randomGenerator = new Random();
	int rand;
	int Oxygen;
	int delay = 5000;
	long Ticker = 01;
	long currentMillis;
	Cells currentCell;
	Cells Temp;
	Cells iterator1;
	
	//protected ArrayList<Draw> worldObjects;
	protected Bitmap background;
	protected Bitmap redCell;
	protected Bitmap yellowCell;
	protected Bitmap blueCell;
	protected Bitmap greenCell;
	

	

	
	
	GameModel(Resources res){
		resources = res;
		
		Init();
		
	}
	
	
	public void Init(){
		myExits = new ArrayList<Cellexit>();
		myCells = new ArrayList<Cells>();
		LoadSprites();

		
		
		//worldObjects = new ArrayList<Draw>();
	}
	
	public void LoadSprites() {
	redCell = BitmapFactory.decodeResource(resources,R.drawable.redcell);
	//blueCell = BitmapFactory.decodeResources(resources,R.drawable.)
	
	}

	public void populate(){
		
		if(myCells.size() <= 5)
		{
			currentMillis = System.currentTimeMillis();
			if(currentMillis > delay + Ticker)
			{
				Ticker = currentMillis;
				int type = RandomGen(5);
				switch(type)
				{
				case 1 : type = 0;
				{
				int tempY = RandomGen(20);
				myCells.add(new Cells(10,300 + tempY,redCell));
				break;
				}
				case 2 : type = 1;
				{
					
				int tempY = RandomGen(20);
				myCells.add(new Cells(10,300+ tempY,redCell));
				break;
				}
				
			}
		}
		}
		
		myExits.add(new Cellexit(300,300,1));
		
	}

	public int RandomGen(int x){
		return rand = randomGenerator.nextInt(x);
	}
	
	public void draw(Canvas g)
	{
		Iterator<Cells> it = myCells.iterator();
		while(it.hasNext())
		{
		it.next().Draw(g);	
		}
		//g.drawBitmap(CellSprite, 50,50, null);
	}
	
	public void Update()
	{
		populate();
		Iterator<Cells> it  = myCells.iterator();
		
		while(it.hasNext())
		{
			iterator1 = it.next();
			iterator1.spriteUpdate(System.currentTimeMillis());
			iterator1.update();
			Iterator<Cellexit> it2 = myExits.iterator();
			while(it2.hasNext())
			{
			
				collision = it2.next().checkCollision(iterator1);
				if(collision == true)
				{
						collision = false; 
						iterator1.setPos(600, 600);
						break;
				}
			}
		}
	}
	
	public boolean pressed(int x, int y)
	{
		{
		Iterator<Cells> it1 = myCells.iterator();
				while(it1.hasNext())
					{
						 Temp = it1.next();
						cellSelected = Temp.checkPos(x, y);
						if(cellSelected == true)
						{
							currentCell = Temp;
							currentCell.selected(false);
						}
						
					}
		}
		
		return false;
		
	}
	
	public void move(float mX, float mY)
	{
		if(currentCell == null)
		{
			return;
		}
		else
		{
		currentCell.Move(mX, mY);
		}
	}
	
	public boolean released()
	{
		
		currentCell = null;
		return false;
		
	}
	
	public void actionMove(int x, int y, Class<? extends Iterator> Cells){
	
	
	}
	
	public float getScreenHeight() {
		return screenHeight;
	}

	public float getScreenWidth() {
		return screenWidth;
	}

	public void setScreenHeight(int h) {
		screenHeight = h;
	}

	public void setScreenWidth(int w) {
		screenWidth = w;
	}
	


	
}
