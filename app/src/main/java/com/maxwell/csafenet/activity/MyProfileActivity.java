package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    TextView tv_name,tv_employeeNumber,tv_designation,tv_company,tv_joining_date,tv_mobile_number,tv_email_id;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String s_user_id;

    CircleImageView iv_profile;
    RelativeLayout layout_progress;
    LinearLayout layout_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        tv_name=(TextView)findViewById(R.id.text_name);
        tv_employeeNumber=(TextView)findViewById(R.id.text_employee_number);
        tv_designation=(TextView)findViewById(R.id.text_designation);
        tv_company=(TextView)findViewById(R.id.text_company);
        tv_joining_date=(TextView)findViewById(R.id.text_joining_date);
        tv_email_id=(TextView)findViewById(R.id.text_email_id);
        tv_mobile_number=(TextView)findViewById(R.id.text_mobile);
        iv_profile=(CircleImageView)findViewById(R.id.imageProfile);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        getUser();

    }


    public  void back(View view){
        onBackPressed();
    }

    private void getUser() {
        String loading = getString(R.string.loading);
        //progressBar.show(loading, false);
        //  final String url = "https://www.maxwellglobalsoftware.com/tvpromo/api/type_promo.php?lang_id=" + languageId + "&type=1&unique_id=" + uniqId;
        final String url = StringConstants.mainUrl+StringConstants.userprofileUrl+StringConstants.inputUserID +"="+s_user_id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                //  getStateMethod(response);

                try {
                    //   getAllValue(response);
                    JSONObject trailerObject=new JSONObject(response);



                        if (trailerObject.has("view_profile")) {
                            JSONArray trailers = trailerObject.getJSONArray("view_profile");
                            for(int i=0;i<trailers.length();i++){

                                JSONObject jsonObject=trailers.getJSONObject(i);
                               if(jsonObject.has("fname")){
                                   tv_name.setText(jsonObject.getString("fname"));
                               }
                               if(jsonObject.has("employee_no")){
                                   tv_employeeNumber.setText(jsonObject.getString("employee_no"));
                               }
                               if(jsonObject.has("designation")){
                                   tv_designation.setText(jsonObject.getString("designation"));
                               } if(jsonObject.has("company_name")){
                                   tv_company.setText(jsonObject.getString("company_name"));
                               }if(jsonObject.has("joining_date")){
                                   tv_joining_date.setText(jsonObject.getString("joining_date"));
                               }if(jsonObject.has("email")){
                                   tv_email_id.setText(jsonObject.getString("email"));
                               }if(jsonObject.has("mobile_no")){
                                   tv_mobile_number.setText(jsonObject.getString("mobile_no"));
                               }

                               if(jsonObject.has("image")){
                                   Glide.with(MyProfileActivity.this)
                                           .load("http://csafenet.com/"+jsonObject.getString("image")) // image url
                                           // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                                           .error(R.drawable.csafenet)  // any image in case of error
                                           // .override(200, 200); // resizing
                                           //.apply(new RequestOptions().placeholder(R.drawable.loading))
                                           .into(iv_profile);
                               }
                            }

                        }

                    layout_progress.setVisibility(View.GONE);
                    layout_home.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*getpromo(response);
                getMovie(response);
                getSerial(response);*/

                Log.i("NowFragment", "--->" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (Log.isLoggable("getStateValueFromApi", Log.INFO))
                    Log.i("getStateValueFromApi", "Call to " + url + " failed" + error.toString());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
