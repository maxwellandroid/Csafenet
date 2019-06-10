package com.maxwell.csafenet.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.TopObserverAdapter;
import com.maxwell.csafenet.model.TopObserverModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopObserverActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<TopObserverModule> topObserverModuleList;
    TopObserverModule topObserverModule;

    RelativeLayout layout_progress;
    LinearLayout layout_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_observer);

        initializeViews();
    }

    public  void back(View view){
        onBackPressed();
    }


    public void initializeViews(){

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        new GetTopObserverDetails().execute();


    }

    private class GetTopObserverDetails extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(TopObserverActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(TopObserverActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.topObserverUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());
                            if(jsonObject.has("top_observance")){
                                JSONArray topObserverArray=jsonObject.getJSONArray("top_observance");

                                topObserverModuleList=new ArrayList<>();

                                for(int i=0;i<topObserverArray.length();i++){
                                    JSONObject observerObject=topObserverArray.getJSONObject(i);
                                    topObserverModule=new TopObserverModule();
                                    topObserverModule.setUserId(observerObject.getString("user_id"));
                                    topObserverModule.setName(observerObject.getString("fname"));
                                    topObserverModule.setCompanyName(observerObject.getString("company_name"));
                                    topObserverModule.setImage("http://csafenet.com/"+observerObject.getString("image"));
                                    topObserverModule.setTotalObservations(observerObject.getString("total_no_of_observance"));
                                    topObserverModuleList.add(topObserverModule);
                                }
                                mAdapter = new TopObserverAdapter(TopObserverActivity.this,topObserverModuleList);
                                recyclerView.setAdapter(mAdapter);
                            }

                            layout_progress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {

                    }
                    if(pDialog.isShowing())
                        pDialog.dismiss();
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(pDialog.isShowing())
                        pDialog.dismiss();

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

    private void showRadioButtonDialog(String url) {

        // custom dialog
        final Dialog dialog = new Dialog(TopObserverActivity.this);
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
