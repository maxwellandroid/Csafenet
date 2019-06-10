package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuestionDetailsActivity extends AppCompatActivity {

    TextView tv_question;
    RadioGroup rg_answer;

    String selectedAnswer="";
    Button submit,cancel;

    String qusetionId;

    SharedPreferences preferences;

    String userid,observationCode;

    String currentDate;
    String fieldId;
    String currentTime;

    TextView tv_message,tv_message_code;

    RadioButton rb_yes,rb_no,rb_na;

    String observationType;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);
        tv_question=(TextView)findViewById(R.id.text_question);
        rg_answer=(RadioGroup)findViewById(R.id.rg_answers);
        submit=(Button)findViewById(R.id.button_submit);
        cancel=(Button)findViewById(R.id.button_cancel);
        rb_yes=(RadioButton)findViewById(R.id.radi_button_yes);
        rb_no=(RadioButton)findViewById(R.id.radi_button_no);
        rb_na=(RadioButton)findViewById(R.id.radi_button_na);
        tv_message=(TextView)findViewById(R.id.text_mesage);
        tv_message_code=(TextView)findViewById(R.id.text_observation_number);

        String question=getIntent().getStringExtra("Question");
        qusetionId=getIntent().getStringExtra("QuestionId");
        fieldId=getIntent().getStringExtra("FieldId");
        observationType=getIntent().getStringExtra("ObservationType");
        tv_question.setText(question);

        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        userid=preferences.getString(StringConstants.prefEmployeeId,"");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         currentDate = sdf.format(new Date());
        DateFormat df = new SimpleDateFormat(" hh:mm a");
         currentTime = df.format(Calendar.getInstance().getTime());


        rg_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, int i) {

                final RadioButton radioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

                selectedAnswer=radioButton.getText().toString().trim();
                if(selectedAnswer.matches("No")){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuestionDetailsActivity.this);
                    alertDialogBuilder.setMessage("Do you want to go to New Observation ?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    Intent i=new Intent(getApplicationContext(),NewObservationActivity.class);
                                    i.putExtra("Platform",observationType);
                                    i.putExtra("ScreenName","");
                                    startActivity(i);
                                    // Toast.makeText(QuestionDetailsActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedAnswer="";
                           radioButton.setChecked(false);
                            dialog.dismiss();


                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                if(selectedAnswer.matches("No")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuestionDetailsActivity.this);
                    alertDialogBuilder.setMessage("Do you want to go to New Observation ?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    Intent i=new Intent(getApplicationContext(),NewObservationActivity.class);
                                    startActivity(i);
                                    // Toast.makeText(QuestionDetailsActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //radioButton.setChecked(false);
                            dialog.dismiss();

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
*/


if(!selectedAnswer.isEmpty()){

    if(selectedAnswer.matches("No")){

    }
    new SubmitAnswerOperation().execute();
}else
    showAlertDialog("Please select Answer");


            }
        });


    }
    public  void back(View view){
       onBackPressed();
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
    protected void onResume() {
        super.onResume();

        observationCode=preferences.getString(StringConstants.prefRegCode,"");

        if(observationCode!=null&&!observationCode.isEmpty()){
            rb_no.setChecked(true);
            rb_yes.setEnabled(false);
            rb_na.setEnabled(false);
           // rg_answer.setEnabled(false);
            tv_message_code.setVisibility(View.VISIBLE);
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Stored Successfully");
            tv_message_code.setText("Code : "+observationCode);
        }
    }

    private class SubmitAnswerOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(QuestionDetailsActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(QuestionDetailsActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hseComplianceAnswers, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("status")){
                            result=jsonObject.getString("status");
                            if(result.matches("Insert successful")){
                             /*   Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                                startActivity(i);*/
                             onBackPressed();
                                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                            }else {
                                //showAlertDialog("Failed");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
/*
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("Data")){

                            JSONArray jsonArray=jsonObject.getJSONArray("Data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject dataObject=jsonArray.getJSONObject(i);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put(StringConstants.inputUserID, userid);
                    MyData.put(StringConstants.inputQuestionId,qusetionId);
                    MyData.put(StringConstants.inputAswer, selectedAnswer);
                    MyData.put(StringConstants.inputDate,currentDate);
                    MyData.put(StringConstants.inputRegDate,currentDate);
                    MyData.put(StringConstants.inputRegTime,currentTime);
                    MyData.put(StringConstants.inputFieldId,fieldId);
                    MyData.put(StringConstants.inputobservationCode, observationCode);
                    MyData.put(StringConstants.inputEnterBy,userid);
                    return MyData; }
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
