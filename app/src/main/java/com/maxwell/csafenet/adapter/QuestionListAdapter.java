package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.maxwell.csafenet.activity.NewObservationActivity;
import com.maxwell.csafenet.model.QuestionsModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for(int i=0;i<questionsModelList.size();i++){
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
        Button  submit=(Button)convertView.findViewById(R.id.button_submit);
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
               /* locationId=questionsModelList.get(position).getLocationId();
                specificLocationId=questionsModelList.get(position).getSpecificLocationId();*/
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
                i.putExtra("Question",questionsModelList.get(viewPosition).getTitle());
                i.putExtra("QuestionId",questionsModelList.get(viewPosition).getId());
                i.putExtra("FieldId",questionsModelList.get(viewPosition).getField_id());
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
       // checkBox.setText(questionsModelList.get(position).getRiskCategory());

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
