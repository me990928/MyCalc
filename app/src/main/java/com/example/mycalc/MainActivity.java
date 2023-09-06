package com.example.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String strTemp = ""; // 入力保持
    private String display = ""; // 表示

    TextView displayValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayValue = findViewById(R.id.DisplayPanel);

    }

    public void numKeyOnClick(View v){
        String input = (String) ((Button) v).getText();

        this.strTemp += input;

        displayValue.setText(this.strTemp);
    }

}