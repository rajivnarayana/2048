package com.webileapps.two048;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.webileapps.two048.BoardModel.IGameEventsListener;
import com.webileapps.two048.SwipeGestureListener.ISwipeListener;

//Controller
public class MainActivity extends ActionBarActivity implements IGameEventsListener, ISwipeListener {

	//Model
	BoardModel boardModel;
	
	//View
	BoardLayout boardLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boardLayout = new BoardLayout(this, this);
		boardLayout.setBackgroundColor(Color.RED);
		setContentView(boardLayout);
		boardModel = new BoardModel(4, boardLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return null;
		}
	}

	@Override
	public int newHighestNumberReached(int newLargestCell) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void numberOfMovesChanged(int numberOfMoves, int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSwipe(int direction) {
		
		switch (direction) {
		case Direction.LEFT:
			this.debugLog("moving left");
			break;
		case Direction.RIGHT:
			this.debugLog("moving right");
			break;
		case Direction.UP:
			this.debugLog("moving up");
			break;
		case Direction.DOWN:
			this.debugLog("moving down");
			break;
		default:
			break;
		}
		this.boardModel.moveInDirection(direction);
	}
	
	private void debugLog(Object message) {
		android.util.Log.v("MainActivity", message.toString());
	}
	

}
