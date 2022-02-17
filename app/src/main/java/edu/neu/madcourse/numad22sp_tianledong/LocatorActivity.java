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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Arrays;

public class LocatorActivity extends AppCompatActivity {
    TextView latitude;
    TextView longitude;
    private FusedLocationProviderClient fusedLocationClient;
    private boolean failToast;

    private static final int REQUEST_LOCATION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);
        failToast = false;
        latitude = findViewById(R.id.latitudeVal);
        longitude = findViewById(R.id.longitudeVal);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_CODE);
        } else {
            getLocation();
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
            getLocation();
        }
    }

    @SuppressLint("SetTextI18n")
    private void getLocation() {
        if (checkLocationPermission()) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude.setText("" + location.getLatitude());
                                longitude.setText("" + location.getLongitude());
                            } else {
                                failToast = true;
                            }
                        }
                    });
            if (failToast) {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No permission", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(LocatorActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(LocatorActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}
