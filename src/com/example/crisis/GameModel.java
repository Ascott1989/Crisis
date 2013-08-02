package com.example.crisis;


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
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
	protected Paint paint;
	protected float screenWidth, screenHeight;
	Resources resources;
	int xPos, yPos;
	boolean cellSelected;
	boolean collision = false;
	protected ArrayList<Cells> myCells;
	protected ArrayList<Cellexit> myExits;
	protected ArrayList<BlockerCell> myWalls;
	Random randomGenerator = new Random();
	InputStreamReader inputStream;
	
	
	int rand;
	int Oxygen;
	int delay = 5000;
	long Ticker = 01;
	long currentMillis;
	char currentNum;
	Cells currentCell;
	Cells Temp;
	Cells iterator1;
	Context thiscontext;
	InputStream descriptor;
	AssetManager assetmgr;
	// protected ArrayList<Draw> worldObjects;
	protected Bitmap background;
	protected Bitmap redCell;
	protected Bitmap yellowCell;
	protected Bitmap blueCell;
	protected Bitmap greenCell;
	protected Bitmap testblocker;

	GameModel(Resources res, Context context) {
		thiscontext = context;
		resources = res;
		xPos = 25;
		yPos = 25;
		
		
		Init();
		

	}

	public void LoadLevel()  {
		while (currentNum != '8') // This is key!
		{
			
				
			
			
			try {
					currentNum = (char) inputStream.read();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
			if(currentNum == '1')
			{
				myWalls.add(new BlockerCell(xPos,yPos,testblocker));
				xPos += 50;
			}
			
			if(currentNum == '#')
			{
				yPos += 50;
				xPos = 25;
			}
			
			if(currentNum == '0')
			{
				xPos += 50;
			}
			if(currentNum == '2')
			{
				myExits.add(new Cellexit(xPos, yPos, 1));
				xPos+=50;
			}
		
		/*	switch (currentNum) {
			case 1:
				currentNum = 1;
				{
					myWalls.add(new BlockerCell(xPos,yPos,testblocker));
					xPos += 100;
					// create blocker increase X Pos by 100;
					break;
				}
			case 2:
				currentNum = '#';
				{
					yPos += 100;
					xPos = 50;
					// increase Y by 100 set X to 50;
					break;
				}
			case 3: 
				currentNum = '0';
				{
					xPos += 100;
					//Increase Xpos by 100 do nothing
					break;
				}
				
			case 4:
				currentNum = '2';
				{
					myExits.add(new Cellexit(xPos, yPos, 1));
					xPos+=100;
				}
			}*/
			
		}
	}

	public void Init() {
	
		assetmgr = thiscontext.getAssets();
		try {
			descriptor = assetmgr.open("level1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		myExits = new ArrayList<Cellexit>();
		myCells = new ArrayList<Cells>();
		myWalls = new ArrayList<BlockerCell>();
		inputStream = new InputStreamReader(descriptor);
	
		LoadSprites();
		LoadLevel();
		

		// worldObjects = new ArrayList<Draw>();
	}

	public void LoadSprites() {
		redCell = BitmapFactory.decodeResource(resources, R.drawable.redcell);
		greenCell = BitmapFactory.decodeResource(resources,
				R.drawable.greencell);
		yellowCell = BitmapFactory.decodeResource(resources,
				R.drawable.yellowcell);
		blueCell = BitmapFactory.decodeResource(resources, R.drawable.bluecell);
		testblocker = BitmapFactory.decodeResource(resources,R.drawable.wall);
	}

	public void populate() {

		if (myCells.size() <= 5) {
			currentMillis = System.currentTimeMillis();
			if (currentMillis > delay + Ticker) {
				Ticker = currentMillis;
				int type = RandomGen(4);
				switch (type) {
				case 1:
					type = 0;
					{
						int tempY = RandomGen(20);
						myCells.add(new Cells(10, 300 + tempY, redCell));
						break;
					}
				case 2:
					type = 1;
					{

						int tempY = RandomGen(20);
						myCells.add(new Cells(10, 300 + tempY, greenCell));
						break;
					}
				case 3:
					type = 2;
					{
						int tempY = RandomGen(20);
						myCells.add(new Cells(10, 300 + tempY, yellowCell));
						break;
					}
				case 4:
					type = 3;
					{
						int tempY = RandomGen(20);
						myCells.add(new Cells(10, 300 + tempY, blueCell));
						break;
					}
				}
			}
		}
	}

	public int RandomGen(int x) {
		return rand = randomGenerator.nextInt(x);
	}

	public void draw(Canvas g) {
		Iterator<Cells> it = myCells.iterator();
		Iterator<BlockerCell> iter = myWalls.iterator();
		
			while(iter.hasNext())
			{
			iter.next().Draw(g);
			}
		
		while (it.hasNext()) {
			it.next().Draw(g);
		}
		
	

	}

	public void Update() {
		populate();
		Iterator<Cells> it = myCells.iterator();

		while (it.hasNext()) {
			iterator1 = it.next();
			iterator1.spriteUpdate(System.currentTimeMillis());
			iterator1.update();
			Iterator<Cellexit> it2 = myExits.iterator();
			while (it2.hasNext()) {

				collision = it2.next().checkCollision(iterator1);
				if (collision == true) {
					collision = false;
					iterator1.setPos(600, 600);
					it.remove();
					break;
				}
			}
		}
	}

	public boolean pressed(int x, int y) {
		{
			Iterator<Cells> it1 = myCells.iterator();
			while (it1.hasNext()) {
				Temp = it1.next();
				cellSelected = Temp.checkPos(x, y);
				if (cellSelected == true) {
					currentCell = Temp;
					currentCell.selected(false);
				}

			}
		}

		return false;

	}

	public void move(float mX, float mY) {
		if (currentCell == null) {
			return;
		} else {
			currentCell.Move(mX, mY);
		}
	}

	public boolean released() {

		currentCell = null;
		return false;

	}

	public void actionMove(int x, int y, Class<? extends Iterator> Cells) {

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
