package com.focus.android.son_content.community;
import com.focus.android.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class topbuttonFragment extends Fragment {
    private Button search;
    private TextView btn_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_menu_community, container, false);
        return view;
    }
}
