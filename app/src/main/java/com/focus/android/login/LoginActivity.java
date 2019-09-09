package com.focus.android.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.focus.android.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        //-------用于测试主界面----------//
       Button Login_button = (Button)findViewById(R.id.bt_login_submit);
        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"此处登录按钮还未添加相关验证逻辑，直接跳转到主界面",Toast.LENGTH_LONG).show();
                Intent Main_Sur = new Intent("com.Focus.MainSurface");
                startActivity(Main_Sur);
            }
        });
    }
}
