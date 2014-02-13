package com.cs279.ShapeWorld;

import java.awt.List;
import java.util.ArrayList;

public class SpriteManager {
	private final ArrayList<Sprite> gameActors = new ArrayList<Sprite>();
	
	public void addSprite(Sprite s) {
		gameActors.add(s);
	}
	
	public ArrayList<Sprite> getAllSprites() {
		return gameActors;
	}
	
}
