package fun.android.shapegame.shapegameandroidclient;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class ServerConnectFragment extends Fragment {
	
	private EditText serverName;
	private EditText serverPort;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    	View view = inflater.inflate(R.layout.activity_connect_server,
	    			container, false);
	    	
	    	return view;
	}
	
	public String getServerName() {
		serverName = (EditText) getView().findViewById(R.id.server_name);
		return serverName.getText().toString();
	}
	
	public int getServerPort() {
		serverPort = (EditText) getView().findViewById(R.id.server_port);
		return Integer.parseInt(serverPort.getText().toString());
	}
}
