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
import com.maxwell.csafenet.model.NotificationModel;
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

public class CrisisNotificationAdapter extends BaseAdapter {

private Context context;


private List<NotificationModel> questionsModelList;
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
public CrisisNotificationAdapter(Context context, List<NotificationModel> questionsModelList){

    this.context=context;
    this.questionsModelList =questionsModelList;


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
                inflate(R.layout.layout_notifications_row, parent, false);
    }

    TextView tv_notification=(TextView)convertView.findViewById(R.id.text_notification);
    TextView tv_project=(TextView)convertView.findViewById(R.id.text_project);

    tv_notification.setText(questionsModelList.get(position).getMessage());
    tv_project.setText(questionsModelList.get(position).getProjectName());

    return convertView;
}


}
