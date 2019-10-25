package com.focus.android.AboutUser;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.R;

import org.json.JSONObject;

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
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        url = "http://49.235.233.124:8080/sign_in/";
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//隐藏顶部的bar；
            actionBar.hide();
        }
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        nickname = findViewById(R.id.nickname);
        sex = findViewById(R.id.sex);
        major = findViewById(R.id.major);
        register = findViewById(R.id.bt_register);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("study_number", pref.getString("username","error"))
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("http://49.235.233.124:8080/get_user_message/")
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    Log.d("TEST", "run: "+responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    if (jsonObject.getBoolean("search_status")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                register.setText("修改");
                                url = "http://49.235.233.124:8080/edit_user_message/";
                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
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
                                    .add("study_number",pref.getString("username","null"))
                                    .build();
                            Request request = new Request.Builder()
                                    .post(requestBody)
                                    .url(url)
                                    .build();
                            Response response = client.newCall(request).execute();
                            responseData = response.body().string();
                            final JSONObject jsonObject = new JSONObject(responseData);
                            if (jsonObject.getBoolean("sign_in_status")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Toast.makeText(RegisterActivity.this, jsonObject.getString("sign_in_message"), Toast.LENGTH_LONG).show();
                                            finish();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
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
