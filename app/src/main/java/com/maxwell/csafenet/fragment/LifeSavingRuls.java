package com.maxwell.csafenet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
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
import com.maxwell.csafenet.activity.HealthAndWelfareActivity;
import com.maxwell.csafenet.model.LifeSavingDetailsModule;

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

public class LifeSavingRuls extends Fragment {

    RequestQueue queue;
    HttpURLConnection connection;
    InputStream stream;
    BufferedReader reader;

    EditText txt_name, emailid, comments, card_title4, card_title5, card_title6, card_title7;
    Button submit;

    List<LifeSavingDetailsModule> lifeSavingDetailsModuleList=new ArrayList<>();
    LifeSavingDetailsModule lifeSavingDetailsModule;

    LinearLayout layout_life_saving_rules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.life_saving_rules, container, false);
        layout_life_saving_rules=(LinearLayout)view.findViewById(R.id.linear_life_saving_rules);

        new GetLifeSavingRulesOperation().execute();
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

    private class GetLifeSavingRulesOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(getContext());
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.lifesavingUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());

                            if(jsonObject.has("live_saving_images")){
                                // JSONArray trainingTitlesArray=jsonObject.getJSONArray("OnlineTraingTitle");

                                JSONArray categoriesArray=jsonObject.getJSONArray("live_saving_images");
                                for (int i=0;i<categoriesArray.length();i++){

                                    JSONObject categoryObject=categoriesArray.getJSONObject(i);
                                    lifeSavingDetailsModule=new LifeSavingDetailsModule();
                                    lifeSavingDetailsModule.setCategory(categoryObject.getString("title"));
                                    lifeSavingDetailsModule.setId(categoryObject.getString("id"));
                                    lifeSavingDetailsModule.setFilepath("http://csafenet.com/"+categoryObject.getString("image_path"));
                                    lifeSavingDetailsModuleList.add(lifeSavingDetailsModule);

                                }

                                if(lifeSavingDetailsModuleList.size()>0){
                                    for(int i=0;i<lifeSavingDetailsModuleList.size();i++){
                                        final TextView rowTextView = new TextView(getActivity());

                                        rowTextView.setText(lifeSavingDetailsModuleList.get(i).getCategory());
                                        rowTextView.setPadding(10,10,10,10);
                                        // add the textview to the linearlayout
                                        layout_life_saving_rules.addView(rowTextView);
                                        rowTextView.setTag(i);

                                        rowTextView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                int position=(int)view.getTag();


                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lifeSavingDetailsModuleList.get(position).getFilepath()));
                                                startActivity(browserIntent);
                                            }
                                        });
                                    }
                                }


                                Log.d("LifeSaverRuel",lifeSavingDetailsModuleList.get(0).getFilepath());


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
