package com.focus.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.change_user_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetisransfer();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void resetisransfer(){
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editer = pref.edit();
        editer.putBoolean("istransfer",true);
        editer.commit();
    }
}
