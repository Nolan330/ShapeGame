package com.cs279.shapegame;

import javax.swing.JFrame;

public class ShapeGame {

	private JFrame frame;
	private GameCanvas gameCanvas;
	
	public static void main(String[] args) {
		new ShapeGame();

	}
	
	public ShapeGame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		
		gameCanvas = new GameCanvas();
		gameCanvas.addCharacter(new MainCharacter());
		
		frame.add(gameCanvas);
		frame.setVisible(true);
		
		
	}

}
