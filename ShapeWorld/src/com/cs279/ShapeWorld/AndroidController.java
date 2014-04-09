package com.cs279.ShapeWorld;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javafx.scene.Scene;

public class AndroidController extends Controller implements Runnable {
	
	private HashMap<Integer, GameEvent> keyMap;
	private Socket socket = null;
	private ServerSocket server = null;
	private Thread thread = null;
	private DataInputStream streamIn  =  null;
	
	static final int DIR_UP = 0;
	static final int DIR_LEFT = 1;
	static final int DIR_RIGHT = 2;
	static final int DIR_DOWN = 3;
	
	public AndroidController(Scene s, int port) {
		super(s);
		
		// set up the map
		keyMap = new HashMap<Integer, GameEvent>();
		keyMap.put(DIR_RIGHT, GameEvent.RIGHT);
		keyMap.put(DIR_LEFT, GameEvent.LEFT);
		keyMap.put(DIR_UP, GameEvent.UP);
		keyMap.put(DIR_DOWN, GameEvent.DOWN);
		
		// start the server and wait for connections
		try {  
			System.out.println("Binding to port " + port + ", please wait  ...");
	        server = new ServerSocket(port);  
	        System.out.println("Server started: " + server);
	        
	        start();
	    }
		catch(IOException ioe) {  
			System.out.println(ioe); 
		}
	}
	
	
	 public void start() {  
		   if (thread == null) {  
			   thread = new Thread((Runnable) this); 
			   thread.start();
	      }
	   }
	   
	   public void stop() {  
		   if (thread != null) {  
			   thread.stop(); 
			   thread = null;
	      }
	   }
	   
	   public void open() throws IOException {  
		   streamIn = 
			new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	   }
	   
	   public void close() throws IOException {  
		   if (socket != null)    socket.close();
		   if (streamIn != null)  streamIn.close();
	   }
	   

	   @Override
	   public void run() {
		   while (thread != null) {   
			   try {  
				   System.out.println("Waiting for a client ..."); 
				   socket = server.accept();
				   System.out.println("Client accepted: " + socket);
				   open();
				   boolean done = false;
				   
				   while (!done) {  
					   try {  
						   Integer cmd = streamIn.readInt();
						   
						   switch(cmd) {
						   case DIR_UP:
							   setLastEvent(keyMap.get(DIR_UP));
							   Thread.sleep(350);
							   setLastEvent(GameEvent.NONE);
							   break;
							   
						   case DIR_RIGHT:
							   setLastEvent(keyMap.get(DIR_RIGHT));
							   break;
							   
						   case DIR_LEFT:
							   setLastEvent(keyMap.get(DIR_LEFT));
							   break;
							   
						   default:
							   setLastEvent(GameEvent.NONE);
						   }
							 	 
					   }
					   catch(IOException | InterruptedException ioe) { done = true; }
	            }
	            close();
	         }
	         catch(IOException ie) { System.out.println("Acceptance Error: " + ie); }
	      }
	   }
	 
}
