package com.focus.android.AboutUser;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private EditText nickname;
    private EditText sex;
    private EditText major;
    private Button register;
    private String nicknametext;
    private String sextext;
    private String majortext;
    private String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nickname = findViewById(R.id.nickname);
        sex = findViewById(R.id.sex);
        major = findViewById(R.id.major);
        register = findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nicknametext = nickname.getText().toString();
                sextext = sex.getText().toString();
                majortext = major.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("nickname", nicknametext)
                                    .add("sex", sextext)
                                    .add("major",majortext)
                                    .add("name",pref.getString("name","null"))
                                    .add("studynumber",pref.getString("username","null"))
                                    .build();
                            Request request = new Request.Builder()
                                    .post(requestBody)
                                    .url("http://49.235.233.124:8080/login/")
                                    .build();
                            Response response = client.newCall(request).execute();
                            responseData = response.body().string();
                            if (responseData.equals("true")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                    }
                }).start();

            }
        });
    }
}
