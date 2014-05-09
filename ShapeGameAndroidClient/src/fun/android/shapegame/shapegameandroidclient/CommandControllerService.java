package fun.android.shapegame.shapegameandroidclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class CommandControllerService extends Service {
	
	private final String TAG = this.getClass().getSimpleName();
	
	private Socket socket = null;
	private DataInputStream console = null;
	private DataOutputStream streamOut = null;
	private boolean socketConnected = false;
	
	private String serverName;
	private int serverPort;
	
	private final Messenger ctrlMessenger = 
			new Messenger(new controlHandler(this));
	static final int DIR_UP = 0;
	static final int DIR_LEFT = 1;
	static final int DIR_RIGHT = 2;
	static final int DIR_DOWN = 3;
	static final int CONNECT = 4;
	static final int DISCONNECT = 5;
	static final int CLOUD_KILL = 10;
	static final int FIREBALL = 12;
	static final int DOUBLE_JUMP = 15;
	static final int RESET = 20;
	
	@Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "ControllerService Started");
    }
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ControllerService Stopped.");
    }

	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	super.onStartCommand(intent, flags, startId);
    	Log.d(TAG, "onStartCommand Invoked");
    	return START_NOT_STICKY;
    }

	@Override
	public IBinder onBind(Intent intent) {
		return ctrlMessenger.getBinder();
	}
	
	private class SocketConnector extends AsyncTask<Void, Void, Boolean> {
		
		@Override
		protected Boolean doInBackground(Void... args) {
			
			Log.d(TAG, "ServerName: " + serverName + " Port: " + serverPort);
			try {  
				socket = new Socket(serverName, serverPort);
				Log.d(TAG, "Connected: " + socket);
				start();
				return socket.isBound();
			}
			catch(UnknownHostException uhe) {  
	    	  Log.d(TAG, "Host unknown: " + uhe.getMessage());
			}
			catch(IOException ioe) {  
	    	  Log.d(TAG, "Unexpected exception: " + ioe.getMessage());
			}
			
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean connected) {
			socketConnected = connected;
		}	
	}
	
	static class controlHandler extends Handler { // Handler of incoming messages from clients.
		private final WeakReference<CommandControllerService> ctrlRef;
		 
		public controlHandler(CommandControllerService cmdCtrl) {
		      ctrlRef = new WeakReference<CommandControllerService>(cmdCtrl);
		}
		
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case DIR_UP:         	
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "DIR_UP PRESSED");
            		ctrlRef.get().sendControl(DIR_UP);
            	}
                break;
                
            case DIR_LEFT:	
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "DIR_LEFT PRESSED");
            		ctrlRef.get().sendControl(DIR_LEFT);
            	}
                break;
                
            case DIR_RIGHT:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "DIR_RIGHT PRESSED");
            		ctrlRef.get().sendControl(DIR_RIGHT);
            	}
                break;
                
            case DIR_DOWN:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "DIR_DOWN PRESSED");
            		ctrlRef.get().sendControl(DIR_DOWN);
            	}
            		
                break;
                
            case CONNECT:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "CONNECT PRESSED");
            		ctrlRef.get().serverName = (String) msg.obj;
            		ctrlRef.get().serverPort = msg.arg1;
            		Log.d(ctrlRef.get().TAG, 
            				"ServerName: " + ctrlRef.get().serverName +
            				" Port: " + ctrlRef.get().serverPort);
            		ctrlRef.get().executeSocketConnector();
            	}
            	break;
            	
            case DISCONNECT:
            	Log.d(ctrlRef.get().TAG, "DISCONNECT PRESSED");
            	if(ctrlRef != null) {
            		//send control to notify player of disconnection
            		ctrlRef.get().stop();
            	}
            	break;
            	
            case CLOUD_KILL:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "CLOUD_KILL INVOKED");
            		ctrlRef.get().sendControl(CLOUD_KILL);
            	}
            	break;
            	
            case FIREBALL:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "FIREBALL INVOKED");
            		ctrlRef.get().sendControl(FIREBALL);
            	}
            	break;
            	
            case DOUBLE_JUMP:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "Double jump INVOKED");
            		ctrlRef.get().sendControl(DOUBLE_JUMP);
            	}
            	break;
            	
            case RESET:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "RESET INVOKED");
            		ctrlRef.get().sendControl(RESET);
            	}
            	break;
            	
            default:
            	if(ctrlRef != null) {
            		Log.d(ctrlRef.get().TAG, "Unrecognized message");
            	}
            }
        }
    }
	
	private void executeSocketConnector() {
		new SocketConnector().execute();
	}
	
	private void start() throws IOException {  
	   console = new DataInputStream(System.in);
	   streamOut = new DataOutputStream(socket.getOutputStream());
	}
		
	private void stop() {  
	   try {  
		   Log.d(TAG, "Disconnecting from server");
		   if (console != null)  
			   console.close();
		   if (streamOut != null)  
			   streamOut.close();
		   if (socket != null)  
			   socket.close();
	   }
	   catch(IOException ioe) {  
		   Log.d(TAG, "Error closing ...");
	   }
	}
	
	private void sendControl(int control) {
		if(socketConnected) {
        	try {
				streamOut.writeInt(control);
				streamOut.flush();
			} 
        	catch (IOException e) {
				Log.d(TAG, "Error writing integer");
			}
        }
		else {
			Log.d(TAG, "Socket not connected");
		}
	}

}
