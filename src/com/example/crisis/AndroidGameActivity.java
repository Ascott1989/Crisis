package com.example.crisis;

import java.io.IOException;

import com.example.crisis.GameModel;
import com.example.crisis.AndroidGameActivity.FastRenderView;



import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class AndroidGameActivity extends Activity implements OnTouchListener {
	FastRenderView renderView;
	MediaPlayer myMusic;
	GameModel model;
	int frameRate;
	Display display;
	SoundPool pool;
	AssetManager assetManager;
	Boolean NoMusic = false;
	boolean pressed = false;
	//Context mycontext;
	

	

	

	public void onCreate(Bundle savedInstanceState) {
		
		 
		 
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		pool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		
		
		frameRate = 60;
		model = new GameModel(getResources(), this.getApplicationContext());
		
		display = getWindowManager().getDefaultDisplay();
		model.setScreenHeight(display.getHeight());
		model.setScreenWidth(display.getWidth());
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		renderView = new FastRenderView(this);
		renderView.setOnTouchListener(this);
		setContentView(renderView);
		//model.Init();
		
		assetManager = getAssets();
		/*try {
			//desc = assetManager.openFd("hit.wav");
			//model.LoadSounds(desc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		//hitSound = pool.load(desc, 1);
		

		myMusic = new MediaPlayer();
		try {
			AssetFileDescriptor descriptor = assetManager
					.openFd("musicloop.mp3");
			myMusic.setDataSource(descriptor.getFileDescriptor(),
					descriptor.getStartOffset(), descriptor.getLength());
			myMusic.prepare();
			myMusic.setLooping(true);
			myMusic.start();

		} catch (IOException e) {
			// Toast textView.setText("Couldn't load music file, " +
			// e.getMessage());
			NoMusic = true;
			myMusic = null;
		}

	}
	
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouchEvent(event);
		// float playerX = model.getPlayerPosX();
		// float playerY = model.getPlayerPosY();
		/*float ballPosx = model.getPlayerPosX();
		float ballPosy = model.getPlayerPosY();*/
		//boolean selected = false;
		
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:{
			int X = (int)event.getX();
			int Y = (int)event.getY();
		model.pressed(X, Y);
		break;
		}
		
		
		case MotionEvent.ACTION_MOVE:{
			float mX = event.getX();
			float mY = event.getY();
			model.move(mX,mY);
			break;
		}
		
		case MotionEvent.ACTION_UP:{
			model.released();
			break;
		}
		
		}
		
		
		
			
		return true;

	}
	
	
	protected void onResume() {
		super.onResume();
		renderView.resume();
		if (!NoMusic)
			myMusic.start();
		else {
			Toast.makeText(this, "NoMusic", 10).show();
		}

	}

	protected void onPause() {
		super.onPause();
		renderView.pause();
		if (!NoMusic)
			myMusic.pause();
	}

	class FastRenderView extends SurfaceView implements Runnable {
		Thread renderThread = null;
		SurfaceHolder holder;
		volatile boolean running = false;

		public FastRenderView(Context context) {
			super(context);
			holder = getHolder();
			
			

		}

		public void resume() {
			running = true;
			renderThread = new Thread(this);
			renderThread.start();

		}

		public void pause() {
			running = false;
			while (true) {
				try {
					renderThread.join();
					break;
				} catch (InterruptedException e) {
					// retry
				}
			}
			renderThread = null;
		}

		public void run() {
			while (running) {
				if (!holder.getSurface().isValid())
					continue;
				Canvas canvas = holder.lockCanvas();
				drawSurface(canvas);
				holder.unlockCanvasAndPost(canvas);
				int i = (int) 1000.0 / frameRate;
				try {
					Thread.sleep(i);
					//model.updatePhysics(i / 1000.f);
				} catch (InterruptedException e) {

				}
			}
		}

		private void drawSurface(Canvas canvas) {
			canvas.drawRGB(255, 0, 0);
			model.Update();
			model.draw(canvas);
			
		}

	}
}
