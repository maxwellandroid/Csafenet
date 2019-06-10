package com.maxwell.csafenet.activity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.AnswersListAdapter;
import com.maxwell.csafenet.adapter.ComplianceViewAdapter;
import com.maxwell.csafenet.model.AnswersDetailsModel;
import com.maxwell.csafenet.model.ComplianceModuleViewModel;
import com.maxwell.csafenet.model.QuestionsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewComplianceModulesActivity extends AppCompatActivity {
    TableLayout tableLayout;
    String field_id="";

    String s_user_id;
    SharedPreferences preferences;

    List<AnswersDetailsModel> answersDetailsModelList;

    EditText et_date;
    ListView list_questions;
    ComplianceViewAdapter adapter;
    TextView tv_no_items;

    List<ComplianceModuleViewModel> complianceModuleViewModelList;
    ComplianceModuleViewModel complianceModuleViewModel;
    AnswersDetailsModel answerDetailsModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_compliance_modules);
        TextView tv_screen_name=(TextView) findViewById(R.id.text_screen_name);
        TextView tv_new=(TextView)findViewById(R.id.text_new_observation) ;
        preferences= PreferenceManager.getDefaultSharedPreferences(ViewComplianceModulesActivity.this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        String screen_name=getIntent().getStringExtra("ScreenName");
        field_id=getIntent().getStringExtra("FieldId");
        tv_screen_name.setText(screen_name);
        if(screen_name.contains("View")){
            screen_name=screen_name.replace("View","");
        }
        tv_new.setText("Add "+screen_name);
        tableLayout=(TableLayout)findViewById(R.id.table_entries);
        et_date=(EditText)findViewById(R.id.date);
        list_questions=(ListView)findViewById(R.id.list_questions);
        tv_no_items=(TextView)findViewById(R.id.text_no_items) ;

      //  tableLayout.removeAllViews();

        getComplianceModule();

    }
    public  void gotonew(View view){
        onBackPressed();

    }
    public  void back(View view){
        onBackPressed();
    }

    public void getComplianceModule(){
        RequestQueue requestQueue = Volley.newRequestQueue(ViewComplianceModulesActivity.this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request. Method.GET, StringConstants.mainUrl + StringConstants.viewComplianceModuleUrl+field_id, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        try {


                            if(response.has("compliance")){

                                JSONArray complianceArray=response.getJSONArray("compliance");
                                complianceModuleViewModelList=new ArrayList<>();
                                for(int i=0;i<complianceArray.length();i++){

                                    complianceModuleViewModel=new ComplianceModuleViewModel();

                                    JSONObject complianceObject=complianceArray.getJSONObject(i);
                                    complianceModuleViewModel.setRegDate(complianceObject.getString("reg_date"));
                                    complianceModuleViewModel.setRegTime(complianceObject.getString("reg_time"));
                                    complianceModuleViewModel.setLocation(complianceObject.getString("location"));
                                    complianceModuleViewModel.setSpecificLocation(complianceObject.getString("specific_location"));
                                    JSONArray questionsArray=complianceObject.getJSONArray("question");
                                    answersDetailsModelList =new ArrayList<>();
                                    for (int j=0;j<questionsArray.length();j++){
                                        JSONObject questionsObject=questionsArray.getJSONObject(j);
                                        answerDetailsModel =new AnswersDetailsModel();
                                        answerDetailsModel.setQuestion(questionsObject.getString("question"));
                                        answerDetailsModel.setId(questionsObject.getString("question_id"));
                                        answerDetailsModel.setAnswer(questionsObject.getString("status"));
                                        answerDetailsModel.setFieldId(questionsObject.getString("field_id"));
                                        answerDetailsModel.setObservationCode(questionsObject.getString("observation_code"));
                                        answersDetailsModelList.add(answerDetailsModel);
                                    }
                                    complianceModuleViewModel.setAnswersDetailsModelList(answersDetailsModelList);
                                    complianceModuleViewModelList.add(complianceModuleViewModel);
                                }
                                if(answersDetailsModelList.size()>0){
                                    tv_no_items.setVisibility(View.GONE);
                                    list_questions.setVisibility(View.VISIBLE);
                                    adapter=new ComplianceViewAdapter(ViewComplianceModulesActivity.this,complianceModuleViewModelList);
                                    list_questions.setAdapter(adapter);
                                }else {
                                    list_questions.setVisibility(View.GONE);
                                    tv_no_items.setVisibility(View.VISIBLE);
                                }

                            }

                            //layout_progress1.setVisibility(View.GONE);
                            // recyclerViewTeam.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getLocalizedMessage());


                        // showAlertDialog(errorMessage);

                    }
                }
        );

// add it to the RequestQueue
        requestQueue.add(getRequest);
    }
}
