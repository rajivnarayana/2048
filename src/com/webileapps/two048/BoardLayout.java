package com.webileapps.two048;

import android.content.Context;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.webileapps.two048.SwipeGestureListener.ISwipeListener;

public class BoardLayout extends FrameLayout implements BoardModel.IBoardElementsListener {

	public static final int NUM_OF_COLUMNS = 4;
	
	GestureDetector gestureDectector;
	
	TextView textView;
	
	public BoardLayout(Context context, ISwipeListener swipeListener) {
		super(context);
		
		textView = new TextView(context);
		textView.setBackgroundColor(Color.WHITE);
		this.addView(textView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		SwipeGestureListener listener = new SwipeGestureListener(swipeListener);
		gestureDectector = new GestureDetector(context, listener);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return gestureDectector.onTouchEvent(ev);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDectector.onTouchEvent(event);
	}

	private void debugLog(Object string) {
		android.util.Log.v("BoardLayout", string.toString());
	}
	
	public void transitionElements(int[][] elementsFrom, int[][] elementsTo) {
		
		//Temporarily this is an implementation of a string.
		
		StringBuffer textBuffer = new StringBuffer("\n\n\n");
		for(int i=0; i< elementsTo.length; i++) {
			textBuffer.append("\t\t\t\t\t");
			for(int j=0; j< elementsTo[0].length; j++) {
				textBuffer.append(elementsTo[i][j]+"\t\t\t\t\t\t");
			}
			textBuffer.append("\n\n\n");
		}
		textView.setText(textBuffer.toString());
	}

	@Override
	public void transitionElements(int[][] elementsTo,
			CellTransition[][] transitions, int direction) {
		// TODO Auto-generated method stub
		
	}
}
