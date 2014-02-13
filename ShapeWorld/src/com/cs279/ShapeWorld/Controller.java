package com.cs279.ShapeWorld;

import javafx.scene.Scene;

public class Controller {
	public enum GameEvent {
		RIGHT, LEFT, UP, DOWN, NONE
	}
	
	private GameEvent lastEvent;
	protected Scene gameSurface;
	
	public Controller(Scene s) {
		gameSurface = s;
	}

	public GameEvent getLastEvent() {
		return lastEvent;
	}

	protected void setLastEvent(GameEvent lastEvent) {
		this.lastEvent = lastEvent;
	}
	
}
