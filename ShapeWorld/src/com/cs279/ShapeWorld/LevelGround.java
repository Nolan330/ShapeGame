package com.cs279.ShapeWorld;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class LevelGround extends Sprite {
	private int trueEndX;
	private transient TilePane tp;

	public LevelGround(String imageLocation, int x, int y, int endX) {
		super(imageLocation, x, y);
		trueEndX = endX;
		initialize();
	}

	private Object readResolve() {
		initialize();
		return this;
	}

	public void initialize() {
		tp = new TilePane();
		tp.setHgap(0);
		tp.setVgap(0);
		tp.setPrefHeight(200);
		double imgWidth = 2;
		tp.setPrefRows(1);
		tp.setPrefColumns((int) (GameEngine.WIDTH/imgWidth));
		tp.setMaxWidth(GameEngine.WIDTH);
		tp.setTranslateY(GameEngine.HEIGHT - 200);
		double x = trueX;

		while (x < trueEndX) {
			tp.getChildren().add(
					new ImageView(new Image(getClass().getResourceAsStream(imageLocation))));
			x += imgWidth;
		}
	}

	public void update(GameEngine ge) {
		StageCamera sc = ge.getStageCamera();
		double visX = sc.getVisibleX(trueX);
		double visEndX = sc.getVisibleX(trueEndX);
		if(isVisible(sc)) {	
			tp.setVisible(true);
			if(visX > 0 && visEndX > GameEngine.WIDTH) {
				tp.setTranslateX(visX);
				tp.setMaxWidth(GameEngine.WIDTH - visX);
			} else if(visX < 0 && visEndX < GameEngine.WIDTH) {
				tp.setTranslateX(0);
				tp.setMaxWidth(visEndX);
			} else {
				tp.setTranslateX(0);
				tp.setMaxWidth(GameEngine.WIDTH);
			}
		} else {
			tp.setVisible(false);
		}
	}
	
	private boolean isVisible(StageCamera sc) {
		boolean inBetween = trueX < sc.getX() && trueEndX > sc.getX();
		boolean leftSide = trueX < (sc.getX() + sc.getWidth()) && trueEndX > (sc.getX() + sc.getWidth());
		boolean rightSide = trueX < (sc.getX() - sc.getWidth()) && trueEndX > (sc.getX() - sc.getWidth());
		return inBetween || leftSide || rightSide;
	}

	public Node getNode() {
		return tp;
	}
	
	public double getWidth() {
		return (trueEndX - trueX) < GameEngine.WIDTH ? trueEndX - trueX : GameEngine.WIDTH;
	}
	
	public double getHeight() {
		return 0;
	}

}
