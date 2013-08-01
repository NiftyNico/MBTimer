package mb.android.redalert;

import com.example.mbtimer.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.webkit.HttpAuthHandler;
 

public class AdpActivity extends Activity {
		
	public class MyWebChromeClient extends WebChromeClient {
		
		@Override
		  public boolean onJsAlert(WebView view, String url, String message,final JsResult result) {
			//handle Alert event, here we are showing AlertDialog
			    new AlertDialog.Builder(AdpActivity.this)
			       .setTitle("JavaScript Alert !")
			       .setMessage(message)
			       .setPositiveButton(android.R.string.ok,
			           new AlertDialog.OnClickListener() {
			              public void onClick(DialogInterface dialog, int which) {
			                     result.confirm();
			               }
			           }).setCancelable(false).create().show();
			   return true;
			  }
	}
	
	private WebView web;
	private ProgressBar loader;
	public static final String ADP_PORTAL = "https://portal.adp.com/wps/employee/employee.jsp";

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adp_layout);
        
        web = (WebView) findViewById(R.id.web);
        web.setVisibility(View.GONE);
        
        loader = (ProgressBar) findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        
        WebSettings webSettings = web.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginsEnabled(true);
        
        web.requestFocusFromTouch();
        
        web.setWebViewClient( new WebViewClient(){
        	@Override
        	public void onPageFinished(WebView view, String url){
                web.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
        	}
        	
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView  view, String  url){
            	
            }
            
            @Override
            public void onReceivedHttpAuthRequest(WebView view,
            		HttpAuthHandler handler, String host, String realm) {
                Settings s = new Settings();
                handler.proceed(s.userName, s.passWord);

            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            	final AlertDialog d = new AlertDialog.Builder(AdpActivity.this).create();
            	d.setTitle("An error has occurred");
            	d.setMessage("Please check to make sure that you have internet connection");
            	d.setButton(DialogInterface.BUTTON_POSITIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int buttonId) {
                                 new Runnable(){
        							@Override
        							public void run() {
        								
        							}
                                 }.run();
                            }
                        });
            	d.show();
               }
        });
        web.loadUrl(ADP_PORTAL);
    }
}