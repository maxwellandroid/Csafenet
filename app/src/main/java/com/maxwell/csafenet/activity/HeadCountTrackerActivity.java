package com.maxwell.csafenet.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.HeadCountAdapter;
import com.maxwell.csafenet.model.EmployeeStatusModel;
import com.maxwell.csafenet.model.HeadCountTrackerModel;
import com.maxwell.csafenet.model.TrainingPassportModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HeadCountTrackerActivity extends AppCompatActivity {
    TableLayout tableLayout;
    RelativeLayout layoutProgress;
    String s_user_id;
    SharedPreferences preferences;
    HeadCountTrackerModel headCountTrackerModel;
    List<HeadCountTrackerModel> headCountTrackerModelList;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<EmployeeStatusModel> employeeStatusModelList;
    EmployeeStatusModel employeeStatusModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_count_tracker);
        initializeViews();

    }
    public  void back(View view){
        onBackPressed();
    }

    public void initializeViews(){

        tableLayout=(TableLayout) findViewById(R.id.table_entries);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewHeadCount);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        {
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(HeadCountTrackerActivity.this);
            tbrow0.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));

            TextView tv01 = new TextView(HeadCountTrackerActivity.this);
            tv01.setText(" Emergency Detail");
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextColor(getResources().getColor(android.R.color.white));
            tv01.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv01.setPadding(10,10,10,10);
            tbrow0.addView(tv01);
            TextView tv1 = new TextView(HeadCountTrackerActivity.this);
            tv1.setText(" Project ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.white));
            tv1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(HeadCountTrackerActivity.this);
            tv2.setText(" Status ");
            tv2.setGravity(Gravity.START);
            tv2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv2.setPadding(10,10,10,10);
            tv2.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv2);
            tableLayout.addView(tbrow0);
        }
        viewHeadCounterTracker();

    }

    public void viewHeadCounterTracker(){
        RequestQueue requestQueue = Volley.newRequestQueue(HeadCountTrackerActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.headCountTrackerUrl+StringConstants.inputUserID+"="+s_user_id, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // display response

                        try {

                            if (jsonObject.has("headcount")) {

                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jsonObject.getJSONArray("headcount");

                                headCountTrackerModelList=new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    headCountTrackerModel=new HeadCountTrackerModel();
                                    // trainingPassportModel.setEmployeeId(dataObject.getString("employee_id"));
                                    headCountTrackerModel.setProjectId(dataObject.getString("project_id"));
                                      headCountTrackerModel.setHeadCountId(dataObject.getString("head_count_id"));
                                    headCountTrackerModel.setEmergecyType(dataObject.getString("emergeny_type"));
                                    headCountTrackerModel.setProject(dataObject.getString("project"));
                                    headCountTrackerModel.setDate(dataObject.getString("date"));

                                    if(dataObject.has("users")){
                                        JSONArray usersArray=dataObject.getJSONArray("users");
                                        employeeStatusModelList=new ArrayList<>();

                                        for(int j=0;j<usersArray.length();j++){
                                            employeeStatusModel=new EmployeeStatusModel();
                                            JSONObject employeeObject=usersArray.getJSONObject(j);
                                            employeeStatusModel.setEmployeeName(employeeObject.getString("emp_name"));
                                            employeeStatusModel.setEmployeeId(employeeObject.getString("emp_id"));
                                            employeeStatusModel.setDesignation(employeeObject.getString("designation"));
                                            employeeStatusModel.setVerifyId(employeeObject.getString("verify_id"));

                                            employeeStatusModelList.add(employeeStatusModel);
                                        }
                                        headCountTrackerModel.setEmployeeStatusModelList(employeeStatusModelList);
                                    }

                                    headCountTrackerModelList.add(headCountTrackerModel);
                                }

                                mAdapter=new HeadCountAdapter(HeadCountTrackerActivity.this,headCountTrackerModelList);
                                recyclerView.setAdapter(mAdapter);

/*
                                if(headCountTrackerModelList.size()>0){

                                    for (int i=0;i<headCountTrackerModelList.size();i++){


                                        TableRow row = new TableRow(getApplicationContext());
                                        TextView tv00 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv00.setText(headCountTrackerModelList.get(i).getEmergencyDetail());
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(headCountTrackerModelList.get(i).getProject());
                                        tv02.setGravity(Gravity.CENTER);
                                        tv02.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(10, 10, 10, 10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        TextView tv03 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv03.setText(headCountTrackerModelList.get(i).getStatus());
                                        tv03.setGravity(Gravity.START);
                                        tv03.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(10, 10, 10, 10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);


                                        tableLayout.addView(row);
                                    }
                                }
*/

                                layoutProgress.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        layoutProgress.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        layoutProgress.setVisibility(View.GONE);
                    }
                }
        );
        requestQueue.add(getRequest);
    }

}
