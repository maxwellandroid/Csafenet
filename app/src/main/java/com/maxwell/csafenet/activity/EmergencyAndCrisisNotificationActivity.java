package com.maxwell.csafenet.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.maxwell.csafenet.adapter.CrisisNotificationAdapter;
import com.maxwell.csafenet.model.NotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmergencyAndCrisisNotificationActivity extends AppCompatActivity {

    TableLayout tableLayout;
    List<String> projectsList=new ArrayList<>();
    String s_user_id;
    SharedPreferences preferences;
    NotificationModel notificationModel;
    List<NotificationModel> notificationModelList;
    RelativeLayout relativeLayout;
    ListView list_emergency_notifications;
    CrisisNotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_and_crisis_notification);

        initializeViews();
    }

    public  void back(View view){
        onBackPressed();
    }

    public void initializeViews(){
        tableLayout=(TableLayout) findViewById(R.id.table_entries);
        list_emergency_notifications=(ListView)findViewById(R.id.list_emergency_notification);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        relativeLayout=(RelativeLayout)findViewById(R.id.rela_animation);

        {
            tableLayout.removeAllViews();
            TableRow tbrow0 = new TableRow(EmergencyAndCrisisNotificationActivity.this);
            tbrow0.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));

            TextView tv1 = new TextView(EmergencyAndCrisisNotificationActivity.this);
            tv1.setText(" Project ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.white));
            tv1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv01 = new TextView(EmergencyAndCrisisNotificationActivity.this);
            tv01.setText(" Notification ");
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextColor(getResources().getColor(android.R.color.white));
            tv01.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv01.setPadding(10,10,10,10);
            tbrow0.addView(tv01);
            tableLayout.addView(tbrow0);
        }
notificationsListing();

    }


    public void notificationsListing(){

        RequestQueue requestQueue = Volley.newRequestQueue(EmergencyAndCrisisNotificationActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.emergencyCrisisNotificationUrl +StringConstants.inputUserID+"="+"26", null,
                new Response.Listener<JSONObject>()
                {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {
                            if(response.has("notification")) {
                                String key;
                                JSONArray array=response.getJSONArray("notification");

                                notificationModelList=new ArrayList<>();
                                if(array.length()>0){
                                    for(int i=0;i<array.length();i++){
                                        JSONObject notificationObject=array.getJSONObject(i);
                                        notificationModel=new NotificationModel();
                                        //notificationModel.setGroupId(notificationObject.getString("groups_id"));
                                       // notificationModel.setGroupName(notificationObject.getString("group_name"));
                                        notificationModel.setProjectId(notificationObject.getString("project_id"));
                                        notificationModel.setProjectName(notificationObject.getString("project"));
                                        notificationModel.setMessage(notificationObject.getString("notification"));
                                        notificationModelList.add(notificationModel);
                                    }

                                    adapter=new CrisisNotificationAdapter(EmergencyAndCrisisNotificationActivity.this, notificationModelList);
                                    list_emergency_notifications.setAdapter(adapter);
                                    if(notificationModelList.size()>0){
                                        for (int i=0;i<notificationModelList.size();i++){
                                            TableRow row = new TableRow(getApplicationContext());

                                            TextView tv02 = new TextView(getApplicationContext());
                                            //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            tv02.setText(notificationModelList.get(i).getProjectName());
                                            tv02.setGravity(Gravity.CENTER);
                                            tv02.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv02.setPadding(10, 10, 10, 10);
                                            tv02.setTextColor(getResources().getColor(android.R.color.black));
                                            //tv7.setPadding(12,12,12,12);
                                            row.addView(tv02);
                                            TextView tv00 = new TextView(getApplicationContext());
                                            //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                            tv00.setText(notificationModelList.get(i).getMessage());
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
                                                    showRadioButtonDialog(notificationModelList.get(position).getMessage());

                                                }
                                            });
                                            tableLayout.addView(row);

                                        }


                                    }else {
                                        TableRow row = new TableRow(getApplicationContext());
                                        row.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        TextView tv00 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv00.setText("No Items Found");
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        tableLayout.addView(row);
                                    }

                                }else {
                                    TableRow row = new TableRow(getApplicationContext());
                                    row.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                    TextView tv00 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv00.setText("No Items Found");
                                    tv00.setGravity(Gravity.CENTER);

                                    tv00.setPadding(10, 10, 10, 10);
                                    tv00.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv00);
                                    tableLayout.addView(row);
                                }

                                relativeLayout.setVisibility(View.GONE);

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
    private void showRadioButtonDialog(String message) {

        // custom dialog
        final Dialog dialog = new Dialog(EmergencyAndCrisisNotificationActivity.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_emergency_crisis_notification_popup);
        TextView tv_message=(TextView)dialog.findViewById(R.id.text_message);
        tv_message.setText(message);
       /* ImageView imageView=(ImageView)dialog.findViewById(R.id.image_big);

        Glide.with(getApplicationContext())
                .load(url) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
                //.apply(new RequestOptions().override(100, 100))
                .into(imageView);*/
        dialog.show();

    }

}
