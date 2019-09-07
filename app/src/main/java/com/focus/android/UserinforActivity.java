package com.focus.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserinforActivity extends AppCompatActivity {
    private TextView btn_icon;
    private Drawable drawable;
    private Button imgreturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_userinfor);

        //个人资料左上角返回图标，点击返回个人中心界面
        imgreturn = (Button) findViewById(R.id.imgreturn);
        imgreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //返回按钮关联
        btn_icon = findViewById(R.id.imgreturn);
        drawable = getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight()); //设置图片的大小
        btn_icon.setCompoundDrawables(drawable,null,null,null);

        //设置按钮关联(没有功能)
        btn_icon = findViewById(R.id.imgsetting);
        drawable = getResources().getDrawable(R.drawable.ic_settings_black_24dp);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight()); //设置图片的大小
        btn_icon.setCompoundDrawables(null,null,drawable,null);

    }
}
