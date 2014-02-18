package com.cs279.ShapeWorld;


public class StageCamera {
	private int x;
	private int y;
	private int width;
	private int height;
	
	public StageCamera(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
	}
	
	public boolean isVisible(Sprite n) {
		return checkXCoordinate(n.getXCoord()) && checkYCoordinate(n.getYCoord());
	}
	
	public boolean checkXCoordinate(double coord) {
		return x < coord && (width + x) > coord;
	}
	
	public boolean checkYCoordinate(double coord) {
		return y < coord && (y + height) > coord;
	}
	
	public double getVisibleX(double coord) {
		return coord - x;
	}
	
	public double getVisibleY(double coord) {
		return coord;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void augmentX(int delta) {
		this.x += delta;
	}
	
	public void augmentY(int delta) {
		this.y += delta;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
