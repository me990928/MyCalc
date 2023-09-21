package com.example.mycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String strTemp = ""; // 入力保持
    private String work = "0";   // 記録
    private String copy = "";
    private int operator = 0;
    private int subOperator = 0;
    private boolean subReset = false;
    private boolean flag = false;

    public ArrayList<String> backCalc = new ArrayList<>();

    // API34対応用変数
    private int add = R.id.keypadAdd;
    private int sub = R.id.keypadSub;
    private int multi = R.id.keypadMulti;
    private int div = R.id.keypadDiv;
    private int equal = R.id.keypadEq;
    private int copyPast = R.id.keypadCopy;
    private int clear = R.id.keypadC;
    private int allClear = R.id.keypadAC;
    private int backSpace = R.id.keypadBS;
    private int random = R.id.random;

    TextView displayValue;
    TextView subDisplayView;

    String subText = "";
    String subAns = "";

    private int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayValue = findViewById(R.id.DisplayPanel);
        subDisplayView = findViewById(R.id.SubDisplayPanel);
        subDisplayView.setText("");

    }

    public void onClickBack(View v){
        Intent intent = new Intent(MainActivity.this, CalcBack.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("calcData", backCalc);
        intent.putExtra("mode", "back");
        startActivity(intent);
    }

    public void onClickBC(View v){
        Intent intent = new Intent(MainActivity.this, CalcBack.class);
        intent.putExtra("calcData", strTemp);
        intent.putExtra("mode", "bc");
        startActivity(intent);
    }

    public void onClickTheta(View v){
        Intent intent = new Intent(MainActivity.this, CalcBack.class);
        intent.putExtra("calcData", strTemp);
        intent.putExtra("mode", "Theta");
        startActivity(intent);
    }

    public void showDisplay(String input){
        try {
            input = threeDotNumber(input);
        } catch (Exception e) {
//            input = e.toString();
//            input = "";
        }
        if (strTemp.endsWith(".") & !input.endsWith(".")){
            input += ".";
        }
        Log.i("dot", strTemp + ":" + input);
        displayValue.setText(input);
    }

    public void showSubDisplay(String input){
        subText = String.format("%s %s ", subText,input);
        subDisplayView.setText(subText);
    }

    public String threeDotNumber(String input){
        DecimalFormat decimalFormat = new DecimalFormat("###,###.################");
        double num = Double.parseDouble(input);
        input = decimalFormat.format(num);
        return input;
    }

    public void numKeyOnClick(View v){
        String input = (String) ((Button) v).getText();

        if (subReset){
            subText = "";
//            showSubDisplay("");
            subReset = false;
        }

        if(strTemp.equals("0") & input.equals("0")){
            strTemp = input;
        }else{
            if (input.equals(".") & strTemp.equals("")){
                strTemp = "0.";
            }else {
                this.strTemp += input;
            }
        }

        if (strTemp.equals("0")){
            if(!input.equals(".") & !input.equals("0")){
                strTemp = input;
            }
        }

        showDisplay(strTemp);

    }

    public void functionOnClick(View v) {

        if (v.getId() == random){
            strTemp = getRandom(strTemp);
            showDisplay(strTemp);
        }

        if (v.getId() == allClear) {
            strTemp = "";
            work = "";
            subText = "";
            cont = 0;
            subDisplayView.setText("");
            operator = 0;
            showDisplay("0");
        }

        if (v.getId() == clear) {
            strTemp = "";
            showDisplay("0");
        }

        if (v.getId() == copyPast) {
            Button s = (Button) findViewById(copyPast);
            if (s.getText().equals("Copy")){
                copy = displayValue.getText().toString();
                ((Button) v).setText("Past");
            }else{
                strTemp = copy;
                if (strTemp.equals("")){
                    strTemp = "0";
                }
                showDisplay(strTemp);
                ((Button) v).setText("Copy");
            }
        }

        if (v.getId() == backSpace) {
            switch (strTemp) {
                case "":
                    showDisplay("0");
                    break;
                default:
                    strTemp = strTemp.substring(0, strTemp.length() - 1);
                    if (strTemp.equals("")) {
                        showDisplay("0");
                    } else {
                        showDisplay(strTemp);
                    }
            }
        }
    }

    public void operatorKeyOnClick(View v){

        showSubDisplay(displayValue.getText().toString());


        subOperator = v.getId();

        if (subOperator == add){
            if (subReset) {
                subText = "";
                showSubDisplay("Ans +");
                subReset = false;
            }
            else{
                showSubDisplay("+");
            }
        } else if (subOperator == sub) {
            if (subReset) {
                subText = "";
                showSubDisplay("Ans -");
                subReset = false;
            }else{
                showSubDisplay("-");
            }
        } else if (subOperator == multi) {
            if (subReset) {
                subText = "";
                showSubDisplay("Ans ×");
                subReset = false;
            }else{
                showSubDisplay("×");
            }
        } else if (subOperator == div) {
            if (subReset) {
                subText = "";
                showSubDisplay("Ans ÷");
                subReset = false;
            }else{
                showSubDisplay("÷");
            }
        } else if (subOperator == R.id.keypadEq) {
            showSubDisplay("=");
            subReset = true;
        }

        if (operator != 0) {
            // operator true
            if (strTemp.length() > 0){
                // not null
                work = doCalc();
                subAns = work;
//                work = work.replaceAll("\\.", "");
                showDisplay(work);
                cont++;
            }
        }else{
            // operator false
            if (strTemp.length() > 0){
                // not null
                work = strTemp;

            }
        }

        if (subOperator == R.id.keypadEq) {
            backCalc.add(String.format("%s %s \n",subText, work));
            Log.e("msg", backCalc.get(0));
        }

        strTemp = "";

        // set operator
        if (v.getId() == R.id.keypadEq){
            operator = 0;
        }else{
            operator = v.getId();
        }

    }

    private String doCalc(){
        try {

            BigDecimal bd1 = new BigDecimal(work);
            BigDecimal bd2 = new BigDecimal(strTemp);
            BigDecimal res = BigDecimal.ZERO;

            if (operator == add) {
                res = bd1.add(bd2);
            } else if (operator == sub) {
                res = bd1.subtract(bd2);
            } else if (operator == multi) {
                res = bd1.multiply(bd2);
            } else if (operator == div) {
                // stop divider zero
                if (!bd2.equals(BigDecimal.ZERO)) {
                    res = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP);
                } else {
                    Toast toast = Toast.makeText(this, "0で割れません", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            if (res.toString().indexOf(".") >= 0) {
                return res.toString().replaceAll("\\.0+$|0+$", "");
            } else {
                return res.toString();
            }
        } catch (Exception e){
            Log.e("Calculation", "割り算エラー: " + work);
            return e.toString();
        }
    }

    private String getRandom(String num){
        Random val = new Random();

        Integer minVal = 0;
        Integer maxVal;
        BigDecimal toInt = new BigDecimal(num);
        try {
            maxVal = toInt.intValue();
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "実行値エラー", Toast.LENGTH_SHORT);
            toast.show();
            return "0";
        }


        Integer res = val.nextInt(maxVal - minVal + 1) + minVal;

        return String.valueOf(res);

    }
}