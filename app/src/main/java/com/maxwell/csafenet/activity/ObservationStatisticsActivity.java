package com.maxwell.csafenet.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.HomePageDetailsModel;
import com.maxwell.csafenet.model.PolicyDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ObservationStatisticsActivity extends AppCompatActivity {

    TextView tv_open_observations,tv_closed_observations,tv_totl_observations,tv_title;

    String openStatistics="0",closedStatistics="0";
    String title="";

    BarChart chart ;
    ArrayList typeOfObservation;
    ArrayList noOfObservations;
    DatePickerDialog datePickerDialog;

    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String dateToStr;
    EditText et_fromdate,et_todate;
    Button b_view;
    String s_fromdate,s_todate;
    RelativeLayout layout_progress;
    public static final int[] COLORFUL_COLORS = {
            Color.rgb(204, 0, 0), Color.rgb(153, 204, 0),Color.rgb(0, 153, 204)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_observation_statistics);

        initializeViews();
    }

    public  void back(View view){
        onBackPressed();
    }

    public void initializeViews(){

        tv_open_observations=(TextView)findViewById(R.id.text_opened_observations_count);
        tv_closed_observations=(TextView)findViewById(R.id.text_closed_observation_count);
        tv_totl_observations=(TextView)findViewById(R.id.total_obsevations_count);
        tv_title=(TextView)findViewById(R.id.text_title);
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        chart = findViewById(R.id.barchart);
        et_fromdate =(EditText)findViewById(R.id.fromdate);
        et_todate =(EditText)findViewById(R.id.todate);
        b_view=(Button)findViewById(R.id.button_view);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        b_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_fromdate=et_fromdate.getText().toString().trim();
                s_todate=et_todate.getText().toString().trim();
                getObservationDetaisDateWise();
            }
        });
        dateToStr=String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);

        title=getIntent().getStringExtra("Screen");

        tv_title.setText(title);

        tv_open_observations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),ViewObservationByStatusActivity.class);
                i.putExtra("Status","opened");
                startActivity(i);
            }
        });

        tv_closed_observations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),ViewObservationByStatusActivity.class);
                i.putExtra("Status","closed");
                startActivity(i);
            }
        });

       // new GetObservationDeatils().execute();
        getObservationDetais();
    }

    public void getObservationDetais(){
        ProgressDialog pDialog=new ProgressDialog(ObservationStatisticsActivity.this);
        RequestQueue requestQueue = Volley.newRequestQueue(ObservationStatisticsActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.observationStatisticsUrl, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                if(!response.matches("")){

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("open_observence")){
                            JSONArray jsonArray=new JSONArray();
                            jsonArray=jsonObject.getJSONArray("open_observence");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject openstatisticsObject=jsonArray.getJSONObject(i);
                                openStatistics=openstatisticsObject.getString("open_observence");

                            }
                        }
                        if(jsonObject.has("closed_observence")){
                            JSONArray jsonArrayClosedStatistics=new JSONArray();
                            jsonArrayClosedStatistics=jsonObject.getJSONArray("closed_observence");
                            for(int i=0;i<jsonArrayClosedStatistics.length();i++){
                                JSONObject closedstatisticsObject=jsonArrayClosedStatistics.getJSONObject(i);
                                closedStatistics=closedstatisticsObject.getString("open_observence");

                            }
                        }

                        typeOfObservation=new ArrayList();
                        noOfObservations=new ArrayList();

                           /* tv_open_observations.setText(openStatistics);
                            tv_closed_observations.setText(closedStatistics);*/
                        int totalOpeservation=Integer.parseInt(openStatistics)+Integer.parseInt(closedStatistics);
                        noOfObservations.add(new BarEntry(Float.valueOf(openStatistics), 0));
                        noOfObservations.add(new BarEntry(Float.valueOf(closedStatistics), 1));
                        noOfObservations.add(new BarEntry(Float.valueOf(totalOpeservation), 2));
                        typeOfObservation.add("Opened");
                        typeOfObservation.add("Closed");
                        typeOfObservation.add("Total");

                        BarDataSet bardataset = new BarDataSet(noOfObservations, "");
                        chart.animateY(5000);
                        chart.getLegend().setEnabled(false);
                        chart.setDescription("");
                        BarData data1 = new BarData(typeOfObservation, bardataset);
                        bardataset.setColors(COLORFUL_COLORS);
                        chart.setData(data1);

                        tv_totl_observations.setText(String.valueOf(totalOpeservation));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                    openStatistics="0";
                    closedStatistics="0";


                }

                layout_progress.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                //     MyData.put(StringConstants.inputEnterbyId, s_user_id);
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);


    }
    public void getObservationDetaisDateWise(){
        ProgressDialog pDialog=new ProgressDialog(ObservationStatisticsActivity.this);
        RequestQueue requestQueue = Volley.newRequestQueue(ObservationStatisticsActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.observationStatisticsUrl+StringConstants.inputFromDate, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                if(!response.matches("")){

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("open_observence")){
                            JSONArray jsonArray=new JSONArray();
                            jsonArray=jsonObject.getJSONArray("open_observence");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject openstatisticsObject=jsonArray.getJSONObject(i);
                                openStatistics=openstatisticsObject.getString("open_observence");

                            }
                        }
                        if(jsonObject.has("closed_observence")){
                            JSONArray jsonArrayClosedStatistics=new JSONArray();
                            jsonArrayClosedStatistics=jsonObject.getJSONArray("closed_observence");
                            for(int i=0;i<jsonArrayClosedStatistics.length();i++){
                                JSONObject closedstatisticsObject=jsonArrayClosedStatistics.getJSONObject(i);
                                closedStatistics=closedstatisticsObject.getString("open_observence");

                            }
                        }

                        typeOfObservation=new ArrayList();
                        noOfObservations=new ArrayList();

                           /* tv_open_observations.setText(openStatistics);
                            tv_closed_observations.setText(closedStatistics);*/
                        int totalOpeservation=Integer.parseInt(openStatistics)+Integer.parseInt(closedStatistics);
                        noOfObservations.add(new BarEntry(Float.valueOf(openStatistics), 0));
                        noOfObservations.add(new BarEntry(Float.valueOf(closedStatistics), 1));
                        noOfObservations.add(new BarEntry(Float.valueOf(totalOpeservation), 2));
                        typeOfObservation.add("Opened");
                        typeOfObservation.add("Closed");
                        typeOfObservation.add("Total");

                        BarDataSet bardataset = new BarDataSet(noOfObservations, "");
                        chart.animateY(5000);
                        chart.getLegend().setEnabled(false);
                        chart.setDescription("");
                        BarData data1 = new BarData(typeOfObservation, bardataset);
                        bardataset.setColors(COLORFUL_COLORS);
                        chart.setData(data1);

                        tv_totl_observations.setText(String.valueOf(totalOpeservation));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                    openStatistics="0";
                    closedStatistics="0";


                }

                layout_progress.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put(StringConstants.inputFromDate, s_fromdate);
                MyData.put(StringConstants.inputToDate, s_todate);

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                //     MyData.put(StringConstants.inputEnterbyId, s_user_id);
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);


    }

    private class GetObservationDeatils extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(ObservationStatisticsActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(ObservationStatisticsActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.observationStatisticsUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());
                            if(jsonObject.has("open_observence")){
                                JSONArray jsonArray=new JSONArray();
                                jsonArray=jsonObject.getJSONArray("open_observence");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject openstatisticsObject=jsonArray.getJSONObject(i);
                                    openStatistics=openstatisticsObject.getString("open_observence");

                                }
                            }
                            if(jsonObject.has("closed_observence")){
                                JSONArray jsonArrayClosedStatistics=new JSONArray();
                                jsonArrayClosedStatistics=jsonObject.getJSONArray("closed_observence");
                                for(int i=0;i<jsonArrayClosedStatistics.length();i++){
                                    JSONObject closedstatisticsObject=jsonArrayClosedStatistics.getJSONObject(i);
                                    closedStatistics=closedstatisticsObject.getString("open_observence");

                                }
                            }

                            typeOfObservation=new ArrayList();
                            noOfObservations=new ArrayList();

                           /* tv_open_observations.setText(openStatistics);
                            tv_closed_observations.setText(closedStatistics);*/
                            int totalOpeservation=Integer.parseInt(openStatistics)+Integer.parseInt(closedStatistics);
                            noOfObservations.add(new BarEntry(Float.valueOf(openStatistics), 0));
                            noOfObservations.add(new BarEntry(Float.valueOf(closedStatistics), 1));
                            noOfObservations.add(new BarEntry(Float.valueOf(totalOpeservation), 2));
                            typeOfObservation.add("Opened");
                            typeOfObservation.add("Closed");
                            typeOfObservation.add("Total");

                            BarDataSet bardataset = new BarDataSet(noOfObservations, "");
                            chart.animateY(5000);
                            chart.getLegend().setEnabled(false);
                            chart.setDescription("");
                            BarData data1 = new BarData(typeOfObservation, bardataset);
                            bardataset.setColors(COLORFUL_COLORS);
                            chart.setData(data1);

                            tv_totl_observations.setText(String.valueOf(totalOpeservation));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                        openStatistics="0";
                        closedStatistics="0";


                    }

                    layout_progress.setVisibility(View.GONE);

                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    //     MyData.put(StringConstants.inputEnterbyId, s_user_id);
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
    public void onClic(final View view){

        final EditText et_date1= (EditText) view;

        datePickerDialog = new DatePickerDialog(ObservationStatisticsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //  date.setText(day + "/" + (month + 1) + "/" + year);
                        if(day<10&&month+1<10){
                            dateToStr="0"+String.valueOf(day)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
                            // currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+"0"+String.valueOf(day);
                        }else if(day<10&&month+1>10){
                            dateToStr="0"+String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
                            // currentDate=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+"0"+String.valueOf(day);
                        }else if(day>10&&month+1<10){
                            dateToStr=String.valueOf(day)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
                            //currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(day);
                        }else {
                            //currentDate=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day);
                            dateToStr=String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
                        }

                        // dateToStr=String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);

                        et_date1.setText(dateToStr);


                    }
                }, year, month, dayOfMonth);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-20000);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();


    }

}
