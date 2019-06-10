package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.DropDownDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CEVSEntryActivity extends AppCompatActivity {
    Spinner sMyStatus,sDependentStatus,sAssistanceRequired;
    EditText et_my_comments,et_dependent_comments,et_how_help,et_how_reach;
    Button submit;
    List<String> mystatus=new ArrayList<>();
    List<String> dependentStatus=new ArrayList<>();
    List<String> assistanceRequired=new ArrayList<>();
    ArrayAdapter<String> dataAdapter,dataAdapter1,dataAdapterAssisatnce;
    String myStatusId="",dependentStatusId="";

    List<String> mystatusidList=new ArrayList<>();
    List<String> dependentstatusIdlist=new ArrayList<>();

    String s_mystatus,s_dependent_status,s_my_comments,s_dependent_comments,s_assistance_required="",s_how_can_help,s_how_can_reach;
    String s_user_id;
    SharedPreferences preferences;
    String emergencyCaseId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cevsentry);

        initializeViews();
    }

    public  void back(View view){
        onBackPressed();
    }

    public void initializeViews(){
        sMyStatus=(Spinner)findViewById(R.id.spinner_status);
        sDependentStatus=(Spinner)findViewById(R.id.spinner_dependent_status);
        sAssistanceRequired=(Spinner)findViewById(R.id.spinner_assistance_required);
        et_my_comments=(EditText)findViewById(R.id.edt_comments);
        et_dependent_comments=(EditText)findViewById(R.id.edt_dependent_comments);
        et_how_help=(EditText)findViewById(R.id.edt_how_can_help);
        et_how_reach=(EditText)findViewById(R.id.edt_how_can_reach);
        submit=(Button)findViewById(R.id.submit);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        mystatus.add("-Select your status-");
        dependentStatus.add("-Select dependent status-");
        assistanceRequired.add("-Select assistance required-");
        assistanceRequired.add("Yes");
        assistanceRequired.add("No");
        dataAdapterAssisatnce=new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_spinner_item, assistanceRequired);
        sAssistanceRequired.setAdapter(dataAdapterAssisatnce);
        emergencyCaseId=getIntent().getStringExtra("EmerncyId");

        statusListing();

        sMyStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!sMyStatus.getSelectedItem().toString().equals("-Select your status-") && !sMyStatus.getSelectedItem().toString().equals("")) {
                    myStatusId=mystatusidList.get(i-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sDependentStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!sDependentStatus.getSelectedItem().toString().equals("-Select dependent status-") && !sDependentStatus.getSelectedItem().toString().equals("")) {
                    dependentStatusId=dependentstatusIdlist.get(i-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sAssistanceRequired.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!sAssistanceRequired.getSelectedItem().toString().equals("-Select assistance required-") && !sAssistanceRequired.getSelectedItem().toString().equals("")) {
                    s_assistance_required=assistanceRequired.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_my_comments=et_my_comments.getText().toString().trim();
                s_dependent_comments=et_dependent_comments.getText().toString().trim();
                s_how_can_help=et_how_help.getText().toString().trim();
                s_how_can_reach=et_how_reach.getText().toString().trim();

                if(!myStatusId.isEmpty()){
                    if(!s_my_comments.isEmpty()){
                    if(!dependentStatusId.isEmpty()){
                        if(!s_dependent_comments.isEmpty()){
                            if(!s_assistance_required.isEmpty()){
                                if(!s_how_can_help.isEmpty()){
                                    if(!s_how_can_reach.isEmpty()){

                                        submitStatusOperation();

                                    }else
                                        Toast.makeText(getApplicationContext(),"Please enter how can reach",Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(getApplicationContext(),"Please enter How can Help",Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(getApplicationContext(),"Please select Assistance Required",Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getApplicationContext(),"Please enter dependent comments",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"Please select dependent status",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"Please enter addition comments",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(),"Please select your status",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void statusListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(CEVSEntryActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.statusListingUrl, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                if(!response.matches("")){
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("status")){
                           mystatusidList=new ArrayList<>();
                           dependentstatusIdlist=new ArrayList<>();
                            JSONArray locationArray=jsonObject.getJSONArray("status");
                            for(int i=0;i<locationArray.length();i++){
                                JSONObject locationObject=locationArray.getJSONObject(i);
                                mystatus.add(locationObject.getString("status"));
                                dependentStatus.add(locationObject.getString("status"));
                                mystatusidList.add(locationObject.getString("id"));
                                dependentstatusIdlist.add(locationObject.getString("id"));
                            }
                            dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, mystatus);
                            sMyStatus.setAdapter(dataAdapter);
                            dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, dependentStatus);
                            sDependentStatus.setAdapter(dataAdapter1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                statusListing();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                //   fgh
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }

    public void submitStatusOperation(){
        RequestQueue requestQueue = Volley.newRequestQueue(CEVSEntryActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.cevsStatusUpdateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("status")){
                        String status=jsonObject.getString("status");

                        if(status.matches("success")){
                            Toast.makeText(getApplicationContext(),"Your status updated successfully",Toast.LENGTH_SHORT).show();
                           onBackPressed();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put(StringConstants.inputEmergencyCaseId,emergencyCaseId);
                MyData.put(StringConstants.inputuserStatus, myStatusId);
                MyData.put(StringConstants.inputDependentStatus, dependentStatusId);
                MyData.put(StringConstants.inputusercomments, s_my_comments);
                MyData.put(StringConstants.inputdependentComment, s_dependent_comments);
                MyData.put(StringConstants.inputAssistanceRequired, s_assistance_required);
                MyData.put(StringConstants.inputHowcanHelp, s_how_can_help);
                MyData.put(StringConstants.inputHowToReach, s_how_can_reach);
                MyData.put(StringConstants.inputUserID, s_user_id);
                return MyData;
            }
        };
        requestQueue.add(MyStringRequest);
    }


}
