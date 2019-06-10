package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.AcheivementLettersAdapter;
import com.maxwell.csafenet.adapter.AcheivementsAdapter;
import com.maxwell.csafenet.adapter.CEVSAdapter;
import com.maxwell.csafenet.adapter.CrisisEmergencyVerificationSystemAdapter;
import com.maxwell.csafenet.adapter.CrisisNotificationAdapter;
import com.maxwell.csafenet.model.CEVSModel;
import com.maxwell.csafenet.model.HeadCountTrackerModel;
import com.maxwell.csafenet.model.NotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CEVSActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RelativeLayout layoutProgress;
    CEVSModel cevsModel;
    List<CEVSModel> cevsModelList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TableLayout tableLayout;
    String s_user_id;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cevs);
        initializeViews();
    }
    public  void back(View view){
        onBackPressed();
    }

    public void initializeViews(){
        recyclerView=(RecyclerView)findViewById(R.id.recyclerViewCEVS);
        tableLayout=(TableLayout) findViewById(R.id.table_entries);
        layoutProgress=(RelativeLayout)findViewById(R.id.rela_animation);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        cevsModelList=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");


        {
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(CEVSActivity.this);
            tbrow0.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
            TextView tv1 = new TextView(CEVSActivity.this);
            tv1.setText(" Project ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.white));
            tv1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv01 = new TextView(CEVSActivity.this);
            tv01.setText(" Crisis / Emergency Case ");
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextColor(getResources().getColor(android.R.color.white));
            tv01.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv01.setPadding(10,10,10,10);
            tbrow0.addView(tv01);
            tableLayout.addView(tbrow0);
        }
    viewCEVS();

    }

    public void viewCEVS(){

        RequestQueue requestQueue = Volley.newRequestQueue(CEVSActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.cevsUrl +StringConstants.inputUserID+"="+s_user_id, null,
                new Response.Listener<JSONObject>()
                {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {
                            if(response.has("verification")) {
                                String key;
                                JSONArray array=response.getJSONArray("verification");

                                cevsModelList=new ArrayList<>();
                                if(array.length()>0){
                                    for(int i=0;i<array.length();i++){
                                        JSONObject notificationObject=array.getJSONObject(i);
                                        cevsModel=new CEVSModel();
                                        //notificationModel.setGroupId(notificationObject.getString("groups_id"));
                                        // notificationModel.setGroupName(notificationObject.getString("group_name"));
                                        cevsModel.setProjectId(notificationObject.getString("project_id"));
                                        cevsModel.setId(notificationObject.getString("verification_id"));
                                        cevsModel.setProject(notificationObject.getString("project"));
                                        cevsModel.setEmergencyCase(notificationObject.getString("notification"));
                                        cevsModelList.add(cevsModel);
                                    }
                                    mAdapter=new CrisisEmergencyVerificationSystemAdapter(getApplicationContext(),cevsModelList);
                                    recyclerView.setAdapter(mAdapter);

/*
                                    if(cevsModelList.size()>0){
                                        for (int i=0;i<cevsModelList.size();i++){
                                            TableRow row = new TableRow(getApplicationContext());

                                            TextView tv02 = new TextView(getApplicationContext());
                                            //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            tv02.setText(cevsModelList.get(i).getProject());
                                            tv02.setGravity(Gravity.CENTER);
                                            tv02.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv02.setPadding(10, 10, 10, 10);
                                            tv02.setTextColor(getResources().getColor(android.R.color.black));
                                            //tv7.setPadding(12,12,12,12);
                                            row.addView(tv02);
                                            TextView tv00 = new TextView(getApplicationContext());
                                            //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            tv00.setText(cevsModelList.get(i).getEmergencyCase());
                                            tv00.setGravity(Gravity.CENTER);
                                            tv00.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv00.setPadding(10, 10, 10, 10);
                                            tv00.setTextColor(getResources().getColor(android.R.color.black));
                                            //tv7.setPadding(12,12,12,12);
                                            row.addView(tv00);
                                            row.setTag(i);
                                            row.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    int position=(int)view.getTag();

                                                    Intent i=new Intent(CEVSActivity.this,CEVSEntryActivity.class);
                                                    i.putExtra("EmerncyId",cevsModelList.get(position).getId());
                                                    startActivity(i);
                                                }
                                            });
                                            tableLayout.addView(row);

                                        }
                                    }
*/

                                    layoutProgress.setVisibility(View.GONE);
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
                        //Log.d("Error.Response", error.getLocalizedMessage());

                    }
                }
        );

        requestQueue.add(getRequest);

    }


}
