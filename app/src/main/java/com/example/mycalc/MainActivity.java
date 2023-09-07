package com.example.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String strTemp = ""; // 入力保持
    private String work = "";
    private String display = ""; // 表示
    private String copy = "";

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

    public void functionOnClick(View v){
        String input = (String) ((Button) v).getText();

        switch (input) {
            case "AC":
                strTemp = "";
                work = "";
                displayValue.setText("0");
                break;
            case "C":
                strTemp = "";
                displayValue.setText("0");
                break;
            case "Copy":
                copy = strTemp;
                ((Button) v).setText("Past");
                break;
            case "Past":
                strTemp = copy;
                displayValue.setText(strTemp);
                break;
            case "BS":
                switch (strTemp){
                    case "":
                        displayValue.setText("0");
                        break;
                    default:
                        strTemp = strTemp.substring(0, strTemp.length() -1);
                        if (strTemp.equals("")) {
                            displayValue.setText("0");
                        } else {
                            displayValue.setText(strTemp);
                        }
                }
                break;
            default:
                break;
        }
    }

    public void operatorKeyOnClick(View v){
        String input = (String) ((Button) v).getText();
        switch (input) {
            default:
                break;
        }
    }
}