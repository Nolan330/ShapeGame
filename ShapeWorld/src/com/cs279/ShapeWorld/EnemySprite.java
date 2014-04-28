package com.cs279.ShapeWorld;

import com.cs279.ShapeWorld.EnemySprite.EnemyType;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class EnemySprite extends Sprite {
	
	public enum EnemyType {
		RED, BLUE
	}

	@XStreamOmitField
	private int startX;
	@XStreamOmitField
	private boolean directionR = true;
	@XStreamOmitField
	private boolean randomPath = false;
	
	//Blue and Red enemies have different properties.
	//They also are destroyed in different manners.
	@XStreamOmitField
	private EnemyType etype = EnemyType.RED;
	
	private int speed;
	private int range;
	
	public EnemySprite(String imageLocation, int x, int y, int speed, int range, EnemyType et) {
		super(imageLocation, x, y);
		initialize();
		this.speed = speed;
		this.range = range;
		this.etype = et;
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
		if(randomPath) {
			double r = Math.random();
			//if randomized turns, 5% chance of turn at any given time. 
			directionR = r > .95 ? !directionR : directionR; 
		}
		if(trueX > startX + range) {
			directionR = false;
		} else if(trueX < startX) {
			directionR = true;
		}
		if(directionR) {
			trueX += speed;
		} else {
			trueX -= speed;
		}
		super.update(ge);
	}
	
	public void setRange(int r) {
		if(r > 0)
			range = r;
	}
	
	public int getRange() {
		return range;
	}
	
	public void setSpeed(int s) {
		if(s > 0)
			speed = s;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setPath(boolean random) {
		randomPath = random;
	}
	
	public EnemyType getEnemyType() {
		return etype;
	}

	public void setEnemyType(EnemyType e) {
		etype = e;
	}
}
