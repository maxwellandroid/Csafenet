package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.maxwell.csafenet.model.DropDownDetails;
import com.maxwell.csafenet.model.QuestionsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityProgramAuditActivity extends AppCompatActivity {

    ListView list_questions;
    QuestionListAdapter adapter;
    List<QuestionsModel> securityProgramAuditQuestions,audiOfSubOrdinatesQuesitions,heatStressAuditQuesstions,actionCardAuditQuestions;
    QuestionsModel questionsModel;
    String title="";
    TextView textViewTitle;
    TextView textViewModules;
    String fIDsecurityProgramAudit,fIDauditOfSubcontractor,fIDheatStress,fIDactioncardAudit;
    List<String> location=new ArrayList<>();
    List<String> specificLocation=new ArrayList<>();
    DropDownDetails dropDownDetails;
    ArrayAdapter<String> dataAdapter;
    List<DropDownDetails> dropDownDetailsList,dropDownDetailsList1;
    Spinner sLocation,sSpecificLocation;
    String locationId="",specificLocationId="";
    SharedPreferences preferences;
    String s_user_id;
    RelativeLayout layout_progress;
    LinearLayout layout_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_program_audit);

        initializeViews();
    }
    public  void back(View view){
        onBackPressed();
    }
    public void  onClic(View view){
        TextView textView=(TextView)view;
        Intent i=new Intent(getApplicationContext(),QuestionDetailsActivity.class);
        i.putExtra("Question",textView.getText().toString());
        startActivity(i);

    }
    public void onViewClick(View view){
        TextView textView=(TextView)view;
        Intent i=new Intent(getApplicationContext(),ViewAuditModules.class);
        i.putExtra("ScreenName",textView.getText().toString());
        if(title.matches("Security Program Audit"))
            i.putExtra("FieldId",fIDsecurityProgramAudit);
        else if(title.matches("SHES Audit of Subcontractors"))
            i.putExtra("FieldId",fIDauditOfSubcontractor);
        else if(title.matches("Heat Stress Audit"))
            i.putExtra("FieldId",fIDheatStress);
        else if(title.matches("PTST LMRA and IIF Action Card Audit"))
            i.putExtra("FieldId",fIDactioncardAudit);
        startActivity(i);
    }

    public void initializeViews(){
        list_questions=(ListView)findViewById(R.id.list_questions);
        textViewTitle=(TextView)findViewById(R.id.text_title) ;
        textViewModules=(TextView)findViewById(R.id.text_view_modules) ;
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        title=getIntent().getStringExtra("Screen");
        textViewTitle.setText(title);
        sLocation=(Spinner)findViewById(R.id.location) ;
        sSpecificLocation=(Spinner)findViewById(R.id.specific_location) ;
        textViewModules.setText("View "+title);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        location=new ArrayList<>();
        specificLocation=new ArrayList<>();
        location.add("-Select Location-");
        specificLocation.add("-Select Specific Location-");

        locationListing();
        specificLocationListing();
        sLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sLocation.getSelectedItem().toString().equals("-Select Location-") && !sLocation.getSelectedItem().toString().equals("")) {
                    locationId=dropDownDetailsList.get(i-1).getLocationId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        sSpecificLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!sSpecificLocation.getSelectedItem().toString().equals("-Select Specific Location-") && !sSpecificLocation.getSelectedItem().toString().equals("")) {
                    specificLocationId=dropDownDetailsList1.get(i-1).getLocationId();

                    if(title.matches("Security Program Audit"))
                    questionListing(fIDsecurityProgramAudit);
                    else if(title.matches("SHES Audit of Subcontractors"))
                    questionListing(fIDauditOfSubcontractor);
                    else if(title.matches("Heat Stress Audit"))
                    questionListing(fIDheatStress);
                    else if(title.matches("PTST LMRA and IIF Action Card Audit"))
                    questionListing(fIDactioncardAudit);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        new GetQuestionsOperation().execute();
    }
    public void specificLocationListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(SecurityProgramAuditActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.specificLocationUrl, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                if(!response.matches("")){

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("location")){
                            dropDownDetailsList1=new ArrayList<>();
                            JSONArray locationArray=jsonObject.getJSONArray("location");
                            for(int i=0;i<locationArray.length();i++){
                                JSONObject locationObject=locationArray.getJSONObject(i);
                                dropDownDetails=new DropDownDetails();
                                dropDownDetails.setLocation(locationObject.getString("specific_location"));
                                dropDownDetails.setLocationId(locationObject.getString("id"));

                                specificLocation.add(locationObject.getString("specific_location"));
                                dropDownDetailsList1.add(dropDownDetails);
                            }
                            dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, specificLocation);
                            sSpecificLocation.setAdapter(dataAdapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                specificLocationListing();
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

    }

    public void locationListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(SecurityProgramAuditActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.locationListUrl, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                if(!response.matches("")){

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("location")){
                            dropDownDetailsList=new ArrayList<>();
                            JSONArray locationArray=jsonObject.getJSONArray("location");
                            for(int i=0;i<locationArray.length();i++){
                                JSONObject locationObject=locationArray.getJSONObject(i);
                                dropDownDetails=new DropDownDetails();
                                dropDownDetails.setLocation(locationObject.getString("location"));
                                dropDownDetails.setLocationId(locationObject.getString("id"));

                                location.add(locationObject.getString("location"));
                                dropDownDetailsList.add(dropDownDetails);
                            }
                            dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, location);
                            sLocation.setAdapter(dataAdapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                locationListing();
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

    }

    private class GetQuestionsOperation extends AsyncTask<String, Void,String> {
        ProgressDialog pDialog=new ProgressDialog(SecurityProgramAuditActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(SecurityProgramAuditActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.auditModuleUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);
                    if(!response.matches("")){
                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());
                            if(jsonObject.has("Security Program Audit")){
                                JSONArray programAuditArray=new JSONArray();
                                programAuditArray=jsonObject.getJSONArray("Security Program Audit");
                                securityProgramAuditQuestions=new ArrayList<>();
                                for(int i=0;i<programAuditArray.length();i++){
                                    JSONObject programAuditObject=programAuditArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(programAuditObject.getString("id"));
                                    questionsModel.setField_id(programAuditObject.getString("field_id"));
                                    questionsModel.setTitle(programAuditObject.getString("question"));
                                    fIDsecurityProgramAudit=programAuditObject.getString("field_id");
                                    securityProgramAuditQuestions.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("SHES Audit of Subcontractors")){
                                JSONArray subtractorsArray=new JSONArray();
                                subtractorsArray=jsonObject.getJSONArray("SHES Audit of Subcontractors");
                                audiOfSubOrdinatesQuesitions=new ArrayList<>();
                                for(int i=0;i<subtractorsArray.length();i++){
                                    JSONObject subtractorsArrayJSONObject=subtractorsArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(subtractorsArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(subtractorsArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(subtractorsArrayJSONObject.getString("question"));
                                    fIDauditOfSubcontractor=subtractorsArrayJSONObject.getString("field_id");
                                    audiOfSubOrdinatesQuesitions.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Heat Stress Audit")){
                                JSONArray heatStressArray=new JSONArray();
                                heatStressArray=jsonObject.getJSONArray("Heat Stress Audit");
                                heatStressAuditQuesstions=new ArrayList<>();
                                for(int i=0;i<heatStressArray.length();i++){
                                    JSONObject heatStressArrayJSONObject=heatStressArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(heatStressArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(heatStressArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(heatStressArrayJSONObject.getString("question"));
                                    fIDheatStress=heatStressArrayJSONObject.getString("field_id");
                                    heatStressAuditQuesstions.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("PTST, LMRA & Action Card Audit")){
                                JSONArray actionCardArray=new JSONArray();
                                actionCardArray=jsonObject.getJSONArray("PTST, LMRA & Action Card Audit");
                                actionCardAuditQuestions=new ArrayList<>();
                                for(int i=0;i<actionCardArray.length();i++){
                                    JSONObject actionCardArrayJSONObject=actionCardArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(actionCardArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(actionCardArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(actionCardArrayJSONObject.getString("question"));
                                    fIDactioncardAudit=actionCardArrayJSONObject.getString("field_id");
                                    actionCardAuditQuestions.add(questionsModel);
                                }
                            }
                            if(title.matches("Security Program Audit")){
                                adapter=new QuestionListAdapter(SecurityProgramAuditActivity.this, securityProgramAuditQuestions,2);
                                list_questions.setAdapter(adapter);
                            }else if(title.matches("SHES Audit of Subcontractors")){
                                adapter=new QuestionListAdapter(SecurityProgramAuditActivity.this, audiOfSubOrdinatesQuesitions,2);
                                list_questions.setAdapter(adapter);
                            }else if(title.matches("Heat Stress Audit")){
                                adapter=new QuestionListAdapter(SecurityProgramAuditActivity.this, heatStressAuditQuesstions,2);
                                list_questions.setAdapter(adapter);
                            }else if(title.matches("PTST LMRA and IIF Action Card Audit")){
                                adapter=new QuestionListAdapter(SecurityProgramAuditActivity.this, actionCardAuditQuestions,2);
                                list_questions.setAdapter(adapter);
                            }

                            layout_progress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            layout_progress.setVisibility(View.GONE);
                            layout_home.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                    layout_progress.setVisibility(View.GONE);
                    layout_home.setVisibility(View.VISIBLE);

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
    public void questionListing(final String fieldId){
        RequestQueue requestQueue = Volley.newRequestQueue(SecurityProgramAuditActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.locationWiseQuestionsAuditUrl, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                if(!response.matches("")){

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("field")){
                            dropDownDetailsList=new ArrayList<>();
                            JSONArray questionArray=jsonObject.getJSONArray("field");
                            securityProgramAuditQuestions=new ArrayList<>();
                            for(int i=0;i<questionArray.length();i++){

                                JSONObject confinedArrayJSONObject=questionArray.getJSONObject(i);
                                questionsModel=new QuestionsModel();
                                questionsModel.setId(confinedArrayJSONObject.getString("question_id"));
                                questionsModel.setField_id(confinedArrayJSONObject.getString("field_id"));
                                questionsModel.setAnswer(confinedArrayJSONObject.getString("answer"));
                                questionsModel.setTitle(confinedArrayJSONObject.getString("question"));
                                questionsModel.setObservationCode(confinedArrayJSONObject.getString("observation_code"));
                                securityProgramAuditQuestions.add(questionsModel);
                                fIDsecurityProgramAudit=confinedArrayJSONObject.getString("field_id");
                            }
                            QuestionListAdapter1 adapter=new QuestionListAdapter1(SecurityProgramAuditActivity.this, securityProgramAuditQuestions,1);
                            list_questions.setAdapter(adapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                MyData.put(StringConstants.inputFieldId, fieldId);
                MyData.put(StringConstants.inputLocationId, locationId);
                MyData.put(StringConstants.inputSpecificLocationId,specificLocationId);
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }
    public class QuestionListAdapter1 extends BaseAdapter {

        private Context context;
        int type;

        private List<QuestionsModel> questionsModelList;
        boolean[] isopened;
        String selectedAnswer="";
        String observationType;

        String qusetionId;

        SharedPreferences preferences;

        String userid,observationCode="";

        String currentDate;
        String fieldId;
        String currentTime;

        public QuestionListAdapter1(Context context, List<QuestionsModel> questionsModelList,int type){

            this.context=context;
            this.questionsModelList =questionsModelList;
            this.type=type;

/*
        for(int i=0;i<answersDetailsModelList1.size();i++){
            isopened[i]=false;
        }
*/

            this.isopened=new boolean[questionsModelList.size()];
            Arrays.fill(isopened,false);


        }
        @Override
        public int getCount() {
            return questionsModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return questionsModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return questionsModelList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.layout_questions_row, parent, false);
            }
            TextView tv_item=(TextView)convertView.findViewById(R.id.text_item);
            TextView tv_status=(TextView)convertView.findViewById(R.id.text_status);
            TextView tv_question=(TextView)convertView.findViewById(R.id.text_question);
            final LinearLayout lv_question_detail_view=(LinearLayout)convertView.findViewById(R.id.layout_question_detail_view);
            TextView tv_question1=(TextView)convertView.findViewById(R.id.text_question1);
            final RadioButton rb_yes=(RadioButton)convertView.findViewById(R.id.radi_button_yes);
            final RadioButton rb_no=(RadioButton)convertView.findViewById(R.id.radi_button_no);
            final RadioButton rb_na=(RadioButton)convertView.findViewById(R.id.radi_button_na);
            final TextView tv_message=(TextView)convertView.findViewById(R.id.text_mesage);
            final TextView tv_message_code=(TextView)convertView.findViewById(R.id.text_observation_number);
            Button submit=(Button)convertView.findViewById(R.id.button_submit);
            Button cancel=(Button)convertView.findViewById(R.id.button_cancel);


            RadioGroup rg_answer=(RadioGroup)convertView.findViewById(R.id.rg_answers);
            tv_question1.setText(questionsModelList.get(position).getTitle());

            tv_item.setText(String.valueOf(position+1));
            tv_question.setText(questionsModelList.get(position).getTitle());
            if(questionsModelList.get(position).getAnswer().matches("-"))
                tv_status.setText("Not Started");
            else tv_status.setText(questionsModelList.get(position).getAnswer());

            if(questionsModelList.get(position).getAnswer().matches("nil")){
                rb_na.setChecked(true);
            }else if(questionsModelList.get(position).getAnswer().matches("Yes")){
                rb_yes.setChecked(true);
            }else if(questionsModelList.get(position).getAnswer().matches("N/A")){
                rb_na.setChecked(true);
            }else if(questionsModelList.get(position).getAnswer().matches("No")){
                rb_no.setChecked(true);
                tv_message_code.setVisibility(View.VISIBLE);
                tv_message_code.setText("Code : "+questionsModelList.get(position).getObservationCode());
            }

            tv_question.setTag(position);
            lv_question_detail_view.setTag(position);
            submit.setTag(position);
            preferences= PreferenceManager.getDefaultSharedPreferences(context);
            userid=preferences.getString(StringConstants.prefEmployeeId,"");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            currentDate = sdf.format(new Date());
            DateFormat df = new SimpleDateFormat(" hh:mm a");
            currentTime = df.format(Calendar.getInstance().getTime());
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int viewPistion=(int)view.getTag();
                    fieldId=questionsModelList.get(viewPistion).getField_id();
                    qusetionId=questionsModelList.get(viewPistion).getId();
               /* locationId=answersDetailsModelList1.get(position).getLocationId();
                specificLocationId=answersDetailsModelList1.get(position).getSpecificLocationId();*/
                    if(!selectedAnswer.isEmpty()){

                        if(selectedAnswer.matches("No")){
                            observationCode=preferences.getString(StringConstants.prefRegCode,"");
                            if(observationCode!=null&&!observationCode.isEmpty()){
                              /*  rb_no.setChecked(true);
                                rb_yes.setEnabled(false);
                                rb_na.setEnabled(false);*/
                                // rg_answer.setEnabled(false);
                                tv_message_code.setVisibility(View.VISIBLE);
                                tv_message.setVisibility(View.VISIBLE);
                                tv_message.setText("Stored Successfully");
                                tv_message_code.setText("Code : "+observationCode);
                            }
                        }
                        submitAnswer(viewPistion);

                    }else
                        showAlertDialog("Please select Answer");



                }
            });
            rg_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup radioGroup, int i) {

                    // final RadioButton radioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

                    if(rb_yes.isChecked()){
                        selectedAnswer=rb_yes.getText().toString();
                    }if(rb_no.isChecked()){
                        selectedAnswer=rb_no.getText().toString().trim();
                    }if(rb_na.isChecked()){
                        selectedAnswer=rb_na.getText().toString().trim();
                    }
                    // selectedAnswer=radioButton.getText().toString().trim();
                    if(selectedAnswer.matches("No")){

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setMessage("Do you want to go to New Observation ?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        Intent i=new Intent(context,NewObservationActivity.class);
                                        if(type==1){
                                            i.putExtra("Platform","compliance");
                                        }else if(type==2){
                                            i.putExtra("Platform","audit");
                                        }
                                        // i.putExtra("Platform",observationType);
                                        i.putExtra("ScreenName","");
                                        context.startActivity(i);

                                        // Toast.makeText(QuestionDetailsActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedAnswer="";
                                rb_no.setChecked(false);
                                dialog.dismiss();


                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                }
            });

            tv_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int viewPosition=(int)lv_question_detail_view.getTag();
            /*  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
              SharedPreferences.Editor editor=preferences.edit();
                editor.remove(StringConstants.prefRegCode);
                editor.apply();
                Intent i=new Intent(context, QuestionDetailsActivity.class);
                if(type==1){
                    i.putExtra("ObservationType","compliance");
                }else if(type==2){
                    i.putExtra("ObservationType","audit");
                }
                i.putExtra("Question",answersDetailsModelList1.get(viewPosition).getTitle());
                i.putExtra("QuestionId",answersDetailsModelList1.get(viewPosition).getId());
                i.putExtra("FieldId",answersDetailsModelList1.get(viewPosition).getField_id());
                context.startActivity(i);*/
                    if(!isopened[viewPosition]){
                        lv_question_detail_view.getTag();
                        lv_question_detail_view.setVisibility(View.VISIBLE);
                        isopened[viewPosition]=true;
                    }else {
                        lv_question_detail_view.getTag();
                        lv_question_detail_view.setVisibility(View.GONE);
                        isopened[viewPosition]=false;
                    }

                }
            });



            // CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.checkbox);
            // checkBox.setText(answersDetailsModelList1.get(position).getRiskCategory());

            //  ImageView iv_menu_image=(ImageView)convertView.findViewById(R.id.image_offer);


            return convertView;
        }

        public void submitAnswer(final int position){
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hseAuditAnswers, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("status")){
                            String result=jsonObject.getString("status");
                            if(result.matches("Insert successful")){
                              /* Intent i=new Intent(context, MainActivity.class);
                                Toast.makeText(context,"Inserted",Toast.LENGTH_SHORT).show();
                                context.startActivity(i);*/
                                /*  onBackPressed();*/
                                questionListing(questionsModelList.get(position).getField_id());
                                Toast.makeText(context,"Inserted",Toast.LENGTH_SHORT).show();
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
                    MyData.put(StringConstants.inputLocationId,locationId);
                    MyData.put(StringConstants.inputSpecificLocationId,specificLocationId);


                    return MyData; }
            };

            requestQueue.add(MyStringRequest);

        }
        public void showAlertDialog(String message){
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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

    }


    public class QuestionListAdapter extends BaseAdapter {

        private Context context;
        int type;

        private List<QuestionsModel> questionsModelList;
        boolean[] isopened;
        String selectedAnswer="";
        String observationType;

        String qusetionId;

        SharedPreferences preferences;

        String userid,observationCode;

        String currentDate;
        String fieldId;
        String currentTime;
        String locationId;
        String specificLocationId;
        public QuestionListAdapter(Context context, List<QuestionsModel> questionsModelList,int type){

            this.context=context;
            this.questionsModelList =questionsModelList;
            this.type=type;

/*
        for(int i=0;i<answersDetailsModelList1.size();i++){
            isopened[i]=false;
        }
*/

            this.isopened=new boolean[questionsModelList.size()];
            Arrays.fill(isopened,false);


        }
        @Override
        public int getCount() {
            return questionsModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return questionsModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return questionsModelList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.layout_questions_row, parent, false);
            }

            TextView tv_item=(TextView)convertView.findViewById(R.id.text_item);
            TextView tv_status=(TextView)convertView.findViewById(R.id.text_status);
            TextView tv_question=(TextView)convertView.findViewById(R.id.text_question);
            final LinearLayout lv_question_detail_view=(LinearLayout)convertView.findViewById(R.id.layout_question_detail_view);
            TextView tv_question1=(TextView)convertView.findViewById(R.id.text_question1);
            final RadioButton rb_yes=(RadioButton)convertView.findViewById(R.id.radi_button_yes);
            final RadioButton rb_no=(RadioButton)convertView.findViewById(R.id.radi_button_no);
            final RadioButton rb_na=(RadioButton)convertView.findViewById(R.id.radi_button_na);
            final TextView tv_message=(TextView)convertView.findViewById(R.id.text_mesage);
            final TextView tv_message_code=(TextView)convertView.findViewById(R.id.text_observation_number);
            Button submit=(Button)convertView.findViewById(R.id.button_submit);
            Button cancel=(Button)convertView.findViewById(R.id.button_cancel);


            RadioGroup rg_answer=(RadioGroup)convertView.findViewById(R.id.rg_answers);
            tv_question1.setText(questionsModelList.get(position).getTitle());

            tv_item.setText(String.valueOf(position+1));
            tv_question.setText(questionsModelList.get(position).getTitle());
            tv_status.setText("Not Started");

            tv_question.setTag(position);
            lv_question_detail_view.setTag(position);
            submit.setTag(position);
            preferences= PreferenceManager.getDefaultSharedPreferences(context);
            userid=preferences.getString(StringConstants.prefEmployeeId,"");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            currentDate = sdf.format(new Date());
            DateFormat df = new SimpleDateFormat(" hh:mm a");
            currentTime = df.format(Calendar.getInstance().getTime());
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int viewPistion=(int)view.getTag();
                   /* fieldId=answersDetailsModelList1.get(viewPistion).getField_id();
                    qusetionId=answersDetailsModelList1.get(viewPistion).getId();
               *//* locationId=answersDetailsModelList1.get(position).getLocationId();
                specificLocationId=answersDetailsModelList1.get(position).getSpecificLocationId();*//*
                    if(!selectedAnswer.isEmpty()){

                        if(selectedAnswer.matches("No")){
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
                        submitAnswer();
                    }else
                        showAlertDialog("Please select Answer");*/

                    Toast.makeText(getApplicationContext(),"Please select location and specific location",Toast.LENGTH_SHORT).show();



                }
            });
            rg_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup radioGroup, int i) {

                    // final RadioButton radioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

                    if(rb_yes.isChecked()){
                        selectedAnswer=rb_yes.getText().toString();
                    }if(rb_no.isChecked()){
                        selectedAnswer=rb_no.getText().toString().trim();
                    }if(rb_na.isChecked()){
                        selectedAnswer=rb_na.getText().toString().trim();
                    }
                    // selectedAnswer=radioButton.getText().toString().trim();
                    if(selectedAnswer.matches("No")){

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setMessage("Do you want to go to New Observation ?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        Intent i=new Intent(context,NewObservationActivity.class);
                                        if(type==1){
                                            i.putExtra("Platform","compliance");
                                        }else if(type==2){
                                            i.putExtra("Platform","audit");
                                        }
                                        // i.putExtra("Platform",observationType);
                                        i.putExtra("ScreenName","");
                                        context.startActivity(i);
                                        // Toast.makeText(QuestionDetailsActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedAnswer="";
                                rb_no.setChecked(false);
                                dialog.dismiss();


                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                }
            });

            tv_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int viewPosition=(int)lv_question_detail_view.getTag();
                    if(!isopened[viewPosition]){
                        lv_question_detail_view.getTag();
                        lv_question_detail_view.setVisibility(View.VISIBLE);
                        isopened[viewPosition]=true;
                    }else {
                        lv_question_detail_view.getTag();
                        lv_question_detail_view.setVisibility(View.GONE);
                        isopened[viewPosition]=false;
                    }

                }
            });



            // CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.checkbox);
            // checkBox.setText(answersDetailsModelList1.get(position).getRiskCategory());

            //  ImageView iv_menu_image=(ImageView)convertView.findViewById(R.id.image_offer);


            return convertView;
        }

        public void submitAnswer(){
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.hseComplianceAnswers, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("status")){
                            String result=jsonObject.getString("status");
                            if(result.matches("Insert successful")){
                              /*  Intent i=new Intent(context, MainActivity.class);
                                Toast.makeText(context,"Inserted",Toast.LENGTH_SHORT).show();
                                context.startActivity(i);*/
                                /*  onBackPressed();*/
                                Toast.makeText(context,"Inserted",Toast.LENGTH_SHORT).show();
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
                    MyData.put(StringConstants.inputLocationId,locationId);
                    MyData.put(StringConstants.inputSpecificLocationId,specificLocationId);


                    return MyData; }
            };

            requestQueue.add(MyStringRequest);

        }
        public void showAlertDialog(String message){
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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

    }


}
