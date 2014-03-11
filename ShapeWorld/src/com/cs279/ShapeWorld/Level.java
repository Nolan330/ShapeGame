package com.cs279.ShapeWorld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class Level {
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private String levelName;
	private GameEngine gameEngine;
	private XStream lvlReader;
	
	public Level(String name) {
		levelName = name;
		createSerializer();
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


	@SuppressWarnings("unchecked")
	/*
	 * Loads a list of actors/sprites and adds them to the level. 
	 * Manually adds them to the Scene after they are loaded into an ArrayList<Sprite>.
	 * String filename - loads from resources
	 */
	public void loadFromXml(String filename) {
		FileReader fr;
		try {
			fr = new FileReader(new File(getClass().getResource(filename)
					.getPath()));
			sprites = (ArrayList<Sprite>) lvlReader.fromXML(fr);
		} catch (FileNotFoundException e) {
		}
		for (Sprite s : sprites) {
			gameEngine.getSceneNodes().getChildren().add(s.getNode());
		}
	}
	
	private void createSerializer() {
		lvlReader = new XStream();
		lvlReader.alias("sprite",com.cs279.ShapeWorld.Sprite.class);
		lvlReader.alias("maincharacter",com.cs279.ShapeWorld.MainCharacter.class);
	}

}
