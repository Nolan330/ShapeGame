package com.cs279.ShapeWorld;



import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class ShapeEngine extends GameEngine {


	public ShapeEngine(int fps, String title) {
		super(fps, title);
		// TODO Auto-generated constructor stub
	}

	
	/*
	 * Realistically, this function is going to read the current level from a variable or file. 
	 * After reading the level, it will load the level/map file via XML or however it is done. And start game. 
	 */
	@Override
	public void initialize(Stage primaryStage) {
		primaryStage.setTitle(getWindowTitle());
		
		setSceneNodes(new Group());
		setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT));
		primaryStage.setScene(getGameSurface());
		setController(new KeyboardController(getGameSurface()));
		
		final Timeline gameLoop = getGameLoop();
		TilePane tp = new TilePane();
		tp.setHgap(0);
		int cols = (int) Math.ceil(WIDTH/100.0);
		tp.setPrefColumns(cols);
		for(int i=0; i < cols; i++) {
			tp.getChildren().add(0, new ImageView(new Image(getClass().getResourceAsStream("/grass-tile.jpg"))));
		}
		tp.setTranslateY(HEIGHT - 200);
		
		MainCharacter mc = new MainCharacter(null, 200, 280);
		spriteManager.addSprite(mc);
		
		for(int i=0; i < 10; i++) {
			ImageView cloud = new ImageView(new Image(getClass()
					.getResourceAsStream("/cloud.png")));
			int y = 0 + (int)(Math.random() * ((150 - 0) + 1));
			spriteManager.addSprite(new Sprite(cloud, i * 270, y));
			getSceneNodes().getChildren().add(0, cloud);
		}

		getSceneNodes().getChildren().add(0, mc.getNode());
		getSceneNodes().getChildren().add(0, tp);
	}
	

}
