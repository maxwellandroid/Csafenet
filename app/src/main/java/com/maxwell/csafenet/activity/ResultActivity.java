package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;

public class ResultActivity extends AppCompatActivity {

    TextView tv_total_questions,tv_answered_questions,tv_correct,tv_total_marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv_total_questions=(TextView)findViewById(R.id.textview_total_questions);
        tv_answered_questions=(TextView)findViewById(R.id.textview_answered_questions);
        tv_correct=(TextView)findViewById(R.id.textview_correct_answers);
        tv_total_marks=(TextView)findViewById(R.id.textview_total_score);

        String total_question=String.valueOf(getIntent().getIntExtra("TotalQuestions",0));
        String total_answerd_questions=String.valueOf(getIntent().getIntExtra("AnsweredQuestions",0));
        String total_correct_answer=String.valueOf(getIntent().getIntExtra("CorrectAnswers",0));

        tv_total_questions.setText("Total No.of Questions : "+total_question);
        tv_answered_questions.setText("No.of Questions Answered : "+total_answerd_questions);
        tv_correct.setText("Correct Answers : "+total_correct_answer);
        tv_total_marks.setText("Total Score : "+total_correct_answer+"/"+total_question);


    }
    public  void back(View view){
    Intent i=new Intent(getApplicationContext(),HSEOnlineTrainingSystemActivity.class);
    startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),HSEOnlineTrainingSystemActivity.class);
        startActivity(i);
    }
}
