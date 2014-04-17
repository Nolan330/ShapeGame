package com.cs279.ShapeWorld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Level {
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private String levelName;
	private GameEngine gameEngine;
	private XStream lvlReader;
	
	@XStreamOmitField
	private Text debugTxt;
	
	@XStreamOmitField
	private ImageView deathScreen;
	
	@XStreamOmitField
	private MainCharacter mainCharacter;
	
	public Level(String name) {
		levelName = name;
		createSerializer();
	}

	public void setGameEngine(GameEngine ge) {
		gameEngine = ge;
	}
	
	public MainCharacter getMainCharacter() {
		return mainCharacter;
	}

	public void addSprite(Sprite s) {
		gameEngine.getSceneNodes().getChildren().add(s.getNode());
		sprites.add(s);
	}

	public List<Sprite> getAllSprites() {
		return sprites;
	}
	
	public void removeSprite(Sprite s) {
		gameEngine.getSceneNodes().getChildren().remove(s);
		sprites.remove(s);
	}
	
	public void setGameOver() {
		mainCharacter.DEAD = true;
		deathScreen.setVisible(true);
		deathScreen.toFront();
	}
	
	public void reset() {
		for(Sprite s : sprites) {
			s.DEAD = false;
		}
		mainCharacter.trueX = 200;
		mainCharacter.trueY = 280;
		mainCharacter.setStanding();
		mainCharacter.node.setVisible(true);
		deathScreen.setVisible(false);
		mainCharacter.jump(MainCharacter.State.JUMPING_UP);
		
		gameEngine.getStageCamera().setX(0);
		gameEngine.getStageCamera().setY(0);
	}
	
	public void initLevelItems() {
		debugTxt = new Text();
		debugTxt.setWrappingWidth(200);
		debugTxt.setTextAlignment(TextAlignment.JUSTIFY);
		debugTxt.setText(levelName);
		debugTxt.setTranslateX(GameEngine.WIDTH - 225);
		debugTxt.setTranslateY(25);
		gameEngine.getSceneNodes().getChildren().add(debugTxt);
		
		deathScreen = new ImageView(new Image(getClass().getResourceAsStream(
				"/DeathScreen.png")));
		deathScreen.setVisible(false);
		deathScreen.setTranslateX(0);
		deathScreen.setTranslateY(0);
		gameEngine.getSceneNodes().getChildren().add(deathScreen);
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
			if(s instanceof MainCharacter) {
				mainCharacter = (MainCharacter) s;
			}
		}
		initLevelItems();
	}
	
	private void createSerializer() {
		lvlReader = new XStream();
		lvlReader.alias("sprite",com.cs279.ShapeWorld.Sprite.class);
		lvlReader.alias("backgroundsprite", com.cs279.ShapeWorld.BackgroundSprite.class);
		lvlReader.alias("maincharacter",com.cs279.ShapeWorld.MainCharacter.class);
		lvlReader.alias("ground", com.cs279.ShapeWorld.LevelGround.class);
		lvlReader.alias("enemy", com.cs279.ShapeWorld.EnemySprite.class);
	}
	
	public void setDebugText(String txt) {
		debugTxt.setText(txt);
	}

}
