package com.tsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurface extends SurfaceView {
	
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
			mThread.start();
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Log.d("zhl", "surface changed");
			
		}
	};
	protected boolean shouldRun = true;

	private Thread mThread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			
			while (shouldRun ) {
				if (y >= getHeight()) {
					Log.d("zhl", "over" + getHeight());
					break;
				}
				if (x >= getWidth()) {
					Log.d("zhl", "x:" + x + ",y:" + y);
					x = 0;
					y++;
				}
				Canvas canvas = mHolder.lockCanvas();
				Paint paint = new Paint();
				while (y < getHeight()) {
					x = 0;
					while (x < getWidth()) {
						int clr = x * 20 + (getHeight()-y) * 20 * getWidth();
						clr |= 0xff000000;
						paint.setColor(clr);
						canvas.drawPoint(x, y, paint);
						x++;
					}
					y++;
				}
				Log.d("zhl", "out");
				mHolder.unlockCanvasAndPost(canvas);
				x++;
			}
		}
	});
}
