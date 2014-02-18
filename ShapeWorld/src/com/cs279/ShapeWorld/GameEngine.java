package com.cs279.ShapeWorld;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class GameEngine {
	private Scene gameSurface;
	private Group sceneNodes;
	private static Timeline gameLoop;
	private StageCamera camera;
	
	private final int framesPerSecond;
	public final static int WIDTH = 640;
	public final static int HEIGHT = 580;
	
	private final String windowTitle;
	protected final SpriteManager spriteManager = new SpriteManager();
	
	private Controller controller;

	public GameEngine(final int fps, final String title) {
		framesPerSecond = fps;
		windowTitle = title;
		camera = new StageCamera(0, 0, WIDTH, HEIGHT);
		buildGameLoop();
	}
	
	@SuppressWarnings("unchecked")
	protected final void buildGameLoop() {
		final Duration frameLength = Duration.millis(1000/getFramesPerSecond());
		
		final KeyFrame frame = new KeyFrame(frameLength,
				new EventHandler() {
					@Override
					public void handle(Event event) {
						//drawScene();
						updateSprites();
						//checkCollisions();
					}
					
		});
		
		setGameLoop(TimelineBuilder.create()
				.cycleCount(Animation.INDEFINITE)
				.keyFrames(frame).build());
		
	}
	
	/*
	 * Initialize Engine. 
	 */
	public abstract void initialize(final Stage primaryStage);
	
	
	/*
	 * Starts the Timeline objects that goes through key frames. 
	 * Runs continuously, called the EventHandler defined above. 
	 */
	public void beginGameLoop() {
		getGameLoop().play();
	}
	
	protected void updateSprites() {
		for(Sprite sprite : spriteManager.getAllSprites()) {
			sprite.update(this);
		}
	}
	
	public StageCamera getStageCamera() {
		return camera;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public void setController(Controller c) {
		controller = c;
	}

	public static Timeline getGameLoop() {
		return gameLoop;
	}

	public static void setGameLoop(Timeline gameLoop) {
		GameEngine.gameLoop = gameLoop;
	}

	public Scene getGameSurface() {
		return gameSurface;
	}

	public void setGameSurface(Scene gameSurface) {
		this.gameSurface = gameSurface;
	}

	public Group getSceneNodes() {
		return sceneNodes;
	}

	public void setSceneNodes(Group sceneNodes) {
		this.sceneNodes = sceneNodes;
	}

	public int getFramesPerSecond() {
		return framesPerSecond;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public SpriteManager getSpriteManager() {
		return spriteManager;
	}
}
