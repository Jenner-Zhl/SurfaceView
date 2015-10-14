package com.tsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurface extends SurfaceView {
	

	private Thread mThread;
	private SurfaceHolder mHolder;
	private int x, y;

	public MySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mHolder = getHolder();
		mHolder.addCallback(mCallback);
		Log.d("zhl", "------");
	}


	private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			shouldRun = false;
			Log.d("zhl", "surface destroyed");
			
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			Log.d("zhl", "surface created");
			newThread();
			mThread.start();
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Log.d("zhl", "surface changed");
			
		}
	};
	protected boolean shouldRun = true;

	private void newThread() {
		x = y = 0;
		shouldRun = true;
		if(mThread != null) {
			Log.d("zhl", "thread state:" + mThread.getState());
		}
		mThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Canvas canvas = mHolder.lockCanvas();
				while (shouldRun ) {
					if (y >= 255) {
						Log.d("zhl", "over");
						break;
					}
					if (x >= 255) {
						Log.d("zhl", "x:" + x + ",y:" + y);
						x = 0;
						y++;
					}
					
					Paint paint = new Paint();
					while (x < 255) {
						int clr = (x << 8) + y;
						clr |= 0xff7f0000;
						paint.setColor(clr);
						canvas.drawPoint(x, y, paint);
						x++;
					}
					
					
				}
				mHolder.unlockCanvasAndPost(canvas);
				
			}
		});
	}
	
}
