package com.cs279.ShapeWorld;

import com.sun.javafx.geom.Rectangle;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShapeEngine extends GameEngine {
	
	private final static int WIDTH = 640;
	private final static int HEIGHT = 580;

	public ShapeEngine(int fps, String title) {
		super(fps, title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(Stage primaryStage) {
		primaryStage.setTitle(getWindowTitle());
		
		setSceneNodes(new Group());
		setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT));
		primaryStage.setScene(getGameSurface());
		setController(new KeyboardController(getGameSurface()));
		
		final Timeline gameLoop = getGameLoop();
		
		MainCharacter mc = new MainCharacter();
		spriteManager.addSprite(mc);
		

		getSceneNodes().getChildren().add(0, mc.getNode());
	}
	

}
