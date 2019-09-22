package com.focus.android.main_tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.focus.android.R;

public class DataGenerator {
    public static final int []mTabRes = new int[]{R.drawable.table_selector,R.drawable.unordered_list_selector,R.drawable.user_selector};
    public static final int []mTabResPressed = new int[]{R.drawable.table_selected,R.drawable.unordered_list_selected,R.drawable.user_selected};
    public static final String []mTabTitle = new String[]{"课表","社区","我的"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[3];
        fragments[0] = TableFragment.newInstance(from);
        fragments[1] = CommunityFragment.newInstance(from);
        fragments[2] = UserFragment.newInstance(from);
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
