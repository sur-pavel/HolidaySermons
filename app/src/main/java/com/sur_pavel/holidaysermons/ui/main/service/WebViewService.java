package com.sur_pavel.holidaysermons.ui.main.service;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewService {
    private WebView webView;
    private String googleSearchUrl = "https://www.google.com/search";

    public WebViewService(WebView webView) {
        this.webView = webView;
        setupWebView();
    }

    private void setupWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
    }

    public void loadUrl(String query) {
        webView.loadUrl(googleSearchUrl + "?q=" + query + " &num=30");
    }

    public void findInWebView(String searchQuery) {
        if (!searchQuery.equals("")) {
            webView.findAllAsync(searchQuery);
        } else {
            webView.clearMatches();
        }
    }
} 