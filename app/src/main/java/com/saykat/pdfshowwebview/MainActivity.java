package com.saykat.pdfshowwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int i = 0;
    private WebView webView;
    private ImageView imageViewLeft, imageViewRight;
    private ProgressBar progress;
    private List<String> imageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.pdfView);
        progress = findViewById(R.id.progress);
        imageViewLeft = findViewById(R.id.iv_previous);
        imageViewRight = findViewById(R.id.iv_next);

        imageList.add("https://images.unsplash.com/photo-1501183007986-d0d080b147f9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80");
        imageList.add("https://images.unsplash.com/photo-1592255454174-3f6a1a3e881f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80");
        imageList.add("https://images.unsplash.com/photo-1549989317-6f14743af1bf?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80");
        imageList.add("https://images.unsplash.com/photo-1533154613417-407cfcf6abb2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80");
        imageList.add("https://images.unsplash.com/photo-1589303694824-1e52225426dd?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=369&q=80");
        imageList.add("https://images.unsplash.com/photo-1603862026698-d2e3215abad0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80");
        imageList.add("https://images.unsplash.com/photo-1512438248247-f0f2a5a8b7f0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=400&q=80");

        showPdfFile(imageList.get(i));

        imageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i--;
                if (i> -1) {
                    showPdfFile(imageList.get(i));
                } else {
                    i=0;
                    Toast.makeText(getApplicationContext(), "You're on first PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (i != imageList.size()) {
                    showPdfFile(imageList.get(i));
                } else {
                    Toast.makeText(getApplicationContext(), "Going to first PDF", Toast.LENGTH_SHORT).show();
                    i=0;
                    showPdfFile(imageList.get(i));
                }
            }
        });
    }

    private void showPdfFile(final String imageString) {
        if (Build.VERSION.SDK_INT >= 19)
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        else
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        showProgress();
        webView.invalidate();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setLoadsImagesAutomatically(true);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
        webView.loadUrl(imageString);
        webView.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkOnPageStartedCalled) {
                    hideProgress();
                } else {
                    showPdfFile(imageString);
                }
            }
        });
    }

    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }
}