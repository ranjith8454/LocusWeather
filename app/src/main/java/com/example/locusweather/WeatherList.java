package com.example.locusweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherList extends AppCompatActivity {
    ListView listView;
    ImageView backbutton;
    ArrayList<WeatherReportObject> weatherReportObjectList=new ArrayList<>();
    Myadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list);
        listView=findViewById(R.id.waetherlist);
        backbutton=findViewById(R.id.img_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("Report",MODE_PRIVATE);
        String response=sharedPreferences.getString("response","");
        try {
            JSONObject object=new JSONObject(response);
            for (int i=1;i<=20;i++){
                JSONObject main=new JSONObject(object.getString("main"));
                JSONArray weather=object.getJSONArray("weather");
                JSONObject weather1=weather.getJSONObject(0);
                WeatherReportObject object1=new WeatherReportObject(weather1.getString("main"),main.getString("temp")+" F",weather1.getString("description"),main.getString("feels_like"));
                weatherReportObjectList.add(object1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter=new Myadapter(WeatherList.this, weatherReportObjectList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences preferences=getSharedPreferences("weatherdisplay",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("main",weatherReportObjectList.get(0).getMain());
                editor.putString("temp",weatherReportObjectList.get(0).getTemp());
                editor.putString("des",weatherReportObjectList.get(0).getDescription());
                editor.putString("feels",weatherReportObjectList.get(0).getFeelslike());
                editor.apply();
                Intent intent=new Intent(WeatherList.this,WeatherView.class);
                startActivity(intent);
            }
        });

    }
    public class Myadapter extends BaseAdapter{
       private Context context;
       private ArrayList<WeatherReportObject> arrayList;
       TextView main,temp;

        public Myadapter(Context context, ArrayList<WeatherReportObject> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= LayoutInflater.from(context).inflate(R.layout.weather_item,parent,false);
            main=convertView.findViewById(R.id.main);
            temp=convertView.findViewById(R.id.temp);
            main.setText(arrayList.get(position).getMain());
            temp.setText("Temp: "+arrayList.get(position).getTemp());

            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}