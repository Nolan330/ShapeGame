package com.cs279.ShapeWorld;

import com.cs279.ShapeWorld.Controller.GameEvent;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MainCharacter extends Sprite {
	//privateInputStream io = Main.class.getClassLoader().getResourceAsStream("main-walk.png");
	private final Image sprite = new Image(getClass().getResourceAsStream("/main-stationary.png"));
	private final Image walking = new Image(getClass().getResourceAsStream("/main-animation.png"));
	private Animation animation;
	
	private Node walkNode;
	private Node standNode;
	
	public MainCharacter() {
		final ImageView iv = new ImageView(sprite);
		final ImageView walk = new ImageView(walking);
		walk.setViewport(new Rectangle2D(0, 0, 100, 100));
		
		animation  = new SpriteAnimation(walk, Duration.millis(750), 8, 1, 0, 0, 100, 100);
		animation.setCycleCount(5);
		//animation.play();
		animation.setOnFinished(new EventHandler<ActionEvent> () {
			@Override
			public void handle(ActionEvent e) {
				
				System.out.println("HANDLE");
				walk.setViewport(new Rectangle2D(0, 800, 100, 100));
				//node.setVisible(false);
			}
		});
		//node.set
		node = walk;
	}

	@Override
	public void update(GameEvent ge) {
		if(ge == GameEvent.RIGHT)
			animation.play();
		//node.setTranslateX(node.getTranslateX() + 2);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean collision(Sprite other) {
		// TODO Auto-generated method stub
		return false;
	}

}
