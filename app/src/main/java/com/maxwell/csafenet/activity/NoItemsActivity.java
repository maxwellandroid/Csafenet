package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;

public class NoItemsActivity extends AppCompatActivity {

    TableLayout tableLayout;

    TextView tv_screen_name;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_items);

        tableLayout=(TableLayout)findViewById(R.id.table_entries);
        tv_screen_name=(TextView)findViewById(R.id.text_screen_name);

        String screenName=getIntent().getStringExtra("Screen");
        tv_screen_name.setText(screenName);

        {

            tableLayout.removeAllViews();

            TableRow tbrow0 = new TableRow(NoItemsActivity.this);
            tbrow0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            TextView tv0 = new TextView(NoItemsActivity.this);
            tv0.setText(" S.No ");
            tv0.setGravity(Gravity.CENTER);
            tv0.setTextColor(getResources().getColor(android.R.color.black));
            tv0.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv0.setPadding(0,10,0,10);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(NoItemsActivity.this);
            tv1.setText(" Employee ID ");
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextColor(getResources().getColor(android.R.color.black));
            tv1.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv1.setPadding(0,10,0,10);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(NoItemsActivity.this);
            tv2.setText(" Name ");
            tv2.setGravity(Gravity.CENTER);
            tv2.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv2.setPadding(0,10,0,10);
            tv2.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv2);
            TextView tv3 = new TextView(NoItemsActivity.this);
            tv3.setText(" Designation ");
            tv3.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv3.setPadding(0,10,0,10);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv3);
            TextView tv4 = new TextView(NoItemsActivity.this);
            tv4.setText(" Company ");
            tv4.setGravity(Gravity.CENTER);
            tv4.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv4.setPadding(0,10,0,10);
            tv4.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv4);
            TextView tv5 = new TextView(NoItemsActivity.this);
            tv5.setText(" Assessor Name ");
            tv5.setGravity(Gravity.CENTER);
            tv5.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv5.setPadding(0,10,0,10);
            tv5.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv5);
            TextView tv6 = new TextView(NoItemsActivity.this);
            tv6.setText(" Action ");
            tv6.setGravity(Gravity.CENTER);
            tv6.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            tv6.setPadding(0,10,0,10);
            tv6.setTextColor(getResources().getColor(android.R.color.black));
            tbrow0.addView(tv6);
            tableLayout.addView(tbrow0);
            TableRow row0=new TableRow(NoItemsActivity.this);
            TextView tv=new TextView(NoItemsActivity.this);
            tv.setText("");
            row0.addView(tv);
            tableLayout.addView(row0);
            TableRow row=new TableRow(getApplicationContext());
            row.setGravity(Gravity.CENTER);
            TextView tv7 = new TextView(getApplicationContext());
            //tv7.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv7.setText("No Records Available");
            tv7.setGravity(Gravity.CENTER);
            tv7.setPadding(0,10,0,10);
            tv7.setTextColor(getResources().getColor(android.R.color.black));
            //tv7.setPadding(12,12,12,12);
            row.addView(tv7);
            tableLayout.addView(row);
        }
    }
    public  void back(View view){
      onBackPressed();
    }

}
