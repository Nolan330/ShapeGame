package com.cs279.ShapeWorld;

import javafx.scene.Scene;

public class Controller {
	public enum GameEvent {
		RIGHT, LEFT, UP, DOWN, NONE, SHOOT, DOUBLE_JUMP, RESET
	}
	
	private GameEvent lastEvent;
	private GameEvent lastActionEvent;
	protected GameEngine ge;
	
	public Controller(GameEngine s) {
		ge = s;
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
		if(lastEvent == GameEvent.RESET) {
			if(ge.level.getMainCharacter().DEAD){
				ge.level.reset();
			}
				
			//lastEvent = GameEvent.NONE;
		}
	}
	
}
