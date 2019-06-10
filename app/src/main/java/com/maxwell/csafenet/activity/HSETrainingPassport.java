package com.maxwell.csafenet.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.MyObservationsModule;
import com.maxwell.csafenet.model.TrainingPassportModel;
import com.squareup.picasso.Picasso;

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

public class HSETrainingPassport extends AppCompatActivity {
    TableLayout tableLayout;
    RelativeLayout layoutProgress;
    String s_user_id;
    SharedPreferences preferences;
    TrainingPassportModel trainingPassportModel;
    List<TrainingPassportModel> trainingPassportModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsetraining_passport);

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

        {
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(HSETrainingPassport.this);
            tbrow0.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
            TextView tv0 = new TextView(HSETrainingPassport.this);
            tv0.setText(" S.No ");
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextColor(getResources().getColor(android.R.color.white));
            tv0.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv0.setPadding(10,10,10,10);
            tbrow0.addView(tv0);
            TextView tv01 = new TextView(HSETrainingPassport.this);
            tv01.setText(" Training Title ");
            tv01.setGravity(Gravity.CENTER);
            tv01.setTextColor(getResources().getColor(android.R.color.white));
            tv01.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv01.setPadding(10,10,10,10);
            tbrow0.addView(tv01);
            TextView tv1 = new TextView(HSETrainingPassport.this);
            tv1.setText(" Trainging Provider ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.white));
            tv1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(HSETrainingPassport.this);
            tv2.setText(" Date ");
            tv2.setGravity(Gravity.START);
            tv2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv2.setPadding(10,10,10,10);
            tv2.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(HSETrainingPassport.this);
            tv3.setText(" Expiry Date ");
            tv3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv3.setPadding(10,10,10,10);
            tv3.setGravity(Gravity.START);
            tv3.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv3);
            TextView tv06 = new TextView(HSETrainingPassport.this);
            tv06.setText(" Training Evidence ");
            tv06.setGravity(Gravity.CENTER);
            tv06.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv06.setPadding(10,10,10,10);
            tv06.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv06);
            tableLayout.addView(tbrow0);
        }
        viewTrainingPassport();

    }

    public void viewTrainingPassport(){
        RequestQueue requestQueue = Volley.newRequestQueue(HSETrainingPassport.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, StringConstants.mainUrl + StringConstants.hseTrainigPassportUrl+StringConstants.inputUserID+"="+s_user_id, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // display response

                        try {

                            if (jsonObject.has("passport")) {

                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jsonObject.getJSONArray("passport");

                                trainingPassportModelList=new ArrayList<>();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject dataObject = jsonArray.getJSONObject(i);
                                    trainingPassportModel=new TrainingPassportModel();
                                   // trainingPassportModel.setEmployeeId(dataObject.getString("employee_id"));
                                    trainingPassportModel.setEmployeename(dataObject.getString("name"));
                                  //  trainingPassportModel.setDesignation(dataObject.getString("designtion"));
                                    trainingPassportModel.setTrainingTitle(dataObject.getString("training_title"));
                                    trainingPassportModel.setTrainingProvider(dataObject.getString("provider"));
                                    trainingPassportModel.setDateDone(dataObject.getString("completed_date"));
                                    trainingPassportModel.setExpiryDate(dataObject.getString("expiry_date"));
                                    trainingPassportModel.setTrainingEvidence(dataObject.getString("image"));
                                    trainingPassportModelList.add(trainingPassportModel);
                                }

                                if(trainingPassportModelList.size()>0){

                                    for (int i=0;i<trainingPassportModelList.size();i++){


                                        TableRow row = new TableRow(getApplicationContext());
                                        TextView tv01 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv01.setText(String.valueOf(i + 1));
                                        tv01.setGravity(Gravity.CENTER);
                                        tv01.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv01.setPadding(10, 10, 10, 10);
                                        tv01.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv01);
                                        TextView tv00 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv00.setText(trainingPassportModelList.get(i).getTrainingTitle());
                                        tv00.setGravity(Gravity.CENTER);
                                        tv00.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv00.setPadding(10, 10, 10, 10);
                                        tv00.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv00);
                                        TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(trainingPassportModelList.get(i).getTrainingProvider());
                                        tv02.setGravity(Gravity.CENTER);
                                        tv02.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(10, 10, 10, 10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        TextView tv03 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv03.setText(trainingPassportModelList.get(i).getDateDone());
                                        tv03.setGravity(Gravity.START);
                                        tv03.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(10, 10, 10, 10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);
                                        TextView tv04 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv04.setText(trainingPassportModelList.get(i).getExpiryDate());
                                        tv04.setGravity(Gravity.START);
                                        tv04.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv04.setPadding(10, 10, 10, 10);
                                        tv04.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv04);

                                        ImageView iv6 = new ImageView(getApplicationContext());
                                        iv6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
                                        iv6.setPadding(0,10,0,10);
                                        // LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,20);
                                        //iv6.requestLayout();
                                        // iv6.getLayoutParams().height = 20;
                                        iv6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                                        Glide.with(getApplicationContext())
                                                .load(trainingPassportModelList.get(i).getTrainingEvidence()) // image url
                                                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                //.error(R.drawable.imagenotfound)  // any image in case of error
                                                // .override(200, 200); // resizing
                                                // .apply(new RequestOptions().override(100, 100))
                                                .into(iv6);
                                        //iv6.setLayoutParams(new ViewGroup.LayoutParams(30,30));
                                        // iv6.setForegroundGravity(Gravity.CENTER);
                                        iv6.setPadding(0,10,0,10);
                                        row.addView(iv6);


                                        iv6.setTag(i);
                                        iv6.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                int position=(int)v.getTag();

                                                String url=trainingPassportModelList.get(position).getTrainingEvidence();
                                                showRadioButtonDialog(url);
                                            }
                                        });


                                        tableLayout.addView(row);
                                    }
                                }

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
    private void showRadioButtonDialog(String url) {

        // custom dialog
        final Dialog dialog = new Dialog(HSETrainingPassport.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_big_imageview);

        ImageView imageView=(ImageView)dialog.findViewById(R.id.image_big);

        Glide.with(getApplicationContext())
                .load(url) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
                //.apply(new RequestOptions().override(100, 100))
                .into(imageView);

        dialog.show();

    }

}
