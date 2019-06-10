package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.DocDetails;

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

public class ClientWiseProcedureActivity extends AppCompatActivity {

    DocDetails docDetails;
    TableLayout tableLayout;
    List<DocDetails> docDetailsListView=new ArrayList<>();
    RelativeLayout layout_progress;
    LinearLayout layout_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_wise_procedure);

        tableLayout=(TableLayout)findViewById(R.id.table_entries);
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        new GetProjectProceudre().execute();

    }

    private class GetProjectProceudre extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(ClientWiseProcedureActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(ClientWiseProcedureActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hsemanagementSystemUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());
                            if(jsonObject.has("assessment")){

                                JSONObject object=jsonObject.getJSONObject("assessment");

                                if(object.has("Client HSE Procedures")){
                                    JSONArray jsonArray=object.getJSONArray("Client HSE Procedures");
                                    docDetailsListView=new ArrayList<>();

                                    for(int i=0;i<jsonArray.length();i++){

                                        JSONObject confinedArrayJSONObject=jsonArray.getJSONObject(i);
                                        docDetails=new DocDetails();
                                        docDetails.setDocType(confinedArrayJSONObject.getString("document"));
                                        docDetails.setDocNo(confinedArrayJSONObject.getString("dnumber"));
                                        docDetails.setDocIssueDate(confinedArrayJSONObject.getString("dissuedate"));
                                        docDetails.setDocRevision(confinedArrayJSONObject.getString("drevision"));
                                        docDetails.setDocTitle(confinedArrayJSONObject.getString("dtitle"));
                                        docDetails.setProjectTitle(confinedArrayJSONObject.getString("project"));
                                        docDetails.setDocOwner(confinedArrayJSONObject.getString("document_owner"));
                                        docDetails.setFilePath("http://csafenet.com/"+confinedArrayJSONObject.getString("file"));
                                        docDetailsListView.add(docDetails);

                                        //  fIDconfinedWorks=confinedArrayJSONObject.getString("field_id");
                                    }
                                    if(docDetailsListView.size()>0){
                                        {

                                            tableLayout.removeAllViews();

                                            TableRow tbrow0 = new TableRow(ClientWiseProcedureActivity.this);
                                            tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                            TextView tv0 = new TextView(ClientWiseProcedureActivity.this);
                                            tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv0.setText(" SL No. ");
                                            tv0.setTextColor(getResources().getColor(android.R.color.white));
                                            tv0.setPadding(10,10,10,10);
                                            tv0.setGravity(Gravity.CENTER);
                                            tbrow0.addView(tv0);
                                           /* TextView tv1 = new TextView(CAHSAPolicies.this);
                                            tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv1.setText(" Doc Type ");
                                            tv1.setGravity(Gravity.CENTER);
                                            tv1.setTextColor(getResources().getColor(android.R.color.white));
                                            tv1.setPadding(10,10,10,10);
                                            tbrow0.addView(tv1);*/
                                            TextView tv2 = new TextView(ClientWiseProcedureActivity.this);
                                            tv2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv2.setText(" Doc No. ");
                                            tv2.setGravity(Gravity.CENTER);
                                            tv2.setPadding(10,10,10,10);
                                            //tv2.setGravity(Gravity.START);
                                            tv2.setTextColor(getResources().getColor(android.R.color.white));
                                            tbrow0.addView(tv2);
                                            TextView tv3 = new TextView(ClientWiseProcedureActivity.this);
                                            tv3.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv3.setText(" Doc Title ");
                                            tv3.setGravity(Gravity.CENTER);
                                            tv3.setPadding(10,10,10,10);
                                            tv3.setTextColor(getResources().getColor(android.R.color.white));
                                            tbrow0.addView(tv3);
                                            TextView tv4 = new TextView(ClientWiseProcedureActivity.this);
                                            tv4.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv4.setText(" Doc Revision ");
                                            tv4.setGravity(Gravity.CENTER);
                                            tv4.setPadding(10,10,10,10);
                                            tv4.setTextColor(getResources().getColor(android.R.color.white));
                                            tbrow0.addView(tv4);
                                            TextView tv5 = new TextView(ClientWiseProcedureActivity.this);
                                            tv5.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv5.setText(" Doc Issue Date ");
                                            tv5.setGravity(Gravity.CENTER);
                                            tv5.setPadding(10,10,10,10);
                                            tv5.setTextColor(getResources().getColor(android.R.color.white));
                                            tbrow0.addView(tv5);
                                            TextView tv6 = new TextView(ClientWiseProcedureActivity.this);
                                            tv6.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv6.setText(" Project Title ");
                                            tv6.setPadding(10,10,10,10);
                                            tv6.setGravity(Gravity.CENTER);
                                            tv6.setTextColor(getResources().getColor(android.R.color.white));
                                            tbrow0.addView(tv6);
                                            TextView tv7 = new TextView(ClientWiseProcedureActivity.this);
                                            tv7.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv7.setText(" Doc Owner ");
                                            tv7.setPadding(10,10,10,10);
                                            tv7.setGravity(Gravity.CENTER);
                                            tv7.setTextColor(getResources().getColor(android.R.color.white));
                                            tbrow0.addView(tv7);
                                            TextView tv8 = new TextView(ClientWiseProcedureActivity.this);
                                            tv8.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv8.setText(" Document ");
                                            tv8.setPadding(10,10,10,10);
                                            tv8.setGravity(Gravity.CENTER);
                                            tv8.setTextColor(getResources().getColor(android.R.color.white));
                                            tbrow0.addView(tv8);
                                            tableLayout.addView(tbrow0);
                                            for(int i=0;i<docDetailsListView.size();i++){
                                                TableRow row=new TableRow(ClientWiseProcedureActivity.this);
                                                TextView tv00 = new TextView(ClientWiseProcedureActivity.this);
                                                tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv00.setPadding(10,10,10,10);
                                                tv00.setText(String.valueOf(i+1));
                                                tv00.setGravity(Gravity.CENTER);
                                                //  tv00.setPadding(12,0,0,0);
                                                row.addView(tv00);


                                               /* TextView tv01 = new TextView(CAHSAPolicies.this);
                                                tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv01.setPadding(10,10,10,10);
                                                tv01.setText(docDetailsListView.get(i).getDocType());
                                                tv01.setGravity(Gravity.CENTER);
                                                row.addView(tv01);*/
                                                TextView tv02 = new TextView(ClientWiseProcedureActivity.this);
                                                tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv02.setPadding(10,10,10,10);
                                                tv02.setText(docDetailsListView.get(i).getDocNo());
                                                tv02.setGravity(Gravity.CENTER);
                                                row.addView(tv02);
                                                TextView tv03 = new TextView(ClientWiseProcedureActivity.this);
                                                tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv03.setPadding(10,10,10,10);
                                                tv03.setText(docDetailsListView.get(i).getDocTitle());
                                                tv03.setGravity(Gravity.CENTER);
                                                row.addView(tv03);
                                                TextView tv04 = new TextView(ClientWiseProcedureActivity.this);
                                                tv04.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv04.setPadding(10,10,10,10);
                                                tv04.setText(docDetailsListView.get(i).getDocRevision());
                                                tv04.setGravity(Gravity.CENTER);
                                                row.addView(tv04);
                                                TextView tv05 = new TextView(ClientWiseProcedureActivity.this);
                                                tv05.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv05.setPadding(10,10,10,10);
                                                String str =docDetailsListView.get(i).getDocIssueDate();
                                                try {
                                                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                                                    String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
                                                    tv05.setText(formattedDate);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                    tv05.setText(docDetailsListView.get(i).getDocIssueDate());
                                                }
                                                //  tv05.setText(docDetailsListView.get(i).getDocIssueDate());
                                                tv05.setGravity(Gravity.CENTER);
                                                row.addView(tv05);
                                                TextView tv06 = new TextView(ClientWiseProcedureActivity.this);
                                                tv06.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv06.setPadding(10,10,10,10);
                                                tv06.setText(docDetailsListView.get(i).getProjectTitle());
                                                tv06.setGravity(Gravity.CENTER);
                                                row.addView(tv06);
                                                TextView tv07 = new TextView(ClientWiseProcedureActivity.this);
                                                tv07.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv07.setPadding(10,10,10,10);
                                                tv07.setText(docDetailsListView.get(i).getDocOwner());
                                                tv07.setGravity(Gravity.CENTER);
                                                row.addView(tv07);
                                                TextView tv08 = new TextView(ClientWiseProcedureActivity.this);
                                                tv08.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                                tv08.setPadding(10,10,10,10);
                                                tv08.setText("View");
                                                tv08.setGravity(Gravity.CENTER);
                                                row.addView(tv08);
                                                tableLayout.addView(row);

                                            /*TableRow row1=new TableRow(ViewEntryActivity.this);
                                            TextView tv11=new TextView(ViewEntryActivity.this);
                                            tv11.setText("");
                                            row1.addView(tv11);
                                            tableLayout.addView(row1);
*/
                                                tv08.setTag(i);
                                                tv08.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        int position=(int)view.getTag();
                                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(docDetailsListView.get(position).getFilePath()));
                                                        startActivity(browserIntent);

                                                 /*   Intent i=new Intent(getApplicationContext(),PDFViewerActivity.class);
                                                    i.putExtra("PdfUrl",docDetailsListView.get(position).getFilePath());
                                                    startActivity(i);*/
                                                    }
                                                });



                                            }

                                        }
                                    }


                                }

                            }else {
                                tableLayout.removeAllViews();
                                TableRow tbrow0 = new TableRow(ClientWiseProcedureActivity.this);
                                tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                TextView tv0 = new TextView(ClientWiseProcedureActivity.this);
                                tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv0.setText(" SL No. ");
                                tv0.setTextColor(getResources().getColor(android.R.color.white));
                                tv0.setPadding(10,10,10,10);
                                tv0.setGravity(Gravity.CENTER);
                                tbrow0.addView(tv0);
                                           /* TextView tv1 = new TextView(CAHSAPolicies.this);
                                            tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                            tv1.setText(" Doc Type ");
                                            tv1.setGravity(Gravity.CENTER);
                                            tv1.setTextColor(getResources().getColor(android.R.color.white));
                                            tv1.setPadding(10,10,10,10);
                                            tbrow0.addView(tv1);*/
                                TextView tv2 = new TextView(ClientWiseProcedureActivity.this);
                                tv2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv2.setText(" Doc No. ");
                                tv2.setGravity(Gravity.CENTER);
                                tv2.setPadding(10,10,10,10);
                                //tv2.setGravity(Gravity.START);
                                tv2.setTextColor(getResources().getColor(android.R.color.white));
                                tbrow0.addView(tv2);
                                TextView tv3 = new TextView(ClientWiseProcedureActivity.this);
                                tv3.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv3.setText(" Doc Title ");
                                tv3.setGravity(Gravity.CENTER);
                                tv3.setPadding(10,10,10,10);
                                tv3.setTextColor(getResources().getColor(android.R.color.white));
                                tbrow0.addView(tv3);
                                TextView tv4 = new TextView(ClientWiseProcedureActivity.this);
                                tv4.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv4.setText(" Doc Revision ");
                                tv4.setGravity(Gravity.CENTER);
                                tv4.setPadding(10,10,10,10);
                                tv4.setTextColor(getResources().getColor(android.R.color.white));
                                tbrow0.addView(tv4);
                                TextView tv5 = new TextView(ClientWiseProcedureActivity.this);
                                tv5.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv5.setText(" Doc Issue Date ");
                                tv5.setGravity(Gravity.CENTER);
                                tv5.setPadding(10,10,10,10);
                                tv5.setTextColor(getResources().getColor(android.R.color.white));
                                tbrow0.addView(tv5);
                                TextView tv6 = new TextView(ClientWiseProcedureActivity.this);
                                tv6.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv6.setText(" Project Title ");
                                tv6.setPadding(10,10,10,10);
                                tv6.setGravity(Gravity.CENTER);
                                tv6.setTextColor(getResources().getColor(android.R.color.white));
                                tbrow0.addView(tv6);
                                TextView tv7 = new TextView(ClientWiseProcedureActivity.this);
                                tv7.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv7.setText(" Doc Owner ");
                                tv7.setPadding(10,10,10,10);
                                tv7.setGravity(Gravity.CENTER);
                                tv7.setTextColor(getResources().getColor(android.R.color.white));
                                tbrow0.addView(tv7);
                                TextView tv8 = new TextView(ClientWiseProcedureActivity.this);
                                tv8.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                                tv8.setText(" Document ");
                                tv8.setPadding(10,10,10,10);
                                tv8.setGravity(Gravity.CENTER);
                                tv8.setTextColor(getResources().getColor(android.R.color.white));
                                tbrow0.addView(tv8);
                                tableLayout.addView(tbrow0);
                                TableRow row=new TableRow(ClientWiseProcedureActivity.this);
                                TextView tv08 = new TextView(ClientWiseProcedureActivity.this);
                                tv08.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                tv08.setPadding(10,10,10,10);
                                tv08.setText("No Items Found");
                                tv08.setGravity(Gravity.CENTER);
                                row.addView(tv08);
                                tableLayout.addView(row);
                            }

                            layout_progress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);
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
                    // MyData.put(StringConstants.inputEnterbyId, s_user_id);
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
