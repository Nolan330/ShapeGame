package com.cs279.ShapeWorld;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class Sprite {
	protected ImageView node;
	protected int trueX;
	protected int trueY;
	private Rectangle2D viewport;

	public Sprite(ImageView n, int x, int y) {
		node = n;
		trueX = x;
		trueY = y;
		viewport = new Rectangle2D(0, 0, n.getImage().getWidth(), n.getImage().getHeight());
	}

	public void update(GameEngine ge) {
		StageCamera sc = ge.getStageCamera();
		// System.out.println("trueX: " + trueX);
		if (sc.isVisible(this)) {
			double visX = sc.getVisibleX(trueX);
			double visY = sc.getVisibleY(trueY);
			node.setVisible(true);
			if (visX < 0) {
				if(visX < node.getImage().getWidth()) {
					node.setViewport(new Rectangle2D(-visX, node.getViewport()
							.getMinY(), node.getImage().getWidth() + visX, node
							.getViewport().getHeight()));
					node.setTranslateX(0);
				}
			} else {
				node.setViewport(viewport);
				node.setTranslateX(visX);
			}
			
			node.setTranslateY(visY);
		} else {
			node.setVisible(false);
		}
	}

	public Node getNode() {
		return node;
	}

	public double getXCoord() {
		return trueX;
	}

	public double getYCoord() {
		return trueY;
	}

	public void setXCoord(double x) {
		node.setTranslateX(x);
	}

	public void setYCoord(double y) {
		node.setTranslateY(y);
	}
	
	public double getWidth() {
		return node.getImage().getWidth();
	}
	
	public double getHeight() {
		return node.getImage().getWidth();
	}

	// public abstract boolean collision(Sprite other);
}
