package com.focus.android;

import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class communityActivity extends AppCompatActivity {
    private Button ITtechnology;
    private Button lost;
    private TextView btn_icon;
    private Drawable drawable;
    private Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_community);

        //校内课程跳转IT技术
        ITtechnology = (Button) findViewById(R.id.ITtechnology);
        ITtechnology.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(communityActivity.this,ItTechnologyActivity.class);
                startActivity(intent);
            }
        });
        //校内课程跳转失物招领
        lost = (Button) findViewById(R.id.lost);
        lost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(communityActivity.this,LostAndFoundActivity.class);
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
