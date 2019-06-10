package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.maxwell.csafenet.adapter.AcheivementsAdapter;
import com.maxwell.csafenet.adapter.QuestionListAdapter;
import com.maxwell.csafenet.model.AwardDetails;
import com.maxwell.csafenet.model.QuestionsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSAAhivementActivity extends AppCompatActivity {

    AwardDetails awardDetails;
    List<AwardDetails> awardDetailsList;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextView tv_no_records;

    RelativeLayout layout_progress;
    LinearLayout layout_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsaahivement);

       TextView tv_screen_name=(TextView)findViewById(R.id.text_acheivements_name);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        tv_no_records=(TextView)findViewById(R.id.tet_no_record) ;
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;

        String screenName=getIntent().getStringExtra("Screen");
      //  tv_screen_name.setText(screenName);

        new GetAwardPhotosOperation().execute();
    }

    public  void back(View view){
       onBackPressed();
    }

    private class GetAwardPhotosOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(HSAAhivementActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(HSAAhivementActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hseAcheivmentphotosUrl, new Response.Listener<String>() {
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
                                awardDetailsList=new ArrayList<>();
                                for(int i=0;i<confinedArray.length();i++){

                                    JSONObject confinedArrayJSONObject=confinedArray.getJSONObject(i);
                                    awardDetails=new AwardDetails();
                                    awardDetails.setDate(confinedArrayJSONObject.getString("date"));
                                    awardDetails.setDescription(confinedArrayJSONObject.getString("description"));
                                    awardDetails.setImageUrl("http://csafenet.com/CsAfENet_GST/"+confinedArrayJSONObject.getString("image"));
                                    awardDetailsList.add(awardDetails);
                                  //  fIDconfinedWorks=confinedArrayJSONObject.getString("field_id");
                                }

                                mAdapter=new AcheivementsAdapter(getApplicationContext(),awardDetailsList);
                                recyclerView.setAdapter(mAdapter);
                            }else {
                                tv_no_records.setVisibility(View.VISIBLE);
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
