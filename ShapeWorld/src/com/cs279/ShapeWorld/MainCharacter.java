package com.cs279.ShapeWorld;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import com.cs279.ShapeWorld.Controller.GameEvent;

public class MainCharacter extends Sprite {
	private Animation animation;

	private final int SPRITE_WIDTH = 100;
	private final int SPRITE_HEIGHT = 100;

	public MainCharacter(ImageView n, int x, int y) {
		super(n, x, y);
		
		node.setViewport(new Rectangle2D(0, 0, 100, 100));
		node.setTranslateY(y);
		node.setTranslateX(x);
		node.setRotationAxis(Rotate.Y_AXIS);

		animation = new SpriteAnimation(node, Duration.millis(500), 8, 1, 0, 0,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		animation.setCycleCount(1);
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
			node.setRotate(0);
			break;
		case LEFT:
			node.setRotate(180);
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
		node.setViewport(new Rectangle2D(0, 800, 100, 100));
	}

	public void setJumping() {
		node.setViewport(new Rectangle2D(0, 0, 100, 100));
	}

	public String getType() {
		return null;
	}

	public boolean collision(Sprite other) {
		return false;
	}

}
