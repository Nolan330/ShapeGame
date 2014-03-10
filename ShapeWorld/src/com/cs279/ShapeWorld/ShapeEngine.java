package com.cs279.ShapeWorld;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class ShapeEngine extends GameEngine {
	
	private static final Map<Integer, String> TILE_MAP;
    static {
        Map<Integer, String> tileMap = new HashMap<Integer, String>();
        tileMap.put(0, "/gray_tile_full.png");
        tileMap.put(1, "/black_tile_full.png");
        tileMap.put(2, "/red_line.png");
        tileMap.put(3, "/blue_tile_basic.png");
        tileMap.put(4, "/light_green_tile_full.png");
        tileMap.put(5, "/light_green_tile_basic.png");
        TILE_MAP = Collections.unmodifiableMap(tileMap);
    }
    private static final Map<Integer, String> CLOUD_MAP;
    static {
    	 Map<Integer, String> cloudMap = new HashMap<Integer, String>();
         cloudMap.put(0, "/cloud_0_small.png");
         cloudMap.put(1, "/cloud_1_small.png");
         cloudMap.put(2, "/cloud_2_small.png");
         cloudMap.put(3, "/clouds_0_small.png");
         cloudMap.put(4, "/clouds_1_small.png");
         cloudMap.put(5, "/clouds_r2_small.png");
         CLOUD_MAP = Collections.unmodifiableMap(cloudMap);
    }
    
    private Random sceneGenerator = new Random();

	public ShapeEngine(int fps, String title) {
		super(fps, title);
		level.setGameEngine(this);
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
		
		ArrayList<TilePane> tilePanes = new ArrayList<TilePane>();
		
		//this should be smarter about drawing in with HEIGHT...to tired to do it now
		for(int rows = 0; rows < 4; rows++) {
			TilePane tp = new TilePane();
			tp.setHgap(0);
			int cols = (int) Math.ceil(WIDTH/50.0);
			tp.setPrefColumns(cols);
			for(int i=0; i < cols; i++) {
				ImageView nextTile = new ImageView(
						new Image(getClass().getResourceAsStream(TILE_MAP.get(sceneGenerator.nextInt(6)))));
				nextTile.setFitWidth(50);
				nextTile.setFitHeight(50);
				tp.getChildren().add(0, nextTile);
			}
			tp.setTranslateY(HEIGHT - 200 + rows*50);
			tilePanes.add(tp);
		}
		
		
		MainCharacter mc = new MainCharacter("/main-animation.png", 200, 280);
		level.addSprite(mc);
		
		for(int i=0; i < 25; i++) {
			//ImageView cloud = new ImageView(new Image(getClass()
			//		.getResourceAsStream())));
			//cloud.setViewport(new Rectangle2D(50, 0, 150, 200));
			int y = 0 + (int)(Math.random() * ((150 - 0) + 1));
			level.addSprite(new Sprite(CLOUD_MAP.get(sceneGenerator.nextInt(6)), i * 270, y));
			//getSceneNodes().getChildren().add(0, cloud);
		}

		for(TilePane tp : tilePanes) {
			//getSceneNodes().getChildren().add(0, tp);
			//level.addSprite();
		}
	}
	

}