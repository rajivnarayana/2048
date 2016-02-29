package com.webileapps.two048;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.webileapps.two048.BoardModel.GameState;
import com.webileapps.two048.BoardModel.IBoardElementsListener;

public class BoardModelTests {

	@Test
	public void testMoveLeft() {
		
		int [][]array = new int[][]{
				{2,0,0,2},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		};
		
		GameState gameState = new BoardModel.GameState();
		gameState.numberOfRows = array.length;
		gameState.array = array;
		BoardModel boardModel = new BoardModel(gameState, new IBoardElementsListener() {

			@Override
			public void transitionElements(int[][] elementsTo,
					CellTransition[][] transitions, int direction) {
				assertEquals(4, elementsTo[0][0]);
				assertEquals(CellTransition.TYPE_INCREMENT,transitions[0][0].transitionType);
				assertEquals(CellTransition.TYPE_DISAPPEAR,transitions[0][3].transitionType);
			}
			
		});
		
		boardModel.moveInDirection(Direction.LEFT);
	}
	
	@Test
	public void testMoveLeft1() {
		
		int [][]array = new int[][]{
				{4,0,4,0},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		};
		
		GameState gameState = new BoardModel.GameState();
		gameState.numberOfRows = array.length;
		gameState.array = array;
		BoardModel boardModel = new BoardModel(gameState, new IBoardElementsListener() {
			
			@Override
			public void transitionElements(int[][] elementsTo,
					CellTransition[][] transitions, int direction) {
				assertEquals(8, elementsTo[0][0]);
			}
		});
		
		boardModel.moveInDirection(Direction.LEFT);
	}
	
	@Test
	public void testMoveLeft2() {
		
		int [][]array = new int[][]{
				{4,4,0,8},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		};
		
		GameState gameState = new BoardModel.GameState();
		gameState.numberOfRows = array.length;
		gameState.array = array;
		BoardModel boardModel = new BoardModel(gameState, new IBoardElementsListener() {
			
			@Override
			public void transitionElements(int[][] elementsTo,
					CellTransition[][] transitions, int direction) {
				assertEquals(8, elementsTo[0][0]);
				assertEquals(8, elementsTo[0][1]);
			}
		});
		
		boardModel.moveInDirection(Direction.LEFT);
	}
	
	@Test
	public void testMoveLeft3() {
		
		int [][]array = new int[][]{
				{4,2,4,0},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		};
		
		GameState gameState = new BoardModel.GameState();
		gameState.numberOfRows = array.length;
		gameState.array = array;
		BoardModel boardModel = new BoardModel(gameState, new IBoardElementsListener() {
			
			@Override
			public void transitionElements(int[][] elementsTo,
					CellTransition[][] transitions, int direction) {
				assertEquals(4, elementsTo[0][0]);
				assertEquals(2, elementsTo[0][1]);
				assertEquals(4, elementsTo[0][2]);
			}
		});
		
		boardModel.moveInDirection(Direction.LEFT);
	}

	@Test
	public void testMoveLeft4() {
		
		int [][]array = new int[][]{
				{0,2,0,2},
				{0,0,0,0},
				{0,0,0,0},
				{0,0,0,0}
		};
		
		GameState gameState = new BoardModel.GameState();
		gameState.numberOfRows = array.length;
		gameState.array = array;
		BoardModel boardModel = new BoardModel(gameState, new IBoardElementsListener() {
			
			@Override
			public void transitionElements(int[][] elementsTo,
					CellTransition[][] transitions, int direction) {
				assertEquals(4, elementsTo[0][0]);
				assertEquals(CellTransition.TYPE_INCREMENT,transitions[0][0].transitionType);
				assertEquals(CellTransition.TYPE_DISAPPEAR,transitions[0][3].transitionType);

			}
		});
		
		boardModel.moveInDirection(Direction.LEFT);
	}

}
