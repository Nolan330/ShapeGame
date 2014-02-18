package com.cs279.ShapeWorld;

import com.cs279.ShapeWorld.Controller.GameEvent;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class MainCharacter extends Sprite {
	private ImageView walk = new ImageView(new Image(getClass()
			.getResourceAsStream("/main-animation.png")));
	private Animation animation;

	private final int SPRITE_WIDTH = 100;
	private final int SPRITE_HEIGHT = 100;

	public MainCharacter(Node n, int x, int y) {
		super(n, x, y);
		walk.setViewport(new Rectangle2D(0, 0, 100, 100));
		walk.setTranslateY(y);
		walk.setTranslateX(x);
		walk.setRotationAxis(Rotate.Y_AXIS);

		animation = new SpriteAnimation(walk, Duration.millis(500), 8, 1, 0, 0,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		animation.setCycleCount(1);
		node = walk;
	}

	@Override
	public void update(GameEngine ge) {
		// super.update(ge);
		GameEvent last = ge.getController().getLastEvent() == null ? GameEvent.NONE
				: ge.getController().getLastEvent();
		switch (last) {
		case RIGHT:
			animation.play();
			// TODO: make generic method for moving sprite
			node.setTranslateX(trueX);
			ge.getStageCamera().augmentX(4);
			// ge.getStageCamera().augmentY();
			walk.setRotate(0);
			break;
		case LEFT:
			walk.setRotate(180);
			animation.play();
			ge.getStageCamera().augmentX(-4);
			node.setTranslateX(trueX);
			break;
		case UP:
			setJumping();
			break;
		case DOWN:
			node.setVisible(false);
			break;
		case NONE:
			animation.stop();
			setStanding();
			break;
		default:
			break;
		}
	}

	public void setStanding() {
		walk.setViewport(new Rectangle2D(0, 800, 100, 100));
	}

	public void setJumping() {
		walk.setViewport(new Rectangle2D(0, 0, 100, 100));
	}

	public String getType() {
		return null;
	}

	public boolean collision(Sprite other) {
		return false;
	}

}
