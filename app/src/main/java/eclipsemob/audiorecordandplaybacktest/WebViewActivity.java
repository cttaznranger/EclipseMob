package eclipsemob.audiorecordandplaybacktest;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.audiocapture.R;

public class WebViewActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.webview1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.google.com");

    }

}