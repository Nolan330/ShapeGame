package fun.android.shapegame.shapegameandroidclient;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class GameControllerActivity extends Activity {
	
	private final String TAG = this.getClass().getSimpleName();
	
	private EditText serverName;
	private EditText serverPort;
	
	private Messenger ctrlSender = null;
	private boolean isBound = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_controller);
		
		serverName = (EditText) findViewById(R.id.serverName);
		serverPort = (EditText) findViewById(R.id.serverPort);
	}
	
	@Override
	protected void onStart() { 
		 super.onStart(); 
		 
		 Intent ctrlIntent = new Intent(CommandControllerService.class.getName()); 
		 bindService(ctrlIntent, ctrlConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStop() {
		 if(isBound) {
			 unbindService(this.ctrlConnection); 
		 }
		 
		 super.onStop(); 
	} 
	
	@Override
    protected void onDestroy() {
        
        try {
            unbindService(this.ctrlConnection);
            stopService(new Intent(GameControllerActivity.this, 
            			CommandControllerService.class));
        } catch (Throwable t) {
            Log.d(TAG, "Failed to unbind from ControllerService");
        }
        
        super.onDestroy();
    }
	
	public void upDirection(View view) {
		Log.d(TAG, "upDirection invoked");
		Message upCtrl = Message.obtain(null, 
				CommandControllerService.DIR_UP);
		        
		sendControl(upCtrl);
	}
	
	public void leftDirection(View view) {
		Log.d(TAG, "leftDirection invoked");
		Message leftCtrl = Message.obtain(null, 
				CommandControllerService.DIR_LEFT);
		        
		sendControl(leftCtrl);
	}
	
	public void rightDirection(View view) {
		Log.d(TAG, "rightDirection invoked");
		Message rightCtrl = Message.obtain(null, 
				CommandControllerService.DIR_RIGHT);
		        
		sendControl(rightCtrl);
	}
	
	public void downDirection(View view) {
		Log.d(TAG, "downDirection invoked");
		Message downCtrl = Message.obtain(null, 
			CommandControllerService.DIR_DOWN);
	        
	    sendControl(downCtrl);
	}
	
	public void connectToServer(View view) {
		Log.d(TAG, "connectToServer invoked");
        Message connectCtrl = Message.obtain(null, 
        	CommandControllerService.CONNECT,
        	Integer.parseInt(serverPort.getText().toString()), 0,
        	serverName.getText().toString());
        
        sendControl(connectCtrl);
	}
	
	private void sendControl(Message ctrl) {
		if (isBound) {
            if (ctrlSender != null) {
                try {
                    ctrlSender.send(ctrl);
                } catch (RemoteException e) {
                	Log.d(TAG, "Remote Exception Thrown");
                }
            }
        }
	}
	
	private ServiceConnection ctrlConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
        	Log.d(TAG, "Connected to service");
        	ctrlSender = new Messenger(service);
        	isBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
        	Log.d(TAG, "Disconnected from service");
        	ctrlSender = null;
            isBound = false;
        }
    };
	
}
