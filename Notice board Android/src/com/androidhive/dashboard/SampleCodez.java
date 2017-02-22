package com.androidhive.dashboard;
import android.os.Bundle;
import androidhive.dashboard.R;
import android.app.Activity;
import android.content.Intent;
 
public class SampleCodez extends Activity 
{
	
	protected boolean active = true;
	protected int splashTime = 5000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.samplecodez);
        
        Thread splashTread = new Thread()
        {
            @Override
            public void run() 
            {
                try 
                {
                    int waited = 0;
                    
		    while(active && (waited < splashTime)) 
                    {
                        sleep(40);
                        if(active)
                        {
                            waited += 100;
                        }
                    }
                } 
                catch(InterruptedException e)
                {
                    // do nothing
                } finally 
                {
                    finish();
                   // startActivity(new Intent(SampleCodez.this,NetConnection.class));
                    startActivity(new Intent(SampleCodez.this,AndroidDashboardDesignActivity.class));
                }
            }
        };
        splashTread.start();
    }
}
