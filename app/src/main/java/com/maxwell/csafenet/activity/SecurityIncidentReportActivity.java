package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecurityIncidentReportActivity extends AppCompatActivity {

    String openStatistics="0",closedStatistics="0";

    TextView tv_total_observations;
    PieChart pieChart;
    ArrayList typeOfObservation;
    ArrayList noOfObservations;

    public static final int[] COLORFUL_COLORS = {
            Color.rgb(204, 0, 0), Color.rgb(153, 204, 0),Color.rgb(0, 153, 204)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_incident_report);

        tv_total_observations=(TextView)findViewById(R.id.total_obsevations_count);
        pieChart=findViewById(R.id.piechart);

        new GetObservationDeatils().execute();
    }
    public  void back(View view){
       onBackPressed();
    }
    private class GetObservationDeatils extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(SecurityIncidentReportActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(SecurityIncidentReportActivity.this);
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                        openStatistics="0";
                        closedStatistics="0";


                    }
                    typeOfObservation=new ArrayList();
                    noOfObservations=new ArrayList();
                    int totalOpeservation=Integer.parseInt(openStatistics)+Integer.parseInt(closedStatistics);
                    noOfObservations.add(new BarEntry(Float.valueOf(openStatistics), 0));
                    noOfObservations.add(new BarEntry(Float.valueOf(closedStatistics), 1));
                    noOfObservations.add(new BarEntry(Float.valueOf(totalOpeservation), 2));

                    PieDataSet dataSet = new PieDataSet(noOfObservations, "");
                    //dataSet.setColor(R.color.white_copy);
                    typeOfObservation.add("Opened");
                    typeOfObservation.add("Closed");
                    typeOfObservation.add("Total");
                    PieData data = new PieData(typeOfObservation, dataSet);
                    data.setValueTextColor(Color.WHITE);
                    pieChart.setData(data);
                    pieChart.setDescription("");
                    pieChart.getLegend().setEnabled(false);
                    pieChart.setCenterTextColor(Color.WHITE);

                    dataSet.setColors(COLORFUL_COLORS);
                    pieChart.animateXY(5000, 5000);



                  //  tv_total_observations.setText("Total Observations : "+String.valueOf(totalOpeservation));
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

}
