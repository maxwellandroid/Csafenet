package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.MyObservationsModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSEActionTrackerActivity extends AppCompatActivity {


    TableLayout tableLayout,tableLayout1;

    List<MyObservationsModule> myObservationsModuleList=new ArrayList<>();
    MyObservationsModule observationsModule;
    String s_user_id;
    SharedPreferences preferences;
    String observations_opened,observation_closed,compliance_opened,compliance_closed,audit_opened,audit_closed;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hseaction_tracker);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");



        tableLayout=(TableLayout)findViewById(R.id.table_entries);
        tableLayout1=(TableLayout)findViewById(R.id.table_observations);
        {
            tableLayout1.removeAllViews();

            TableRow tbrow0 = new TableRow(HSEActionTrackerActivity.this);
            tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            TextView tv0 = new TextView(HSEActionTrackerActivity.this);
            tv0.setText(" Item ");
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextColor(getResources().getColor(android.R.color.black));
            tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv0.setPadding(10,10,10,10);
            tbrow0.addView(tv0);
            TextView tv01 = new TextView(HSEActionTrackerActivity.this);
            tv01.setText(" Opened ");
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextColor(getResources().getColor(android.R.color.black));
            tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv01.setPadding(10,10,10,10);
            tbrow0.addView(tv01);
            TextView tv1 = new TextView(HSEActionTrackerActivity.this);
            tv1.setText(" Closed ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.black));
            tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(HSEActionTrackerActivity.this);
            tv2.setText(" Total ");
            tv2.setGravity(Gravity.START);
            tv2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv2.setPadding(10,10,10,10);
            tv2.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv2);
            tableLayout1.addView(tbrow0);

        }

        {
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(HSEActionTrackerActivity.this);
            tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            TextView tv0 = new TextView(HSEActionTrackerActivity.this);
            tv0.setText(" S.No ");
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextColor(getResources().getColor(android.R.color.black));
            tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv0.setPadding(10,10,10,10);
            tbrow0.addView(tv0);
            TextView tv01 = new TextView(HSEActionTrackerActivity.this);
            tv01.setText(" Date ");
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextColor(getResources().getColor(android.R.color.black));
            tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv01.setPadding(10,10,10,10);
            tbrow0.addView(tv01);
            TextView tv1 = new TextView(HSEActionTrackerActivity.this);
            tv1.setText(" Observation Number ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.black));
            tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(HSEActionTrackerActivity.this);
            tv2.setText(" Name ");
            tv2.setGravity(Gravity.START);
            tv2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv2.setPadding(10,10,10,10);
            tv2.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(HSEActionTrackerActivity.this);
            tv3.setText(" Company ");
            tv3.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv3.setPadding(10,10,10,10);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv3);
            TextView tv03 = new TextView(HSEActionTrackerActivity.this);
            tv03.setText(" Project ");
            tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv03.setPadding(10,10,10,10);
            tv03.setGravity(Gravity.CENTER);
            tv03.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv03);
            TextView tv003 = new TextView(HSEActionTrackerActivity.this);
            tv003.setText(" Location ");
            tv003.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv003.setPadding(10,10,10,10);
            tv003.setGravity(Gravity.CENTER);
            tv003.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv003);
            TextView tv4 = new TextView(HSEActionTrackerActivity.this);
            tv4.setText(" Observation Type ");
            tv4.setGravity(Gravity.CENTER);
            tv4.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv4.setPadding(10,10,10,10);
            tv4.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv4);
                TextView tv5 = new TextView(HSEActionTrackerActivity.this);
                tv5.setText(" Action By ");
                tv5.setGravity(Gravity.CENTER);
                tv5.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                tv5.setPadding(0,10,0,10);
                tv5.setTextColor(getResources().getColor(android.R.color.black));
                tbrow0.addView(tv5);
            TextView tv6 = new TextView(HSEActionTrackerActivity.this);
            tv6.setText(" Status ");
            tv6.setGravity(Gravity.CENTER);
            tv6.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv6.setPadding(10,10,10,10);
            tv6.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv6);

            tableLayout.addView(tbrow0);
        }
        new ViewMyObservation().execute();

    }
    public  void back(View view){
        onBackPressed();
    }
    private class ViewMyObservation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(HSEActionTrackerActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(HSEActionTrackerActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hseActionTrackingUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.trim());

                            observations_opened=jsonObject.getString("observation_open");
                            observation_closed=jsonObject.getString("observation_close");
                            compliance_opened=jsonObject.getString("compliance_open");
                            compliance_closed=jsonObject.getString("compliance_close");
                            audit_opened=jsonObject.getString("audit_open");
                            audit_closed=jsonObject.getString("audit_close");
                            if (jsonObject.has("observation_all")) {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jsonObject.getJSONArray("observation_all");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dataObject = jsonArray.getJSONObject(i);

                                    observationsModule = new MyObservationsModule();

                                    observationsModule.setUserid(dataObject.getString("user_id"));
                                    observationsModule.setEmployeeNumber(dataObject.getString("employee_no"));
                                    observationsModule.setDesignation(dataObject.getString("designation"));
                                    observationsModule.setUserName(dataObject.getString("employe_name"));
                                    observationsModule.setLocationName(dataObject.getString("location_name"));
                                    observationsModule.setObservation(dataObject.getString("observation_type_name"));
                                    observationsModule.setCompanyName(dataObject.getString("company_name"));
                                    observationsModule.setCompanyId(dataObject.getString("company"));
                                    observationsModule.setRegCode(dataObject.getString("reg_code"));
                                    observationsModule.setProject(dataObject.getString("project"));
                                    observationsModule.setProjectName(dataObject.getString("project_name"));
                                    observationsModule.setLocationId(dataObject.getString("location"));
                                    observationsModule.setEnterDate(dataObject.getString("enter_date"));
                                    observationsModule.setEnterTime(dataObject.getString("enter_time"));
                                    // observationsModule.setObservatioType(dataObject.getString("observation_type"));
                                    observationsModule.setActionDesc(dataObject.getString("action_desc"));
                                    observationsModule.setUnsafeCondition(dataObject.getString("unsafe_condition"));
                                    observationsModule.setActionTaken(dataObject.getString("action_taken"));
                                    observationsModule.setRecommended(dataObject.getString("recommend"));
                                    observationsModule.setActionBy(dataObject.getString("action_by"));
                                    observationsModule.setActionByName(dataObject.getString("action_by_name"));
                                    observationsModule.setSafety(dataObject.getString("safety"));
                                    observationsModule.setSafetyCategory(dataObject.getString("safe_category_name"));
                                    observationsModule.setSafetyOthers(dataObject.getString("safety_others"));
                                    observationsModule.setSafetyExplain(dataObject.getString("safety_explain"));
                                    observationsModule.setRisk(dataObject.getString("risk"));
                                    observationsModule.setRiskOthers(dataObject.getString("risk_others"));
                                    observationsModule.setRiskExplain(dataObject.getString("risk_explain"));
                                    observationsModule.setRoot(dataObject.getString("root"));
                                    observationsModule.setRegDate(dataObject.getString("reg_date"));
                                    observationsModule.setStatus(dataObject.getString("status"));

                                    myObservationsModuleList.add(observationsModule);


                                }

                                for(int i=0;i<3;i++){
                                    if(i==0){
                                        TableRow row = new TableRow(getApplicationContext());
                                        TextView tv000 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv000.setText("Observation");
                                        tv000.setGravity(Gravity.CENTER);
                                        tv000.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv000.setPadding(10, 10, 10, 10);
                                        tv000.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv000);
                                        TextView tv00 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv00.setText(observations_opened);
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(observation_closed);
                                        tv02.setGravity(Gravity.CENTER);
                                        tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(10, 10, 10, 10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        TextView tv03 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv03.setText(String.valueOf(Integer.parseInt(observations_opened)+Integer.parseInt(observation_closed)));
                                        tv03.setGravity(Gravity.CENTER);
                                        tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(10, 10, 10, 10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);
                                        tableLayout1.addView(row);
                                    }
                                    if(i==1){
                                        TableRow row = new TableRow(getApplicationContext());
                                        TextView tv000 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv000.setText("Complaince");
                                        tv000.setGravity(Gravity.CENTER);
                                        tv000.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv000.setPadding(10, 10, 10, 10);
                                        tv000.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv000);
                                        TextView tv00 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv00.setText(compliance_opened);
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(compliance_closed);
                                        tv02.setGravity(Gravity.CENTER);
                                        tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(10, 10, 10, 10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        TextView tv03 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv03.setText(String.valueOf(Integer.parseInt(compliance_opened)+Integer.parseInt(compliance_closed)));
                                        tv03.setGravity(Gravity.CENTER);
                                        tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(10, 10, 10, 10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);
                                        tableLayout1.addView(row);
                                    }
                                    if(i==2){
                                        TableRow row = new TableRow(getApplicationContext());
                                        TextView tv000 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv000.setText("Audit");
                                        tv000.setGravity(Gravity.CENTER);
                                        tv000.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv000.setPadding(10, 10, 10, 10);
                                        tv000.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv000);
                                        TextView tv00 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv00.setText(audit_opened);
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(audit_closed);
                                        tv02.setGravity(Gravity.CENTER);
                                        tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(10, 10, 10, 10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        TextView tv03 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv03.setText(String.valueOf(Integer.parseInt(audit_opened)+Integer.parseInt(audit_closed)));
                                        tv03.setGravity(Gravity.CENTER);
                                        tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(10, 10, 10, 10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);
                                        tableLayout1.addView(row);
                                    }
                                }


                                if (myObservationsModuleList.size() > 0) {

                                    for (int i = 0; i < myObservationsModuleList.size(); i++) {


                                        TableRow row = new TableRow(getApplicationContext());
                                        TextView tv01 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv01.setText(String.valueOf(i + 1));
                                        tv01.setGravity(Gravity.CENTER);
                                        tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv01.setPadding(10, 10, 10, 10);
                                        tv01.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv01);
                                        TextView tv00 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv00.setText(myObservationsModuleList.get(i).getEnterDate());
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(myObservationsModuleList.get(i).getRegCode());
                                        tv02.setGravity(Gravity.CENTER);
                                        tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(10, 10, 10, 10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        TextView tv03 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv03.setText(myObservationsModuleList.get(i).getUserName());
                                        tv03.setGravity(Gravity.CENTER);
                                        tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(10, 10, 10, 10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);
                                        TextView tv04 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv04.setText(myObservationsModuleList.get(i).getCompanyName());
                                        tv04.setGravity(Gravity.CENTER);
                                        tv04.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv04.setPadding(10, 10, 10, 10);
                                        tv04.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv04);
                                        TextView tv004 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv004.setText(myObservationsModuleList.get(i).getProjectName());
                                        tv004.setGravity(Gravity.START);
                                        tv004.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv004.setPadding(10,10,10,10);
                                        tv004.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv004);
                                        TextView tv0004 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv0004.setText(myObservationsModuleList.get(i).getLocationName());
                                        tv0004.setGravity(Gravity.CENTER);
                                        tv0004.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv0004.setPadding(10,10,10,10);
                                        tv0004.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv0004);
                                        TextView tv05 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv05.setText(myObservationsModuleList.get(i).getObservation());
                                        tv05.setGravity(Gravity.CENTER);
                                        tv05.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv05.setPadding(10, 10, 10, 10);
                                        tv05.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv05);
                                        TextView tv06 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv06.setText(myObservationsModuleList.get(i).getActionByName());
                                        tv06.setGravity(Gravity.CENTER);
                                        tv06.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv06.setPadding(0,10,0,10);
                                        tv06.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv06);

                                        TextView tv07 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        if(myObservationsModuleList.get(i).getStatus().matches("1")){
                                            tv07.setText("Opened");
                                            tv07.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                        }else if(myObservationsModuleList.get(i).getStatus().matches("0")){
                                            tv07.setText("Closed");
                                            tv07.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                                        }

                                        tv07.setGravity(Gravity.CENTER);
                                        tv07.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv07.setPadding(10, 10, 10, 10);

                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv07);

                                        tableLayout.addView(row);
                                    }
                                    //result=jsonObject.getString("status");
                                } else {
                                    TableRow row = new TableRow(getApplicationContext());
                                    TextView tv01 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv01.setText("No Records Found");
                                    tv01.setGravity(Gravity.CENTER);
                                    //tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv01.setPadding(10, 10, 10, 10);
                                    tv01.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv01);
                                    tableLayout.addView(row);
                                }
                            } else {
                                TableRow row = new TableRow(getApplicationContext());
                                TextView tv01 = new TextView(getApplicationContext());
                                //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                tv01.setText("No Records Found");
                                tv01.setGravity(Gravity.CENTER);
                                //tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                tv01.setPadding(10, 10, 10, 10);
                                tv01.setTextColor(getResources().getColor(android.R.color.black));
                                //tv7.setPadding(12,12,12,12);
                                row.addView(tv01);
                                tableLayout.addView(row);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        TableRow row = new TableRow(getApplicationContext());
                        TextView tv01 = new TextView(getApplicationContext());
                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tv01.setText("No Records Found");
                        tv01.setGravity(Gravity.CENTER);
                        //tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                        tv01.setPadding(10, 10, 10, 10);
                        tv01.setTextColor(getResources().getColor(android.R.color.black));
                        //tv7.setPadding(12,12,12,12);
                        row.addView(tv01);
                        tableLayout.addView(row);
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
                    return MyData;
                }
            };

            requestQueue.add(MyStringRequest);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading..");
            pDialog.setTitle("");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog.isShowing())
                pDialog.dismiss();
        }
    }

}
