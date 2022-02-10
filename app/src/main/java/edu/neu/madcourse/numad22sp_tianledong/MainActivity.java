package edu.neu.madcourse.numad22sp_tianledong;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
                Intent intent_info = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent_info);
                break;
            case R.id.clickyButton:
                Intent intent_clicky = new Intent(MainActivity.this, ClickyActivity.class);
                startActivity(intent_clicky);
                break;
        }
    }
}