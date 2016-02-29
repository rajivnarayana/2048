package com.webileapps.two048;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
	
	ISwipeListener listener;
	
	public SwipeGestureListener(ISwipeListener listener) {
		this.listener = listener;
	}
	
	public void setSwipeListener(ISwipeListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (velocityX ==0 || velocityY == 0) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}
		
		if (Math.abs(velocityX) > Math.abs(velocityY)) {
			if (velocityX > 0) {
				this.listener.onSwipe(Direction.RIGHT);
			} else {
				this.listener.onSwipe(Direction.LEFT);
			}
		} else {
			if (velocityY > 0) {
				this.listener.onSwipe(Direction.DOWN);
			} else {
				this.listener.onSwipe(Direction.UP);
			}
		}
		return super.onFling(e1, e2, velocityX, velocityY);
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}
	
	public interface ISwipeListener {
		public void onSwipe(int direction);
	}
}