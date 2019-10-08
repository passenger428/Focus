package com.focus.android.main_tab;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.focus.android.Courses;
import com.focus.android.DateGet;
import com.focus.android.R;
import com.focus.android.TypeTransition;
import com.focus.android.login.LoginActivity;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.List;

public class TableFragment extends Fragment {

    private SharedPreferences pref;
    private SharedPreferences.Editor editer;

    private static final String ARG_PARAM1 = "param1";
    public static int studyweek;
    private static int weekday;
    private LinearLayout weekdayall;
    private TextView weekdayword;
    private TextView weekdaydate;
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
                        resetisransfer();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
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
                            classes[i][j][0] = courses.get(0).getCourse_name();
                            classes[i][j][1] = courses.get(0).getCourse_teacher();
                            classes[i][j][2] = courses.get(0).getCourse_address();
                            classes[i][j][3] = courses.get(0).getCourse_number();
                            classes[i][j][4] = Arrays.toString(TypeTransition.stringtoint(courses.get(0).getWeek()));
                            classes[i][j][5] = String.valueOf(courses.get(0).getWeek_day());
                            classes[i][j][6] = String.valueOf(courses.get(0).getSection_number());
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
    //重置是否通过验证的存储标记
    public void resetisransfer(){
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editer = pref.edit();
        editer.putBoolean("istransfer",true);
        editer.commit();
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
