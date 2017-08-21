package com.cdemo.exactarithmetic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView addText, subText, mulText, divText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addText = (TextView) findViewById(R.id.add_text);
        subText = (TextView) findViewById(R.id.sub_text);
        mulText = (TextView) findViewById(R.id.mul_text);
        divText = (TextView) findViewById(R.id.div_text);
        addText.setText("加 运算结果 " + addArith(0.32, 0.45));
        subText.setText("减 运算结果 " + subArith(0.67, 0.33));
        mulText.setText("乘 运算结果 " + mulArith(0.91, 0.25));
        divText.setText("除 运算结果 " + divArith(0.37, 0.75));
    }

    private double addArith(double d1, double d2) {
        return Arith.add(d1, d2);
    }

    private double subArith(double d1, double d2) {
        return Arith.sub(d1, d2);
    }

    private double mulArith(double d1, double d2) {
        return Arith.mul(d1, d2);
    }

    private double divArith(double d1, double d2) {
        try {
            return Arith.div(d1, d2, 3);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
