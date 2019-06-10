package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.adapter.AnswersListAdapter;
import com.maxwell.csafenet.adapter.AnswersedQuestionListAdapter;
import com.maxwell.csafenet.model.AnswersDetailsModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAnsweredQuestionsByLocationActivty extends AppCompatActivity {
    List<AnswersDetailsModel> answersDetailsModelList=new ArrayList<>();
    ListView list_questions;
    AnswersedQuestionListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answered_questions_by_location_activty);

        list_questions=(ListView)findViewById(R.id.list_questions);
        Intent i = getIntent();

        answersDetailsModelList = (List<AnswersDetailsModel>) i.getSerializableExtra("answersList");

        if(answersDetailsModelList.size()>0){
            list_questions.setVisibility(View.VISIBLE);
            adapter=new AnswersedQuestionListAdapter(ViewAnsweredQuestionsByLocationActivty.this,answersDetailsModelList);
            list_questions.setAdapter(adapter);
        }
    }
    public  void back(View view){
        onBackPressed();
    }
}
