package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;

public class HouseKeepingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_keeping);
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
        Intent i=new Intent(getApplicationContext(),ViewAnsweredQuestionActivity.class);
        i.putExtra("ScreenName",textView.getText().toString());
        startActivity(i);
    }


}
