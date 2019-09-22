package com.focus.android.main_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.focus.android.R;

public class TableFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.table_fragment,container,false);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }
    public static TableFragment newInstance(String param1) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
}
