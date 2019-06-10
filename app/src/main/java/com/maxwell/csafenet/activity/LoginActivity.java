package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.LoginDetailsModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button button;

    String s_username,s_password;
    SharedPreferences preferences;
    SharedPreferences.Editor editor ;
    Boolean islogin=false;
    String android_id;
    LoginDetailsModule module;

    String TAG="FireBaseResponse";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      /*  String tkn = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());
        Toast.makeText(LoginActivity.this, "Current token ["+tkn+"]",
                Toast.LENGTH_LONG).show();
        Log.d("App", "Token ["+tkn+"]");
*/
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                       // Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        islogin=preferences.getBoolean("isLogin",false);
        module=new LoginDetailsModule();

        if(islogin){
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
        username = (EditText) findViewById(R.id.p_login_username);
        password = (EditText) findViewById(R.id.p_login_password);
        button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_username=username.getText().toString().trim();
                s_password=password.getText().toString().trim();
                if(!username.getText().toString().isEmpty()){

                    if(!password.getText().toString().isEmpty()){

                        new LoginOperation().execute();
                    }else
                        Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter username",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private class LoginOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(LoginActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.loginUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("status")){
                            result=String.valueOf(jsonObject.getInt("status"));
                            if(result.matches("1")){
                                module.setId(jsonObject.getString("id"));
                                module.setName(jsonObject.getString("fname"));
                                module.setEmployeeNumber(jsonObject.getString("employee_no"));
                                module.setDesignation(jsonObject.getString("designation"));
                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                editor.putBoolean("isLogin",true);
                                editor.putString(StringConstants.prefEmployeeId,module.getId());
                                editor.putString(StringConstants.prefEmployeeName,module.getName());
                                editor.putString(StringConstants.prefEmployeeNo,module.getEmployeeNumber());
                                editor.putString(StringConstants.prefEmployeeDesignation,module.getDesignation());
                                editor.commit();
                                editor.apply();
                                startActivity(i);
                                finish();
                            }else {
                                showAlertDialog("Invalid Login Details");
                            }
                        }
                    } catch (JSONException e) {
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
                    MyData.put(StringConstants.inputUsername, s_username); //Add the data you'd like to send to the server.
                    MyData.put(StringConstants.inputPassword, s_password); //Add the data you'd like to send to the server.
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

    public void showAlertDialog(String message){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
