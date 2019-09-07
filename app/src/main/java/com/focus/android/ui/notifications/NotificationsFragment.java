package com.focus.android.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.focus.android.R;
import com.focus.android.UserinforActivity;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_selfcenter, container, false);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonevent();

    }

    public void buttonevent(){
        Button userbtnTwo = (Button) getView().findViewById(R.id.userbtnTwo);
        userbtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserinforActivity.class);
                startActivity(intent);
            }
        });
    }
}