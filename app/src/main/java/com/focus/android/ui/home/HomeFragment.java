package com.focus.android.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.focus.android.Add_class_message;
import com.focus.android.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private View body;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_classtable, container, false);

        body = root;

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button moreButton = getView().findViewById(R.id.topclassmenu_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                more_fun_dialog(getActivity());
            }
        });
    }
    //更多功能dialog
    public void more_fun_dialog(final Activity nowActivity){
        final AlertDialog.Builder windows = new AlertDialog.Builder(nowActivity);
        windows.setTitle("MORE");
        final View dialogview = LayoutInflater.from(nowActivity).inflate(R.layout.dialog_class_button_more, null);
        windows.setView(dialogview);
        windows.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        windows.show();
        use_more_fun_Button(dialogview, nowActivity);
    }
    //添加课程dialog
    public void Add_Class_dialogDraw(Activity nowActivity) {
        AlertDialog.Builder windows = new AlertDialog.Builder(nowActivity);
        windows.setTitle("添加课程");
        final View dialogview = LayoutInflater.from(nowActivity).inflate(R.layout.dialog_add_class, null);
        windows.setView(dialogview);
        windows.setPositiveButton("添加课程", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Add_Class_Block(set_class_message(dialogview));
            }
        });
        windows.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        windows.show();
    }
    //添加一个课程单元
    public boolean Add_Class_Block(Add_class_message ACM) {

        int whichdays = ACM.gors_Class_day(false, null);
        if (whichdays < 1 || whichdays > 7) {
            Toast.makeText(getContext(), "输入日期不合法", Toast.LENGTH_SHORT).show();
        } else {
            RelativeLayout day = getView().findViewById(R.id.RL_monday);
            //生成指定RelativeLayout实例
            switch (whichdays) {
                case 1:
                    day = getView().findViewById(R.id.RL_monday);
                    break;
                case 2:
                    day = getView().findViewById(R.id.RL_tuesday);
                    break;
                case 3:
                    day = getView().findViewById(R.id.RL_wednesday);
                    break;
                case 4:
                    day = getView().findViewById(R.id.RL_thursday);
                    break;
                case 5:
                    day = getView().findViewById(R.id.RL_friday);
                    break;
                case 6:
                    day = getView().findViewById(R.id.RL_saturday);
                    break;
                case 7:
                    day = getView().findViewById(R.id.RL_sunday);
                    break;
            }
            //获取左侧时间栏长度并确定一个课程单元高度
            View tm = body.findViewById(R.id.class_time_message);
            int heigh = tm.getHeight() / 5;
            int pos =(heigh * (ACM.gors_Class_time(false, null)-1));
            //创建课程单元view
            View class_block = LayoutInflater.from(getActivity()).inflate(R.layout.class_block, null);
            //设置课程单元外观尺寸以及生成位置
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, heigh);
            class_block.setLayoutParams(params);
            class_block.setY(pos);
            TextView tx = class_block.findViewById(R.id.class_block_text);
            tx.setText(ACM.gors_Class_name(false,null)+ACM.gors_Class_room(false,null));
            day.addView(class_block);
        }



        return false;
    }
    //获取课程相关信息
    public Add_class_message set_class_message(View dialog) {
        TextView tx;
        String[] message_group = new String[6];
        tx = dialog.findViewById(R.id.addclass_classname);
        message_group[0] = tx.getText().toString();
        tx = dialog.findViewById(R.id.addclass_teacher);
        message_group[1] = tx.getText().toString();
        tx = dialog.findViewById(R.id.addclass_classroom);
        message_group[2] = tx.getText().toString();
        tx = dialog.findViewById(R.id.addclass_classtime);
        message_group[3] = tx.getText().toString();
        tx = dialog.findViewById(R.id.addclass_classday);
        message_group[4] = tx.getText().toString();
        tx = dialog.findViewById(R.id.addclass_classweeks);
        message_group[5] = tx.getText().toString();

        return new Add_class_message(message_group);

    }
    //more控件功能
    public void use_more_fun_Button(View dialogview,final Activity nowActivity){
        Button add = dialogview.findViewById(R.id.class_button_addclass);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Class_dialogDraw(nowActivity);
            }
        });
        Button fresh = dialogview.findViewById(R.id.class_button_fresh);
        fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(nowActivity, "正在开发中", Toast.LENGTH_SHORT).show();
            }
        });
    }
}