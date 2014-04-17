package com.cs279.ShapeWorld;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class EnemySprite extends Sprite {

	@XStreamOmitField
	private int startX;
	@XStreamOmitField
	private boolean directionR = true;
	
	private int range;
	
	public EnemySprite(String imageLocation, int x, int y) {
		super(imageLocation, x, y);
	}
	
	private Object readResolve() {
		initialize();
		startX = trueX;
		return this;
	}
	
	public boolean collision(Sprite other) {
		if(super.collision(other)) {
			other.DEAD = true;
		}
		return other.DEAD;
	}
	
	public void update(GameEngine ge) {
		if(trueX > startX + range) {
			directionR = false;
		} else if(trueX < startX) {
			directionR = true;
		}
		if(directionR) {
			trueX += 2;
		} else {
			trueX -= 2;
		}
		super.update(ge);
	}
}
