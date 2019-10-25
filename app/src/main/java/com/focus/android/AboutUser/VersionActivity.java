package com.focus.android.AboutUser;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.R;

public class VersionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//隐藏顶部的bar；
            actionBar.hide();
        }
    }
}