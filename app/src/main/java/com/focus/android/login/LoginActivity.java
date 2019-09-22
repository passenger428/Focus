package com.focus.android.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.R;
import com.focus.android.main_tab.BottomTabLayoutActivity;

public class LoginActivity extends AppCompatActivity {
    private String user_name;
    private String pass_word;
    private SharedPreferences pref;
    private SharedPreferences.Editor editer;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
    private Button Login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//隐藏顶部的bar；
            actionBar.hide();
        }
        //获取一系列组件

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editer = pref.edit();
        if (!pref.getBoolean("istransfer",true)){
            Intent intent = new Intent(LoginActivity.this,BottomTabLayoutActivity.class);
            startActivity(intent);
        }
        usernameEdit = findViewById(R.id.et_login_username);
        passwordEdit = findViewById(R.id.et_login_pwd);
        rememberPass = findViewById(R.id.cb_remember_login);
        Login_button = (Button)findViewById(R.id.bt_login_submit);
        boolean isRemember = pref.getBoolean("remember_password",false);//获取SharedPreferences中的remember_password的值
        if (isRemember){
            user_name = pref.getString("username","");
            pass_word = pref.getString("password","");
            usernameEdit.setText(user_name);
            passwordEdit.setText(pass_word);
            rememberPass.setChecked(true);
        }
        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name = usernameEdit.getText().toString();
                pass_word = passwordEdit.getText().toString();
                String responseData = new String("true");
                /*try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", user_name)
                            .add("password", pass_word)
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("")
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();

                }catch(Exception e){
                    e.printStackTrace();
                }*/
                if (responseData.equals("true")){
                    if (rememberPass.isChecked()) {
                        editer.putBoolean("remember_password", true);
                        editer.putString("username", user_name);
                        editer.putString("password", pass_word);
                    }else{
                        editer.clear();
                    }
                    editer.putBoolean("istransfer",false);
                    editer.apply();
                    Intent intent = new Intent(LoginActivity.this, BottomTabLayoutActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"教务系统账号或密码不正确",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
