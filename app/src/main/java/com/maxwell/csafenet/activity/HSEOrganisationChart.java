package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.AcheivementLettersAdapter;
import com.maxwell.csafenet.model.AwardDetails;
import com.maxwell.csafenet.model.DocDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSEOrganisationChart extends AppCompatActivity {

    DocDetails docDetails;
    TableLayout tableLayout;
    List<DocDetails> docDetailsListView=new ArrayList<>();

    TextView tv_name;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cahsapolicies);

        tableLayout=(TableLayout)findViewById(R.id.table_entries);
        tv_name=(TextView)findViewById(R.id.textview_name);
        tv_name.setText("HSE Organisation Chart");

        new GetOrganisationChart().execute();


    }

    private class GetOrganisationChart extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(HSEOrganisationChart.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(HSEOrganisationChart.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hseOrganisationChartUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());

                            if(jsonObject.has("achievements")){
                                JSONArray confinedArray=new JSONArray();
                                confinedArray=jsonObject.getJSONArray("achievements");
                                docDetailsListView=new ArrayList<>();
                                for(int i=0;i<confinedArray.length();i++){

                                    JSONObject confinedArrayJSONObject=confinedArray.getJSONObject(i);
                                    docDetails=new DocDetails();
                                    docDetails.setDocIssueDate(confinedArrayJSONObject.getString("date"));
                                    docDetails.setDocRevision(confinedArrayJSONObject.getString("rev_no"));
                                    docDetails.setDocTitle(confinedArrayJSONObject.getString("document"));
                                    docDetails.setProjectTitle(confinedArrayJSONObject.getString("project"));
                                    docDetails.setFilePath("http://csafenet.com/"+confinedArrayJSONObject.getString("file"));
                                    docDetailsListView.add(docDetails);

                                    //  fIDconfinedWorks=confinedArrayJSONObject.getString("field_id");
                                }

                                if(docDetailsListView.size()>0){
                                    {

                                        tableLayout.removeAllViews();

                                        TableRow tbrow0 = new TableRow(HSEOrganisationChart.this);
                                        tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        TextView tv0 = new TextView(HSEOrganisationChart.this);
                                        tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv0.setText(" SL No. ");
                                        tv0.setTextColor(getResources().getColor(android.R.color.black));
                                        tv0.setPadding(10,10,10,10);
                                        tv0.setGravity(Gravity.CENTER);
                                        tbrow0.addView(tv0);
                                        TextView tv1 = new TextView(HSEOrganisationChart.this);
                                        tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv1.setText(" Date ");
                                        tv1.setGravity(Gravity.CENTER);
                                        tv1.setTextColor(getResources().getColor(android.R.color.black));
                                        tv1.setPadding(10,10,10,10);
                                        tbrow0.addView(tv1);

                                        TextView tv4 = new TextView(HSEOrganisationChart.this);
                                        tv4.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv4.setText(" Rev Number ");
                                        tv4.setGravity(Gravity.CENTER);
                                        tv4.setPadding(10,10,10,10);
                                        tv4.setTextColor(getResources().getColor(android.R.color.black));
                                        tbrow0.addView(tv4);
                                        TextView tv5 = new TextView(HSEOrganisationChart.this);
                                        tv5.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv5.setText(" Doc Title ");
                                        tv5.setGravity(Gravity.CENTER);
                                        tv5.setPadding(10,10,10,10);
                                        tv5.setTextColor(getResources().getColor(android.R.color.black));
                                        tbrow0.addView(tv5);
                                        TextView tv6 = new TextView(HSEOrganisationChart.this);
                                        tv6.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv6.setText(" Project Title ");
                                        tv6.setPadding(10,10,10,10);
                                        tv6.setGravity(Gravity.CENTER);
                                        tv6.setTextColor(getResources().getColor(android.R.color.black));
                                        tbrow0.addView(tv6);
                                        TextView tv7 = new TextView(HSEOrganisationChart.this);
                                        tv7.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv7.setText(" Document ");
                                        tv7.setPadding(10,10,10,10);
                                        tv7.setGravity(Gravity.CENTER);
                                        tv7.setTextColor(getResources().getColor(android.R.color.black));
                                        tbrow0.addView(tv7);

                                        tableLayout.addView(tbrow0);

                                        /*TableRow row0=new TableRow(ViewEntryActivity.this);
                                        TextView tv=new TextView(ViewEntryActivity.this);
                                        tv.setText("");
                                        row0.addView(tv);
                                        tableLayout.addView(row0);*/


                                        for(int i=0;i<docDetailsListView.size();i++){

                                            TableRow row=new TableRow(HSEOrganisationChart.this);


                                            TextView tv00 = new TextView(HSEOrganisationChart.this);
                                            tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv00.setPadding(10,10,10,10);
                                            tv00.setText(String.valueOf(i+1));
                                            tv00.setGravity(Gravity.CENTER);
                                            //  tv00.setPadding(12,0,0,0);
                                            row.addView(tv00);
                                            TextView tv05 = new TextView(HSEOrganisationChart.this);
                                            tv05.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv05.setPadding(10,10,10,10);
                                            tv05.setText(docDetailsListView.get(i).getDocIssueDate());
                                            tv05.setGravity(Gravity.CENTER);
                                            row.addView(tv05);
                                            TextView tv04 = new TextView(HSEOrganisationChart.this);
                                            tv04.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv04.setPadding(10,10,10,10);
                                            tv04.setText(docDetailsListView.get(i).getDocRevision());
                                            tv04.setGravity(Gravity.CENTER);
                                            row.addView(tv04);
                                            TextView tv03 = new TextView(HSEOrganisationChart.this);
                                            tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv03.setPadding(10,10,10,10);
                                            tv03.setText(docDetailsListView.get(i).getDocTitle());
                                            tv03.setGravity(Gravity.CENTER);
                                            row.addView(tv03);
                                            TextView tv06 = new TextView(HSEOrganisationChart.this);
                                            tv06.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv06.setPadding(10,10,10,10);
                                            tv06.setText(docDetailsListView.get(i).getProjectTitle());
                                            tv06.setGravity(Gravity.CENTER);
                                            row.addView(tv06);
                                            TextView tv07 = new TextView(HSEOrganisationChart.this);
                                            tv07.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                            tv07.setPadding(10,10,10,10);
                                            tv07.setText("View");
                                            tv07.setGravity(Gravity.CENTER);
                                            row.addView(tv07);
                                            tableLayout.addView(row);

                                            tv07.setTag(i);
                                            tv07.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    int position=(int)view.getTag();

                                                    Intent i=new Intent(getApplicationContext(),PDFViewerActivity.class);
                                                    i.putExtra("PdfUrl",docDetailsListView.get(position).getFilePath());
                                                    startActivity(i);
                                                }
                                            });


                                            /*TableRow row1=new TableRow(ViewEntryActivity.this);
                                            TextView tv11=new TextView(ViewEntryActivity.this);
                                            tv11.setText("");
                                            row1.addView(tv11);
                                            tableLayout.addView(row1);
*/


                                        }

                                    }
                                }

                            }else {
                                //tv_no_records.setVisibility(View.VISIBLE);
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


    public  void back(View view){
       onBackPressed();
    }

}
