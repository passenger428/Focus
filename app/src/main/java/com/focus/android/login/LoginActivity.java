package com.focus.android.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.Course;
import com.focus.android.Courses;
import com.focus.android.R;
import com.focus.android.TypeTransition;
import com.focus.android.main_tab.BottomTabLayoutActivity;

import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private String responseData_for_beizhu;
    private JSONObject jsonObject;
    private JSONObject jsonObject_for_two;
    private String responseData ;
    private String responseData_for_course ;
    private String responseData_for_two;
    private boolean tof;
    private String user_name;
    private String pass_word;
    private SharedPreferences pref;
    private SharedPreferences.Editor editer;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
    private Button Login_button;
    private Boolean ToF;
    private void putToDB(){
        Course[]courses = Course.getCourses(responseData_for_course);
        LitePal.getDatabase();
        //清除数据库
        DataSupport.deleteAll(Courses.class);
        //向本地数据库中添加课程信息
        for (int i = 0;i<Course.numOfcourse;i++) {
            Courses percourses = new Courses();
            percourses.setCourse_name(courses[i].course_name);
            percourses.setCourse_number(courses[i].course_number);
            percourses.setCourse_address(courses[i].course_address);
            percourses.setCourse_teacher(courses[i].course_teacher);
            percourses.setSection_number(courses[i].section_number);
            percourses.setWeek(TypeTransition.inttostring(courses[i].week));//将整形数组转化为字符串
            percourses.setWeek_day(courses[i].week_day);
            percourses.save();
        }


            if (rememberPass.isChecked()) {
                editer.putBoolean("remember_password", true);

            }else{
                editer.clear();
            }
            editer.putBoolean("istransfer",false);
            editer.putString("username", user_name);
            editer.putString("password", pass_word);
            try {
                editer.putString("name", jsonObject.getString("username"));
                Log.d("TEST", "putToDB: "+pref.getString("name","error"));
                editer.putString("beizhu",responseData_for_beizhu);
            }catch (Exception e){
                e.printStackTrace();
            }
            editer.apply();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LoginActivity.this, BottomTabLayoutActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    private void handleResult(){
        if (tof){

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try{
                        OkHttpClient client_for_course = new OkHttpClient();
                        RequestBody requestBody_for_course = new FormBody.Builder()
                                .add("username", user_name)
                                .add("password", pass_word)
                                .build();
                        Request request_for_course = new Request.Builder()
                                .post(requestBody_for_course)
                                .url("http://49.235.233.124:8080/get_schedule/")//更改为请求课表的url
                                .build();
                        Response response_for_course = client_for_course.newCall(request_for_course).execute();
                        responseData_for_two = response_for_course.body().string();
                        Log.d("kebiao", "run: "+responseData_for_two);
                        jsonObject_for_two = new JSONObject(responseData_for_two);
                        responseData_for_course = jsonObject_for_two.getString("schedule_body");
                        responseData_for_beizhu = jsonObject_for_two.getString("schedule_extra");

                        putToDB();

                    }catch (Exception e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"请求课表信息失败",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }).start();


        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this,"教务系统账号或密码不正确",Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//隐藏顶部的bar；
            actionBar.hide();
        }

        //验证之前是否登陆验证通过，如果之前有通过，则跳向主界面
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editer = pref.edit();
        ToF = false;
        if (!pref.getBoolean("istransfer",true)){
            Intent intent = new Intent(LoginActivity.this,BottomTabLayoutActivity.class);
            startActivity(intent);
            finish();
        }

        //获取一系列组件
        usernameEdit = findViewById(R.id.et_login_username);
        passwordEdit = findViewById(R.id.et_login_pwd);
        rememberPass = findViewById(R.id.cb_remember_login);
        Login_button = (Button)findViewById(R.id.bt_login_submit);
        //如果上一次勾选了记住密码，则直接从存储文件中导入账号密码
        boolean isRemember = pref.getBoolean("remember_password",false);//获取SharedPreferences中的remember_password的值
        if (isRemember){
            user_name = pref.getString("username","");
            pass_word = pref.getString("password","");
            usernameEdit.setText(user_name);
            passwordEdit.setText(pass_word);
            rememberPass.setChecked(true);
        }
        //登陆按钮的响应事件
        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToF = true;
                //获取输入框中的账号密码

                user_name = usernameEdit.getText().toString();
                pass_word = passwordEdit.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("username", user_name)
                                    .add("password", pass_word)
                                    .build();
                            Request request = new Request.Builder()
                                    .post(requestBody)
                                    .url("http://49.235.233.124:8080/login/")
                                    .build();
                            Response response = client.newCall(request).execute();
                            responseData = response.body().string();
                            Log.d("LOGINTEST", "run: "+responseData);
                            jsonObject = new JSONObject(responseData);
                            tof = jsonObject.getBoolean("login_status");
                            Log.d("LOGINTEST", "run: "+tof);
                            handleResult();

                        }catch(Exception e){
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"无法连接服务器",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();


            }
        });

    }





}
