package com.cs279.shapegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class GameCanvas extends Canvas {
	
	final int WIDTH = 600;
	final int HEIGHT = 400;
	
	private ArrayList<Character> chars;

	public GameCanvas() {
		setSize(WIDTH, HEIGHT);
		
		chars = new ArrayList<Character>();
	}
	
	public void addCharacter(Character c) {
		chars.add(c);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(Character c : chars) {
			c.draw(g);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.stepForward();
		}
		repaint();
	}

}
