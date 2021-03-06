package com.cs279.ShapeWorld;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Sprite {
	
	@XStreamOmitField protected ImageView node;
	@XStreamOmitField protected boolean DEAD = false;
	
	protected String imageLocation; 
	protected int trueX;
	protected int trueY;

	public Sprite(String imageLocation, int x, int y) {
		trueX = x;
		trueY = y;
		this.imageLocation = imageLocation;
	}
	
	private Object readResolve() {
		initialize();
		return this;
	}

	public void update(GameEngine ge) {		
		StageCamera sc = ge.getStageCamera();
		if(DEAD) {
			node.setVisible(false);
		} else if(ge.getLevel().isCompleted()) {
			return;
		} else if (sc.isVisible(this)) {
			double visX = sc.getVisibleX(trueX);
			double visY = sc.getVisibleY(trueY);
			node.setVisible(true);
			if (visX < 0) {
				if (visX < node.getImage().getWidth()) {
					node.setViewport(new Rectangle2D(-visX, node.getViewport()
							.getMinY(), node.getImage().getWidth() + visX, node
							.getViewport().getHeight()));
					node.setTranslateX(0);
				}
			} else {
				node.setViewport(new Rectangle2D(0, 0, node.getImage().getWidth(), node.getImage()
						.getHeight()));
				node.setTranslateX(visX);
			}

			node.setTranslateY(visY);
		} else {
			node.setVisible(false);
		}
	}
	
	public boolean collision(Sprite other) {
		return !this.equals(other) 
					&& !DEAD
					&& node.isVisible()
					&& node.getBoundsInParent().intersects(other.getNode().getBoundsInParent());
	}

	/*
	 * Used in serialization process to create the node object. 
	 */
	protected void initialize() {
		node = new ImageView(new Image(getClass().getResourceAsStream(
				imageLocation)));
	}
	
	public String toString() {
		return "imageLocation: " + imageLocation + "\ntrueX: " + trueX + "\ntrueY: " + trueY;
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
}
