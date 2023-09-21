package com.example.mycalc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CalcBack extends AppCompatActivity {

    TextView mainContents;
    Intent recevedIntent;
    ArrayList<String> message;
    String bcVal = "";
    String ThetaVal = "";
    String backCalc = "";
    String mode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backcalc);

        mainContents = findViewById(R.id.debug);

        recevedIntent = getIntent();

        mode = recevedIntent.getStringExtra("mode");

        switch (mode){
            case "back":
                showBackCalc();
                break;
            case "bc":
                showBc();
                break;
            case "Theta":
                showTheta();
            default:
                break;
        }
    }

    public void showBackCalc(){

        message = recevedIntent.getStringArrayListExtra("calcData");

        for (String item : message){
            backCalc +=  item + "\n";
            Log.i("msg", item);
        }

        mainContents.setText(backCalc);

    }

    public void showBc(){
        String context = "";
        bcVal = recevedIntent.getStringExtra("calcData");

        context += "10進法："+bcVal+"\n";

        Integer num;

        try {
            num = Integer.parseInt(bcVal);
        }catch (Exception e){
            mainContents.setText("実行値エラー");
            Toast toast = Toast.makeText(this, "整数を入力してください", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // 二進化
        String binary = Integer.toBinaryString(num);
        context += "2進法："+binary+"\n";

        // 8進化
        String Oct = Integer.toOctalString(num);
        context += "8進法："+Oct+"\n";

        // 16進化
        String Hex = Integer.toHexString(num);
        context += "16進法："+Hex+"\n";

        mainContents.setText(context);
    }
    public void showTheta(){
        String context = "";
        ThetaVal = recevedIntent.getStringExtra("calcData");

        context += "元の数字："+ThetaVal+"\n";

        Double num;

        try {
            num = Double.parseDouble(ThetaVal);
        }catch (Exception e){
            mainContents.setText("実行値エラー");
            Toast toast = Toast.makeText(this, "数値を入力してください", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // sin
        double sin = Math.sin(Math.toRadians(num));
        context += "Sin：" + toScale(3,String.valueOf(sin)) + "\n";

        // cos
        double cos = Math.cos(Math.toRadians(num));
        context += "Cos：" + toScale(3,String.valueOf(cos)) + "\n";

        // tan
        double tan = Math.tan(Math.toRadians(num));
        context += "Tan：" + toScale(3,String.valueOf(tan)) + "\n";


        mainContents.setText(context);
    }

    private String toScale(Integer scale, String val){
        BigDecimal res = new BigDecimal(val);
        BigDecimal ans = res.setScale(scale, RoundingMode.HALF_UP);

        return ans.toString();
    }
}
