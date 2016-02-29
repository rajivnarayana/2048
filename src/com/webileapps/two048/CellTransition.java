package com.webileapps.two048;

public class CellTransition {
	public static final int TYPE_NONE = 0;
	public static final int TYPE_APPEAR = 1;
	public static final int TYPE_DISAPPEAR = 2;
	public static final int TYPE_INCREMENT = 3;
	int transitionType;
	int placesMoved;
}
