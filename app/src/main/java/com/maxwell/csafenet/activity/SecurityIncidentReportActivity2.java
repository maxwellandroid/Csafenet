package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.IncidentManagementModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityIncidentReportActivity2 extends AppCompatActivity {

    TableLayout tableLayout;

    List<IncidentManagementModule> incidentManagementModuleList;
    IncidentManagementModule module;
    String s_user_id;
    SharedPreferences preferences;
    RelativeLayout layout_progress;
    LinearLayout layout_home;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_incident_report2);

        tableLayout=(TableLayout)findViewById(R.id.table_entries);
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        {
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(getApplicationContext());
            tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            TextView tv0 = new TextView(getApplicationContext());
            tv0.setText(" S.No ");
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextColor(getResources().getColor(android.R.color.white));
            tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv0.setPadding(10,10,10,10);
            tbrow0.addView(tv0);
            TextView tv01 = new TextView(getApplicationContext());
            tv01.setText(" Date of Incident ");
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextColor(getResources().getColor(android.R.color.white));
            tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv01.setPadding(10,10,10,10);
            tbrow0.addView(tv01);
            TextView tv1 = new TextView(getApplicationContext());
            tv1.setText(" Date Reported ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.white));
            tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv03 = new TextView(getApplicationContext());
            tv03.setText(" Reported to ");
            tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv03.setPadding(10,10,10,10);
            tv03.setGravity(Gravity.CENTER);
            tv03.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv03);
            TextView tv003 = new TextView(getApplicationContext());
            tv003.setText(" Local Time ");
            tv003.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv003.setPadding(10,10,10,10);
            tv003.setGravity(Gravity.CENTER);
            tv003.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv003);
            TextView tv5 = new TextView(getApplicationContext());
            tv5.setText(" Reported Time ");
            tv5.setGravity(Gravity.CENTER);
            tv5.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv5.setPadding(0,10,0,10);
            tv5.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv5);
            /*TextView tv05 = new TextView(getApplicationContext());
            tv05.setText(" Uploaded Image ");
            tv05.setGravity(Gravity.CENTER);
            tv05.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv05.setPadding(0,10,0,10);
            tv05.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv05);*/

            tableLayout.addView(tbrow0);
        }
        new ViewSecurityIncidentReport().execute();

    }
    public  void back(View view){
        onBackPressed();
    }

    private class ViewSecurityIncidentReport extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(SecurityIncidentReportActivity2.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(SecurityIncidentReportActivity2.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl +StringConstants.securityIncidentReportUrl , new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);
                    if(!response.matches("")) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.trim());
                            if (jsonObject.has("security_manage")) {
                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jsonObject.getJSONArray("security_manage");
                                incidentManagementModuleList=new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    module=new IncidentManagementModule();
                                    module.setDateOfIncidet(dataObject.getString("date_of_incident"));
                                    module.setDateReported(dataObject.getString("reported_date"));
                                    module.setReportedTo(dataObject.getString("fname"));
                                    module.setLocalTime(dataObject.getString("local_time"));
                                    module.setReportedTime(dataObject.getString("reported_time"));
                                    module.setImagePath(dataObject.getString("upload_phone"));
                                    incidentManagementModuleList.add(module);
                                }
                                if (incidentManagementModuleList.size() > 0) {
                                    for (int i = 0; i < incidentManagementModuleList.size(); i++) {
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
                                        tv00.setText(incidentManagementModuleList.get(i).getDateOfIncidet());
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(incidentManagementModuleList.get(i).getDateReported());
                                        tv02.setGravity(Gravity.CENTER);
                                        tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(10, 10, 10, 10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        TextView tv004 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv004.setText(incidentManagementModuleList.get(i).getReportedTo());
                                        tv004.setGravity(Gravity.START);
                                        tv004.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv004.setPadding(10,10,10,10);
                                        tv004.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv004);
                                        TextView tv0004 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv0004.setText(incidentManagementModuleList.get(i).getLocalTime());
                                        tv0004.setGravity(Gravity.CENTER);
                                        tv0004.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv0004.setPadding(10,10,10,10);
                                        tv0004.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv0004);
                                        TextView tv06 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv06.setText(incidentManagementModuleList.get(i).getReportedTime());
                                        tv06.setGravity(Gravity.CENTER);
                                        tv06.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv06.setPadding(0,10,0,10);
                                        tv06.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv06);
                                    /*    ImageView iv1=new ImageView(getApplicationContext());
                                        iv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        Glide.with(SecurityIncidentReportActivity2.this)
                                                .load("http://csafenet.com/"+incidentManagementModuleList.get(i).getImagePath()) // image url
                                                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                //.error(R.drawable.imagenotfound)  // any image in case of error
                                                // .override(200, 200); // resizing
                                                //.apply(new RequestOptions().placeholder(R.drawable.loading))
                                                .into(iv1);
                                        row.addView(iv1);
*/

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

                            layout_progress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);
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
                        layout_progress.setVisibility(View.GONE);
                        layout_home.setVisibility(View.VISIBLE);
                    }
                    layout_progress.setVisibility(View.GONE);
                    layout_home.setVisibility(View.VISIBLE);
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
