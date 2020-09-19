package com.maniu.mn_2020_4_20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.maniu.arouter.ARouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpActivity(View view) {
        ARouter.getInstance().jumpActivity("login/login",this);
    }
}