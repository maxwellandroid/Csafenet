package com.maxwell.csafenet.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.maxwell.csafenet.adapter.AnswersListAdapter;
import com.maxwell.csafenet.model.AnswersDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAnsweredQuestionActivity extends AppCompatActivity {
    TableLayout tableLayout;
    String field_id="";
    String s_user_id;
    SharedPreferences preferences;
    List<AnswersDetailsModel>answersDetailsModelList;
    AnswersDetailsModel answersDetailsModel;
    String currentDate;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String dateToStr;
    EditText et_date;
    ListView list_questions;
    AnswersListAdapter adapter;
    TextView tv_no_items;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answered_question);

        TextView tv_screen_name=(TextView) findViewById(R.id.text_screen_name);
        TextView tv_new=(TextView)findViewById(R.id.text_new_observation) ;
        preferences= PreferenceManager.getDefaultSharedPreferences(ViewAnsweredQuestionActivity.this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");

        String screen_name=getIntent().getStringExtra("ScreenName");
        field_id=getIntent().getStringExtra("FieldId");
        tv_screen_name.setText(screen_name);
        if(screen_name.contains("View")){
            screen_name=screen_name.replace("View","");
        }
        tv_new.setText("Add "+screen_name);

        tableLayout=(TableLayout)findViewById(R.id.table_entries);

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(new Date());*/

        et_date=(EditText)findViewById(R.id.date);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if(dayOfMonth<10&&month+1<10){
            dateToStr="0"+String.valueOf(dayOfMonth)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
            currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+"0"+String.valueOf(dayOfMonth);
        }else if(dayOfMonth<10&&month+1>10){
            dateToStr="0"+String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
            currentDate=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+"0"+String.valueOf(dayOfMonth);
        }else if(dayOfMonth>10&&month+1<10){
            dateToStr=String.valueOf(dayOfMonth)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
            currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
        }else {
            currentDate = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
            dateToStr=String.valueOf(dayOfMonth)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
        }

      /*  if(month+1<10){
            dateToStr=String.valueOf(dayOfMonth)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
            currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
        }else {

        }*/
        et_date.setText(dateToStr);
        list_questions=(ListView)findViewById(R.id.list_questions);
        tv_no_items=(TextView)findViewById(R.id.text_no_items) ;

        tableLayout.removeAllViews();

        TableRow tbrow0 = new TableRow(getApplicationContext());
        tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
        TextView tv0 = new TextView(getApplicationContext());
        tv0.setText(" Item ");
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextColor(getResources().getColor(android.R.color.white));
        tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
        tv0.setPadding(0,10,0,10);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getApplicationContext());
        tv1.setText(" Question ");
        tv1.setGravity(Gravity.LEFT);
        tv1.setTextColor(getResources().getColor(android.R.color.white));
        tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
        tv1.setPadding(0,10,0,10);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getApplicationContext());
        tv2.setText(" Reg Date ");
        tv2.setGravity(Gravity.CENTER);
        tv2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
        tv2.setPadding(0,10,0,10);
        tv2.setTextColor(getResources().getColor(android.R.color.white));
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getApplicationContext());
        tv3.setText(" Remarks ");
        tv3.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
        tv3.setPadding(0,10,0,10);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextColor(getResources().getColor(android.R.color.white));
        tbrow0.addView(tv3);
        tableLayout.addView(tbrow0);

        new GetAnswerQuestions().execute();

    }
    public  void back(View view){
        onBackPressed();
    }
    public  void gotonew(View view){
        onBackPressed();


    }

    public void onClic(final View view){

        final EditText et_date1= (EditText) view;

        datePickerDialog = new DatePickerDialog(ViewAnsweredQuestionActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //  date.setText(day + "/" + (month + 1) + "/" + year);

                        if(day<10&&month+1<10){
                            dateToStr="0"+String.valueOf(day)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
                            currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+"0"+String.valueOf(day);
                        }else if(day<10&&month+1>10){
                            dateToStr="0"+String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
                            currentDate=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+"0"+String.valueOf(day);
                        }else if(day>10&&month+1<10){
                            dateToStr=String.valueOf(day)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
                            currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(day);
                        }else {
                            currentDate=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day);
                            dateToStr=String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
                        }
                        /*if(month+1<10){
                            currentDate=String.valueOf(year)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(day);
                            dateToStr=String.valueOf(day)+"-"+"0"+String.valueOf(month+1)+"-"+String.valueOf(year);
                        }else {
                            currentDate=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day);
                            dateToStr=String.valueOf(day)+"-"+String.valueOf(month+1)+"-"+String.valueOf(year);
                        }
*/

                        et_date1.setText(dateToStr);

                        new GetAnswerQuestions().execute();


                    }
                }, year, month, dayOfMonth);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-20000);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }


    private class GetAnswerQuestions extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(ViewAnsweredQuestionActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAnsweredQuestionActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.viewAnsweredQuestionsUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());

                            if(jsonObject.has("view_hse_onsubmit")){
                                JSONArray jsonArray=jsonObject.getJSONArray("view_hse_onsubmit");
                                answersDetailsModelList=new ArrayList<>();
                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    answersDetailsModel=new AnswersDetailsModel();
                                    answersDetailsModel.setQuestionId(jsonObject1.getString("question_id"));
                                    answersDetailsModel.setQuestion(jsonObject1.getString("question"));
                                    answersDetailsModel.setFieldId(jsonObject1.getString("field_id"));
                                    answersDetailsModel.setAnswer(jsonObject1.getString("answer"));
                                    answersDetailsModel.setObservationCode(jsonObject1.getString("observation_code"));
                                    answersDetailsModel.setRegDate(jsonObject1.getString("reg_date"));
                                    answersDetailsModelList.add(answersDetailsModel);
                                    //imageUrl="http://csafenet.com/"+jsonObject1.getString("image_path");
                                }
                                if(answersDetailsModelList.size()>0) {

                                    for (int i=0;i<answersDetailsModelList.size();i++){
                                        TableRow row=new TableRow(getApplicationContext());
                                        TextView tv01 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv01.setText(String.valueOf(i+1));
                                        tv01.setGravity(Gravity.CENTER);
                                        tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv01.setPadding(5,10,5,10);
                                        tv01.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv01);TextView tv02 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv02.setText(answersDetailsModelList.get(i).getQuestion());
                                        tv02.setGravity(Gravity.LEFT);
                                        tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv02.setPadding(5,10,5,10);
                                        tv02.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv02);
                                        String reg_date=answersDetailsModelList.get(i).getRegDate();
                                        SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
                                        Date newDate=spf.parse(reg_date);
                                        spf= new SimpleDateFormat("dd-MM-yyyy");
                                        reg_date = spf.format(newDate);

                                      //  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                       // reg_date=sdf.format(reg_date);
                                        TextView tv03 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv03.setText(reg_date);
                                        tv03.setGravity(Gravity.CENTER);
                                        tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv03.setPadding(5,10,5,10);
                                        tv03.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);
                                        row.addView(tv03);
                                        TextView tv04 = new TextView(getApplicationContext());
                                        //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        tv04.setText(answersDetailsModelList.get(i).getObservationCode());
                                        tv04.setGravity(Gravity.CENTER);
                                        tv04.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                        tv04.setPadding(5,10,5,10);
                                        tv04.setTextColor(getResources().getColor(android.R.color.black));
                                        //tv7.setPadding(12,12,12,12);

                                        row.addView(tv04);
                                        tableLayout.addView(row);
                                    }
                                }


                                if(answersDetailsModelList.size()>0){
                                    tv_no_items.setVisibility(View.GONE);
                                    list_questions.setVisibility(View.VISIBLE);
                                    adapter=new AnswersListAdapter(ViewAnsweredQuestionActivity.this,answersDetailsModelList);
                                    list_questions.setAdapter(adapter);
                                }else {
                                    list_questions.setVisibility(View.GONE);
                                    tv_no_items.setVisibility(View.VISIBLE);
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                            list_questions.setVisibility(View.GONE);
                            tv_no_items.setVisibility(View.VISIBLE);

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
                    MyData.put(StringConstants.inputFieldId, field_id);
                    MyData.put(StringConstants.inputRegDate, currentDate);
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
