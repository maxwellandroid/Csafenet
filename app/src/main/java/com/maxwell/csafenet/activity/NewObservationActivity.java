package com.maxwell.csafenet.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import com.maxwell.csafenet.adapter.CustomGridAdapter;
import com.maxwell.csafenet.model.DropDownDetails;
import com.maxwell.csafenet.model.MyObservationsModule;
import com.maxwell.csafenet.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewObservationActivity extends AppCompatActivity {

    Spinner company, location, project, actionBy,safetyCategory;
    TextView daate, time, imageUriName;
    Map<String, String> companyList = null;
    Map<String, String> locationlist = null;
    Map<String, String> projectList = null;
    Map<String, String> actionByList = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText username, designation, descriptionObser, immediateCorrection,activityDescription, employeeNo,furtherRecomendation;
    ArrayList<String> companyarrayList = new ArrayList<String>();
    ArrayList<String> locationrrayList = new ArrayList<String>();
    ArrayList<String> projectrrayList = new ArrayList<String>();
    ArrayList<String> actionrrayList = new ArrayList<String>();
    ArrayList<String> actionbyList = new ArrayList<String>();
    ArrayList<String> safteyCategoryList = new ArrayList<String>();
    String companyid, projctId, locationId, actionById, filePath = "",safetyCategoryID;
    ArrayAdapter<String> dataAdapter;

    TextView tv_observation_type;

    Button Add;
    private static final int SELECT_PICTURE = 100,REQUEST_CAMERA = 1888;
    final int PERMISSION_REQUEST_CODE=123;
    Button SelectGlarry;
    private static final String TAG = "NewObservationActivity";
    private Bitmap bitmap;

    String s_name,s_designation,s_employee_number,s_user_id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

DropDownDetails dropDownDetails;

RadioGroup rg_type_of_observation;
String s_observation_type="",s_risk="",s_root_cause="";
    ArrayList<String> listrisk=new ArrayList<>();
    ArrayList<String>listRootCause=new ArrayList<>();
    List<DropDownDetails> dropDownDetailsList,dropDownDetailsList1,dropDownDetailsList2,dropDownDetailsList3,dropDownDetailsList4,dropDownDetailsList5,dropDownDetailsList6,dropDownDetailsList7;
    DropDownDetails[] dropDownDetailList;
    SpinnerAdapter adapter;
    String s_projectId;
    CustomGridAdapter customGridAdapter;
    GridView gridRisk;
    LinearLayout linearLayout,linearRootCause;

    MyObservationsModule myObservationsModule=new MyObservationsModule();

    RadioButton rb_safe,rb_safe_condition,rb_unsafe_act,rb_unsafe_condition,rb_near_miss;

    String screenName="";
    LinearLayout layout_top;

    LinearLayout layout_feedback,layout_status,layout_evidence_photos;
    TextView tv_file_type_message;

    EditText et_feed_back;
    RadioGroup rg_status;
    ImageView iv_evidence1,iv_evidence2;
    String s_feed_back,s_status="1";

    String platform="";

    RadioButton rb_open,rb_closed;
    TextView tv_evidence_photo_title,tv_complained_phot_title;

    LinearLayout layout_risk_other_description,layout_root_cause_others_description,layout_safety_others,layoutsafetyPPE;
    EditText et_risk_others,et_root_others,et_safety_others;
    String s_risk_others_description,s_root_cause_others_description,s_safety_others;
    CheckBox cb_head,cb_eyes_face,cb_ears,cb_breathing_protection,cb_arms_hands,cb_body,cb_legs_feet;
    String safety_explain="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_observation);
        layout_top=(LinearLayout)findViewById(R.id.layout_top);
        username = (EditText) findViewById(R.id.edt_name);
        employeeNo = (EditText) findViewById(R.id.edt_employeeNo);
        designation = (EditText) findViewById(R.id.edt_desigantion);
        descriptionObser = (EditText) findViewById(R.id.edt_descriptionUnSafeActCondition);
        immediateCorrection = (EditText) findViewById(R.id.edt_imm_corre_action);
        furtherRecomendation = (EditText) findViewById(R.id.edt_further_recomondation);
        activityDescription = (EditText) findViewById(R.id.edt_activity_description);
        daate = (TextView) findViewById(R.id.datee);
        time = (TextView) findViewById(R.id.edt_time);
        Add = (Button) findViewById(R.id.submit);
        company = (Spinner) findViewById(R.id.spinner_company);
        location = (Spinner) findViewById(R.id.location);
        project = (Spinner) findViewById(R.id.project);
        actionBy = (Spinner) findViewById(R.id.actionby);
        safetyCategory = (Spinner) findViewById(R.id.safety_category);
        SelectGlarry = (Button) findViewById(R.id.chooseFile);
        imageUriName = (TextView) findViewById(R.id.image_uri);
        rg_type_of_observation=(RadioGroup)findViewById(R.id.radio_group);
        gridRisk=(GridView)findViewById(R.id.grid_risk) ;
        linearLayout=(LinearLayout)findViewById(R.id.linear_risk);
        linearRootCause=(LinearLayout)findViewById(R.id.linear_root_cause);
        rb_safe=(RadioButton)findViewById(R.id.safe_act);
        rb_safe_condition=(RadioButton)findViewById(R.id.safe_condition);
        rb_unsafe_act=(RadioButton)findViewById(R.id.unsafe_act);
        rb_unsafe_condition=(RadioButton)findViewById(R.id.unsafe_condition);
        rb_near_miss=(RadioButton)findViewById(R.id.near_miss);
        tv_file_type_message=(TextView)findViewById(R.id.text_file_type_message);
        layout_feedback=(LinearLayout)findViewById(R.id.layout_feed_back);
        layout_evidence_photos=(LinearLayout)findViewById(R.id.layout_evidence_photos);
        layout_status=(LinearLayout)findViewById(R.id.layout_satatus);
        rb_open=(RadioButton)findViewById(R.id.rb_opened);
        rb_closed=(RadioButton)findViewById(R.id.rb_closed);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor=preferences.edit();
        editor.remove(StringConstants.prefRegCode);
        editor.apply();
        tv_observation_type=(TextView)findViewById(R.id.text_observation_type);
        tv_evidence_photo_title=(TextView)findViewById(R.id.text_evedent_photo_title);
        tv_complained_phot_title=(TextView)findViewById(R.id.text_complaind_photo_title);
        cb_head=(CheckBox)findViewById(R.id.cb_head);
        cb_eyes_face=(CheckBox)findViewById(R.id.cb_eyes_face);
        cb_ears=(CheckBox)findViewById(R.id.cb_ears);
        cb_breathing_protection=(CheckBox)findViewById(R.id.cb_breathing_protection);
        cb_arms_hands=(CheckBox)findViewById(R.id.cb_arms_hands);
        cb_body=(CheckBox)findViewById(R.id.cb_body);
        cb_legs_feet=(CheckBox)findViewById(R.id.cb_legs_feet);

        et_feed_back=(EditText)findViewById(R.id.edit_feed_back);
        rg_status=(RadioGroup)findViewById(R.id.radio_group_status) ;
        iv_evidence1=(ImageView)findViewById(R.id.image_evident1);
        iv_evidence2=(ImageView)findViewById(R.id.image_evident2);

        layout_risk_other_description=(LinearLayout)findViewById(R.id.layout_risk_others_description);
        layout_root_cause_others_description=(LinearLayout)findViewById(R.id.layout_root_cause_others_description);
        layout_safety_others=(LinearLayout)findViewById(R.id.layout_safety_others_description);
        layoutsafetyPPE=(LinearLayout)findViewById(R.id.layout_safety_ppe);
        et_risk_others=(EditText)findViewById(R.id.edt_risk_other_description) ;
        et_root_others=(EditText)findViewById(R.id.edt_root_cause_other_description);
        et_safety_others=(EditText)findViewById(R.id.edt_saftety_other_description);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(new Date());
        DateFormat df = new SimpleDateFormat(" hh:mm a");
        String currentTime = df.format(Calendar.getInstance().getTime());
        Log.i("StringDate", currentTime);
        daate.setText(currentDate);
        time.setText(currentTime);

        myObservationsModule= (MyObservationsModule) getIntent().getSerializableExtra("Observations");
        if(getIntent()!=null){
            screenName=getIntent().getStringExtra("ScreenName");
            platform=getIntent().getStringExtra("Platform");
        }
        if(myObservationsModule!=null){
            layout_evidence_photos.setVisibility(View.VISIBLE);
            username.setText(myObservationsModule.getUserName());
            employeeNo.setText(myObservationsModule.getEmployeeNumber());
            daate.setText(myObservationsModule.getEnterDate());
            time.setText(myObservationsModule.getEnterTime());

            if(!myObservationsModule.getUploaded_image().isEmpty()&&!myObservationsModule.getUploaded_image().matches("")){
                Glide.with(NewObservationActivity.this)
                        .load("http://csafenet.com/"+myObservationsModule.getUploaded_image()) // image url
                        // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                        //.error(R.drawable.imagenotfound)  // any image in case of error
                        // .override(200, 200); // resizing
                        //.apply(new RequestOptions().placeholder(R.drawable.loading))
                        .into(iv_evidence1);

            }
            if(!myObservationsModule.getImage2().isEmpty()&&!myObservationsModule.getImage2().matches("")){
                Glide.with(NewObservationActivity.this)
                        .load("http://csafenet.com/"+myObservationsModule.getImage2()) // image url
                        // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                        //.error(R.drawable.imagenotfound)  // any image in case of error
                        // .override(200, 200); // resizing
                        //.apply(new RequestOptions().placeholder(R.drawable.loading))
                        .into(iv_evidence2);

            }

            activityDescription.setText(myObservationsModule.getActionDesc(), TextView.BufferType.EDITABLE);
            descriptionObser.setText(myObservationsModule.getUnsafeCondition(), TextView.BufferType.EDITABLE);
            immediateCorrection.setText(myObservationsModule.getActionTaken(), TextView.BufferType.EDITABLE);
            furtherRecomendation.setText(myObservationsModule.getRecommended(), TextView.BufferType.EDITABLE);
            tv_observation_type.setText("Edit Observation");

            if(myObservationsModule.getStatus().matches("2")){
                rb_closed.setChecked(true);
            }

            if(myObservationsModule.getSafetyExplain().length()>0){
                if(myObservationsModule.getSafetyExplain().contains(",")){
                    String safetyExplain[]=myObservationsModule.getSafetyExplain().split(",");
                    for(int i=0;i<safetyExplain.length;i++){
                        String safet=safetyExplain[i];
                        if(safet.matches(cb_head.getText().toString().trim())){
                            cb_head.setChecked(true);
                        } if(safet.matches(cb_eyes_face.getText().toString().trim())){
                            cb_eyes_face.setChecked(true);
                        } if(safet.matches(cb_ears.getText().toString().trim())){
                            cb_ears.setChecked(true);
                        } if(safet.matches(cb_breathing_protection.getText().toString().trim())){
                            cb_breathing_protection.setChecked(true);
                        } if(safet.matches(cb_arms_hands.getText().toString().trim())){
                            cb_arms_hands.setChecked(true);
                        } if(safet.matches(cb_body.getText().toString().trim())){
                            cb_body.setChecked(true);
                        } if(safet.matches(cb_legs_feet.getText().toString().trim())){
                            cb_legs_feet.setChecked(true);
                        }
                    }
                }
                else {
                    String safet=myObservationsModule.getSafetyExplain();
                    if(safet.matches(cb_head.getText().toString().trim())){
                        cb_head.setChecked(true);
                    } if(safet.matches(cb_eyes_face.getText().toString().trim())){
                        cb_eyes_face.setChecked(true);
                    } if(safet.matches(cb_ears.getText().toString().trim())){
                        cb_ears.setChecked(true);
                    } if(safet.matches(cb_breathing_protection.getText().toString().trim())){
                        cb_breathing_protection.setChecked(true);
                    } if(safet.matches(cb_arms_hands.getText().toString().trim())){
                        cb_arms_hands.setChecked(true);
                    } if(safet.matches(cb_body.getText().toString().trim())){
                        cb_body.setChecked(true);
                    } if(safet.matches(cb_legs_feet.getText().toString().trim())){
                        cb_legs_feet.setChecked(true);
                    }
                }
            }

            if(myObservationsModule.getObservation().matches("safe_act")){
                rb_safe.setChecked(true);
                s_observation_type="1";
            }  if(myObservationsModule.getObservation().matches("safe_condition")){
                rb_safe_condition.setChecked(true);
                s_observation_type="2";
            }  if(myObservationsModule.getObservation().matches("unsafe_act")){
                rb_unsafe_act.setChecked(true);
                s_observation_type="3";
            }  if(myObservationsModule.getObservation().matches("unsafe_condition")){
                rb_unsafe_condition.setChecked(true);
                s_observation_type="4";
            }  if(myObservationsModule.getObservation().matches("never_miss")){
                rb_near_miss.setChecked(true);
                s_observation_type="5";
            }
            //rg_type_of_observation.check(rg_type_of_observation.getChildAt(myObservationsModule.getObservatioType()));

            if(screenName.matches("Observation For Action")){
                tv_evidence_photo_title.setVisibility(View.VISIBLE);
                SelectGlarry.setText("Choose Proof Image");
            }else {
                tv_complained_phot_title.setText("Uploaded Photo");
                SelectGlarry.setText("Choose Image");
            }

            Add.setText("Update");
        }

        company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        safetyCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(safteyCategoryList.get(i).equals("PPE")){
                    layoutsafetyPPE.setVisibility(View.VISIBLE);
                }else
                    layoutsafetyPPE.setVisibility(View.GONE);

                if(safteyCategoryList.get(i).equals("Others")){
                    layout_safety_others.setVisibility(View.VISIBLE);
                }else
                    layout_safety_others.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        companyarrayList.add("-Select Company-");
        locationrrayList.add("-Select Location-");
        projectrrayList.add("-Select Project-");
        actionrrayList.add("-Select Action-");
        actionbyList.add("-Select Action-");
        safteyCategoryList.add("-Select Safety Category-");


     //   new ProjectsListOperation().execute();
        projectListing();
       // new CompanyListOperation().execute();
        companyListing();
       // new LocationListOperation().execute();
        locationListing();
       // new SafetyCategoryListOperation().execute();
        safetyCategoryListing();
      //  new RisksListOperation().execute();
        riskListing();
     //   new RootCauseListOperation().execute();
        rootcauseListing();

       // new ActionByOperation().execute();
        actionByListing();
        rg_type_of_observation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton button=(RadioButton)findViewById(selectedId);
                s_observation_type=button.getText().toString();
                if(s_observation_type.matches("Safe Act")){
                    s_observation_type="1";
                }else if(s_observation_type.matches("Safe Condition")){
                    s_observation_type="2";
                }else if(s_observation_type.matches("Unsafe Act")){
                    s_observation_type="3";
                }else if(s_observation_type.matches("Unsafe Condition")){
                    s_observation_type="4";
                }else if(s_observation_type.matches("Near Miss")){
                    s_observation_type="5";
                }
            }
        });
        rg_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton button=(RadioButton)findViewById(selectedId);
                s_status=button.getText().toString();

            }
        });

        if(screenName!=null&&screenName.matches("Observation For Action")){

          /*  layout_top.setFocusable(false);
            layout_top.setFocusableInTouchMode(false);
            layout_top.setClickable(false);*/

            descriptionObser.setFocusable(false);
            descriptionObser.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            descriptionObser.setClickable(false);

            immediateCorrection.setFocusable(false);
            immediateCorrection.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            immediateCorrection.setClickable(false);

            furtherRecomendation.setFocusable(false);
            furtherRecomendation.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            furtherRecomendation.setClickable(false);

            activityDescription.setFocusable(false);
            activityDescription.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            activityDescription.setClickable(false);

            company.setEnabled(false);
            company.setClickable(false);
            project.setEnabled(false);
            project.setClickable(false);
            location.setEnabled(false);
            location.setClickable(false);
            safetyCategory.setEnabled(false);
            safetyCategory.setClickable(false);
            actionBy.setEnabled(false);
            actionBy.setClickable(false);

            rb_near_miss.setEnabled(false);
            rb_unsafe_condition.setEnabled(false);
            rb_unsafe_act.setEnabled(false);
            rb_safe_condition.setEnabled(false);
            rb_safe.setEnabled(false);

          //  SelectGlarry.setClickable(false);
           // SelectGlarry.setVisibility(View.GONE);
          //  SelectGlarry.setEnabled(false);
          //  imageUriName.setVisibility(View.GONE);

           // tv_file_type_message.setVisibility(View.GONE);

            layout_status.setVisibility(View.VISIBLE);
            layout_evidence_photos.setVisibility(View.VISIBLE);
            layout_feedback.setVisibility(View.VISIBLE);
            if(myObservationsModule.getStatus().matches("2")){
                rb_closed.setChecked(true);
            }else {
                rb_open.setChecked(true);
            }

        }else {

        }

        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        s_name=preferences.getString(StringConstants.prefEmployeeName,"");
        s_employee_number=preferences.getString(StringConstants.prefEmployeeNo,"");
        s_designation=preferences.getString(StringConstants.prefEmployeeDesignation,"");
        username.setText(s_name);
        employeeNo.setText(s_employee_number);
        designation.setText(s_designation);


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            s_risk="";
            s_root_cause="";
            if(listrisk.size()>0){
                for(int i=0;i<listrisk.size();i++){
                    s_risk+=", "+listrisk.get(i);
                }
                s_risk=s_risk.substring(2);
            }
           if(listRootCause.size()>0){
               for(int i=0;i<listRootCause.size();i++){
                   s_root_cause+=", "+listRootCause.get(i);
               }
               s_root_cause=s_root_cause.substring(2);
           }

                    s_feed_back=et_feed_back.getText().toString();
                if(screenName.matches("Observation For Action")){
                    if(!s_status.isEmpty()){
                        if(s_status.matches("Closed")){
                            s_status="2";
                        }else
                            s_status="1";
                    }

                    new UpdateActionBy().execute();

                }else {

                    if (!company.getSelectedItem().toString().equals("-Select Company-") && !company.getSelectedItem().toString().equals("")) {
                        int selected_id = company.getSelectedItemPosition();
                        companyid = dropDownDetailsList1.get(selected_id - 1).getCompanyId();
                        if (!project.getSelectedItem().toString().equals("-Select Project-") && !project.getSelectedItem().toString().equals("")) {
                            int selectedProgram_id = project.getSelectedItemPosition();
                            projctId = dropDownDetailsList.get(selectedProgram_id - 1).getProjectsId();

                            if (!location.getSelectedItem().toString().equals("-Select Location-") && !location.getSelectedItem().toString().equals("")) {
                                int selectedLocation_id = location.getSelectedItemPosition();
                                locationId = dropDownDetailsList2.get(selectedLocation_id - 1).getLocationId();
                                if (!safetyCategory.getSelectedItem().toString().equals("-Select Safety Category-") && !safetyCategory.getSelectedItem().toString().equals("")) {
                                    int selectedSafety_id = safetyCategory.getSelectedItemPosition();
                                    safetyCategoryID = dropDownDetailsList3.get(selectedSafety_id - 1).getSafetyCategoryId();
                                    if (s_observation_type != null && !s_observation_type.isEmpty()) {
                                        if (!s_risk.isEmpty()) {
                                            if (!s_root_cause.isEmpty()) {

                                                if (!activityDescription.getText().toString().isEmpty()) {

                                                    if (!descriptionObser.getText().toString().isEmpty()) {

                                                        if (!immediateCorrection.getText().toString().isEmpty()) {

                                                            if (!furtherRecomendation.getText().toString().isEmpty()) {

                                                                if (!actionBy.getSelectedItem().toString().equals("-Select Action-") && !actionBy.getSelectedItem().toString().equals("")) {
                                                                    int selectedActionby_id = actionBy.getSelectedItemPosition();
                                                                    actionById = dropDownDetailsList6.get(selectedActionby_id - 1).getActionById();
                                                                    if(!s_status.isEmpty()){
                                                                        if(s_status.matches("Closed")){
                                                                            s_status="2";
                                                                        }else
                                                                            s_status="1";
                                                                    }

                                                                    s_risk_others_description=et_risk_others.getText().toString().trim();
                                                                    s_root_cause_others_description=et_root_others.getText().toString().trim();
                                                                    s_safety_others=et_safety_others.getText().toString().trim();

                                                                    if(cb_head.isChecked()){
                                                                        safety_explain = safety_explain+cb_head.getText().toString().trim()+",";
                                                                    }
                                                                    if(cb_eyes_face.isChecked()){
                                                                        safety_explain=safety_explain+cb_eyes_face.getText().toString().trim()+",";
                                                                    }
                                                                    if(cb_ears.isChecked()){
                                                                        safety_explain=safety_explain+cb_ears.getText().toString().trim()+",";
                                                                    }
                                                                    if(cb_breathing_protection.isChecked()){
                                                                        safety_explain=safety_explain+cb_breathing_protection.getText().toString().trim()+",";
                                                                    }
                                                                    if(cb_arms_hands.isChecked()){
                                                                        safety_explain=safety_explain+cb_arms_hands.getText().toString().trim()+",";
                                                                    }
                                                                    if(cb_body.isChecked()){
                                                                        safety_explain=safety_explain+cb_body.getText().toString().trim()+",";
                                                                    }
                                                                    if(cb_legs_feet.isChecked()){
                                                                        safety_explain=safety_explain+cb_legs_feet.getText().toString().trim();
                                                                    }
                                                                    if(myObservationsModule!=null){
                                                                        new UpdateObservation().execute();
                                                                    }else {

                                                                        insertNewObservation();
                                                                       // new InsertNewObservationOperation().execute();
                                                                    }
                                                                } else {
                                                                    showAlertDialog("Please Select Action By");
                                                                }

                                                            } else
                                                                showAlertDialog("Please Enter Further Recomendation");


                                                        } else
                                                            showAlertDialog("Please Enter Immediate Corrective Action Taken");


                                                    } else
                                                        showAlertDialog("Please Enter Description of Safe act/condition");


                                                } else
                                                    showAlertDialog("Please Enter Activity Description");

                                            } else
                                                showAlertDialog("Please Check Root Cause");

                                        } else
                                            showAlertDialog("Please Check Risk");

                                    } else
                                        showAlertDialog("Please Select Type of Observation");


                                } else
                                    showAlertDialog("Please Select Safety Category");

                            } else
                                showAlertDialog("Please Select Location");

                        } else
                            showAlertDialog("Please Select Project");
                    } else
                        showAlertDialog("Please Select Company");


                }



            }
        });

        SelectGlarry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(NewObservationActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(NewObservationActivity.this);
                if (items[item].equals("Take Photo")) {
                            cameraIntent();
                            //  dispatchTakePictureIntent();

                } else if (items[item].equals("Choose from Library")) {
                        openImageChooser();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(NewObservationActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  //  Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                    // main logic
                } else {
                 //   Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {

                            requestPermission();

                        }
                    }
                }
                break;

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
        // requestPermissions( new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_REQUEST_CODE);
    }
    private void cameraIntent()
    {
        if(checkPermission()){
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    public static boolean isCameraAvailable(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cb_asphyxiation:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_caught_objects:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_electrocution:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_cut_by:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_body_pos:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_environmental_damage:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_fire_explosion:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_falling_heights:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_property_damage:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_noise_vibration:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_strike_object:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_slips_trips:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_toxic_effect:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_temp_extreams:
                str = checked?((CheckBox) view).getText().toString():"";
                break;
            case R.id.cb_risk_others:
                str = checked?((CheckBox) view).getText().toString():"";
                break;

        }
      //  Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
       onBackPressed();
    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    filePath = encodedImage;
                    // Set the image in ImageView
                    if (path != null) {
                        File f = new File(path);
                        String imageName = f.getName();
                        imageUriName.setText(imageName);

                    }
                  //  Toast.makeText(getApplicationContext(),filePath, Toast.LENGTH_SHORT).show();

                }
            } else if (requestCode == REQUEST_CAMERA){

              /*  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                byte[] imageBytes = bytes.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                filePath = encodedImage;
*/
              onCaptureImageResult(data);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] imageBytes = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        filePath = encodedImage;
        File destination = new File(getApplicationContext().getFilesDir().getPath(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Uri fileUri=data.getData();
       /*Uri fileUri=getImageUri(DriverJobListActivity.this,thumbnail);
        cameraImagePath=getPath(getApplicationContext(),fileUri);*/


        Uri fileUri=Uri.fromFile(destination);
      String  cameraImagePath=getPath(getApplicationContext(),fileUri);
        //cameraImagePath=destination.getParent();

        String filename=cameraImagePath.substring(cameraImagePath.lastIndexOf(File.separator)+1);
        if(cameraImagePath!=null&&cameraImagePath!=""&&cameraImagePath.length()>0){
            imageUriName.setText(filename);
        }
      //  Toast.makeText(getApplicationContext(),filePath, Toast.LENGTH_SHORT).show();

       /* }else {

            Toast.makeText(getApplicationContext(),"Storage Permission Denied", Toast.LENGTH_SHORT).show();
        }
*/

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    public void projectListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        dropDownDetailsList=new ArrayList<>();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.projectsListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("project")){

                        JSONArray jsonArray=jsonObject.getJSONArray("project");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject=jsonArray.getJSONObject(i);
                            dropDownDetails=new DropDownDetails();
                            dropDownDetails.setProject(dataObject.getString("project"));
                            dropDownDetails.setProjectsId(dataObject.getString("id"));

                            projectrrayList.add(dataObject.getString("project"));
                            dropDownDetailsList.add(dropDownDetails);
                        }

                        dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_item, projectrrayList);
                        project.setAdapter(dataAdapter);

                        if(myObservationsModule!=null){
                            project.setSelection(projectrrayList.indexOf(myObservationsModule.getProjectName()));
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
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }
    public void companyListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        dropDownDetailsList1=new ArrayList<>();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.companyListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("company")){

                        JSONArray jsonArray=jsonObject.getJSONArray("company");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject=jsonArray.getJSONObject(i);
                            dropDownDetails=new DropDownDetails();
                            dropDownDetails.setCompanyName(dataObject.getString("company_name"));
                            dropDownDetails.setCompanyId(dataObject.getString("id"));

                            companyarrayList.add(dataObject.getString("company_name"));
                            dropDownDetailsList1.add(dropDownDetails);
                        }

                        dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_item, companyarrayList);
                        company.setAdapter(dataAdapter);

                        if(myObservationsModule!=null){
                            company.setSelection(companyarrayList.indexOf(myObservationsModule.getCompanyName()));
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
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }

    public void locationListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        dropDownDetailsList2=new ArrayList<>();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.locationListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("location")){

                        JSONArray jsonArray=jsonObject.getJSONArray("location");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject=jsonArray.getJSONObject(i);
                            dropDownDetails=new DropDownDetails();
                            dropDownDetails.setLocation(dataObject.getString("location"));
                            dropDownDetails.setLocationId(dataObject.getString("id"));
                            locationrrayList.add(dataObject.getString("location"));
                            dropDownDetailsList2.add(dropDownDetails);
                        }

                        dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_item, locationrrayList);
                        location.setAdapter(dataAdapter);
                        if(myObservationsModule!=null){
                            location.setSelection(locationrrayList.indexOf(myObservationsModule.getLocationName()));
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
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }

    public void safetyCategoryListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        dropDownDetailsList3=new ArrayList<>();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.safetyCategoryListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("saftycategory")){

                        JSONArray jsonArray=jsonObject.getJSONArray("saftycategory");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject=jsonArray.getJSONObject(i);
                            dropDownDetails=new DropDownDetails();
                            dropDownDetails.setSafetyCategory(dataObject.getString("name"));
                            dropDownDetails.setSafetyCategoryId(dataObject.getString("id"));

                            safteyCategoryList.add(dataObject.getString("name"));
                            dropDownDetailsList3.add(dropDownDetails);
                        }

                        dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_item, safteyCategoryList);
                        safetyCategory.setAdapter(dataAdapter);
                        if(myObservationsModule!=null){
                            safetyCategory.setSelection(safteyCategoryList.indexOf(myObservationsModule.getSafetyCategory()));
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
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }
    public void riskListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        dropDownDetailsList4=new ArrayList<>();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.riskListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("riskcategory")){

                        JSONArray jsonArray=jsonObject.getJSONArray("riskcategory");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject=jsonArray.getJSONObject(i);
                            dropDownDetails=new DropDownDetails();
                            dropDownDetails.setRiskCategory(dataObject.getString("name"));
                            dropDownDetails.setRiskCategoryId(dataObject.getString("id"));
                            // listrisk.add(dataObject.getString("name"));

                            dropDownDetailsList4.add(dropDownDetails);
                        }
                        for(int i=0;i<dropDownDetailsList4.size();i++){
                            CheckBox cb = new CheckBox(NewObservationActivity.this);
                            cb.setText(dropDownDetailsList4.get(i).getRiskCategory());
                            cb.setId(Integer.parseInt(dropDownDetailsList4.get(i).getRiskCategoryId()));

                            if(myObservationsModule!=null){
                                String risk=myObservationsModule.getRisk();




                                if(risk.contains(",")){
                                    String[] values = risk.split(",");
                                    List<String> risk_list=new ArrayList<>();
                                    risk_list= Arrays.asList(values);

                                    for (int j=0;j<risk_list.size();j++){
                                        if (cb.getId()==Integer.parseInt(risk_list.get(j).trim())){
                                            cb.setChecked(true);
                                            listrisk.add( Integer.toString(cb.getId()));
                                           if(cb.getText().toString().equals("Others")){
                                               layout_risk_other_description.setVisibility(View.VISIBLE);
                                               et_risk_others.setText(myObservationsModule.getRiskOthers());
                                           }
                                        }else {
                                            listrisk.remove( Integer.toString(cb.getId()));
                                        }
                                    }
                                }else if(risk.length()>0&&!risk.contains(",")){
                                    if (cb.getId()==Integer.parseInt(risk)){
                                        cb.setChecked(true);
                                        listrisk.add( Integer.toString(cb.getId()));
                                        if(cb.getText().toString().equals("Others")){
                                            layout_risk_other_description.setVisibility(View.VISIBLE);
                                            et_risk_others.setText(myObservationsModule.getRiskOthers());
                                        }



                                    }else {
                                        listrisk.remove( Integer.toString(cb.getId()));
                                    }
                                }

                            }
                            if(screenName.matches("Observation For Action")){

                                cb.setEnabled(false);
                                cb.setClickable(false);
                            }

                            cb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    String chk = null;
                                    chk = Integer.toString(v.getId());

                                    if (((CheckBox) v).isChecked()) {
                                        listrisk.add(chk);
                                        if(chk.matches(dropDownDetailsList4.get(dropDownDetailsList4.size()-1).getRiskCategoryId())){
                                            layout_risk_other_description.setVisibility(View.VISIBLE);
                                        }

                                    } else {
                                        listrisk.remove(chk);
                                        if(chk.matches(dropDownDetailsList4.get(dropDownDetailsList4.size()-1).getRiskCategoryId())){
                                            layout_risk_other_description.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            });
                            linearLayout.addView(cb);
                        }
                            /*customGridAdapter=new CustomGridAdapter(NewObservationActivity.this,dropDownDetailsList4);
                            gridRisk.setAdapter(customGridAdapter);
*/
                           /* dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                    android.R.layout.simple_spinner_item, projectrrayList);
                            project.setAdapter(dataAdapter);*/
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
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }
    public void rootcauseListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        dropDownDetailsList5=new ArrayList<>();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.rootCauseListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("rootcausecategory")){

                        JSONArray jsonArray=jsonObject.getJSONArray("rootcausecategory");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject=jsonArray.getJSONObject(i);
                            dropDownDetails=new DropDownDetails();
                            dropDownDetails.setRootCause(dataObject.getString("name"));
                            dropDownDetails.setRootCauseId(dataObject.getString("id"));

                            //listRootCause.add(dataObject.getString("name"));
                            dropDownDetailsList5.add(dropDownDetails);
                        }

                        for(int i=0;i<dropDownDetailsList5.size();i++){
                            CheckBox cb = new CheckBox(NewObservationActivity.this);
                            cb.setText(dropDownDetailsList5.get(i).getRootCause());
                            cb.setId(Integer.parseInt(dropDownDetailsList5.get(i).getRootCauseId()));

                            if(myObservationsModule!=null){
                                String risk=myObservationsModule.getRoot();
                                if(risk.contains(",")){
                                    String[] values = risk.split(",");
                                    List<String> risk_list=new ArrayList<>();
                                    risk_list= Arrays.asList(values);

                                    for (int j=0;j<risk_list.size();j++){
                                        if (cb.getId()==Integer.parseInt(risk_list.get(j).trim())){
                                            cb.setChecked(true);
                                            listRootCause.add( Integer.toString(cb.getId()));
                                        }else {
                                            listRootCause.remove( Integer.toString(cb.getId()));
                                        }
                                    }
                                }else if(risk.length()>0&&!risk.contains(",")){
                                    if (cb.getId()==Integer.parseInt(risk)){
                                        cb.setChecked(true);
                                        listRootCause.add( Integer.toString(cb.getId()));
                                    }else {
                                        listRootCause.remove( Integer.toString(cb.getId()));
                                    }
                                }

                            }

                            if(screenName.matches("Observation For Action")){

                                cb.setEnabled(false);
                                cb.setClickable(false);
                            }

                            cb.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    String chk = null;
                                    chk = Integer.toString(v.getId());
                                    if (((CheckBox) v).isChecked()) {
                                        listRootCause.add(chk);
                                        if(chk.matches(dropDownDetailsList5.get(dropDownDetailsList5.size()-1).getRootCauseId())){
                                            layout_root_cause_others_description.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        listRootCause.remove(chk);
                                        if(chk.matches(dropDownDetailsList5.get(dropDownDetailsList5.size()-1).getRootCauseId())){
                                            layout_root_cause_others_description.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            });

                            linearRootCause.addView(cb);
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
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }
    public void actionByListing(){
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        dropDownDetailsList6=new ArrayList<>();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.actionByListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("actionby")){

                        JSONArray jsonArray=jsonObject.getJSONArray("actionby");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject=jsonArray.getJSONObject(i);
                            dropDownDetails=new DropDownDetails();
                            dropDownDetails.setActionBy(dataObject.getString("fname"));
                            dropDownDetails.setActionById(dataObject.getString("id"));
                            //listRootCause.add(dataObject.getString("name"));
                            dropDownDetailsList6.add(dropDownDetails);
                            actionrrayList.add(dataObject.getString("fname")+" - "+dataObject.getString("designation"));
                            actionbyList.add(dataObject.getString("fname"));
                        }
                        dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_item, actionrrayList);
                        actionBy.setAdapter(dataAdapter);

                        if(myObservationsModule!=null){
                            actionBy.setSelection(actionbyList.indexOf(myObservationsModule.getActionByName()));
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
                MyData.put(StringConstants.inputUserID, s_user_id);
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);
    }


    public void insertNewObservation(){

        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.newObservation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                try {
                    JSONObject jsonObject=new JSONObject(response.trim());
                    if(jsonObject.has("status")){
                        String   result=jsonObject.getString("status");
                        if(result.matches("Insert successful")){
                            Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();

                            editor.putString(StringConstants.prefRegCode,jsonObject.getString("reg_id"));
                            editor.commit();
                            editor.apply();

                            onBackPressed();
                        }else {
                            showAlertDialog("Failed");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();



                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.getMessage());
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put(StringConstants.inputUserID, s_user_id);
                MyData.put(StringConstants.inputCompany,companyid);
                MyData.put(StringConstants.inputProject, projctId);
                MyData.put(StringConstants.inputLocation,locationId);
                MyData.put(StringConstants.inputSafetyCategory, safetyCategoryID);
                MyData.put(StringConstants.inputSafteyOthers, s_safety_others);
                MyData.put(StringConstants.inputSafteyExplain, safety_explain);
                MyData.put(StringConstants.inputDate,daate.getText().toString());
                MyData.put(StringConstants.inputTime, time.getText().toString());
                MyData.put(StringConstants.inputRegDate,daate.getText().toString());
                MyData.put(StringConstants.inputRegDate, time.getText().toString());
                MyData.put(StringConstants.inputObservationType,s_observation_type);
                MyData.put(StringConstants.inputRisk,s_risk);
                MyData.put(StringConstants.inputRoot,s_root_cause);
                MyData.put(StringConstants.inputRootOther,s_root_cause_others_description);
                MyData.put(StringConstants.inputRiskOthers,s_risk_others_description);
                MyData.put(StringConstants.inputActivityDescription, activityDescription.getText().toString());
                MyData.put(StringConstants.inputunsafeCodition,descriptionObser.getText().toString());
                MyData.put(StringConstants.inputCorrectiveActionTaken,immediateCorrection.getText().toString() );
                MyData.put(StringConstants.inputRecommendation,furtherRecomendation.getText().toString());
                MyData.put(StringConstants.inputActionBy, actionById);
                MyData.put(StringConstants.inputImage,filePath);
                MyData.put(StringConstants.inputPlatform,platform);
                MyData.put(StringConstants.inputFeedback,s_feed_back);
                MyData.put(StringConstants.inputStatus,s_status);
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }

    private class UpdateObservation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(NewObservationActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.updateObservationUrl, new Response.Listener<String>() {
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
                              /*  Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                                startActivity(i);*/
                                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else if(result.matches("UPDATE successful")){

                                Intent i=new Intent(getApplicationContext(),ViewObservationsActivity.class);
                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                                startActivity(i);
                               // onBackPressed();
                            }else {

                                showAlertDialog("Failed");
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
                    MyData.put(StringConstants.inputRegCode, myObservationsModule.getRegCode());
                    MyData.put(StringConstants.inputCompany,companyid);
                    MyData.put(StringConstants.inputProject, projctId);
                    MyData.put(StringConstants.inputLocation,locationId);
                    MyData.put(StringConstants.inputSafetyCategoryUpdate, safetyCategoryID);
                    MyData.put(StringConstants.inputSafteyOthers, s_safety_others);
                    MyData.put(StringConstants.inputSafteyExplain, safety_explain);
                    MyData.put(StringConstants.inputDate,daate.getText().toString());
                    MyData.put(StringConstants.inputTime, time.getText().toString());
                    MyData.put(StringConstants.inputRegDate,daate.getText().toString());
                    MyData.put(StringConstants.inputRegDate, time.getText().toString());
                    MyData.put(StringConstants.inputObservationType,s_observation_type);
                    MyData.put(StringConstants.inputRisk,s_risk);
                    MyData.put(StringConstants.inputRoot,s_root_cause);
                    MyData.put(StringConstants.getInputActivityDescriptionUpdate, activityDescription.getText().toString());
                    MyData.put(StringConstants.inputunsafeCodition,descriptionObser.getText().toString());
                    MyData.put(StringConstants.getInputCorrectiveActionUpdate,immediateCorrection.getText().toString() );
                    MyData.put(StringConstants.getInputRecommendationUpdate,furtherRecomendation.getText().toString());
                    MyData.put(StringConstants.inputActionBy, actionById);
                    MyData.put(StringConstants.inputImage,filePath);
                    MyData.put(StringConstants.inputFeedback,s_feed_back);
                    MyData.put(StringConstants.inputStatus,s_status);

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
    private class UpdateActionBy extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(NewObservationActivity.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(NewObservationActivity.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.updateActionByUrl, new Response.Listener<String>() {
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
                              /*Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                                startActivity(i);*/
                                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else if(result.matches("UPDATE successful")){


                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                               // onBackPressed();
                                String action;
                                Intent i=new Intent(getApplicationContext(),ObservationForActionActivity.class);
                                startActivity(i);
                            }else {

                                showAlertDialog("Failed");
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
                    MyData.put(StringConstants.inputRegCode, myObservationsModule.getRegCode());
                    MyData.put(StringConstants.inputImage,filePath);
                    MyData.put(StringConstants.inputFeedback,s_feed_back);
                    MyData.put(StringConstants.inputStatus,s_status);
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


}
