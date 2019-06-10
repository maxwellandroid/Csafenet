package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.OnlineTestQuestionsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineTestActivity extends AppCompatActivity {

    TextView tv_duration,tv_result,tv_question;
    RadioGroup rg_answers;
    RadioButton rb_option1,rb_option2,rb_option3;

    String selectedAnswer="";
    int correctAnswercount=0,answeredQuestions=0,count=0;
    String questionsAnswers;

    Gson gson = new Gson();
    List<String> questionsList,option1List,option2List,option3List,imageUrlList,answers,questionIds,titles;

    SharedPreferences preferences;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    int position=0;

    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_test);
        tv_duration=(TextView)findViewById(R.id.text_duration);
        tv_result=(TextView)findViewById(R.id.text_result);
        tv_question=(TextView)findViewById(R.id.text_question);
        rg_answers=(RadioGroup)findViewById(R.id.rg_answer);
        rb_option1=(RadioButton) findViewById(R.id.rb_option1);
        rb_option2=(RadioButton) findViewById(R.id.rb_option2);
        rb_option3=(RadioButton) findViewById(R.id.rb_option3);


        questionsList=new ArrayList<>();
        option1List=new ArrayList<>();
        option2List=new ArrayList<>();
        option3List=new ArrayList<>();
        imageUrlList=new ArrayList<>();
        answers=new ArrayList<>();
        questionIds=new ArrayList<>();
        titles=new ArrayList<>();
        preferences= PreferenceManager.getDefaultSharedPreferences(OnlineTestActivity.this);
        userid=preferences.getString(StringConstants.prefEmployeeId,"");

        String string = preferences.getString("listQuestions", "");
        Type type = new TypeToken<List<String>>() {
        }.getType();
        questionsList = gson.fromJson(string, type);

        String stringOption1 = preferences.getString("listOption1", "");
        Type type1 = new TypeToken<List<String>>() {
        }.getType();
        option1List = gson.fromJson(stringOption1, type1);

        String stringOption2 = preferences.getString("listOption2", "");
        Type type2 = new TypeToken<List<String>>() {
        }.getType();
        option2List = gson.fromJson(stringOption2, type2);

        String stringOption3 = preferences.getString("listOption3", "");
        Type type3 = new TypeToken<List<String>>() {
        }.getType();
        option3List = gson.fromJson(stringOption3, type3);
        String stringQuestionIds = preferences.getString("listQuestionIds", "");
        Type type6 = new TypeToken<List<String>>() {
        }.getType();
        questionIds = gson.fromJson(stringQuestionIds, type6);
        String stringTitles = preferences.getString("listTitles", "");
        Type type7 = new TypeToken<List<String>>() {
        }.getType();
        titles = gson.fromJson(stringTitles, type7);  String stringOption4 = preferences.getString("listAnswers", "");
        Type type4 = new TypeToken<List<String>>() {
        }.getType();
        answers = gson.fromJson(stringOption4, type4);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mAdapter=new OnlineTestQuestionsAdapter(OnlineTestActivity.this,questionsList,option1List,option2List,option3List,answers,questionIds,titles);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(mAdapter);


        if(questionsList.size()>0){
            tv_question.setText(questionsList.get(0));
        }else {
            tv_question.setText("No Questions Available");
        }
        if(option1List.size()>0){
            rb_option1.setText(option1List.get(0));
        }
       if(option2List.size()>0){
           rb_option2.setText(option2List.get(0));
       }
       if(option3List.size()>0){
           rb_option3.setText(option3List.get(0));
       }




/*
        new CountDownTimer(61000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_duration.setText("Duration : " + millisUntilFinished / 1000 +" seconds");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                Intent i=new Intent(getApplicationContext(),HSEOnlineTrainingSystemActivity.class);
                startActivity(i);
            }

        }.start();
*/

/*

        if(questionsList.size()>0){
            tv_result.setText("Next");
        }else {
            tv_result.setText("Submit");
        }
*/

       /* //create radio buttons

            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option1List.get(0));
            radioButton.setId(i);
            rg_answers.addView(radioButton);
*/

        rg_answers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int index = rg_answers.indexOfChild(findViewById(rg_answers.getCheckedRadioButtonId()));
                if(index==0){
                    selectedAnswer="a";
                }
                else if(index==1){
                    selectedAnswer="b";
                }
                else if(index==2) {
                    selectedAnswer="c";
                }


        /*
                if(radioGroup.getCheckedRadioButtonId()==-1){
                   selectedAnswer="";

                }else {
                    RadioButton selectedRadioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                    selectedAnswer=selectedRadioButton.getText().toString();

                }*/
            }
        });

        tv_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              /*  if(selectedAnswer.matches(answers.get(position))){
                    correctAnswercount=correctAnswercount+1;
                }
                if(!selectedAnswer.isEmpty()){
                    answeredQuestions=answeredQuestions+1;
                }


                if(position<questionsList.size()-1){

                    tv_result.setText("Next");
                    position++;

                }else {


*/
              preferences=PreferenceManager.getDefaultSharedPreferences(OnlineTestActivity.this);
              answeredQuestions=preferences.getInt("AnsweredQuestions",0);
              correctAnswercount=preferences.getInt("CorrectAnswersCount",0);
              questionsAnswers=preferences.getString("QuestionAnswers","");
                questionsAnswers = questionsAnswers.startsWith(",") ? questionsAnswers.substring(1) : questionsAnswers;

                /*    Intent i=new Intent(getApplicationContext(), ResultActivity.class);
                    i.putExtra("TotalQuestions",questionsList.size());
                    i.putExtra("AnsweredQuestions",answeredQuestions);
                    i.putExtra("CorrectAnswers",correctAnswercount);
                    startActivity(i);*/
              new SaveResultOperation().execute();
              //  }

              /*  count=count+1;
                if(selectedAnswer.matches("3356")){
                    CorrectAnswercount=CorrectAnswercount+1;
                }
                if(!selectedAnswer.isEmpty()){
                    answeredQuestions=answeredQuestions+1;
                }
                Intent i=new Intent(getApplicationContext(),ResultActivity.class);
                i.putExtra("TotalQuestions",count);
                i.putExtra("CorrectAnswers",CorrectAnswercount);
                i.putExtra("AnsweredQuestions",answeredQuestions);
                startActivity(i);*/
            }
        });
    }

    public  void back(View view){
       onBackPressed();
    }

    private class SaveResultOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(OnlineTestActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(OnlineTestActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.onlineTestResultUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);
                    if(!response.matches("")){
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.has("success")){
                                String messageCode=jsonObject.getString("success");
                                if(messageCode.matches("ok")){
                                    Intent i=new Intent(getApplicationContext(), ResultActivity.class);
                                    if(jsonObject.has("total_question")){
                                        i.putExtra("TotalQuestions",Integer.parseInt(jsonObject.getString("total_question")));
                                    }
                                    if(jsonObject.has("correct_answer")){
                                        i.putExtra("CorrectAnswers",Integer.parseInt(jsonObject.getString("correct_answer")));
                                    }
                                    if(jsonObject.has("answered_question")){
                                        i.putExtra("AnsweredQuestions",Integer.parseInt(jsonObject.getString("answered_question")));
                                    }
                                    startActivity(i);
                                }
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
                         MyData.put(StringConstants.inputUserID,userid );
                         MyData.put(StringConstants.inputTitleId,titles.get(0));
                         MyData.put(StringConstants.inputQuestionsId,questionsAnswers);
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
