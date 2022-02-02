package edu.neu.madcourse.numad22sp_tianledong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import edu.neu.numad22sp_tianledong.R;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
    }


    private void setClickyButtonText(String buttonName) {
        TextView textView = (TextView) findViewById(R.id.clickyButtonText);
        textView.setText("Pressed: " + buttonName);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clickyButtonA:
                setClickyButtonText("A");
                break;
            case R.id.clickyButtonB:
                setClickyButtonText("B");
                break;
            case R.id.clickyButtonC:
                setClickyButtonText("C");
                break;
            case R.id.clickyButtonD:
                setClickyButtonText("D");
                break;
            case R.id.clickyButtonE:
                setClickyButtonText("E");
                break;
            case R.id.clickyButtonF:
                setClickyButtonText("F");
                break;
        }
    }
}

