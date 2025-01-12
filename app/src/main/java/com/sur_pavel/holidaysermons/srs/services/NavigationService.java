package com.sur_pavel.holidaysermons.srs.services;

import android.webkit.WebView;

public class NavigationService {
    private WebView webView;

    public NavigationService(WebView webView) {
        this.webView = webView;
    }

    public void goForward() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }

    public void goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }
} 