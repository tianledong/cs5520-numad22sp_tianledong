package edu.neu.madcourse.numad22sp_tianledong;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import edu.neu.numad22sp_tianledong.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Arrays;

public class LocatorActivity extends AppCompatActivity {
    TextView latitude;
    TextView longitude;
    private static final int REQUEST_LOCATION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);

        latitude = findViewById(R.id.latitudeVal);
        longitude = findViewById(R.id.longitudeVal);
        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_CODE);
        } else {
            getLocationByNetwork();
        }
    }
    /*
     * When permission grants
     * Reference: https://stackoverflow.com/questions/52997092/requestpermission-how-to-wait-until-granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
            getLocationByNetwork();
        }
    }

    @SuppressLint("SetTextI18n")
    private void getLocationByNetwork() {
        if (checkLocationPermission()) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location GPS = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (GPS == null) {
                Toast.makeText(this, "Unable to get location by GPS", Toast.LENGTH_SHORT).show();
            } else {
                latitude.setText("" + GPS.getLatitude());
                longitude.setText("" + GPS.getLongitude());
            }
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(LocatorActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(LocatorActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
