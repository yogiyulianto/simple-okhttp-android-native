package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    String temp;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView img = findViewById(R.id.img);
        final TextView tvTemp = findViewById(R.id.temp);
        final TextView tvPressure = findViewById(R.id.pressure);

        String url = "https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";

        OkHttpClient http = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        http.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(MainActivity.this, "tidak terhubung dengan server", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String restData = response.body().string();
                Log.v("berhasil", restData);

                try {
                    JSONObject jsonObject = new JSONObject(restData);
                    final JSONArray arrayWeather = jsonObject.getJSONArray("weather");
                    final JSONObject objWeather = new JSONObject(arrayWeather.get(0).toString());

                    String tmp = objWeather.getString("main");
                    desc = objWeather.getString("description");
                    Log.v("berhasil", tmp);
                    temp = tmp;

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            tvTemp.setText(temp);
                            tvPressure.setText(desc);

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
