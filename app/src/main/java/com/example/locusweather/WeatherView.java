package com.example.locusweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherView extends AppCompatActivity {
    TextView temp,desc,main,feelslike;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_view);
        temp=findViewById(R.id.tempview);
        desc=findViewById(R.id.desc);
        main=findViewById(R.id.mainview);
        feelslike=findViewById(R.id.feelslike);
        back=findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences preferences=getSharedPreferences("weatherdisplay",MODE_PRIVATE);
        temp.setText(preferences.getString("temp",""));
        feelslike.setText("Feels like: "+preferences.getString("feels","")+"F");
        desc.setText(preferences.getString("des",""));
        main.setText(preferences.getString("main",""));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}