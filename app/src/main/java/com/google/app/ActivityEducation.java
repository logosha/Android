package com.google.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

public class ActivityEducation extends MyAbstractToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);


        WebView webview = (WebView)this.findViewById(R.id.webViewEducation);
        webview.getSettings();
        webview.setBackgroundColor(getResources().getColor(R.color.colorWeb));
        String htmlAsString = getString(R.string.education_html_text);
        webview.loadData(htmlAsString, "text/html", "UTF-8");

    }


}
