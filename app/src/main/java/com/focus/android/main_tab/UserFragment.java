package com.focus.android.main_tab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.focus.android.AboutUser.RegisterActivity;
import com.focus.android.AboutUser.UserInformationActivity;
import com.focus.android.R;
import com.focus.android.login.LoginActivity;

public class UserFragment extends Fragment {
    private SharedPreferences pref;
    private SharedPreferences.Editor editer;
    private RelativeLayout information;
    private RelativeLayout register;
    private RelativeLayout change_account;
    private RelativeLayout focus_time;
    private RelativeLayout release;
    private RelativeLayout contact_us;
    private RelativeLayout version_now;
    private static final String ARG_PARAM1 = "param1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.user_fragment,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        information = getActivity().findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getContext(), UserInformationActivity.class);
               startActivity(intent);
            }
        });
        register = getActivity().findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);

            }
        });
        change_account = getActivity().findViewById(R.id.change_account);
        change_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetisransfer();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
        focus_time = getActivity().findViewById(R.id.focus_time);
        focus_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        release = getActivity().findViewById(R.id.release);
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        contact_us = getActivity().findViewById(R.id.contact_us);
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        version_now = getActivity().findViewById(R.id.version_now);
        version_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
    //重置是否通过验证的存储标记
    public void resetisransfer(){
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editer = pref.edit();
        editer.putBoolean("istransfer",true);
        editer.commit();
    }
    public static UserFragment newInstance(String param1) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
}
