package com.cs279.ShapeWorld;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.thoughtworks.xstream.XStream;

import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShapeEngine extends GameEngine {


	public ShapeEngine(int fps, String title) {
		super(fps, title);
		level.setGameEngine(this);
	}

	
	@Override
	public void initialize(Stage primaryStage) {
		primaryStage.setTitle(getWindowTitle());
		
		setSceneNodes(new Group());
		setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT));
		primaryStage.setScene(getGameSurface());
		setController(new KeyboardController(getGameSurface()));
		
		final Timeline gameLoop = getGameLoop();
		level.loadFromXml("/level.xml");
		
	}
	
	

}