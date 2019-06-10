package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.BulletinAdapter;
import com.maxwell.csafenet.model.BulletinModules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HSECommunicationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsecommunication_details);
    }

    private class GetBulletinsOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(HSECommunicationDetailsActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(HSECommunicationDetailsActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hseBbulletinsUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());

/*
                            if(jsonObject.has("Bulletin")){
                                bulletinModulesList=new ArrayList<>();
                                JSONArray bulletinArray=jsonObject.getJSONArray("Bulletin");
                                for(int i=0;i<bulletinArray.length();i++){
                                    bulletinModules=new BulletinModules();
                                    JSONObject bulletinObject=bulletinArray.getJSONObject(i);
                                    bulletinModules.setId(bulletinObject.getString("id"));
                                    bulletinModules.setIssuedate(bulletinObject.getString("issue_date"));
                                    bulletinModules.setIssuer(bulletinObject.getString("originator"));
                                    bulletinModules.setTitle(bulletinObject.getString("title"));
                                    bulletinModules.setShortdescription(bulletinObject.getString("short_desc"));
                                    bulletinModules.setCategory(bulletinObject.getString("classification"));
                                    bulletinModules.setFilePath("http://www.csafenet.com/"+bulletinObject.getString("attachment_path"));
                                    bulletinModulesList.add(bulletinModules);

                                }

                                if(bulletinModulesList.size()>0){
                                    tv_norecords.setVisibility(View.GONE);
                                    listBulletins.setVisibility(View.VISIBLE);
                                    adapter=new BulletinAdapter(HSEBulletin.this,bulletinModulesList);
                                    listBulletins.setAdapter(adapter);
                                }
                                else {
                                    tv_norecords.setVisibility(View.VISIBLE);
                                    listBulletins.setVisibility(View.GONE);
                                }
                            }
*/


                            /*layout_progress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);*/

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

}
