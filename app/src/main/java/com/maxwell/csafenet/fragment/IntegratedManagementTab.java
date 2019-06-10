package com.maxwell.csafenet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.activity.HSEOnlineTrainingSystemActivity;
import com.maxwell.csafenet.activity.HealthAndWelfareActivity;
import com.maxwell.csafenet.activity.OnlineTrainingListActivity;
import com.maxwell.csafenet.model.ImagesModule;
import com.maxwell.csafenet.model.LifeSavingDetailsModule;
import com.maxwell.csafenet.model.OnlineTrainingDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegratedManagementTab extends Fragment {

    RequestQueue queue;
    HttpURLConnection connection;
    InputStream stream;
    BufferedReader reader;

    EditText txt_name, emailid, comments, card_title4, card_title5, card_title6, card_title7;
    Button submit;

    ImageView imageView;
    String imageUrl;

    List<LifeSavingDetailsModule> imageslist=new ArrayList<>();
    LifeSavingDetailsModule lifeSavingDetailsModule;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.management_statement, container, false);

      initializeViews();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);

                    return true;

                }

                return false;
            }
        });
    }

    public void initializeViews(){
        imageView=(ImageView)view.findViewById(R.id.image_managmentSystem);

        new GetIntegratedManagmentimage().execute();
    }


    private class GetIntegratedManagmentimage extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(getContext());
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.imssUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());

                            if(jsonObject.has("intergrated_image")){
                              JSONArray jsonArray=jsonObject.getJSONArray("intergrated_image");

                              for(int i=0;i<jsonArray.length();i++){
                                  lifeSavingDetailsModule=new LifeSavingDetailsModule();
                                  JSONObject jsonObject1=jsonArray.getJSONObject(i);

                                  /*lifeSavingDetailsModule.setCategory(jsonObject1.getString("title"));
                                  lifeSavingDetailsModule.setId(jsonObject1.getString("id"));
                                  lifeSavingDetailsModule.setFilepath("http://csafenet.com/"+jsonObject1.getString("image_path"));
                                  imageslist.add(lifeSavingDetailsModule);*/
                                  imageUrl="http://csafenet.com/"+jsonObject1.getString("image_path");
                              }


                                Glide.with(getContext())
                                        .load(imageUrl) // image url
                                        // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                        //.error(R.drawable.imagenotfound)  // any image in case of error
                                        // .override(200, 200); // resizing
                                        //.apply(new RequestOptions().placeholder(R.drawable.loading))
                                        .into(imageView);



                            }



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
