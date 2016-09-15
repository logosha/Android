package com.google.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class ActivityEducation extends MyAbstractToolbarActivity{

    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);


        WebView webview = (WebView)this.findViewById(R.id.webViewEducation);
        webview.getSettings();
        webview.setBackgroundColor(getResources().getColor(R.color.colorWeb));
        String htmlAsString = getString(R.string.education_html_text);
        webview.loadData(htmlAsString, "text/html", "UTF-8");

        btn1 = (Button) findViewById(R.id.btnUniverSite);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.univer.kharkov.ua/en"));
                startActivity(intent);
            }
        });
        btn2 = (Button) findViewById(R.id.btnUniverLocation);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:50.004472, 36.228194"));
                startActivity(intent);
            }
        });

    }



}
