package com.webileapps.two048;

public class BoardModel {

	private int[][] array;
	private int numberOfRows;
	
	private int highestNumberReached = 0;
	
	//If we want 2 to show up all the time add MAX_SQUARE_NUMBER_ADDED = 1
	//If we want 2 and 4 to show up replace MAX_SQUARE_NUMBER_ADDED = 2
	//If we want 2,4 and 8 to show up replace MAX_SQUARE_NUMBER_ADDED = 3
	private static final int MAX_SQUARE_NUMBER_ADDED = 2;
	
	private IBoardElementsListener boardListener;
	private IGameEventsListener gameEventsListener;
	
	public BoardModel(int numberOfSquaresOnEachSide, IBoardElementsListener listener) {

		this.numberOfRows = numberOfSquaresOnEachSide;
		array = new int[numberOfSquaresOnEachSide][numberOfSquaresOnEachSide];
		for(int i=0; i<numberOfSquaresOnEachSide; i++) {
			for(int j=0; j<numberOfSquaresOnEachSide; j++) {
				//0 denotes an empty cell.
				array[i][j] = 0;
			}
		}
		
		this.boardListener = listener;
				
		this.moveInDirection(Direction.LEFT);
	}
	
	public BoardModel(GameState gameState, IBoardElementsListener listener) {
		this.array = gameState.array;
		this.numberOfRows = gameState.numberOfRows;
		this.boardListener = listener;
	}
	
	private int[][] copyArray(int[][] givenArray) {
		int [][]copyArray = new int[givenArray.length][givenArray[0].length];
		for(int i=0; i<copyArray.length; i++) {
			for (int j=0; j<copyArray[i].length; j++) {
				copyArray[i][j] = givenArray[i][j];
			}
		}
		return copyArray;
	}
	
	/**
	 * Adds a number to an empty cell.
	 * The numbers are usually 2 or 4.
	 */
	private void addNumberToAnEmptyCell(CellTransition[][] transitions) {
		int emptyCellsCount = 0;
		//count the number of empty cells.
		for(int i=0; i<this.numberOfRows; i++) {
			for(int j=0; j<this.numberOfRows; j++) {
				if(array[i][j] == 0) {
					emptyCellsCount++;
				}
			}
		}
		
		//Pick a random empty cell.
		
		int randomCell = (int) (emptyCellsCount * Math.random());
		
		int randomSquare = 1 + (int) (MAX_SQUARE_NUMBER_ADDED * Math.random());
		int randomNumber = 1 << randomSquare;
		
		this.setHighestNumberReached(Math.max(this.highestNumberReached, randomNumber));
		
		boolean placedRandom = false;
		//Place it in a of the cell.
		for(int i=0, cell = 0; i<this.numberOfRows; i++) {
			for(int j=0; j<this.numberOfRows; j++, cell++) {
				if(array[i][j] != 0) {
					continue;
				}
				if(cell < randomCell) {
					cell++;
					continue;
				}
				this.newCellAt(i, j, randomNumber, transitions);
				placedRandom = true;
				break;
			}
			if (placedRandom)
				break;
		}
	}
	
	private void notifyBoardListeners(CellTransition[][] transitions, int direction) {
		if (this.boardListener == null) {
			return;
		}
		
		this.boardListener.transitionElements(copyArray(array), transitions, direction);
	}
	
	private void notifyNewHighestNumberReached(int number) {
		if (this.gameEventsListener == null) {
			return;
		}
		this.gameEventsListener.newHighestNumberReached(number);
	}
	
	public void setHighestNumberReached(int highestNumberReached) {
		if(highestNumberReached == this.highestNumberReached) {
			return;
		}
		this.highestNumberReached = highestNumberReached;
		this.notifyNewHighestNumberReached(highestNumberReached);
	}
	
	//rearranges the pieces based on the direction.
	public void moveInDirection(int direction) {
		int[][]oldArray = this.copyArray(array);
		
		CellTransition [][]translationArray = new CellTransition[this.numberOfRows][this.numberOfRows];
		
		//sumup all the elements in the given direction
		if (direction == Direction.LEFT) {
			for(int row = 0; row < numberOfRows; row++) {
				for(int col = 0; col < numberOfRows; col++) {
					//see if there are empty cells and stuff needs to be moved.
					if(array[row][col] == 0) {
						//look ahead and move elements
						for (int index = col+1; index < numberOfRows; index++) {
							if(array[row][index] != 0) {
								this.moveCell(row, index, row, col, translationArray);
								break;
							}
						}
					}
					
					//check if the next element is same so we can double current.
					
					if (col == numberOfRows - 1) {
						continue;
					}
					
					if (array[row][col] == 0) {
						continue;
					}
					
					for(int index=col+1; index<numberOfRows; index++) {
						
						if (array[row][index] == 0) {
							continue;
						}

						if (array[row][col] == array[row][index]) {
							this.mergeCell(row, index, row, col, translationArray);
						}
						break;
					}
				}
			}
		} else if (direction == Direction.RIGHT) {
			for(int row = 0; row < numberOfRows; row++) {
				for(int col = numberOfRows-1; col >= 0; col--) {
					//see if there are empty cells and stuff needs to be moved.
					if(array[row][col] == 0) {
						//look ahead and move elements
						for (int index = col-1; index >= 0; index--) {
							if(array[row][index] != 0) {
								this.moveCell(row, index, row, col, translationArray);
								break;
							}
						}
					}
					
					//check if the next element is same so we can double current.
					
					if (col == 0) {
						continue;
					}
					
					if (array[row][col] == 0) {
						continue;						
					}

					for (int index = col - 1; index >= 0; index--) {
						if (array[row][index] == 0) {
							continue;
						}

						if (array[row][col] == array[row][index]) {
							this.mergeCell(row, index, row, col, translationArray);
						}
						break;
					}
				}
			}			
		} else if (direction == Direction.UP) {
			for(int col = 0; col < numberOfRows; col++) {
				for(int row = 0; row < numberOfRows; row++) {
					//see if there are empty cells and stuff needs to be moved.
					if(array[row][col] == 0) {
						//look ahead and move elements
						for (int index = row+1; index < numberOfRows; index++) {
							if(array[index][col] != 0) {
								this.moveCell(index, col, row, col, translationArray);
								break;
							}
						}
					}
					
					//check if the next element is same so we can double current.
					
					if (row == numberOfRows-1) {
						continue;
					}
					
					if (array[row][col] == 0) {
						continue;						
					}

					for (int index = row+1; index<numberOfRows; index++) {
						if (array[index][col] == 0) {
							continue;
						}
						if (array[row][col] == array[index][col]) {
							this.mergeCell(index, col, row, col, translationArray);
						}
						break;
					}
				}
			}
		} else if (direction == Direction.DOWN) {
			for(int col = 0; col < numberOfRows; col++) {
				for(int row = numberOfRows-1; row >= 0; row--) {
					//see if there are empty cells and stuff needs to be moved.
					if(array[row][col] == 0) {
						//look ahead and move elements
						for (int index = row-1; index >= 0; index--) {
							if(array[index][col] != 0) {
								this.moveCell(index, col, row, col, translationArray);
								break;
							}
						}
					}
					
					//check if the next element is same so we can double current.
					
					if (row == 0) {
						continue;
					}
					
					if (array[row][col] == 0) {
						continue;						
					}

					for (int index = row-1; index>=0; index--) {
						if (array[index][col] == 0) {
							continue;
						}

						if (array[row][col] == array[index][col]) {
							this.mergeCell(index, col, row, col, translationArray);
						}
						break;
					}
				}
			}
		}
		
		this.addNumberToAnEmptyCell(translationArray);
		
		this.notifyBoardListeners(translationArray, direction);
		
	}
	
	private void moveCell(int rowFrom, int colFrom, int rowTo, int colTo, CellTransition[][] translations) {
		array[rowTo][colTo] = array[rowFrom][colFrom];
		array[rowFrom][colFrom] = 0;
		if(translations[rowFrom][colFrom] == null) {
			translations[rowFrom][colFrom] = new CellTransition();
		}
		CellTransition cellTransition = translations[rowFrom][colFrom];
		cellTransition.placesMoved = colFrom-colTo == 0?rowTo-rowFrom:colTo-colFrom;
		cellTransition.transitionType = CellTransition.TYPE_NONE;
	}
	
	private void mergeCell(int rowFrom, int colFrom, int rowTo, int colTo, CellTransition[][] translations) {
		array[rowTo][colTo] <<= 1;
		array[rowFrom][colFrom] = 0;

		if(translations[rowFrom][colFrom] == null) {
			translations[rowFrom][colFrom] = new CellTransition();
		}
		CellTransition cellTransition = translations[rowFrom][colFrom];
		cellTransition.placesMoved = colFrom-colTo == 0?rowTo-rowFrom:colTo-colFrom;
		cellTransition.transitionType = CellTransition.TYPE_DISAPPEAR;

		if(translations[rowTo][colTo] == null) {
			translations[rowTo][colTo] = new CellTransition();
		}
		CellTransition cellTransition2 = translations[rowTo][colTo];
		cellTransition2.transitionType = CellTransition.TYPE_INCREMENT;
	}
	
	private void newCellAt(int rowAt, int colAt, int value, CellTransition[][] translations) {
		array[rowAt][colAt] = value;

		if(translations[rowAt][colAt] == null) {
			translations[rowAt][colAt] = new CellTransition();
		}
		
		CellTransition cellTransition = translations[rowAt][colAt];
		cellTransition.transitionType = CellTransition.TYPE_APPEAR;
	}
	
	//An interface to listen to find out about the changes in various pieces
	public interface IBoardElementsListener {
		public void transitionElements(int[][] elementsTo, CellTransition[][] transitions, int direction);
	}
	
	public void setBoardElementsListener(IBoardElementsListener elementsListener) {
		this.boardListener = elementsListener;
	}
	
	public interface IGameEventsListener {
		public int newHighestNumberReached(int newLargestCell);
		public void gameOver();
		
		//not used as of now.
		public void numberOfMovesChanged(int numberOfMoves, int score);
	}
	
	public void setGameEventsListener(IGameEventsListener gameEventsListener) {
		this.gameEventsListener = gameEventsListener;
	}
	
	
	public static class GameState {
		int [][]array;
		int numberOfRows;
		int numberOfMoves;
	}
	
}
