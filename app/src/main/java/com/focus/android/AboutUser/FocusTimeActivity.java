package com.focus.android.AboutUser;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.focus.android.Courses;
import com.focus.android.R;
import com.focus.android.TypeTransition;
import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FocusTimeActivity extends AppCompatActivity {
    private String data_for_subjects;
    private String data_for_time;
    private String responseData;
    private SharedPreferences pref;
    private ChartProgressBar mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_time);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {//隐藏顶部的bar；
            actionBar.hide();
        }
        TextView title = findViewById(R.id.tv_navigation_label);
        title.setText("专注时间");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("study_number", pref.getString("username","null"))
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("http://49.235.233.124:8080/get_class_status/")
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    Log.d("TEST", "run: "+responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    data_for_subjects = jsonObject.getString("subjects");
                    data_for_time = jsonObject.getString("time");
                    if (jsonObject.getBoolean("status")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setUI();
                            }
                        });
                    }else{
                        List<Courses> Allcourse = DataSupport.findAll(Courses.class);
                        String[] subjects = new String[50];
                        int num = 0;
                        for (int i = 0;i<Allcourse.size();i++){
                            if (i==0){
                                subjects[num] = Allcourse.get(i).getCourse_name();
                                num++;

                            }else{
                                boolean isrepeat = false;
                                for (int j=0;j<num;j++){
                                    if (subjects[j].equals(Allcourse.get(i).getCourse_name())){
                                        isrepeat = true;
                                        break;
                                    }
                                }
                                if (!isrepeat){
                                    subjects[num] = Allcourse.get(i).getCourse_name();
                                    num++;
                                }
                            }
                        }
                        String[] subjectsfinal = new String[num];

                        for (int i=0;i<num;i++){
                            subjectsfinal[i] = subjects[i];
                        }
                        int[] time = new int[num];
                        for (int i =0;i<num;i++){
                            time[i]=0;
                        }
                        Log.d("TEST", "run: "+TypeTransition.stringArrayToString(subjectsfinal));
                        OkHttpClient client_for_storage = new OkHttpClient();
                        RequestBody requestBody_for_storage = new FormBody.Builder()
                                .add("study_number",pref.getString("username","null"))
                                .add("subjects", TypeTransition.stringArrayToString(subjectsfinal))
                                .add("time", TypeTransition.inttostring(time))
                                .build();
                        Request request_for_storage = new Request.Builder()
                                .post(requestBody_for_storage)

                                .url("http://49.235.233.124:8080/edit_class_status/")//更改为存储专注时间的链接

                                .build();
                        Response response_for_storage = client_for_storage.newCall(request_for_storage).execute();
                        String responseData_for_inital = response_for_storage.body().string();
                        Log.d("TEST", "run: "+responseData_for_inital);
                        response = client.newCall(request).execute();
                        responseData = response.body().string();
                        jsonObject = new JSONObject(responseData );
                        data_for_subjects = jsonObject.getString("subjects");
                        Log.d("TEST", "run: "+responseData );
                        data_for_time = jsonObject.getString("time");
                        if (jsonObject.getBoolean("status")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setUI();
                                }
                            });
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


    }
    private void setUI(){
        ArrayList<BarData> dataList = new ArrayList<>();
        String[] subjects = TypeTransition.stringToStringArray(data_for_subjects);
        int[] time = TypeTransition.stringtoint(data_for_time);
        for (int i=0;i<subjects.length;i++) {
            BarData data = new BarData(subjects[i], time[i], time[i]+"min");
            dataList.add(data);
        }
        mChart = (ChartProgressBar) findViewById(R.id.ChartProgressBar);
        int max = 0;
        for (int i=0;i<time.length;i++){
            if (time[i]>max)
                max = time[i];
        }
        if (max==0){
            mChart.setMaxValue(10);
        }else {
            mChart.setMaxValue(max);
        }
        mChart.setDataList(dataList);
        mChart.build();
    }

}
