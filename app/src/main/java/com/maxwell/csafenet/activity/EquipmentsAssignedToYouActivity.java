package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.EquipmentModel;
import com.maxwell.csafenet.model.MyObservationsModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentsAssignedToYouActivity extends AppCompatActivity {

    TableLayout tableLayout;
    RelativeLayout layout_progress;
    String s_user_id;
    SharedPreferences preferences;
    List<EquipmentModel> equipmentModelList;
    EquipmentModel equipmentModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipments_assigned_to_you);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        layout_progress=(RelativeLayout)findViewById(R.id.rela_animation) ;
        tableLayout=(TableLayout)findViewById(R.id.table_entries);
        {
            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(EquipmentsAssignedToYouActivity.this);
            tbrow0.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext));
            TextView tv1 = new TextView(EquipmentsAssignedToYouActivity.this);
            tv1.setText(" Equipment Type ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.white));
            tv1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv1.setPadding(10,10,10,10);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(EquipmentsAssignedToYouActivity.this);
            tv2.setText(" Equipment Name ");
            tv2.setGravity(Gravity.START);
            tv2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv2.setPadding(10,10,10,10);
            tv2.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(EquipmentsAssignedToYouActivity.this);
            tv3.setText(" Equipment Manufacturer ");
            tv3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv3.setPadding(10,10,10,10);
            tv3.setGravity(Gravity.START);
            tv3.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv3);
            TextView tv4 = new TextView(EquipmentsAssignedToYouActivity.this);
            tv4.setText(" Equipment S.No ");
            tv4.setGravity(Gravity.CENTER);
            tv4.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv4.setPadding(10,10,10,10);
            tv4.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv4);
            TextView tv5 = new TextView(EquipmentsAssignedToYouActivity.this);
            tv5.setText(" From ");
            tv5.setGravity(Gravity.CENTER);
            tv5.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv5.setPadding(0,10,0,10);
            tv5.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv5);
            TextView tv06 = new TextView(EquipmentsAssignedToYouActivity.this);
            tv06.setText(" To ");
            tv06.setGravity(Gravity.CENTER);
            tv06.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv06.setPadding(10,10,10,10);
            tv06.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv06);
            TextView tv6 = new TextView(EquipmentsAssignedToYouActivity.this);
            tv6.setText(" Status ");
            tv6.setGravity(Gravity.CENTER);
            tv6.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            tv6.setPadding(10,10,10,10);
            tv6.setTextColor(getResources().getColor(android.R.color.white));
            tbrow0.addView(tv6);
            tableLayout.addView(tbrow0);
        }
        listEquipments();


    }

    public void listEquipments(){

        RequestQueue requestQueue = Volley.newRequestQueue(EquipmentsAssignedToYouActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.equipmentsAssignedUrl, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                if(!response.matches("")) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.trim());
                        if (jsonObject.has("list")) {

                            JSONArray jsonArray = new JSONArray();
                            jsonArray = jsonObject.getJSONArray("list");
                            equipmentModelList=new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dataObject = jsonArray.getJSONObject(i);

                                equipmentModel = new EquipmentModel();
                                equipmentModel.setEquipmentName(dataObject.getString("equipment_name"));
                                equipmentModel.setEquipmentType(dataObject.getString("type"));
                                equipmentModel.setManufacturer(dataObject.getString("manufacturer"));
                                equipmentModel.setEquipmentSerialNo(dataObject.getString("equipment_serial_no"));
                                equipmentModel.setStatus(dataObject.getString("status_name"));
                                equipmentModel.setFromDate(dataObject.getString("from_date"));
                                equipmentModel.setToDate(dataObject.getString("to_date"));

                              equipmentModelList.add(equipmentModel);
                            }

                            if (equipmentModelList.size() > 0) {

                                for (int i = 0; i < equipmentModelList.size(); i++) {


                                    TableRow row = new TableRow(getApplicationContext());
                                    TextView tv01 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv01.setText(equipmentModelList.get(i).getEquipmentType());
                                    tv01.setGravity(Gravity.CENTER);
                                    tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv01.setPadding(10, 10, 10, 10);
                                    tv01.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv01);
                                    TextView tv00 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv00.setText(equipmentModelList.get(i).getEquipmentType());
                                    tv00.setGravity(Gravity.CENTER);
                                    tv00.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv00.setPadding(10, 10, 10, 10);
                                    tv00.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv00);
                                    TextView tv02 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv02.setText(equipmentModelList.get(i).getManufacturer());
                                    tv02.setGravity(Gravity.CENTER);
                                    tv02.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv02.setPadding(10, 10, 10, 10);
                                    tv02.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv02);
                                    TextView tv03 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv03.setText(equipmentModelList.get(i).getEquipmentSerialNo());
                                    tv03.setGravity(Gravity.START);
                                    tv03.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv03.setPadding(10, 10, 10, 10);
                                    tv03.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv03);
                                    TextView tv04 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv04.setText(equipmentModelList.get(i).getFromDate());
                                    tv04.setGravity(Gravity.START);
                                    tv04.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv04.setPadding(10, 10, 10, 10);
                                    tv04.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv04);
                                    TextView tv05 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv05.setText(equipmentModelList.get(i).getToDate());
                                    tv05.setGravity(Gravity.CENTER);
                                    tv05.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv05.setPadding(10, 10, 10, 10);
                                    tv05.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv05);
                                    TextView tv06 = new TextView(getApplicationContext());
                                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    tv06.setText(equipmentModelList.get(i).getStatus());
                                    tv06.setGravity(Gravity.CENTER);
                                    tv06.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                    tv06.setPadding(10,10,10,10);
                                    tv06.setTextColor(getResources().getColor(android.R.color.black));
                                    //tv7.setPadding(12,12,12,12);
                                    row.addView(tv06);



                                    tableLayout.addView(row);
                                }
                                //result=jsonObject.getString("status");
                            } else {
                                TableRow row = new TableRow(getApplicationContext());
                                TextView tv01 = new TextView(getApplicationContext());
                                //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                tv01.setText("No Records Found");
                                tv01.setGravity(Gravity.CENTER);
                                //tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                                tv01.setPadding(10, 10, 10, 10);
                                tv01.setTextColor(getResources().getColor(android.R.color.black));
                                //tv7.setPadding(12,12,12,12);
                                row.addView(tv01);
                                tableLayout.addView(row);
                            }
                        } else {
                            TableRow row = new TableRow(getApplicationContext());
                            TextView tv01 = new TextView(getApplicationContext());
                            //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tv01.setText("No Records Found");
                            tv01.setGravity(Gravity.CENTER);
                            //tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                            tv01.setPadding(10, 10, 10, 10);
                            tv01.setTextColor(getResources().getColor(android.R.color.black));
                            //tv7.setPadding(12,12,12,12);
                            row.addView(tv01);
                            tableLayout.addView(row);
                        }

                        layout_progress.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    TableRow row = new TableRow(getApplicationContext());
                    TextView tv01 = new TextView(getApplicationContext());
                    //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv01.setText("No Records Found");
                    tv01.setGravity(Gravity.CENTER);
                    //tv01.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                    tv01.setPadding(10, 10, 10, 10);
                    tv01.setTextColor(getResources().getColor(android.R.color.black));
                    //tv7.setPadding(12,12,12,12);
                    row.addView(tv01);
                    tableLayout.addView(row);
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
    public  void back(View view){
        onBackPressed();
    }
}
