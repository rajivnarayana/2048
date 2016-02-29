package com.webileapps.two048;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MyActivity extends ActionBarActivity {

	FrameLayout layout;
	
	TextView child1;
	TextView child2;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = new FrameLayout(this);
		layout.setBackgroundColor(Color.LTGRAY);
		LayoutTransition transition = new LayoutTransition();
//		layout.setLayoutTransition(transition);
		this.addViews();
		setContentView(layout);
	};
	
	private void addViews() {
		child1 = new TextView(this);
		child1.setBackgroundColor(Color.BLUE);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(200, 200);
		lp.setMargins(50, 100, 0, 0);
		layout.addView(child1, lp);
		
		child2 = new TextView(this);
		child2.setBackgroundColor(Color.BLUE);
		FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(200, 200);
		lp2.setMargins(400, 100, 0, 0);
		layout.addView(child2, lp2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add("Animate");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		this.mergeTwoCells();
		return true;
	}
	
	private void animateSubviews() {
		LayoutTransition layoutTransition = layout.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        View child = layout.getChildAt(0);
        MarginLayoutParams lp =  (MarginLayoutParams) child.getLayoutParams();
        lp.setMargins(lp.leftMargin, lp.topMargin+200, lp.rightMargin, lp.bottomMargin);
        child.requestLayout();
	}
	
	
	//TODO: Change to dips per second
	private static final int ANIMATION_VELOCITY = 500; //50 pxs per second.
	
	private void mergeTwoCells() {
		
		final MarginLayoutParams lp1 = (MarginLayoutParams) child1.getLayoutParams();		
		TranslateAnimation animation1 = new TranslateAnimation(0, -lp1.leftMargin, 0, 0);
		animation1.setInterpolator(new LinearInterpolator());
		animation1.setFillAfter(true);
		animation1.setDuration(lp1.leftMargin * 1000/ANIMATION_VELOCITY);
		
		
		MarginLayoutParams lp2 = (MarginLayoutParams) child2.getLayoutParams();
		TranslateAnimation animation2 = new TranslateAnimation(0, -lp2.leftMargin, 0, 0);
		animation2.setInterpolator(new LinearInterpolator());
//		animation2.setFillAfter(true);
		animation2.setDuration(lp2.leftMargin * 1000/ANIMATION_VELOCITY);
		
		animation2.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				lp1.leftMargin = 0;
				
				child1.setLayoutParams(lp1);
				layout.removeView(child2);

//				mergeCells(child1, child2);
			}
		});
		child1.startAnimation(animation1);
		child2.startAnimation(animation2);
	}
	
	private void mergeCells(View childToRemain, View childToRemove) {
	}
}
