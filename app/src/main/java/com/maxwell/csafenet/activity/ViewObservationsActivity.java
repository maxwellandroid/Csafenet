package com.maxwell.csafenet.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.MyObservationsModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewObservationsActivity extends AppCompatActivity {


    TableLayout tableLayout;

    List<MyObservationsModule> myObservationsModuleList=new ArrayList<>();
    MyObservationsModule observationsModule;
    String s_user_id;
    SharedPreferences preferences;

    DatePickerDialog datePickerDialog;

    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String dateToStr;
    EditText et_date;
    RelativeLayout layout_progress;
    LinearLayout layout_home;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observations);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");

        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;

        tableLayout=(TableLayout)findViewById(R.id.table_entries);

        et_date=(EditText)findViewById(R.id.date);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        /*if(dayOfMonth<10){

        }*/

        dateToStr=String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
        et_date.setText(dateToStr);

            {
                tableLayout.removeAllViews();

                TableRow tbrow0 = new TableRow(ViewObservationsActivity.this);
                tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                TextView tv0 = new TextView(ViewObservationsActivity.this);
                tv0.setText(" S.No ");
                tv0.setGravity(Gravity.CENTER);
                tv0.setTextColor(getResources().getColor(android.R.color.white));
                tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv0.setPadding(10,10,10,10);
                tbrow0.addView(tv0);
                TextView tv01 = new TextView(ViewObservationsActivity.this);
                tv01.setText(" Date ");
                tv01.setGravity(Gravity.CENTER);
                tv01.setTextColor(getResources().getColor(android.R.color.white));
                tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv01.setPadding(10,10,10,10);
                tbrow0.addView(tv01);
                TextView tv1 = new TextView(ViewObservationsActivity.this);
                tv1.setText(" Observation Number ");
                tv1.setGravity(Gravity.CENTER);
                tv1.setTextColor(getResources().getColor(android.R.color.white));
                tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv1.setPadding(10,10,10,10);
                tbrow0.addView(tv1);
                TextView tv2 = new TextView(ViewObservationsActivity.this);
                tv2.setText(" Name ");
                tv2.setGravity(Gravity.START);
                tv2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv2.setPadding(10,10,10,10);
                tv2.setTextColor(getResources().getColor(android.R.color.white));
                tbrow0.addView(tv2);
                TextView tv3 = new TextView(ViewObservationsActivity.this);
                tv3.setText(" Company ");
                tv3.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv3.setPadding(10,10,10,10);
                tv3.setGravity(Gravity.START);
                tv3.setTextColor(getResources().getColor(android.R.color.white));
                tbrow0.addView(tv3);
                TextView tv4 = new TextView(ViewObservationsActivity.this);
                tv4.setText(" Observation Type ");
                tv4.setGravity(Gravity.CENTER);
                tv4.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv4.setPadding(10,10,10,10);
                tv4.setTextColor(getResources().getColor(android.R.color.white));
                tbrow0.addView(tv4);
                TextView tv5 = new TextView(ViewObservationsActivity.this);
                tv5.setText(" Action By ");
                tv5.setGravity(Gravity.CENTER);
                tv5.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv5.setPadding(0,10,0,10);
                tv5.setTextColor(getResources().getColor(android.R.color.white));
                tbrow0.addView(tv5);
                TextView tv6 = new TextView(ViewObservationsActivity.this);
                tv6.setText(" Status ");
                tv6.setGravity(Gravity.CENTER);
                tv6.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv6.setPadding(10,10,10,10);
                tv6.setTextColor(getResources().getColor(android.R.color.white));
                tbrow0.addView(tv6);
                TextView tv06 = new TextView(ViewObservationsActivity.this);
                tv06.setText(" Image ");
                tv06.setGravity(Gravity.CENTER);
                tv06.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv06.setPadding(10,10,10,10);
                tv06.setTextColor(getResources().getColor(android.R.color.white));
                tbrow0.addView(tv06);
                TextView tv7 = new TextView(ViewObservationsActivity.this);
                tv7.setText(" Action ");
                tv7.setGravity(Gravity.CENTER);
                tv7.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                tv7.setPadding(10,10,10,10);
                tv7.setTextColor(getResources().getColor(android.R.color.white));
                tbrow0.addView(tv7);
                tableLayout.addView(tbrow0);
            }

            new ViewMyObservation().execute();

    }
    public  void back(View view){
        onBackPressed();
    }
    public  void gotonew(View view){
        Intent intent = new Intent(getApplicationContext(), NewObservationActivity.class);
        startActivity(intent);
    }

    public void onClic(final View view){

        final EditText et_date1= (EditText) view;

        datePickerDialog = new DatePickerDialog(ViewObservationsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //  date.setText(day + "/" + (month + 1) + "/" + year);

                        dateToStr=String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);

                        et_date1.setText(dateToStr);


                    }
                }, year, month, dayOfMonth);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-20000);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();


    }

    private class ViewMyObservation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(ViewObservationsActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(ViewObservationsActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.viewMyObservation, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.trim());
                            if (jsonObject.has("Data")) {

                                JSONArray jsonArray = new JSONArray();
                                jsonArray = jsonObject.getJSONArray("Data");

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
                                    observationsModule.setUploaded_image(dataObject.getString("image"));
                                    observationsModule.setImage2(dataObject.getString("image2"));
                                    myObservationsModuleList.add(observationsModule);
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
                                        tv03.setGravity(Gravity.START);
                                        tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(10, 10, 10, 10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);
                                        TextView tv04 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv04.setText(myObservationsModuleList.get(i).getCompanyName());
                                        tv04.setGravity(Gravity.START);
                                        tv04.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv04.setPadding(10, 10, 10, 10);
                                        tv04.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv04);
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
                                        }else if(myObservationsModuleList.get(i).getStatus().matches("2")){
                                            tv07.setText("Closed");
                                            tv07.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                                        }
                                        tv07.setGravity(Gravity.CENTER);
                                        tv07.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv07.setPadding(10, 10, 10, 10);
                                       // tv07.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv07);
                                        ImageView iv6 = new ImageView(getApplicationContext());
                                        iv6.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        iv6.setPadding(0,10,0,10);
                                        // LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,20);
                                        //iv6.requestLayout();
                                        // iv6.getLayoutParams().height = 20;
                                        iv6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                                        Glide.with(getApplicationContext())
                                                .load("http://csafenet.com/"+myObservationsModuleList.get(i).getUploaded_image()) // image url
                                                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                //.error(R.drawable.imagenotfound)  // any image in case of error
                                                // .override(200, 200); // resizing
                                                // .apply(new RequestOptions().override(100, 100))
                                                .into(iv6);
                                        //iv6.setLayoutParams(new ViewGroup.LayoutParams(30,30));
                                        // iv6.setForegroundGravity(Gravity.CENTER);
                                        iv6.setPadding(0,10,0,10);
                                        row.addView(iv6);

                                        ImageView tv08 = new ImageView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv08.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv08.setImageDrawable(getResources().getDrawable(R.drawable.edit));
                                        // tv08.setGravity(Gravity.CENTER);
                                        // tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv08.setPadding(10, 10, 10, 10);
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv08);


                                        tv08.setTag(i);

                                        row.setTag(i);
                                        row.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent i = new Intent(getApplicationContext(), NewObservationActivity.class);
                                                i.putExtra("Observations",  myObservationsModuleList.get(position));
                                                i.putExtra("ScreenName","View My Observation");
                                                startActivity(i);
                                            }
                                        });
                                        iv6.setTag(i);

                                        iv6.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                int position=(int)v.getTag();

                                                String url="http://csafenet.com/"+myObservationsModuleList.get(position).getUploaded_image();
                                                showRadioButtonDialog(url);
                                            }
                                        });

                                        tv08.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent i = new Intent(getApplicationContext(), NewObservationActivity.class);
                                                i.putExtra("Observations",  myObservationsModuleList.get(position));
                                                i.putExtra("ScreenName","View My Observation");
                                                startActivity(i);
                                            }
                                        });

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
                    MyData.put(StringConstants.inputEnterbyId, s_user_id);
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
    private void showRadioButtonDialog(String url) {

        // custom dialog
        final Dialog dialog = new Dialog(ViewObservationsActivity.this);
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
