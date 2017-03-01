package com.example.maxim.musify.activities;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.maxim.musify.R;
import com.example.maxim.musify.bindings.BindingManager;
import com.example.maxim.musify.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityMainBinding binding  =  DataBindingUtil.setContentView(this,R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        BindingManager manager = new BindingManager(this);
        binding.setBind(manager);
    }
}
