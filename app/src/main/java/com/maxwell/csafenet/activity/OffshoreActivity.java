package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.maxwell.csafenet.model.HomePageDetailsModel;
import com.maxwell.csafenet.model.PolicyDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OffshoreActivity extends AppCompatActivity {

    TextView tv_title,tv_description,tv_heading1,tv_heading2,tv_heading3,tv_heading4,tv_heading5,tv_heading6,tv_heading7,tv_newsletter1, tv_newsletter2,tv_newsletter3,tv_newsletter4,tv_alerts1,tv_alerts2,tv_alerts3,tv_alerts4,tv_alerts5;
    ImageView banner,iv_policy;

    LinearLayout ll_policy,ll_alerts,ll_news_letter,ll_tips,ll_key,ll_risk,ll_stress;

    String title="";

    List<HomePageDetailsModel> homePageDetailsModelList,homePageDetailsModelList1,homePageDetailsModelList2,homePageDetailsModelList3,homePageDetailsModelList4,homePageDetailsModelList5;
    HomePageDetailsModel homePageDetailsModel;
    List<PolicyDetailsModel> policyDetailsModelList;
    PolicyDetailsModel policyDetailsModel;
    RelativeLayout layout_progress;
    LinearLayout layout_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offshore);

        initializeViews();

        new GetSecuirityManagemnetDetails().execute();
    }
    public  void back(View view){
        onBackPressed();
    }
    private class GetSecuirityManagemnetDetails extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(OffshoreActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(OffshoreActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.offShoreSafetytUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());
                            if(jsonObject.has("Data")){


                                    String desacription=jsonObject.getString("Data");
                                    Spanned sp = Html.fromHtml(desacription);
                                    tv_description.setText(sp);



                            }

                            if(jsonObject.has("Policys")){
                                JSONArray policyArray=jsonObject.getJSONArray("Policys");
                                policyDetailsModelList=new ArrayList<>();

                                for (int i=0;i<policyArray.length();i++){
                                    policyDetailsModel=new PolicyDetailsModel();
                                    JSONObject policyObject=policyArray.getJSONObject(i);
                                    if(!policyObject.has("error")){
                                        policyDetailsModel.setId(policyObject.getString("id"));
                                        policyDetailsModel.setCategory(policyObject.getString("category"));
                                        policyDetailsModel.setFilepath("http://csafenet.com/"+policyObject.getString("file_path"));
                                        policyDetailsModelList.add(policyDetailsModel);
                                    }
                                }
                                if(policyDetailsModelList.size()>0){
                                    for (int i=0;i<policyDetailsModelList.size();i++){
                                        final  ImageView imageView=new ImageView(OffshoreActivity.this);
                                        Glide.with(OffshoreActivity.this)
                                                .load(policyDetailsModelList.get(i).getFilepath()) // image url
                                                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                                //.error(R.drawable.imagenotfound)  // any image in case of error
                                                // .override(200, 200); // resizing
                                                //.apply(new RequestOptions().placeholder(R.drawable.loading))
                                                .into(imageView);
                                        ll_policy.addView(imageView);
                                    }
                                }else {
                                    final TextView rowTextView = new TextView(OffshoreActivity.this);

                                    rowTextView.setText("");
                                    rowTextView.setPadding(10,10,10,10);
                                    // add the textview to the linearlayout
                                    ll_policy.addView(rowTextView);
                                }

                            }
                            if(jsonObject.has("Alerts")){
                                JSONArray alertsArray=jsonObject.getJSONArray("Alerts");


                                homePageDetailsModelList=new ArrayList<>();
                                for (int i=0;i<alertsArray.length();i++) {
                                    JSONObject alertsObjects = alertsArray.getJSONObject(i);
                                    if (!alertsObjects.has("error")) {
                                        homePageDetailsModel = new HomePageDetailsModel();
                                        homePageDetailsModel.setId(alertsObjects.getString("id"));
                                        homePageDetailsModel.setCategory(alertsObjects.getString("category"));
                                        homePageDetailsModel.setDate(alertsObjects.getString("date"));
                                        homePageDetailsModel.setTitle(alertsObjects.getString("title"));
                                        homePageDetailsModel.setFilepath("http://csafenet.com/" + alertsObjects.getString("file_path"));
                                        homePageDetailsModel.setReg_date(alertsObjects.getString("reg_date"));
                                        homePageDetailsModel.setReg_time(alertsObjects.getString("reg_time"));
                                        homePageDetailsModelList.add(homePageDetailsModel);
                                    }
                                }
                                if(homePageDetailsModelList.size()>0){

                                    for (int i=0;i<homePageDetailsModelList.size();i++){

                                        final TextView rowTextView = new TextView(OffshoreActivity.this);

                                        rowTextView.setText(homePageDetailsModelList.get(i).getTitle());
                                        rowTextView.setPadding(10,10,10,10);
                                        // add the textview to the linearlayout
                                        ll_alerts.addView(rowTextView);
                                        rowTextView.setTag(i);

                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageDetailsModelList.get(position).getFilepath()));
                                                startActivity(browserIntent);
                                            }
                                        });

                                        // save a reference to the textview for later
                                        // myTextViews[i] = rowTextView;
                                    }


                                }else {
                                    final TextView rowTextView = new TextView(OffshoreActivity.this);

                                    rowTextView.setText("");
                                    rowTextView.setPadding(10,10,10,10);
                                    // add the textview to the linearlayout
                                    ll_alerts.addView(rowTextView);
                                }
                            }
                            if(jsonObject.has("NewsLetter")){
                                JSONArray alertsArray=jsonObject.getJSONArray("NewsLetter");

                                homePageDetailsModelList1=new ArrayList<>();
                                for (int i=0;i<alertsArray.length();i++){
                                    JSONObject alertsObjects=alertsArray.getJSONObject(i);

                                    if(!alertsObjects.has("error")){


                                        homePageDetailsModel=new HomePageDetailsModel();
                                        homePageDetailsModel.setId(alertsObjects.getString("id"));
                                        homePageDetailsModel.setCategory(alertsObjects.getString("category"));
                                        homePageDetailsModel.setDate(alertsObjects.getString("date"));
                                        homePageDetailsModel.setTitle(alertsObjects.getString("title"));
                                        homePageDetailsModel.setFilepath("http://csafenet.com/"+alertsObjects.getString("file_path"));
                                        homePageDetailsModel.setReg_date(alertsObjects.getString("reg_date"));
                                        homePageDetailsModel.setReg_time(alertsObjects.getString("reg_time"));
                                        homePageDetailsModelList1.add(homePageDetailsModel);
                                    }
                                }
                                if(homePageDetailsModelList1.size()>0){

                                    for (int i=0;i<homePageDetailsModelList1.size();i++){

                                        final TextView rowTextView = new TextView(OffshoreActivity.this);

                                        rowTextView.setText(homePageDetailsModelList1.get(i).getTitle());
                                        rowTextView.setPadding(10,10,10,10);
                                        // add the textview to the linearlayout
                                        ll_news_letter.addView(rowTextView);
                                        rowTextView.setTag(i);
                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageDetailsModelList1.get(position).getFilepath()));
                                                startActivity(browserIntent);
                                            }
                                        });

                                        // save a reference to the textview for later
                                        // myTextViews[i] = rowTextView;
                                    }


                                }else {
                                    final TextView rowTextView = new TextView(OffshoreActivity.this);

                                    rowTextView.setText("");
                                    rowTextView.setPadding(10,10,10,10);
                                    // add the textview to the linearlayout
                                    ll_news_letter.addView(rowTextView);
                                }
                            }
                            if(jsonObject.has("Key")){
                                JSONArray keyArray=jsonObject.getJSONArray("Key");

                                homePageDetailsModelList2=new ArrayList<>();
                                for (int i=0;i<keyArray.length();i++) {
                                    JSONObject alertsObjects = keyArray.getJSONObject(i);
                                    if (!alertsObjects.has("error")) {

                                        homePageDetailsModel = new HomePageDetailsModel();
                                        homePageDetailsModel.setId(alertsObjects.getString("id"));
                                        homePageDetailsModel.setCategory(alertsObjects.getString("category"));
                                        homePageDetailsModel.setDate(alertsObjects.getString("date"));
                                        homePageDetailsModel.setTitle(alertsObjects.getString("title"));
                                        homePageDetailsModel.setFilepath("http://csafenet.com/" + alertsObjects.getString("file_path"));
                                        homePageDetailsModel.setReg_date(alertsObjects.getString("reg_date"));
                                        homePageDetailsModel.setReg_time(alertsObjects.getString("reg_time"));
                                        homePageDetailsModelList2.add(homePageDetailsModel);
                                    }

                                }
                                if(homePageDetailsModelList2.size()>0){

                                    for (int i=0;i<homePageDetailsModelList2.size();i++){

                                        final TextView rowTextView = new TextView(OffshoreActivity.this);

                                        rowTextView.setText(homePageDetailsModelList2.get(i).getTitle());
                                        rowTextView.setPadding(10,10,10,10);
                                        // add the textview to the linearlayout
                                        ll_key.addView(rowTextView);
                                        rowTextView.setTag(i);
                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageDetailsModelList2.get(position).getFilepath()));
                                                startActivity(browserIntent);
                                            }
                                        });

                                        // save a reference to the textview for later
                                        // myTextViews[i] = rowTextView;
                                    }


                                }else {

                                    final TextView rowTextView = new TextView(OffshoreActivity.this);

                                    rowTextView.setText("");
                                    rowTextView.setPadding(10,10,10,10);
                                    // add the textview to the linearlayout
                                    ll_key.addView(rowTextView);

                                }
                            }
                            if(jsonObject.has("Reports")){
                                JSONArray reportArray=jsonObject.getJSONArray("Reports");

                                homePageDetailsModelList3=new ArrayList<>();
                                for (int i=0;i<reportArray.length();i++) {
                                    JSONObject alertsObjects = reportArray.getJSONObject(i);
                                    if (!alertsObjects.has("error")) {

                                        homePageDetailsModel = new HomePageDetailsModel();
                                        homePageDetailsModel.setId(alertsObjects.getString("id"));
                                        homePageDetailsModel.setCategory(alertsObjects.getString("category"));
                                        homePageDetailsModel.setDate(alertsObjects.getString("date"));
                                        homePageDetailsModel.setTitle(alertsObjects.getString("title"));
                                        homePageDetailsModel.setFilepath("http://csafenet.com/" + alertsObjects.getString("file_path"));
                                        homePageDetailsModel.setReg_date(alertsObjects.getString("reg_date"));
                                        homePageDetailsModel.setReg_time(alertsObjects.getString("reg_time"));
                                        homePageDetailsModelList3.add(homePageDetailsModel);
                                    }

                                }
                                if(homePageDetailsModelList3.size()>0){

                                    for (int i=0;i<homePageDetailsModelList3.size();i++){

                                        final TextView rowTextView = new TextView(OffshoreActivity.this);

                                        rowTextView.setText(homePageDetailsModelList3.get(i).getTitle());
                                        rowTextView.setPadding(10,10,10,10);
                                        // add the textview to the linearlayout
                                        ll_stress.addView(rowTextView);
                                        rowTextView.setTag(i);
                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageDetailsModelList3.get(position).getFilepath()));
                                                startActivity(browserIntent);
                                            }
                                        });

                                        // save a reference to the textview for later
                                        // myTextViews[i] = rowTextView;
                                    }


                                }else {

                                    final TextView rowTextView = new TextView(OffshoreActivity.this);

                                    rowTextView.setText("");
                                    rowTextView.setPadding(10,10,10,10);
                                    // add the textview to the linearlayout
                                    ll_stress.addView(rowTextView);

                                }
                            }
                            if(jsonObject.has("Risks")){
                                JSONArray reportArray=jsonObject.getJSONArray("Risks");

                                homePageDetailsModelList4=new ArrayList<>();
                                for (int i=0;i<reportArray.length();i++) {
                                    JSONObject alertsObjects = reportArray.getJSONObject(i);
                                    if (!alertsObjects.has("error")) {

                                        homePageDetailsModel = new HomePageDetailsModel();
                                        homePageDetailsModel.setId(alertsObjects.getString("id"));
                                        homePageDetailsModel.setCategory(alertsObjects.getString("category"));
                                        homePageDetailsModel.setDate(alertsObjects.getString("date"));
                                        homePageDetailsModel.setTitle(alertsObjects.getString("title"));
                                        homePageDetailsModel.setFilepath("http://csafenet.com/" + alertsObjects.getString("file_path"));
                                        homePageDetailsModel.setReg_date(alertsObjects.getString("reg_date"));
                                        homePageDetailsModel.setReg_time(alertsObjects.getString("reg_time"));
                                        homePageDetailsModelList4.add(homePageDetailsModel);
                                    }

                                }
                                if(homePageDetailsModelList4.size()>0){

                                    for (int i=0;i<homePageDetailsModelList4.size();i++){

                                        final TextView rowTextView = new TextView(OffshoreActivity.this);

                                        rowTextView.setText(homePageDetailsModelList4.get(i).getTitle());
                                        rowTextView.setPadding(10,10,10,10);
                                        // add the textview to the linearlayout
                                        ll_risk.addView(rowTextView);
                                        rowTextView.setTag(i);
                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageDetailsModelList4.get(position).getFilepath()));
                                                startActivity(browserIntent);
                                            }
                                        });
                                        // save a reference to the textview for later
                                        // myTextViews[i] = rowTextView;
                                    }


                                }else {

                                    final TextView rowTextView = new TextView(OffshoreActivity.this);

                                    rowTextView.setText("");
                                    rowTextView.setPadding(10,10,10,10);
                                    // add the textview to the linearlayout
                                    ll_risk.addView(rowTextView);

                                }
                            }
                            if(jsonObject.has("Tips")){
                                JSONArray tipsArray=jsonObject.getJSONArray("Tips");

                                homePageDetailsModelList5=new ArrayList<>();
                                for (int i=0;i<tipsArray.length();i++) {
                                    JSONObject alertsObjects = tipsArray.getJSONObject(i);
                                    if (!alertsObjects.has("error")) {

                                        homePageDetailsModel = new HomePageDetailsModel();
                                        homePageDetailsModel.setId(alertsObjects.getString("id"));
                                        homePageDetailsModel.setCategory(alertsObjects.getString("category"));
                                        homePageDetailsModel.setDate(alertsObjects.getString("date"));
                                        homePageDetailsModel.setTitle(alertsObjects.getString("title"));
                                        homePageDetailsModel.setFilepath("http://csafenet.com/" + alertsObjects.getString("file_path"));
                                        homePageDetailsModel.setReg_date(alertsObjects.getString("reg_date"));
                                        homePageDetailsModel.setReg_time(alertsObjects.getString("reg_time"));
                                        homePageDetailsModelList5.add(homePageDetailsModel);
                                    }

                                }
                                if(homePageDetailsModelList5.size()>0){

                                    for (int i=0;i<homePageDetailsModelList5.size();i++){

                                        final TextView rowTextView = new TextView(OffshoreActivity.this);

                                        rowTextView.setText(homePageDetailsModelList5.get(i).getTitle());
                                        rowTextView.setPadding(10,10,10,10);
                                        // add the textview to the linearlayout
                                        ll_tips.addView(rowTextView);
                                        rowTextView.setTag(i);
                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();

                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(homePageDetailsModelList5.get(position).getFilepath()));
                                                startActivity(browserIntent);
                                            }
                                        });
                                        // save a reference to the textview for later
                                        // myTextViews[i] = rowTextView;
                                    }


                                }else {

                                    final TextView rowTextView = new TextView(OffshoreActivity.this);

                                    rowTextView.setText("");
                                    rowTextView.setPadding(10,10,10,10);
                                    // add the textview to the linearlayout
                                    ll_tips.addView(rowTextView);

                                }
                            }
                            layout_progress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

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
    public void initializeViews(){

        tv_title=(TextView)findViewById(R.id.text_title);
        tv_description=(TextView)findViewById(R.id.text_description);
        tv_heading1=(TextView)findViewById(R.id.text_heading1);
        tv_heading2=(TextView)findViewById(R.id.text_heading2);
        tv_heading3=(TextView)findViewById(R.id.text_heading3);
        tv_heading4=(TextView)findViewById(R.id.text_heading4);
        tv_heading5=(TextView)findViewById(R.id.text_heading5);
        tv_heading6=(TextView)findViewById(R.id.text_heading6);
        tv_heading7=(TextView)findViewById(R.id.text_heading7);
        tv_newsletter1=(TextView)findViewById(R.id.text_newsletter1);
        tv_newsletter2 =(TextView)findViewById(R.id.text_newsletter2);
        tv_newsletter3=(TextView)findViewById(R.id.text_newsletter3);
        tv_newsletter4=(TextView)findViewById(R.id.text_newsletter4);
        tv_alerts1=(TextView)findViewById(R.id.text_alerts1);
        tv_alerts2=(TextView)findViewById(R.id.text_alerts2);
        tv_alerts3=(TextView)findViewById(R.id.text_alerts3);
        tv_alerts4=(TextView)findViewById(R.id.text_alerts4);
        tv_alerts5=(TextView)findViewById(R.id.text_alerts5);
        banner=(ImageView)findViewById(R.id.image_banner);
        iv_policy=(ImageView)findViewById(R.id.image_policy);

        ll_alerts=(LinearLayout)findViewById(R.id.linear_alerts);
        ll_key=(LinearLayout)findViewById(R.id.linear_key_documentation);
        ll_news_letter=(LinearLayout)findViewById(R.id.linear_news_letter);
        ll_tips=(LinearLayout)findViewById(R.id.linear_tips);
        ll_risk=(LinearLayout)findViewById(R.id.linear_risk);
        ll_stress=(LinearLayout)findViewById(R.id.linear_heat_stress);
        ll_policy=(LinearLayout)findViewById(R.id.linear_policy);

        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
    }

}
