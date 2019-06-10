package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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
import com.google.gson.Gson;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.HomePageDetailsModel;
import com.maxwell.csafenet.model.OnlineTrainingDetails;
import com.maxwell.csafenet.model.PolicyDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnlineTrainingListActivity extends AppCompatActivity {

    LinearLayout layout_online_test_titles;

    List<OnlineTrainingDetails> onlineTrainingDetailsList;

    List<String> questions,option1,option2,option3,imageUrl,questionsId,answer,title;

    OnlineTrainingDetails onlineTrainingDetails;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RelativeLayout layout_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_training_list);

        preferences= PreferenceManager.getDefaultSharedPreferences(OnlineTrainingListActivity.this);
        editor=preferences.edit();


        initializeViews();

    }

    public  void back(View view){
        onBackPressed();
    }
    public void initializeViews(){
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        layout_online_test_titles=(LinearLayout)findViewById(R.id.linear_online_test_titles);

        new GetOnlineTestTitles().execute();
    }

        private class GetOnlineTestTitles extends AsyncTask<String, Void,String> {

            ProgressDialog pDialog=new ProgressDialog(OnlineTrainingListActivity.this);
            String result="";
            RequestQueue requestQueue = Volley.newRequestQueue(OnlineTrainingListActivity.this);
            @Override
            protected String doInBackground(String... strings) {
                StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.onlineTrainingUrl, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.
                        Log.d("Response",response);

                        if(!response.matches("")){

                            try {
                                JSONObject jsonObject=new JSONObject(response.trim());

                                if(jsonObject.has("TITLE")){
                                   // JSONArray trainingTitlesArray=jsonObject.getJSONArray("OnlineTraingTitle");

                                    onlineTrainingDetails=new OnlineTrainingDetails();

                                    onlineTrainingDetailsList=new ArrayList<>();
                                    questions=new ArrayList<>();
                                    option1=new ArrayList<>();
                                    option2=new ArrayList<>();
                                    option3=new ArrayList<>();
                                    imageUrl=new ArrayList<>();
                                    questionsId=new ArrayList<>();
                                    answer=new ArrayList<>();
                                    title=new ArrayList<>();
                                    onlineTrainingDetails.setTitle(jsonObject.getString("TITLE"));

                                    JSONArray quesionsArray=jsonObject.getJSONArray("Question");



                                    for(int j=0;j<quesionsArray.length();j++){
                                        JSONObject questionObjects=quesionsArray.getJSONObject(j);
                                        if(!questionObjects.has("error")){
                                            String question=questionObjects.getString("question");
                                            String questionId=questionObjects.getString("id");
                                            String titles=questionObjects.getString("title");
                                            String s_option1=questionObjects.getString("option1");
                                            String s_option2=questionObjects.getString("option2");
                                            String s_option3=questionObjects.getString("option3");
                                            String s_answer=questionObjects.getString("answer");

                                            questions.add(question);
                                            questionsId.add(questionId);
                                            title.add(titles);
                                            option1.add(s_option1);
                                            option2.add(s_option2);
                                            option3.add(s_option3);
                                            answer.add(s_answer);

                                        }
                                    }

                                    Log.d("Questions",questions.toString());
                                    Log.d("Option1",option1.toString());
                                    Log.d("Option2",option2.toString());
                                    Log.d("Option3",option3.toString());

                                    Gson gson = new Gson();
                                    String jsonQuestions = gson.toJson(questions);
                                    String jsonQuestionIds = gson.toJson(questionsId);
                                    String jsonTitless = gson.toJson(title);
                                    String jsonOption1 = gson.toJson(option1);
                                    String jsonOption2 = gson.toJson(option2);
                                    String jsonOption3 = gson.toJson(option3);
                                    String jsonAnswer=gson.toJson(answer);


                                    //onlineTrainingDetails.setQuestionId(questionId);
                                    JSONArray imagesArray=jsonObject.getJSONArray("Images");

                                    for(int i=0;i<imagesArray.length();i++){
                                        JSONObject imagesArrayJSONObject=imagesArray.getJSONObject(i);
                                        if(imagesArrayJSONObject.has("image")) {
                                            imageUrl.add("http://csafenet.com/" + imagesArrayJSONObject.getString("image"));
                                        }
                                    }
                                 //  onlineTrainingDetails.setImageUrl(imageUrl);
                                    String jsonimages = gson.toJson(imageUrl);
                                    editor.putString("listQuestions", jsonQuestions);
                                    editor.putString("listQuestionIds",jsonQuestionIds);
                                    editor.putString("listTitles",jsonTitless);
                                    editor.putString("listOption1", jsonOption1);
                                    editor.putString("listOption2", jsonOption2);
                                    editor.putString("listOption3", jsonOption3);
                                    editor.putString("listImages",jsonimages);
                                    editor.putString("listAnswers",jsonAnswer);
                                    editor.commit();
                                  onlineTrainingDetailsList.add(onlineTrainingDetails);


                                    if(onlineTrainingDetailsList.size()>0){
                                        for(int i=0;i<onlineTrainingDetailsList.size();i++){

                                            final TextView rowTextView = new TextView(OnlineTrainingListActivity.this);

                                            rowTextView.setText(onlineTrainingDetails.getTitle());
                                            rowTextView.setPadding(10,10,10,10);
                                            // add the textview to the linearlayout
                                            layout_online_test_titles.addView(rowTextView);
                                            rowTextView.setTag(i);

                                            rowTextView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    int position=(int) view.getTag();

                                                  //  Log.d("onlinetraininglist",onlineTrainingDetailsList.toString());

                                                   Intent i=new Intent(getApplicationContext(),HSEOnlineTrainingSystemActivity.class);
                                                   i.putExtra("TitleName",onlineTrainingDetails.getTitle());
                                                    startActivity(i);

                                                }
                                            });
                                        }
                                    }

                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            layout_progress.setVisibility(View.GONE);
                            layout_online_test_titles.setVisibility(View.VISIBLE);
                        }else {

                        }

                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //This code is executed if there is an error.
                        layout_progress.setVisibility(View.GONE);
                        layout_online_test_titles.setVisibility(View.VISIBLE);

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
