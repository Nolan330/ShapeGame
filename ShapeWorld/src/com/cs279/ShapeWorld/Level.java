package com.cs279.ShapeWorld;

import java.util.ArrayList;
import java.util.List;

public class Level {
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private String levelName;
	private GameEngine gameEngine;
	
	public Level(String name) {
		levelName = name;
	}
	
	public void setGameEngine(GameEngine ge) {
		gameEngine = ge;
	}
	
	public void addSprite(Sprite s) {
		gameEngine.getSceneNodes().getChildren().add(0, s.getNode());
		sprites.add(s);
	}
	
	public List<Sprite> getAllSprites() {
		return sprites;
	}

}
