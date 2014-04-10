package com.cs279.ShapeWorld;

import javafx.scene.Scene;

public class Controller {
	public enum GameEvent {
		RIGHT, LEFT, UP, DOWN, NONE, SHOOT
	}
	
	private GameEvent lastEvent;
	private GameEvent lastActionEvent;
	protected Scene gameSurface;
	
	public Controller(Scene s) {
		gameSurface = s;
	}

	public GameEvent getLastEvent() {
		return lastEvent;
	}
	
	public GameEvent getLastActionEvent() {
		return lastActionEvent;
	}

	protected void setLastEvent(GameEvent lastEvent) {
		this.lastEvent = lastEvent;
		if(lastEvent != GameEvent.NONE || lastEvent != GameEvent.UP) {
			lastActionEvent = lastEvent;
		}
	}
	
}
