package fun.android.shapegame.shapegameandroidclient;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;

public class GameControllerActivity extends Activity {
	
	private final String TAG = this.getClass().getSimpleName();
	
	private ServerConnectFragment scFragment;
	private GameControllerFragment gcFragment;

	private Messenger ctrlSender = null;
	private boolean isBound = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blank);
		
		scFragment = new ServerConnectFragment();
		
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		
		trans.replace(R.id.fragment_container, scFragment);
		trans.addToBackStack(null);
		trans.commit();
		
		gcFragment = new GameControllerFragment();
	}
	
	@Override
	protected void onStart() { 
		 super.onStart(); 
		 
		 Intent ctrlIntent = new Intent(CommandControllerService.class.getName()); 
		 bindService(ctrlIntent, ctrlConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStop() {
		
		 Message disconnectCtrl = Message.obtain(null, 
	        	CommandControllerService.DISCONNECT);
		
		 sendControl(disconnectCtrl);
		 
		 if(isBound) {
			 unbindService(this.ctrlConnection); 
		 }
		 
		 super.onStop(); 
	} 
	
	@Override
    protected void onDestroy() {
        
        try {
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
        	scFragment.getServerPort(), 0, scFragment.getServerName());
        
        sendControl(connectCtrl);
        
        FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragment_container, gcFragment);
		trans.addToBackStack(null);
		trans.commit();
	}
	
	void sendControl(Message ctrl) {
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
