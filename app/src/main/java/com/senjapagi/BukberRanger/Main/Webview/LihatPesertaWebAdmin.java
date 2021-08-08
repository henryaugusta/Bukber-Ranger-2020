package com.senjapagi.BukberRanger.Main.Webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.senjapagi.BukberRanger.API.apiClient;
import com.senjapagi.BukberRanger.Main.General.dashboard_main;
import com.senjapagi.qrtomysql.R;

public class LihatPesertaWebAdmin extends AppCompatActivity {

    WebView websiteView;
    LinearLayout errorFrame;
    String url = apiClient.URL_MANAGE_PESERTA_AKTIF;
    Button btnRefreshPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_peserta_web_admin);
        websiteView = (WebView) findViewById(R.id.webview);
        String url = apiClient.URL_MANAGE_PESERTA_AKTIF;

        errorFrame = findViewById(R.id.error_frame);
        btnRefreshPage = findViewById(R.id.btn_refresh_page);
        btnRefreshPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LihatPesertaWebAdmin.this, "Refresh", Toast.LENGTH_SHORT).show();
//                WebView websiteView = (WebView) findViewById(R.id.webview);
//                websiteView.getSettings().setDomStorageEnabled(true);
//                websiteView.getSettings().setJavaScriptEnabled(true);
//                String url = apiClient.URL_MANAGE_PESERTA_AKTIF;
//                websiteView.loadUrl(url);
            }
        });

        WebSettings webSettings = websiteView.getSettings();
        websiteView.getSettings().setDomStorageEnabled(true);
        websiteView.getSettings().setJavaScriptEnabled(true);
        websiteView.setWebChromeClient(new WebChromeClient());
        webSettings.setJavaScriptEnabled(true);
        websiteView.loadUrl(url);
        websiteView.setWebViewClient(new WebViewClient());
        websiteView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.webview).setVisibility(View.VISIBLE);
                findViewById(R.id.error_frame).setVisibility(View.GONE);
                findViewById(R.id.loading_indicator).setVisibility(View.GONE);
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                findViewById(R.id.error_frame).setVisibility(View.VISIBLE);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Toast.makeText(LihatPesertaWebAdmin.this, "Berhasil Refresh", Toast.LENGTH_SHORT).show();
                findViewById(R.id.loading_indicator).setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                findViewById(R.id.loading_indicator).setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, request);
            }

        });

        findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LihatPesertaWebAdmin.this, dashboard_main.class);
                startActivity(intent);
            }
        });
    }
//    WebView websiteView = (WebView) findViewById(R.id.webview);
//    String url = apiClient.URL_MANAGE_PESERTA_AKTIF;
//                websiteView.loadUrl(url);




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


