package mb.android.redalert;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
 

@SuppressLint("SetJavaScriptEnabled")
public class AdpActivity extends Activity {
		
	//public class MyWebChromeClient extends WebChromeClient {}
	
	private WebView web;
	private ProgressBar loader;
	public static final String ADP_PORTAL = "https://portal.adp.com/wps/myportal/sitemap/Employee/TimeAttendance/MyTimecard/";

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adp_layout);
        
        web = (WebView) findViewById(R.id.web);
        web.setVisibility(View.GONE);
        
        loader = (ProgressBar) findViewById(R.id.adp_loader);
        loader.setVisibility(View.VISIBLE);
        
        WebSettings webSettings = web.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //webSettings.setPluginsEnabled(true);
        
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
            	if(url.contains("agateway.adp.com/siteminderagent/bad/login")){
                	
                	final Dialog dialog = new Dialog(AdpActivity.this);
                    dialog.setContentView(R.layout.simple_dialog);
                    dialog.setTitle("Login attempt failed");

                    //ImageView image = (ImageView) dialog.findViewById(R.drawable.adp_login_normal);
                    final TextView text = (TextView) dialog.findViewById(R.id.simple_dialot_text);
                    final Button cancel = (Button) dialog.findViewById(R.id.simple_dialog_cancel);
                    final Button confirm = (Button) dialog.findViewById(R.id.simple_dialog_confirm);
                    
                    text.setText("Would you like to change your login credentials?");
                    
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        	SettingsActivity.setupLoginDialog(AdpActivity.this);
                        	dialog.dismiss();
                        }
                    });

                    dialog.show();
            	}
            }
            
            @Override
            public void onReceivedHttpAuthRequest(WebView view,
            		HttpAuthHandler handler, String host, String realm) {
                Settings s = new Settings(AdpActivity.this);
                handler.proceed(s.userName, s.passWord);

            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            	final Dialog dialog = new Dialog(AdpActivity.this);
                dialog.setContentView(R.layout.simple_dialog);
            	dialog.setTitle("An error has occurred");
            	
                final TextView text = (TextView) dialog.findViewById(R.id.simple_dialot_text);
                final Button cancel = (Button) dialog.findViewById(R.id.simple_dialog_cancel);
                final Button confirm = (Button) dialog.findViewById(R.id.simple_dialog_confirm);

                cancel.setVisibility(View.GONE);
            	text.setText("Please check to make sure that you have internet connection");
            	confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	dialog.dismiss();
                    }
                });
            	dialog.show();
               }
        });
        web.loadUrl(ADP_PORTAL);
    }
}