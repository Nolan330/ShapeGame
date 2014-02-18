package com.cs279.ShapeWorld;

import javafx.scene.Node;

public class Sprite {
	protected Node node;
	protected int trueX;
	protected int trueY;
	
	public Sprite(Node n, int x, int y) {
		node = n;
		trueX = x;
		trueY = y;
	}

	public void update(GameEngine ge) {
		//System.out.println("trueX: " + trueX);
		if(ge.getStageCamera().isVisible(this)) {
			node.setVisible(true);
			node.setTranslateX(ge.getStageCamera().getVisibleX(trueX));
			node.setTranslateY(ge.getStageCamera().getVisibleY(trueY));
			//System.out.println("visibleX: " + ge.getStageCamera().getVisibleX(trueX));
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
	
	//public abstract boolean collision(Sprite other);
}
