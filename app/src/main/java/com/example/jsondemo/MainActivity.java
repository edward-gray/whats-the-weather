package com.example.jsondemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    final private String WEATHER_APP_ID = "99d63250141fcf334a790a1e94efeb9f";

    EditText cityEditText;
    TextView weatherTextView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getWeather(View view) {
        String result = new DownloadWebContent().download(getUrlAsString());
            try {
                JSONObject jsonResult = new JSONObject(result);
                String weather = jsonResult.getString("weather");

                JSONArray jsonArray = new JSONArray(weather);
                for (int i=0; i < jsonArray.length(); i++) {
                    JSONObject insideArray = (JSONObject) jsonArray.get(i);
                    weatherTextView.setText(insideArray.get("description").toString().toUpperCase());
                }
                InputMethodManager inputManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                weatherTextView.setText("I couldn't find the city.\nPlease try again...");
                e.printStackTrace();
            }
        }

    private String getUrlAsString() {
        return "https://api.openweathermap.org/data/2.5/weather?q=" +
                cityEditText.getText().toString().toLowerCase().replaceAll(" ", "%20") +
                "&appid=" +
                WEATHER_APP_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = (EditText) findViewById(R.id.cityEditText);
        weatherTextView = (TextView) findViewById(R.id.weatherTextView);
        weatherTextView.setText("");
    }
}
