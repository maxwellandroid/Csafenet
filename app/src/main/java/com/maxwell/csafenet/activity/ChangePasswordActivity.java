package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText et_old_password,et_new_password,et_confirm_new_password;

    String s_old_password,s_new_password,s_confirm_new_password;
    Button button_submit;

    SharedPreferences preferences;

    String s_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");

        initializeViews();

    }
    public  void back(View view){
        onBackPressed();
    }


    public void initializeViews(){

        et_old_password=(EditText)findViewById(R.id.edittext_old_password);
        et_new_password=(EditText)findViewById(R.id.edittext_new_password);
        et_confirm_new_password=(EditText)findViewById(R.id.edittext_confirm_new_password);
        button_submit=(Button)findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_old_password=et_old_password.getText().toString().trim();
                s_new_password=et_new_password.getText().toString().trim();
                s_confirm_new_password=et_confirm_new_password.getText().toString().trim();

                if(!s_old_password.isEmpty()){
                    if(!s_new_password.isEmpty()){

                        if(s_new_password.matches(s_confirm_new_password)){

                          new ChangePasswordOperation().execute();

                        }else {
                            Toast.makeText(getApplicationContext(),"New password and confirm password doesnot match",Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"Please enter new password",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Please enter old password",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private class ChangePasswordOperation extends AsyncTask<String, Void,String> {

        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl +StringConstants.changePasswordUrl , new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response.trim());
                            if(jsonObject.has("Login")){
                                JSONArray loginArray=jsonObject.getJSONArray("Login");
                                for(int i=0;i<loginArray.length();i++){
                                    JSONObject object=loginArray.getJSONObject(i);
                                    String status=object.getString("response");
                                    if(status.matches("success")){
                                        Toast.makeText(ChangePasswordActivity.this,"Password changed successfully",Toast.LENGTH_SHORT).show();
                                        Intent i1=new Intent(ChangePasswordActivity.this, MainActivity.class);
                                        startActivity(i1);
                                    }else {
                                        Toast.makeText(ChangePasswordActivity.this,status,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                        }catch (JSONException e) {
                            e.printStackTrace();
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
                    MyData.put(StringConstants.inputUserID, s_user_id);
                    MyData.put(StringConstants.inputOldPassword, s_old_password);
                    MyData.put(StringConstants.inputNewPassword, s_new_password);
                    return MyData;
                }
            };

            requestQueue.add(MyStringRequest);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
