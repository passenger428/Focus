package com.focus.android.AboutUser;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.R;

import org.json.JSONObject;

public class UserInformationActivity extends AppCompatActivity {
    private TextView name;
    private TextView sex;
    private TextView nickname;

    private TextView study_number;
    private TextView major;
    private SharedPreferences pref;
    private String responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//隐藏顶部的bar；
            actionBar.hide();
        }
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        nickname = findViewById(R.id.nickname);
        study_number = findViewById(R.id.study_number);
        major = findViewById(R.id.major);
        responseData = "null";//服务器恢复正常时删除
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username", pref.getString("username","error"))
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("http://49.235.233.124:8080/login/")
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();*/
                    if (responseData.equals("null")){
                        name.setText("未注册");
                        sex.setText("未注册");
                        nickname.setText("未注册");
                        study_number.setText("未注册");
                        major.setText("未注册");
                    }else{
                        JSONObject jsonObject = new JSONObject(responseData);
                        name.setText(jsonObject.getString("name"));
                        sex.setText(jsonObject.getString("sex"));
                        nickname.setText(jsonObject.getString("nickname"));
                        study_number.setText(jsonObject.getString("study_number"));
                        major.setText(jsonObject.getString("major"));
                    }
                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UserInformationActivity.this,"无法连接服务器",Toast.LENGTH_LONG).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
