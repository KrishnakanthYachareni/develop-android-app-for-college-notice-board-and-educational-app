package com.androidhive.dashboard;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidhive.dashboard.R;


public class PhotosActivity extends Activity {

	WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView = (WebView)findViewById(R.id.webk);
        
		WebSettings webSetting = webView.getSettings();
		webSetting.setBuiltInZoomControls(true);
		webSetting.setJavaScriptEnabled(true);
		
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("file:///android_asset/help/index.html");
	}

	private class WebViewClient extends android.webkit.WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) 
		{
			return super.shouldOverrideUrlLoading(view, url);
		}
	}
	
}

