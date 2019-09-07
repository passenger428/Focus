package com.focus.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    }


}
