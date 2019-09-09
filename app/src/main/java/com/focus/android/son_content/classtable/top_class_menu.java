package com.focus.android.son_content.classtable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.focus.android.R;
import com.focus.android.DateGet;

public class top_class_menu extends LinearLayout {
    public top_class_menu(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.top_class_menu,this);
        set_top_textview_date(DateGet.getWeekDay());
        set_textview_text(R.id.topclassmenu_text_month,DateGet.getMonth());
    }
    public void set_textview_text(int TextViewPOS,String text){
        TextView tx = findViewById(TextViewPOS);
        tx.setText(text);
    }
    public void set_top_textview_date(String[] dateGroup){
        set_textview_text(R.id.week_Monday,dateGroup[0]);
        set_textview_text(R.id.week_Tuesday,dateGroup[1]);
        set_textview_text(R.id.week_Wednesday,dateGroup[2]);
        set_textview_text(R.id.week_thursday,dateGroup[3]);
        set_textview_text(R.id.week_Friday,dateGroup[4]);
        set_textview_text(R.id.week_saturday,dateGroup[5]);
        set_textview_text(R.id.week_sunday,dateGroup[6]);

        LinearLayout tcm_content;tcm_content = findViewById(set_week_key());
        tcm_content.setBackgroundColor(getResources().getColor(R.color.today_back_color));
    }
    public int set_week_key(){
        int week = DateGet.getToday();
        int today_key = 0;
        switch (week){
            case 1:
                today_key = R.id.tcm_content_sunday;
                break;
            case 2:
                today_key = R.id.tcm_content_monday;
                break;
            case 3:
                today_key = R.id.tcm_content_tuesday;
                break;
            case 4:
                today_key = R.id.tcm_content_wendesday;
                break;
            case 5:
                today_key = R.id.tcm_content_thursday;
                break;
            case 6:
                today_key = R.id.tcm_content_friday;
                break;
            case 7:
                today_key = R.id.tcm_content_saturday;
                break;
        }
        return today_key;
    }


}
