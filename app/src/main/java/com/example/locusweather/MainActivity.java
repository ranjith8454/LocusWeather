package com.example.locusweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.GeoPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText cityname;
    Button lookup;
    String lat,lon;
    String APIkey="89d37266f60ce20929813ef7b77c0520";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityname = (EditText) findViewById(R.id.edt_cityname);
        lookup = (Button) findViewById(R.id.getcityordinates);


        lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityname.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter City Name", Toast.LENGTH_SHORT).show();
                }else {
                    getcityCoOrdinates();
                }


            }
        });
    }

    private void getcityCoOrdinates() {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        try {
            address = coder.getFromLocationName(cityname.getText().toString(), 5);
            if (address.size()==0) {
                Toast.makeText(getApplicationContext(), "Please Enter Correct City Name", Toast.LENGTH_SHORT).show();
            }else{
                Address location = address.get(0);
                lat= String.valueOf(location.getLatitude());
                lon  = String.valueOf(location.getLongitude());
            //   Toast.makeText(getApplicationContext(), "latitude = "+lat+"\n"+"longitude= "+lon, Toast.LENGTH_SHORT).show();
                if (!lat.isEmpty()&&!lon.isEmpty()){
                    weatherreport();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void weatherreport() {

        StringRequest request=new StringRequest(Request.Method.GET, "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+APIkey, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               Log.e("response",response.toString());
                SharedPreferences sharedPreferences=getSharedPreferences("Report",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("response",response);
                editor.apply();
               Intent intent=new Intent(getApplicationContext(),WeatherList.class);
               startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
            }
        });
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}