package com.focus.android.main_tab;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.focus.android.Course;
import com.focus.android.CourseConver;
import com.focus.android.Courses;
import com.focus.android.DateGet;
import com.focus.android.R;
import com.focus.android.TypeTransition;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TableFragment extends Fragment {

    private String responseData_for_course;
    private String Data;

    private SharedPreferences pref;
    private SharedPreferences.Editor editer;

    private static final String ARG_PARAM1 = "param1";
    public static int studyweek;
    private static int weekday;
    private LinearLayout weekdayall;
    private TextView weekdayword;
    private TextView weekdaydate;
    private TextView name;
    private TextView beizhu;
    private BoomMenuButton bmb;
    private String [][][]classes = new String[7][5][7];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.table_fragment,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        bmb = (BoomMenuButton) getActivity().findViewById(R.id.bmb);
        NiceSpinner spinner = getActivity().findViewById(R.id.nice_spinner);
        //设置姓名
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        name = getActivity().findViewById(R.id.name);
        name.setText(pref.getString("name","error"));
        //设置备注信息
        beizhu = getActivity().findViewById(R.id.beizhu);
        beizhu.setText(pref.getString("beizhu","与服务器通讯错误"));
        studyweek = getStudyWeek();//获取周次
        weekday = DateGet.getTodayWeek();//获取星期日期
        //获取星期和日期显示模块的组件
        weekdayall = getActivity().findViewById(DataGenerator.WeekDaysAll[weekday-1]);
        weekdayword = getActivity().findViewById(DataGenerator.WeekDayWord[weekday-1]);
        weekdaydate = getActivity().findViewById(DataGenerator.WeekDayDate[weekday-1]);
        //设置spinner的position为周次-1
        spinner.setSelectedIndex(studyweek-1);
        //改变当日星期的显示
        weekdayall.setBackgroundColor(getResources().getColor(R.color.today_back_color));
        weekdayword.setTextColor(getResources().getColor(android.R.color.white));
        weekdaydate.setTextColor(getResources().getColor(android.R.color.white));
        //将本周的日期显示文本设置到UI组件中
        for (int i= 0;i<7;i++) {
            TextView weekdaytext = getActivity().findViewById(DataGenerator.WeekDayDate[i]);
            weekdaytext.setText(DataGenerator.StudyWeek[studyweek-1][i]);
        }
        //设置课表的显示
        setClass(studyweek);
        setCardOnClick();
        //设置spinner的选择响应事件
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

                if (position == studyweek-1){
                    weekdayall.setBackgroundColor(getResources().getColor(R.color.today_back_color));
                    weekdayword.setTextColor(getResources().getColor(android.R.color.white));
                    weekdaydate.setTextColor(getResources().getColor(android.R.color.white));
                    setClass(studyweek);
                    for (int i= 0;i<7;i++) {
                        TextView weekdaytext = getActivity().findViewById(DataGenerator.WeekDayDate[i]);
                        weekdaytext.setText(DataGenerator.StudyWeek[position][i]);
                    }
                }else{
                    setClass(position+1);
                    weekdayall.setBackgroundColor(getResources().getColor(android.R.color.white));
                    weekdayword.setTextColor(getResources().getColor(R.color.today_back_color));
                    weekdaydate.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    for (int i= 0;i<7;i++) {
                        TextView weekdaytext = getActivity().findViewById(DataGenerator.WeekDayDate[i]);
                        weekdaytext.setText(DataGenerator.StudyWeek[position][i]);
                    }
                }
            }
        });
        /*
         设置两个爆炸按钮，以及他们的点击事件
         */
        HamButton.Builder builder1 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {

                    }
                })
                .normalImageRes(R.drawable.like)
                .normalText("我不碰手机啦！")
                .subNormalText("进入专注模式")
                .normalColorRes(R.color.today_back_color);
        HamButton.Builder builder2 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                OkHttpClient client_for_course = new OkHttpClient();
                                RequestBody requestBody_for_course = new FormBody.Builder()
                                            .add("username", pref.getString("username",""))
                                            .add("password", pref.getString("password",""))
                                            .build();
                                Request request_for_course = new Request.Builder()
                                        .post(requestBody_for_course)
                                        .url("http://49.235.233.124:8080/get_schedule/")//更改为请求课表的url
                                        .build();
                                Response response_for_course = client_for_course.newCall(request_for_course).execute();
                                responseData_for_course = response_for_course.body().string();
                                    Log.d("TEST", "run: "+responseData_for_course);

                                JSONObject jsonObject = new JSONObject(responseData_for_course);
                                Data = jsonObject.getString("schedule_body");
                                    Log.d("TEST", "run: "+Data);
                                }catch (Exception e){
                                    e.printStackTrace();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(),"更新失败", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                                CourseConver cc = new CourseConver();
                                Course[]courses = cc.getCourses(Data);
                                Course.numOfcourse = courses.length;
                                LitePal.getDatabase();
                                //清除数据库
                                DataSupport.deleteAll(Courses.class);
                                //向本地数据库中添加课程信息
                                for (int i = 0;i<Course.numOfcourse;i++) {
                                    Courses percourses = new Courses();
                                    percourses.setCourse_name(courses[i].getCourse_name());
                                    Log.d("ceshi", "runname: "+courses[i].getCourse_name());
                                    percourses.setCourse_number(courses[i].getCourse_number());
                                    Log.d("ceshi", "runnumber: "+courses[i].getCourse_number());
                                    percourses.setCourse_address(courses[i].getCourse_address());
                                    percourses.setCourse_teacher(courses[i].getCourse_teacher());
                                    percourses.setSection_number(courses[i].getSection_number());
                                    percourses.setWeek(TypeTransition.inttostring(courses[i].getWeek()));//将整形数组转化为字符串
                                    percourses.setWeek_day(courses[i].getWeek_day());
                                    percourses.save();
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setClass(studyweek);
                                    }
                                });

                            }
                        }).start();

                    }
                })
                .normalImageRes(R.drawable.sync)
                .normalText("更新课表")
                .normalColorRes(R.color.today_back_color);
        bmb.addBuilder(builder1);
        bmb.addBuilder(builder2);

    }
    private void setCardOnClick(){
        for (int i=0;i<7;i++){
            for (int j=0;j<5;j++){
                final int weekday = i;
                final int sec = j;
                CardView cv = getActivity().findViewById(DataGenerator.AllClasses[i][j]);
                cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                        dialog.setTitle(classes[weekday][sec][0]);
                        dialog.setMessage("教师\t\t\t"+classes[weekday][sec][1]+"\n地点\t\t\t"+classes[weekday][sec][2]
                        +"\n编号\t\t\t"+classes[weekday][sec][3]+"\n周次\t\t\t"+classes[weekday][sec][4]);
                        dialog.setCancelable(true);
                        dialog.show();
                    }
                });
            }
        }
    }
    //课表显示函数
    private void setClass(int stuweek){
        for (int i = 0;i<7;i++){
            for (int j=0;j<5;j++){
                CardView viewNow = getActivity().findViewById(DataGenerator.AllClasses[i][j]);
                List<Courses> courses = DataSupport.where("week_day = ? and section_number = ?",String.valueOf(i+1),String.valueOf(j+1)).find(Courses.class);
                int size = courses.size();
                if (size == 0){
                    viewNow.setClickable(false);
                    viewNow.setVisibility(View.INVISIBLE);
                }else if (size == 1){
                    boolean isThisweek = false;
                    int []week = TypeTransition.stringtoint(courses.get(0).getWeek());//将字符串转换为整形数组
                    for (int k =0;k<week.length;k++){
                        if (week[k]==stuweek) {
                            isThisweek = true;
                            break;
                        }
                    }
                    if (isThisweek){
                        viewNow.setClickable(true);
                        viewNow.setVisibility(View.VISIBLE);
                        classes[i][j][0] = courses.get(0).getCourse_name();
                        classes[i][j][1] = courses.get(0).getCourse_teacher();
                        classes[i][j][2] = courses.get(0).getCourse_address();
                        classes[i][j][3] = courses.get(0).getCourse_number();
                        classes[i][j][4] = Arrays.toString(TypeTransition.stringtoint(courses.get(0).getWeek()));
                        classes[i][j][5] = String.valueOf(courses.get(0).getWeek_day());
                        classes[i][j][6] = String.valueOf(courses.get(0).getSection_number());
                        TextView csnow = getActivity().findViewById(DataGenerator.Classes[i][j]);
                        csnow.setText(classes[i][j][0]+"\n"+classes[i][j][1]+"\n@"+classes[i][j][2]);
                    }else {
                        viewNow.setClickable(false);
                        viewNow.setVisibility(View.INVISIBLE);
                    }
                }else{
                    boolean isThisweek = false;
                    for (int a=0;a<size;a++){
                        int []week = TypeTransition.stringtoint(courses.get(a).getWeek());
                        for (int k =0;k<week.length;k++){
                            if (week[k]==stuweek) {
                                isThisweek = true;
                                break;
                            }
                        }
                        if (isThisweek){
                            viewNow.setClickable(true);
                            viewNow.setVisibility(View.VISIBLE);
                            classes[i][j][0] = courses.get(a).getCourse_name();
                            classes[i][j][1] = courses.get(a).getCourse_teacher();
                            classes[i][j][2] = courses.get(a).getCourse_address();
                            classes[i][j][3] = courses.get(a).getCourse_number();
                            classes[i][j][4] = Arrays.toString(TypeTransition.stringtoint(courses.get(a).getWeek()));
                            classes[i][j][5] = String.valueOf(courses.get(a).getWeek_day());
                            classes[i][j][6] = String.valueOf(courses.get(a).getSection_number());
                            TextView csnow = getActivity().findViewById(DataGenerator.Classes[i][j]);
                            csnow.setText(classes[i][j][0]+"\n"+classes[i][j][1]+"\n@"+classes[i][j][2]);
                            break;
                        }
                    }
                    if (!isThisweek){
                        viewNow.setClickable(false);
                        viewNow.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    public static TableFragment newInstance(String param1) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    //获取周次的具体方法
    public static int getStudyWeek(){
        int studyweek;
        int day;
        String today = DateGet.getDateAll();
        for (studyweek = 0;studyweek<20;studyweek++){
            for (day = 0;day<7;day++){
                if (DataGenerator.StudyWeek[studyweek][day].equals(today)){
                    return studyweek+1;
                }
            }
        }
        return 0;
    }
}
