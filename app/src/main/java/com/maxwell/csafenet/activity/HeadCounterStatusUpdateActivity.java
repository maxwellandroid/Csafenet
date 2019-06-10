package com.maxwell.csafenet.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.CrisisEmergencyVerificationSystemAdapter;
import com.maxwell.csafenet.adapter.HeadCountAdapter;
import com.maxwell.csafenet.adapter.HeadCountStatusAdapter;
import com.maxwell.csafenet.model.AnswersDetailsModel;
import com.maxwell.csafenet.model.EmployeeStatusModel;
import com.maxwell.csafenet.model.HeadCountTrackerModel;
import com.maxwell.csafenet.model.MyObservationsModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadCounterStatusUpdateActivity extends AppCompatActivity {

    HeadCountTrackerModel headCountTrackerModel;
    List<EmployeeStatusModel> employeeStatusModelList=new ArrayList<>();
    EditText et_project,et_emergency_type,et_date;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    String s_date,s_prject,s_emergency_type;
    String s_user_id;
    SharedPreferences preferences;
    TextView tv_scan;
    private IntentIntegrator qrScan;
    String headCountId;
    List<HeadCountTrackerModel> headCountTrackerModelList;
    EmployeeStatusModel employeeStatusModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_counter_status_update);
        Intent i = getIntent();
        headCountTrackerModel = (HeadCountTrackerModel)i.getSerializableExtra("HeadCountDetails");
        s_date=headCountTrackerModel.getDate();
        s_prject=headCountTrackerModel.getProject();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        s_emergency_type=headCountTrackerModel.getEmergecyType();
        headCountId=headCountTrackerModel.getHeadCountId();

        employeeStatusModelList=headCountTrackerModel.getEmployeeStatusModelList();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s_date);
             s_date = new SimpleDateFormat("dd-MM-yyyy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();

        }
        initializeViews();

    }
    public  void back(View view){
        Intent i=new Intent(getApplicationContext(),HeadCountTrackerActivity.class);
        startActivity(i);
        finish();
    }

    public void initializeViews(){
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewHeadCountUsers);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        et_date=(EditText)findViewById(R.id.edittext_date);
        et_project=(EditText)findViewById(R.id.edittext_project);
        et_emergency_type=(EditText)findViewById(R.id.edittext_emergency_type);
        et_project.setText(s_prject);
        tv_scan=(TextView)findViewById(R.id.text_scan_user);
        et_emergency_type.setText(s_emergency_type);
        et_date.setText(s_date);

        mAdapter=new HeadCountStatusAdapter(getApplicationContext(),employeeStatusModelList);
        recyclerView.setAdapter(mAdapter);
        qrScan = new IntentIntegrator(this);
        tv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data

                    //converting the data to json
                   String userId=result.getContents();
                   updateStatus(userId);

                   Toast.makeText(HeadCounterStatusUpdateActivity.this,userId,Toast.LENGTH_SHORT).show();


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void updateStatus(String employeeId){
        RequestQueue requestQueue = Volley.newRequestQueue(HeadCounterStatusUpdateActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.updateHeadCountUserStatusUrl+StringConstants.inputHeadUserId+"="+s_user_id+"&"+StringConstants.inputHeadCountId+"="+headCountId+"&"+StringConstants.inputEmployeeId+"="+employeeId, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // display response

                        try {

                            if (jsonObject.has("status")) {
                                String result=jsonObject.getString("status");
                                if(result.matches("success")){

                                    Toast.makeText(getApplicationContext(),"User status Verified",Toast.LENGTH_SHORT).show();
                                    getStatus();
                                }else if(result.matches("Already Inserted")){
                                    Toast.makeText(getApplicationContext(),"User status already updated",Toast.LENGTH_SHORT).show();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }
        );
        requestQueue.add(getRequest);

    }

    public void getStatus(){
        RequestQueue requestQueue = Volley.newRequestQueue(HeadCounterStatusUpdateActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.checkHeadCountUserStatusUrl+StringConstants.inputUserID+"="+s_user_id+"&"+StringConstants.inputHeadCountIdNew+"="+headCountId, null,
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

                                mAdapter=new HeadCountStatusAdapter(getApplicationContext(),employeeStatusModelList);
                                recyclerView.setAdapter(mAdapter);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage=StringConstants.ErrorMessage(error);
                        if(errorMessage.matches("Connection TimeOut! Please check your internet connection.")){
                            getStatus();
                        }

                    }
                }
        );
        requestQueue.add(getRequest);

    }
}
