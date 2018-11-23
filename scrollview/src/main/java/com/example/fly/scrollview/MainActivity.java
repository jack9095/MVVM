package com.example.fly.scrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String reservation = "null";
        if (!TextUtils.isEmpty(reservation) && !"null".equals(reservation)) {
//        if (!TextUtils.isEmpty(reservation)) {
            Log.e("MainActivity",reservation);
        }else{
            Log.e("MainActivity","----- else ----");
        }
    }
}
