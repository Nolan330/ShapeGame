package com.cs279.shapegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class MainCharacter implements Character {
	private ArrayList<Image> walkMotion;
	private Iterator<Image> itWalk;
	private Image curImg;
	
	private int xLoc = 100;
	private int yLoc = 100;
	
	static final File dir = new File("resources/characters/main/walk/");
	
	public MainCharacter() {
		walkMotion = new ArrayList<Image>();
		
		if (dir.isDirectory()) { // make sure it's a directory
            for (final File f : dir.listFiles()) {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(f);

                    System.out.println("image: " + f.getName());
                    System.out.println(" width : " + img.getWidth());
                    System.out.println(" height: " + img.getHeight());
                    System.out.println(" size  : " + f.length());
                    
                    walkMotion.add(img);
                } catch (final IOException e) {
                }
            }
        }
		
		itWalk = walkMotion.iterator();
		curImg = itWalk.next();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(curImg, xLoc, yLoc, 100, 100, Color.WHITE, null);
	}

	@Override
	public void stepForward() {
		if(itWalk.hasNext()) {
			curImg = itWalk.next();
		} else {
			itWalk = walkMotion.iterator();
			curImg = itWalk.next();
		}
		xLoc += 15;
		if(xLoc > 600) xLoc = 0;
	}
}
