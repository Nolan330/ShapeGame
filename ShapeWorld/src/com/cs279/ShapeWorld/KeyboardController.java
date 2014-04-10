package com.cs279.ShapeWorld;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardController extends Controller {
	private HashMap<KeyCode, GameEvent> keyMap;

	public KeyboardController(Scene s) {
		super(s);
		keyMap = new HashMap<KeyCode, GameEvent>();
		keyMap.put(KeyCode.RIGHT, GameEvent.RIGHT);
		keyMap.put(KeyCode.LEFT, GameEvent.LEFT);
		keyMap.put(KeyCode.UP, GameEvent.UP);
		keyMap.put(KeyCode.DOWN, GameEvent.DOWN);
		keyMap.put(KeyCode.S, GameEvent.SHOOT);
		initEventHandler();
	}
	
	private void initEventHandler() {
		gameSurface.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(keyMap.containsKey(event.getCode())) {
					setLastEvent(keyMap.get(event.getCode()));
				}
			}
		});
		
		gameSurface.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				setLastEvent(GameEvent.NONE);
			}
			
		});
	}
	
}
