package com.cs279.ShapeWorld;



import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShapeEngine extends GameEngine {

	private static final int port = 6060;
	
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
		//setController(new KeyboardController(getGameSurface()));
		setController(new KeyboardController(getGameSurface()));
		
		final Timeline gameLoop = getGameLoop();
		level.loadFromXml("/level.xml");
		
	}
	
	

}