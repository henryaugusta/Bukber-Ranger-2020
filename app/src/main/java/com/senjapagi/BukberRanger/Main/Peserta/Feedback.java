package com.senjapagi.BukberRanger.Main.Peserta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.BukberRanger.Main.General.dashboard_main;
import com.senjapagi.qrtomysql.R;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        WebView websiteView;

        String url = getIntent().getStringExtra("URL");
        if(url.equals("1")){
            url= apiClient.URL_FEEDBACK_APLIKASI;
        }else if(url.equals("2")){
            url=apiClient.URL_FEEDBACK_PANITIA;
        }
        websiteView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = websiteView.getSettings();
        websiteView.getSettings().setDomStorageEnabled(true);
        websiteView.getSettings().setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        websiteView.loadUrl(url);
        websiteView.setWebViewClient(new WebViewClient());
        websiteView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.webview).setVisibility(View.VISIBLE);
                findViewById(R.id.progressloading).setVisibility(View.GONE);
                findViewById(R.id.textLoading).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Feedback.this, dashboard_main.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView webview = (WebView) findViewById(R.id.webview);

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        WebView webview = (WebView) findViewById(R.id.webview);

        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        WebView webview = (WebView) findViewById(R.id.webview);
        super.onRestoreInstanceState(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }


}

