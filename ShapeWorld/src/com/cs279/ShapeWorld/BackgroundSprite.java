package com.cs279.ShapeWorld;

public class BackgroundSprite extends Sprite {
	
	public BackgroundSprite(String imageLocation, int x, int y) {
		super(imageLocation, x, y);
	}

	private Object readResolve() {
		initialize();
		return this;
	}
	
	public void update(GameEngine ge) {

	}
	
	public boolean collision(Sprite other) {
		return false;
	}

}
