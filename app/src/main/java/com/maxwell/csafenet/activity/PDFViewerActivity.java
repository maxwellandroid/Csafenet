package com.maxwell.csafenet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.maxwell.csafenet.R;

public class PDFViewerActivity extends AppCompatActivity {

    WebView webView;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        url=getIntent().getStringExtra("PdfUrl");
        webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);
    }
}
