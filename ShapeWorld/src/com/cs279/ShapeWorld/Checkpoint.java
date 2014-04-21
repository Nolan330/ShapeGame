package com.cs279.ShapeWorld;

public class Checkpoint extends Sprite {
	public Checkpoint(String imageLocation, int x, int y) {
		super(imageLocation, x, y);
	}
	
	private Object readResolve() {
		initialize();
		return this;
	}
}
