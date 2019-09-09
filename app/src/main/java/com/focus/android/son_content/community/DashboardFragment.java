package com.focus.android.son_content.community;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.focus.android.R;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private DashboardViewModel dashboardViewModel;

    @Override
    public void onStart() {
        Button button1 = (Button) getView().findViewById(R.id.schoolcourse);
        button1.setOnClickListener(this);
        Button button2 = (Button) getView().findViewById(R.id.ITtechnology);
        button2.setOnClickListener(this);
        Button button3 = (Button) getView().findViewById(R.id.lost);
        button3.setOnClickListener(this);
        replaceFragment(new itFragment());

        btn_icon  = getView().findViewById(R.id.search);
        drawable =  getResources().getDrawable(R.drawable.ic_search_black_24dp);
        drawable.setBounds(10,0,drawable.getMinimumWidth(),drawable.getMinimumHeight()); //设置图片的大小
        btn_icon.setCompoundDrawables(drawable,null,null,null);
        super.onStart();
    }

    private Button search;
    private TextView btn_icon;
    private Drawable drawable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_community, container, false);
        return root;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.schoolcourse:
                replaceFragment(new itFragment());
                break;
            case R.id.ITtechnology:
                replaceFragment(new schoolcourseFragment());
                break;
            case R.id.lost:
                replaceFragment(new lostandfoundFragment());
                break;
            default:
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager  = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.bottom_layout,fragment);
        transaction.commit();
    }
}