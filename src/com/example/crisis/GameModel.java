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
	boolean phageCreated = false;
	protected ArrayList<Cells> myCells;
	protected ArrayList<Cellexit> myExits;
	protected ArrayList<BlockerCell> myWalls;
	Random randomGenerator = new Random();
	InputStreamReader inputStream;
	Cellexit iterator2;
	phage myPhage;
	boolean phageSelected = false; 
	int rand;
	int Oxygen;
	int delay = 5000;
	long Ticker = 01;
	long currentMillis;
	char currentNum;
	Cells currentCell;
	Cells Temp;
	int startX, startY;
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
	protected Bitmap sickleCell;
	protected Bitmap redExit, blueExit, greenExit, yellowExit, phageSprite,
			phageIcon;
	
	
	int currentLevel = 1; 

	GameModel(Resources res, Context context) {
		thiscontext = context;
		resources = res;
		xPos = 25;
		yPos = 25;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(16f);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		Init();

	}

	public void LoadLevel() {
		
		assetmgr = thiscontext.getAssets();
		try {
			if (currentLevel == 1) 
			{
				descriptor = assetmgr.open("level1.txt");
			} else if (currentLevel == 2) {
				descriptor = assetmgr.open("level2.txt");
			} else if (currentLevel == 3) {
				descriptor = assetmgr.open("level3.txt");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		inputStream = new InputStreamReader(descriptor);
		
		while (currentNum != '8') // This is key!
		{

			try {
				currentNum = (char) inputStream.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (currentNum == '1') {
				myWalls.add(new BlockerCell(xPos, yPos, testblocker));
				xPos += 50;
			}

			if (currentNum == '#') {
				yPos += 45;
				xPos = 25;
			}

			if (currentNum == '0') {
				xPos += 50;
			}
			if (currentNum == '2') {
				myExits.add(new Cellexit(xPos, yPos, redExit, 1)); // Red Exit - 2
				xPos += 50;
			}

			if (currentNum == '5') {
				myExits.add(new Cellexit(xPos, yPos, blueExit, 4)); // Blue Exit - 5
				xPos += 50;
			}

			if (currentNum == '6') {
				myExits.add(new Cellexit(xPos, yPos, yellowExit, 3)); // Yellow Exit - 6
				xPos += 50;
			}
			if (currentNum == '7') {
				myExits.add(new Cellexit(xPos, yPos, greenExit, 2)); //Green Exit - 7
				xPos += 50;
			}
			if (currentNum == '3') {
				startX = xPos;
				startY = yPos;
				xPos += 50;
			}

		}
	}

	public void Init() {

		

		myExits = new ArrayList<Cellexit>();
		myCells = new ArrayList<Cells>();
		myWalls = new ArrayList<BlockerCell>();
		

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
		testblocker = BitmapFactory.decodeResource(resources, R.drawable.wall);
		sickleCell = BitmapFactory.decodeResource(resources,
				R.drawable.sicklecell);
		redExit = BitmapFactory.decodeResource(resources, R.drawable.redexit);
		blueExit = BitmapFactory.decodeResource(resources, R.drawable.blueexit);
		phageSprite = BitmapFactory.decodeResource(resources, R.drawable.phage);
		phageIcon = BitmapFactory.decodeResource(resources,
				R.drawable.phageicon);
		yellowExit = BitmapFactory.decodeResource(resources,
				R.drawable.yellowexit);
		greenExit = BitmapFactory.decodeResource(resources,
				R.drawable.greenexit);
	}

	public void populate() {

		if (myCells.size() <= 5) {
			currentMillis = System.currentTimeMillis();
			if (currentMillis > delay + Ticker) {
				Ticker = currentMillis;
				int type = RandomGen(5);
				switch (type) {
				case 1:
					type = 0;
					{
						int tempY = RandomGen(20);
						myCells.add(new Cells(10, startY + tempY, redCell, 1));
						break;
					}
				case 2:
					type = 1;
					{

						int tempY = RandomGen(20);
						myCells.add(new Cells(10, startY + tempY, greenCell, 2));
						break;
					}
				case 3:
					type = 2;
					{
						int tempY = RandomGen(20);
						myCells.add(new Cells(10, startY + tempY, yellowCell, 3));
						break;
					}
				case 4:
					type = 3;
					{
						int tempY = RandomGen(20);
						myCells.add(new Cells(10, startY + tempY, blueCell, 4));
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
		g.drawBitmap(phageIcon, 30, 30, null);
		Iterator<Cells> it = myCells.iterator();
		Iterator<BlockerCell> iter = myWalls.iterator();
		Iterator<Cellexit> it1 = myExits.iterator();

		while (iter.hasNext()) {
			iter.next().Draw(g);
		}

		while (it.hasNext()) {
			it.next().Draw(g);
		}

		while (it1.hasNext()) {
			it1.next().Draw(g);
		}
		if (phageCreated == true) {
			myPhage.Draw(g);
		}
		g.drawText("O2 = " + Oxygen, 40, 130, paint);

	}

	public void checkStatus()
	{
		if(Oxygen >= 20 )
		{
		
			if(currentLevel != 3)
			{
				
				Oxygen = 0;
				/*myExits.clear();
				myCells.clear();
				myWalls.clear();*/
				myExits = new ArrayList<Cellexit>();
				myCells = new ArrayList<Cells>();
				myWalls = new ArrayList<BlockerCell>();
				//clear arrays before loading level
				//Display Inter-level educational information - wait(5000)
				currentNum = 0;
				xPos = 25;
				yPos = 25;
				currentLevel++;
				LoadLevel();
			
			}
				
		}
	}
	
	public void Update() {
		
		checkStatus();
		populate();
		Iterator<Cells> it = myCells.iterator();

		while (it.hasNext()) {
			iterator1 = it.next();
			iterator1.spriteUpdate(System.currentTimeMillis());
			iterator1.update();
			
			if(phageCreated)
			{
				if(iterator1.getSickled())
				{
					if(myPhage.checkCollision(iterator1))
					{
						it.remove();
						Oxygen += 5;
						//myPhage = null;
						phageCreated = false; 
					}
				}
			}
			Iterator<Cellexit> it2 = myExits.iterator();

			while (it2.hasNext()) {
				iterator2 = it2.next();

				collision = iterator2.checkCollision(iterator1);
				iterator2.spriteUpdate(System.currentTimeMillis());
				if (collision == true) {
					collision = false;
					if (iterator2.getType() != iterator1.getType()) {
						it.remove();
						Oxygen -= 20;
						// Decrease Oxygen
					} else {
						it.remove();
						Oxygen += 10;
						// increase oxygen
					}

					break;
				}
			}

			Iterator<BlockerCell> it3 = myWalls.iterator();
			{
				while (it3.hasNext()) {

					if (it3.next().checkCollision(iterator1)) {
						
						Oxygen -= 30;
						iterator1.setCollision(sickleCell); // Pass in sickle
															// cell sprite.
					}
				}

			}

			if (phageCreated) {
				myPhage.spriteUpdate(System.currentTimeMillis());
			}
		}
	}

	public boolean pressed(int x, int y) 
	{
		{
			if (x <= 60 && x >= 0 && y <= 60 && y >= 0 && phageCreated == false)
			{
				phageCreated = true;
				myPhage = new phage(10, startY, phageSprite);
				}
			
			if(phageCreated)
			{
				if(myPhage.checkPos(x,y) == true )
					phageSelected = true;
					else{
						phageSelected = false; 
					}
			}
				


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
		if (currentCell != null) {
			if (currentCell.checkSickled() == false) {
				currentCell.Move(mX, mY);
			}
		}
		if(phageSelected)
		{
			myPhage.Move(mX,mY);
		}
	}

	public boolean released() {

		currentCell = null;
		phageSelected = false;
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
