package com.example.xeviu.quicktrade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button b_settings,b_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_settings=(Button)findViewById(R.id.btn_settings);
        b_items=(Button)findViewById(R.id.btn_items);

        b_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AccountSettings.class);
                startActivityForResult(i,1);
            }
        });
        b_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainItems.class);
                startActivityForResult(i,1);
            }
        });
    }
}
