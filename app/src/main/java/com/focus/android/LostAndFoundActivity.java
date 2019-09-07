package com.focus.android;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LostAndFoundActivity extends AppCompatActivity {
    private Button schoolcourse ;
    private Button ITtechnology;
    private TextView btn_icon;
    private Drawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lostandfound);

        //失物招领跳转校内课程
        schoolcourse = (Button) findViewById(R.id.schoolcourse);
        schoolcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostAndFoundActivity.this,communityActivity.class);
                startActivity(intent);
            }
        });

        //失物招领跳转IT技术
        ITtechnology = (Button) findViewById(R.id.ITtechnology);
        ITtechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LostAndFoundActivity.this,ItTechnologyActivity.class);
                startActivity(intent);
            }
        });

        //搜索按钮关联
        btn_icon  = findViewById(R.id.search);
        drawable = getResources().getDrawable(R.drawable.ic_search_black_24dp);
        drawable.setBounds(10,0,drawable.getMinimumWidth(),drawable.getMinimumHeight()); //设置图片的大小
        btn_icon.setCompoundDrawables(drawable,null,null,null);
    }

}
