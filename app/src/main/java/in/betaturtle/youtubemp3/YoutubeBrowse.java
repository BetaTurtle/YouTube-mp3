package in.betaturtle.youtubemp3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by dave on 18/1/15.
 */
public class YoutubeBrowse extends Activity {
    WebView mWebView;
    Button button;
    EditText text;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_listview);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.m.youtube.com");

    }


    public void openBrowser(){
        Uri uri=Uri.parse(text.getText().toString());
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}