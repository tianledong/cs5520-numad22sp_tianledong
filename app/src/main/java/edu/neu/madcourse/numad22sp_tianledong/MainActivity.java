package edu.neu.madcourse.numad22sp_tianledong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.neu.numad22sp_tianledong.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aboutButton:
                Intent intentInfo = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intentInfo);
                break;
            case R.id.clickyButton:
                Intent intentClicky = new Intent(MainActivity.this, ClickyActivity.class);
                startActivity(intentClicky);
                break;
            case R.id.linkCollectorButton:
                Intent intentLinkCollector = new Intent(MainActivity.this, LinkCollectorActivity.class);
                startActivity(intentLinkCollector);
                break;
            case R.id.locatorButton:
                Intent intentLocator = new Intent(MainActivity.this, LocatorActivity.class);
                startActivity(intentLocator);
                break;
        }
    }
}