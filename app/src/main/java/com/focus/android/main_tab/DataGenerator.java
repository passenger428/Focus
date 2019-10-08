package com.focus.android.main_tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.focus.android.R;

public class DataGenerator {
    public static final int []mTabRes = new int[]{R.drawable.table_selector,R.drawable.unordered_list_selector,R.drawable.user_selector};
    public static final int []mTabResPressed = new int[]{R.drawable.table_selected,R.drawable.unordered_list_selected,R.drawable.user_selected};
    public static final String []mTabTitle = new String[]{"课表","社区","我的"};
    public static final int []WeekDaysAll = new int[]{R.id.mon,R.id.tues,R.id.wednes,R.id.thurs,R.id.fri,R.id.satur,R.id.sun};
    public static final int []WeekDayWord = new int[]{R.id.monday,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday,R.id.saturday,R.id.sunday};
    public static final int []WeekDayDate = new int[]{R.id.monday_date,R.id.tuesday_date,R.id.wednesday_date,R.id.thursday_date,R.id.friday_date,R.id.saturday_date,R.id.sunday_date};
    public static final int [][]Classes = new int[][]{
            {R.id.class1_1,R.id.class1_2,R.id.class1_3,R.id.class1_4,R.id.class1_5},
            {R.id.class2_1,R.id.class2_2,R.id.class2_3,R.id.class2_4,R.id.class2_5},
            {R.id.class3_1,R.id.class3_2,R.id.class3_3,R.id.class3_4,R.id.class3_5},
            {R.id.class4_1,R.id.class4_2,R.id.class4_3,R.id.class4_4,R.id.class4_5},
            {R.id.class5_1,R.id.class5_2,R.id.class5_3,R.id.class5_4,R.id.class5_5},
            {R.id.class6_1,R.id.class6_2,R.id.class6_3,R.id.class6_4,R.id.class6_5},
            {R.id.class7_1,R.id.class7_2,R.id.class7_3,R.id.class7_4,R.id.class7_5}};
    public static final int [][]AllClasses = new int[][]{
            {R.id.allclass1_1,R.id.allclass1_2,R.id.allclass1_3,R.id.allclass1_4,R.id.allclass1_5},
            {R.id.allclass2_1,R.id.allclass2_2,R.id.allclass2_3,R.id.allclass2_4,R.id.allclass2_5},
            {R.id.allclass3_1,R.id.allclass3_2,R.id.allclass3_3,R.id.allclass3_4,R.id.allclass3_5},
            {R.id.allclass4_1,R.id.allclass4_2,R.id.allclass4_3,R.id.allclass4_4,R.id.allclass4_5},
            {R.id.allclass5_1,R.id.allclass5_2,R.id.allclass5_3,R.id.allclass5_4,R.id.allclass5_5},
            {R.id.allclass6_1,R.id.allclass6_2,R.id.allclass6_3,R.id.allclass6_4,R.id.allclass6_5},
            {R.id.allclass7_1,R.id.allclass7_2,R.id.allclass7_3,R.id.allclass7_4,R.id.allclass7_5}};
    public static final String [][]StudyWeek = new String[][]{
            {"9.2","9.3","9.4","9.5","9.6","9.7","9.8"},
            {"9.9","9.10","9.11","9.12","9.13","9.14","9.15"},
            {"9.16","9.17","9.18","9.19","9.20","9.21","9.22"},
            {"9.23","9.24","9.25","9.26","9.27","9.28","9.29"},
            {"9.30","10.1","10.2","10.3","10.4","10.5","10.6"},
            {"10.7","10.8","10.9","10.10","10.11","10.12","10.13"},
            {"10.14","10.15","10.16","10.17","10.18","10.19","10.20"},
            {"10.21","10.22","10.23","10.24","10.25","10.26","10.27"},
            {"10.28","10.29","10.30","10.31","11.1","11.2","11.3"},
            {"11.4","11.5","11.6","11.7","11.8","11.9","11.10"},
            {"11.11","11.12","11.13","11.14","11.15","11.16","11.17"},
            {"11.18","11.19","11.20","11.21","11.22","11.23","11.24"},
            {"11.25","11.26","11.27","11.28","11.29","11.30","12.1"},
            {"12.2","12.3","12.4","12.5","12.6","12.7","12.8"},
            {"12.9","12.10","12.11","12.12","12.13","12.14","12.15"},
            {"12.16","12.17","12.18","12.19","12.20","12.21","12.22"},
            {"12.23","12.24","12.25","12.26","12.27","12.28","12.29"},
            {"12.30","12.31","1.1","1.2","1.3","1.4","1.5"},
            {"1.6","1.7","1.8","1.9","1.10","1.11","1.12"}};
    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[3];
        fragments[0] = TableFragment.newInstance(from);
        fragments[1] = CommunityFragment.newInstance(from);
        fragments[2] = UserFragment.newInstance(from);
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
