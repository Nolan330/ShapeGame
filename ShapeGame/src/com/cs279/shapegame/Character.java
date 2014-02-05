package com.cs279.shapegame;

import java.awt.Graphics;
import java.awt.Image;

public interface Character {
	
	void draw(Graphics g);
	
	void stepForward();
}
