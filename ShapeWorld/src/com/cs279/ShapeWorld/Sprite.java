package com.cs279.ShapeWorld;

import com.cs279.ShapeWorld.Controller.GameEvent;

import javafx.scene.Node;

public abstract class Sprite {
	protected Node node;

	public abstract void update(GameEvent ge);
	
	public abstract String getType();
	
	public Node getNode() {
		return node;
	}
	
	public abstract boolean collision(Sprite other);
}
