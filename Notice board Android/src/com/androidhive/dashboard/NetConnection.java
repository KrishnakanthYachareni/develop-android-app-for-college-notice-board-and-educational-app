package com.androidhive.dashboard;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
 
public class NetConnection extends Activity {
	

	private SharedPreferences prefs;
	private String prefName = "report";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
        	prefs = getSharedPreferences(prefName, MODE_PRIVATE);
        	String net_ip=prefs.getString("ip", "http://www.jntuhcem.esy.es/cnb/");
        	
        	URL url = new URL(net_ip);
        	executeReq(url);
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putInt("connection", 1);
        	editor.commit();
 
        	finish();
        	startActivity(new Intent(NetConnection.this,CNB_Login.class));            
        }
        catch(Exception e)
        {
        	Toast.makeText(getApplicationContext(), "Check Network Connection " +
        			"and IP Address ", Toast.LENGTH_LONG).show();
 
        	SharedPreferences.Editor editor = prefs.edit();
        	editor.putInt("connection", 0);
        	editor.commit();
                
        	finish();
        	startActivity(new Intent(NetConnection.this,CNB_Login.class));
        }
    }
        
    public void executeReq(URL urlObject) throws IOException {
        	
    	HttpURLConnection conn = null;
    	conn = (HttpURLConnection) urlObject.openConnection();
    	conn.setReadTimeout(30000);//milliseconds
    	conn.setConnectTimeout(3500);//milliseconds
    	conn.setRequestMethod("GET");
    	conn.setDoInput(true);
 
    	// Start connect
    	conn.connect();
    	InputStream response =conn.getInputStream();
    	Log.d("Response:", response.toString());
    }
}
