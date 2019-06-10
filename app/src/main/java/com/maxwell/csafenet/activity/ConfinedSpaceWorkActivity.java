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

public class ConfinedSpaceWorkActivity extends AppCompatActivity {

    ListView list_questions;

    QuestionListAdapter adapter;

    List<QuestionsModel> confinedSpaceWorkQuestionsList,exacavationQuestionsList,vehicleOperationQuestionsList,hotWorkQuestionsList,liftingQuestionList=new ArrayList<>(),droppedObjectPreventionQuestionsList,safeIsolationEnergyQuestionsList,scaffoldingQuestionsList,simultaneousOperationsQuestionsList,workingAtHeightQuestionsList,houseKeepingQuestionList,lineOfFireQuestionsList,personalProtectiveQuestionsList,routineLifeTasksQuestions,barricadesQuestionsList;
    SharedPreferences preferences;
    String s_user_id;
    QuestionsModel questionsModel;
    String title="";
    TextView textViewTitle;
    TextView textViewModules;
    String fIDconfinedWorks,fIDexcavations,fIDHeavyEquipments,fIDhotWorks,fIDlifting,fIDdropedObject,fIDSafeIsolation,fIDscaffolding,fIDsimops,fIDworkatHeight,fIDhouseKeeping,fIDlineofFire,fIDppe,fIDroutineLifeTask,fIDopenHoles;
    RelativeLayout layout_progress;
    LinearLayout layout_home;
    List<String> location=new ArrayList<>();
    List<String> specificLocation=new ArrayList<>();
    DropDownDetails dropDownDetails;
    ArrayAdapter<String> dataAdapter;
    List<DropDownDetails> dropDownDetailsList,dropDownDetailsList1;
    Spinner sLocation,sSpecificLocation;
    String locationId="",specificLocationId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confined_space_work);

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
        Intent i=new Intent(getApplicationContext(),ViewComplianceModulesActivity.class);
        i.putExtra("ScreenName",textView.getText().toString());
        if(title.matches("Confined Space Work"))
        i.putExtra("FieldId",fIDconfinedWorks);
        else if (title.matches("Excavations"))
        i.putExtra("FieldId",fIDexcavations);
        else if (title.matches("Heavy Equipment and Vehicle Operations"))
        i.putExtra("FieldId",fIDHeavyEquipments);
        else if (title.matches("Hot Works"))
        i.putExtra("FieldId",fIDhotWorks);
        else if (title.matches("Lifting and Hoisting"))
        i.putExtra("FieldId",fIDlifting);
        else if (title.matches("Dropped Object Prevention"))
        i.putExtra("FieldId",fIDdropedObject);
        else if (title.matches("Safe Isolation of Energy"))
        i.putExtra("FieldId",fIDSafeIsolation);
        else if (title.matches("Scaffolding and other forms of access"))
        i.putExtra("FieldId",fIDscaffolding);
        else if (title.matches("Simultaneous Operations"))
        i.putExtra("FieldId",fIDsimops);
        else if (title.matches("Working at Height"))
        i.putExtra("FieldId",fIDworkatHeight);
        else if (title.matches("Housekeeping"))
        i.putExtra("FieldId",fIDhouseKeeping);
        else if (title.matches("Line of Fire"))
        i.putExtra("FieldId",fIDlineofFire);
        else if (title.matches("Personal protective Equipment"))
        i.putExtra("FieldId",fIDppe);
        else if (title.matches("Routine Life Task"))
        i.putExtra("FieldId",fIDroutineLifeTask);
        else if (title.matches("Barricades and Open Holes"))
        i.putExtra("FieldId",fIDopenHoles);
        startActivity(i);
    }

    public void initializeViews(){
        list_questions=(ListView)findViewById(R.id.list_questions);
        textViewTitle=(TextView)findViewById(R.id.text_title) ;
        textViewModules=(TextView)findViewById(R.id.text_view_modules);
        layout_home=(LinearLayout)findViewById(R.id.layout_home) ;
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        sLocation=(Spinner)findViewById(R.id.location) ;
        sSpecificLocation=(Spinner)findViewById(R.id.specific_location) ;
        title=getIntent().getStringExtra("Screen");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        textViewTitle.setText(title);
        textViewModules.setText("View "+title);

        location=new ArrayList<>();
        specificLocation=new ArrayList<>();
        location.add("-Select Location-");
        specificLocation.add("-Select Specific Location-");
        if(title.matches("Simultaneous Operations")){
            textViewTitle.setText(title+" (SIMOPS)");
        }

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
                    if(specificLocationId!=null){
                        {
                            if(title.matches("Confined Space Work"))
                            questionListing(fIDconfinedWorks);
                            else if (title.matches("Excavations"))
                            questionListing(fIDexcavations);
                            else if (title.matches("Heavy Equipment and Vehicle Operations"))
                            questionListing(fIDHeavyEquipments);
                            else if (title.matches("Hot Works"))
                            questionListing(fIDhotWorks);
                            else if (title.matches("Lifting and Hoisting"))
                            questionListing(fIDlifting);
                            else if (title.matches("Dropped Object Prevention"))
                            questionListing(fIDdropedObject);
                            else if (title.matches("Safe Isolation of Energy"))
                            questionListing(fIDSafeIsolation);
                            else if (title.matches("Scaffolding and other forms of access"))
                            questionListing(fIDscaffolding);
                            else if (title.matches("Simultaneous Operations"))
                            questionListing(fIDsimops);
                            else if (title.matches("Working at Height"))
                            questionListing(fIDworkatHeight);
                            else if (title.matches("Housekeeping"))
                            questionListing(fIDhouseKeeping);
                            else if (title.matches("Line of Fire"))
                            questionListing(fIDlineofFire);
                            else if (title.matches("Personal protective Equipment"))
                            questionListing(fIDppe);
                            else if (title.matches("Routine Life Task"))
                            questionListing(fIDroutineLifeTask);
                            else if (title.matches("Barricades and Open Holes"))
                            questionListing(fIDopenHoles);

                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
      /*  if (!sLocation.getSelectedItem().toString().equals("-Select Location-") && !sLocation.getSelectedItem().toString().equals("")) {
            int selectedLocation_id = sLocation.getSelectedItemPosition();
            locationId=dropDownDetailsList.get(selectedLocation_id-1).getLocationId();
        }if (!sSpecificLocation.getSelectedItem().toString().equals("-Select Specific Location-") && !sSpecificLocation.getSelectedItem().toString().equals("")) {
            int selectedLocation_id = sSpecificLocation.getSelectedItemPosition();
            specificLocationId=dropDownDetailsList1.get(selectedLocation_id-1).getSpecificLocationId();
        }*/
        new GetQuestionsOperation().execute();
    }

    public void locationListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(ConfinedSpaceWorkActivity.this);
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
    public void questionListing(final String fieldId){
        RequestQueue requestQueue = Volley.newRequestQueue(ConfinedSpaceWorkActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.locationWiseQuestionsUrl, new Response.Listener<String>() {
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
                            confinedSpaceWorkQuestionsList=new ArrayList<>();
                            for(int i=0;i<questionArray.length();i++){

                                JSONObject confinedArrayJSONObject=questionArray.getJSONObject(i);
                                questionsModel=new QuestionsModel();
                                questionsModel.setId(confinedArrayJSONObject.getString("question_id"));
                                questionsModel.setField_id(confinedArrayJSONObject.getString("field_id"));
                                questionsModel.setAnswer(confinedArrayJSONObject.getString("answer"));
                                questionsModel.setTitle(confinedArrayJSONObject.getString("question"));
                                questionsModel.setObservationCode(confinedArrayJSONObject.getString("observation_code"));
                                confinedSpaceWorkQuestionsList.add(questionsModel);
                                fIDconfinedWorks=confinedArrayJSONObject.getString("field_id");
                            }
                          QuestionListAdapter1  adapter=new QuestionListAdapter1(ConfinedSpaceWorkActivity.this, confinedSpaceWorkQuestionsList,1);
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
    public void specificLocationListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(ConfinedSpaceWorkActivity.this);
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
    private class GetQuestionsOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(ConfinedSpaceWorkActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(ConfinedSpaceWorkActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.complianceInspectionUrl, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    if(!response.matches("")){

                        try {
                            JSONObject jsonObject=new JSONObject(response.trim());

                            if(jsonObject.has("Confined Space Work")){
                                JSONArray confinedArray=new JSONArray();
                                confinedArray=jsonObject.getJSONArray("Confined Space Work");
                                confinedSpaceWorkQuestionsList=new ArrayList<>();
                                for(int i=0;i<confinedArray.length();i++){

                                    JSONObject confinedArrayJSONObject=confinedArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(confinedArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(confinedArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(confinedArrayJSONObject.getString("question"));
                                    confinedSpaceWorkQuestionsList.add(questionsModel);
                                    fIDconfinedWorks=confinedArrayJSONObject.getString("field_id");
                                }
                            }
                            if(jsonObject.has("Excavations ")){
                                JSONArray excavationsArray=new JSONArray();
                                excavationsArray=jsonObject.getJSONArray("Excavations ");
                                exacavationQuestionsList=new ArrayList<>();
                                for(int i=0;i<excavationsArray.length();i++){

                                    JSONObject excavationsArrayJSONObject=excavationsArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(excavationsArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(excavationsArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(excavationsArrayJSONObject.getString("question"));
                                    fIDexcavations=excavationsArrayJSONObject.getString("field_id");
                                    exacavationQuestionsList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Heavy Equipment and Vehicle Operations")){
                                JSONArray vechicleOperationArray=new JSONArray();
                                vechicleOperationArray=jsonObject.getJSONArray("Heavy Equipment and Vehicle Operations");
                                vehicleOperationQuestionsList=new ArrayList<>();
                                for(int i=0;i<vechicleOperationArray.length();i++){

                                    JSONObject vechicleOperationArrayJSONObject=vechicleOperationArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(vechicleOperationArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(vechicleOperationArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(vechicleOperationArrayJSONObject.getString("question"));
                                    fIDHeavyEquipments=vechicleOperationArrayJSONObject.getString("field_id");
                                    vehicleOperationQuestionsList.add(questionsModel);
                                }
                            }

                            if(jsonObject.has("Hot Works")){
                                JSONArray hotworksArray=new JSONArray();
                                hotworksArray=jsonObject.getJSONArray("Hot Works");
                                hotWorkQuestionsList=new ArrayList<>();
                                for(int i=0;i<hotworksArray.length();i++){

                                    JSONObject hotworksArrayJSONObject=hotworksArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(hotworksArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(hotworksArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(hotworksArrayJSONObject.getString("question"));
                                    fIDhotWorks=hotworksArrayJSONObject.getString("field_id");

                                    hotWorkQuestionsList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Lifting")){
                                JSONArray hotworksArray=new JSONArray();
                                hotworksArray=jsonObject.getJSONArray("Lifting");
                                liftingQuestionList=new ArrayList<>();
                                for(int i=0;i<hotworksArray.length();i++){

                                    JSONObject hotworksArrayJSONObject=hotworksArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(hotworksArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(hotworksArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(hotworksArrayJSONObject.getString("question"));
                                    fIDhotWorks=hotworksArrayJSONObject.getString("field_id");

                                    liftingQuestionList.add(questionsModel);
                                }
                            }


                            if(jsonObject.has("Dropped Object Prevention")){
                                JSONArray dropeedObjectsArray=new JSONArray();
                                dropeedObjectsArray=jsonObject.getJSONArray("Dropped Object Prevention");
                                droppedObjectPreventionQuestionsList=new ArrayList<>();
                                for(int i=0;i<dropeedObjectsArray.length();i++){

                                    JSONObject dropeedObjectsArrayJSONObject=dropeedObjectsArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(dropeedObjectsArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(dropeedObjectsArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(dropeedObjectsArrayJSONObject.getString("question"));
                                    fIDdropedObject=dropeedObjectsArrayJSONObject.getString("field_id");
                                    droppedObjectPreventionQuestionsList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Safe Isolation of Energy")){
                                JSONArray safeIsolationArray=new JSONArray();
                                safeIsolationArray=jsonObject.getJSONArray("Safe Isolation of Energy");
                                safeIsolationEnergyQuestionsList=new ArrayList<>();
                                for(int i=0;i<safeIsolationArray.length();i++){

                                    JSONObject safeIsolationArrayJSONObject=safeIsolationArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(safeIsolationArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(safeIsolationArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(safeIsolationArrayJSONObject.getString("question"));
                                    fIDSafeIsolation=safeIsolationArrayJSONObject.getString("field_id");
                                    safeIsolationEnergyQuestionsList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Scaffolding and other forms of access")){
                                JSONArray scaffoldingArray=new JSONArray();
                                scaffoldingArray=jsonObject.getJSONArray("Scaffolding and other forms of access");
                                scaffoldingQuestionsList=new ArrayList<>();
                                for(int i=0;i<scaffoldingArray.length();i++){

                                    JSONObject scaffoldingArrayJSONObject=scaffoldingArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(scaffoldingArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(scaffoldingArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(scaffoldingArrayJSONObject.getString("question"));
                                    fIDscaffolding=scaffoldingArrayJSONObject.getString("field_id");
                                    scaffoldingQuestionsList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Simultaneous OPerations (SIMOPS)")){
                                JSONArray simultaneousArray=new JSONArray();
                                simultaneousArray=jsonObject.getJSONArray("Simultaneous OPerations (SIMOPS)");
                                simultaneousOperationsQuestionsList=new ArrayList<>();
                                for(int i=0;i<simultaneousArray.length();i++){

                                    JSONObject simultaneousArrayJSONObject=simultaneousArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(simultaneousArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(simultaneousArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(simultaneousArrayJSONObject.getString("question"));
                                    fIDsimops=simultaneousArrayJSONObject.getString("field_id");
                                    simultaneousOperationsQuestionsList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Working at Height")){
                                JSONArray workingHeightArray=new JSONArray();
                                workingHeightArray=jsonObject.getJSONArray("Working at Height");
                                workingAtHeightQuestionsList=new ArrayList<>();
                                for(int i=0;i<workingHeightArray.length();i++){

                                    JSONObject workingHeightArrayJSONObject=workingHeightArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(workingHeightArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(workingHeightArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(workingHeightArrayJSONObject.getString("question"));
                                    fIDworkatHeight=workingHeightArrayJSONObject.getString("field_id");
                                    workingAtHeightQuestionsList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("HoseKeeping ")){
                                JSONArray houseKeepingArray=new JSONArray();
                                houseKeepingArray=jsonObject.getJSONArray("HoseKeeping ");
                                houseKeepingQuestionList=new ArrayList<>();
                                for(int i=0;i<houseKeepingArray.length();i++){

                                    JSONObject houseKeepingArrayJSONObject=houseKeepingArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(houseKeepingArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(houseKeepingArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(houseKeepingArrayJSONObject.getString("question"));
                                    fIDhouseKeeping=houseKeepingArrayJSONObject.getString("field_id");
                                    houseKeepingQuestionList.add(questionsModel);
                                }
                            }
                            if(jsonObject.has("Line of Fire")){
                                JSONArray lofArray=new JSONArray();
                                lofArray=jsonObject.getJSONArray("Line of Fire");
                                lineOfFireQuestionsList=new ArrayList<>();
                                for(int i=0;i<lofArray.length();i++){

                                    JSONObject lofArrayJSONObject=lofArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(lofArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(lofArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(lofArrayJSONObject.getString("question"));
                                    lineOfFireQuestionsList.add(questionsModel);
                                    fIDlineofFire=lofArrayJSONObject.getString("field_id");
                                }
                            }
                            if(jsonObject.has("Personal Protective Equipment")){
                                JSONArray personalProtectiveArray=new JSONArray();
                                personalProtectiveArray=jsonObject.getJSONArray("Personal Protective Equipment");
                                personalProtectiveQuestionsList=new ArrayList<>();
                                for(int i=0;i<personalProtectiveArray.length();i++){

                                    JSONObject personalProtectiveArrayJSONObject=personalProtectiveArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(personalProtectiveArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(personalProtectiveArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(personalProtectiveArrayJSONObject.getString("question"));
                                    personalProtectiveQuestionsList.add(questionsModel);
                                    fIDppe=personalProtectiveArrayJSONObject.getString("field_id");
                                }
                            }
                        if(jsonObject.has("Barricades and Open Holes")){
                                JSONArray baricadesArray=new JSONArray();
                                baricadesArray=jsonObject.getJSONArray("Barricades and Open Holes");
                                barricadesQuestionsList=new ArrayList<>();
                                for(int i=0;i<baricadesArray.length();i++){
                                    JSONObject baricadesArrayJSONObject=baricadesArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(baricadesArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(baricadesArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(baricadesArrayJSONObject.getString("question"));
                                    barricadesQuestionsList.add(questionsModel);
                                    fIDopenHoles=baricadesArrayJSONObject.getString("field_id");
                                }
                            }
                            if(jsonObject.has("Routine Life Tasks ")){
                                JSONArray routineTaskArray=new JSONArray();
                                routineTaskArray=jsonObject.getJSONArray("Routine Life Tasks ");
                                routineLifeTasksQuestions=new ArrayList<>();
                                for(int i=0;i<routineTaskArray.length();i++){
                                    JSONObject routineTaskArrayJSONObject=routineTaskArray.getJSONObject(i);
                                    questionsModel=new QuestionsModel();
                                    questionsModel.setId(routineTaskArrayJSONObject.getString("id"));
                                    questionsModel.setField_id(routineTaskArrayJSONObject.getString("field_id"));
                                    questionsModel.setTitle(routineTaskArrayJSONObject.getString("question"));
                                    routineLifeTasksQuestions.add(questionsModel);
                                    fIDroutineLifeTask=routineTaskArrayJSONObject.getString("field_id");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(title.matches("Confined Space Work")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, confinedSpaceWorkQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Excavations")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, exacavationQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Heavy Equipment and Vehicle Operations")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, vehicleOperationQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Hot Works")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, hotWorkQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }
                        if(title.matches("Lifting and Hoisting")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, liftingQuestionList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Dropped Object Prevention")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, droppedObjectPreventionQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Safe Isolation of Energy")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, safeIsolationEnergyQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Scaffolding and other forms of access")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, scaffoldingQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }
                        if(title.matches("Simultaneous Operations")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, simultaneousOperationsQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Working at Height")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, workingAtHeightQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Housekeeping")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, houseKeepingQuestionList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Line of Fire")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, lineOfFireQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }
                        if(title.matches("Personal protective Equipment")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, personalProtectiveQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Routine Life Task")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, routineLifeTasksQuestions,1);
                            list_questions.setAdapter(adapter);
                        }else if(title.matches("Barricades and Open Holes")){
                            adapter=new QuestionListAdapter(ConfinedSpaceWorkActivity.this, barricadesQuestionsList,1);
                            list_questions.setAdapter(adapter);
                        }
                        layout_progress.setVisibility(View.GONE);
                        layout_home.setVisibility(View.VISIBLE);
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

/*
    @Override
    protected void onResume() {
        super.onResume();

       adapter.observationCode=adapter.preferences.getString(StringConstants.prefRegCode,"");

        if(adapter.observationCode!=null&&!adapter.observationCode.isEmpty()){
            adapter.rb_no.setChecked(true);
            rb_yes.setEnabled(false);
            rb_na.setEnabled(false);
            // rg_answer.setEnabled(false);
            tv_message_code.setVisibility(View.VISIBLE);
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Stored Successfully");
            tv_message_code.setText("Code : "+observationCode);
        }
    }
*/

    public class QuestionListAdapter extends BaseAdapter {

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
                    fieldId=questionsModelList.get(viewPistion).getField_id();
                    qusetionId=questionsModelList.get(viewPistion).getId();
               /* locationId=answersDetailsModelList1.get(position).getLocationId();
                specificLocationId=answersDetailsModelList1.get(position).getSpecificLocationId();*/
/*
                    if(!selectedAnswer.isEmpty()){

                        if(selectedAnswer.matches("No")){
                            observationCode=preferences.getString(StringConstants.prefRegCode,"");
                            if(observationCode!=null&&!observationCode.isEmpty()){
                              */
/*  rb_no.setChecked(true);
                                rb_yes.setEnabled(false);
                                rb_na.setEnabled(false);*//*

                                // rg_answer.setEnabled(false);
                                tv_message_code.setVisibility(View.VISIBLE);
                                tv_message.setVisibility(View.VISIBLE);
                                tv_message.setText("Stored Successfully");
                                tv_message_code.setText("Code : "+observationCode);
                            }
                        }
                        submitAnswer();

                    }else
                        showAlertDialog("Please select Answer");

*/


                    Toast.makeText(context,"Please select location and specific location",Toast.LENGTH_SHORT).show();

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

}
