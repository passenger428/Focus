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

import com.focus.android.Course;
import com.focus.android.Courses;
import com.focus.android.R;
import com.focus.android.TypeTransition;
import com.focus.android.main_tab.BottomTabLayoutActivity;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

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

        //验证之前是否登陆验证通过，如果之前有通过，则跳向主界面
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editer = pref.edit();
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
                //获取输入框中的账号密码
                user_name = usernameEdit.getText().toString();
                pass_word = passwordEdit.getText().toString();
                //设置两个通信结果字符串的初始化值
                String responseData = new String("true");
                String responseData_for_course = new String("initial");
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
                    /*try{
                        OkHttpClient client_for_course = new OkHttpClient();
                        Request request_for_course = new Request.Builder()
                                .url("")//添加请求课表的url
                                .build();
                        Response response_for_course = client_for_course.newCall(request_for_course).execute();
                        responseData_for_course = response_for_course.body().string();

                    }catch (Exception e){
                        e.printStackTrace();
                    }*/
                    Course []courses = Course.getCourses(responseData_for_course);
                    LitePal.getDatabase();
                    //清除数据库
                    DataSupport.deleteAll(Courses.class);
                    //向本地数据库中添加课程信息
                    for (int i = 0;i<Course.numOfcourse;i++){
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
