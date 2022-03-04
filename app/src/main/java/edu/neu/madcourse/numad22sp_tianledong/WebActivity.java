package edu.neu.madcourse.numad22sp_tianledong;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import edu.neu.numad22sp_tianledong.R;

public class WebActivity extends AppCompatActivity {
    private EditText latitude;
    private EditText longitude;
    private Button okButton;
    private static final String TAG = "WebActivity";
    private TextView airQualityValue;
    private TextView coValue;
    private TextView pm25Value;
    private ProgressBar progressBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        latitude = findViewById(R.id.webLatitude);
        longitude = findViewById(R.id.webLongitude);
        okButton = findViewById(R.id.webOkButton);
        airQualityValue = findViewById(R.id.webResultValue);
        coValue = findViewById(R.id.webResultCoValue);
        pm25Value = findViewById(R.id.webResultPM2_5Value);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
//        imageView = findViewById(R.id.webImageView);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                PingWebServiceTask pingWebServiceTask = new PingWebServiceTask();
                if (!latitude.getText().toString().isEmpty() && !longitude.getText().toString().isEmpty()) {
                    String url = "https://api.openweathermap.org/data/2.5/air_pollution?lat=" + latitude.getText().toString() +
                            "&lon=" + longitude.getText().toString() + "&appid=294959a2fda36d366b0e3b9fa9b16380";
                    pingWebServiceTask.execute(url);
                } else {
                    Toast.makeText(getApplication(), "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

        });
    }


    private void setData(String airQualityValue, String coValue, String pm25Value) {
        String airQuality = "";
        switch (airQualityValue) {
            case "1":
                airQuality = "Good";
                break;
            case "2":
                airQuality = "Fair";
                break;
            case "3":
                airQuality = "Moderate";
                break;
            case "4":
                airQuality = "Poor";
                break;
            case "5":
                airQuality = "Very Poor";
                break;
        }
        this.airQualityValue.setText(airQuality);
        this.coValue.setText(coValue);
        this.pm25Value.setText(pm25Value);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    private class PingWebServiceTask extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jObject = new JSONObject();
            try {
                URL url = new URL(params[0]);
                String resp = NetworkUtil.httpResponse(url);
                jObject = new JSONObject(resp);
                return jObject;
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, "IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "JSONException");
                e.printStackTrace();
            }
            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            String result;
            String coResult;
            String pm25Result;
            JSONArray resultArray;
            JSONObject components = new JSONObject();

            try {
                resultArray = jObject.getJSONArray("list");
                result = resultArray.getJSONObject(0).getJSONObject("main").getString("aqi");
                components = resultArray.getJSONObject(0).getJSONObject("components");
                coResult = components.getString("co");
                pm25Result = components.getString("pm2_5");
                setData(result, coResult, pm25Result);
            } catch (JSONException e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
