package fun.android.shapegame.shapegameandroidclient;

import java.util.ArrayList;

import android.app.Fragment;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameControllerFragment extends Fragment 
	implements OnGesturePerformedListener {
	
	private final String TAG = this.getClass().getSimpleName();
	
	GameControllerActivity gcActivity;
	
	GestureLibrary spellLib;
	GestureOverlayView gestureView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    	View view = inflater.inflate(R.layout.activity_game_controller,
	    			container, false);
	    	
	    	gcActivity = (GameControllerActivity) getActivity();
	    	if(gcActivity != null) {
		    	spellLib = GestureLibraries.fromRawResource(gcActivity, R.raw.spells);
		        if (!spellLib.load()) {
		        	gcActivity.finish();
		        }
	    	}

	    	return view;
	}
	
	@Override
	public void onResume() {
		super.onResume(); 
		
		gestureView = (GestureOverlayView) 
        		getView().findViewById(R.id.spellDetectorView);
        
        gestureView.addOnGesturePerformedListener(this);
	}
	

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		
		ArrayList<Prediction> predictions = spellLib.recognize(gesture);
		
		if (predictions.size() > 0) {
			if (predictions.get(0).score > 1.0) {
				String spell = predictions.get(0).name;
				
				if (spell.equals("cloudkill")) {
					Log.d(TAG, "CLOUDKILLER invoked");
					Message cloudkillCtrl = Message.obtain(null, 
							CommandControllerService.CLOUD_KILL);
					        
					gcActivity.sendControl(cloudkillCtrl);
				}
				else if (spell.equals("fireball")) {
					Log.d(TAG, "FIREBALL invoked");
					Message fireballCtrl = Message.obtain(null, 
							CommandControllerService.FIREBALL);
					        
					gcActivity.sendControl(fireballCtrl);
				}
				else if (spell.equals("doublejump")) {
					Log.d(TAG, "DOUBLEJUMP invoked");
					Message doublejumpCtrl = Message.obtain(null, 
							CommandControllerService.DOUBLE_JUMP);
					        
					gcActivity.sendControl(doublejumpCtrl);
				}
				else if (spell.equals("reset")) {
					Log.d(TAG, "RESET invoked");
					Message resetCtrl = Message.obtain(null, 
							CommandControllerService.RESET);
					        
					gcActivity.sendControl(resetCtrl);
				}
				else {
					Log.d(TAG, "spell name: " + spell + 
							" Score: " + predictions.get(0).score);
				}
			}
		}
	}
	
}
