package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.activity.ResultActivity;

import java.util.List;

public class OnlineTestQuestionsAdapter extends RecyclerView.Adapter<OnlineTestQuestionsAdapter.ViewHolder>{

    List<String> questionList,option1,option2,option3,answer,questionIds,titles;;
    Context context;
    String selectedAnswer="";
    int correctAnswercount=0,answeredQuestions=0,count=0;
    int checked_position=-1;
    String questionAnswered="";

    public OnlineTestQuestionsAdapter(Context mcontext, List<String> questionLsit,List<String> option1,List<String> option2,List<String> option3,List<String> answer,List<String> questionIds,List<String> titles){
        this.context=mcontext;
        this.questionList =questionLsit;
        this.option1=option1;
        this.option2=option2;
        this.option3=option3;
        this.answer=answer;
        this.questionIds=questionIds;
        this.titles=titles;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_online_test_questions, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        viewHolder.tv_question.setText(String.valueOf(position+1)+" . "+questionList.get(position));
        viewHolder.rboption1.setText(option1.get(position));
        viewHolder.rbOption2.setText(option2.get(position));
        viewHolder.rbOption3.setText(option3.get(position));
        viewHolder.preferences= PreferenceManager.getDefaultSharedPreferences(context);
        viewHolder.editor=viewHolder.preferences.edit();
        selectedAnswer="";
        viewHolder.editor.remove("CorrectAnswersCount");
        viewHolder.editor.remove("AnsweredQuestions");
        viewHolder.editor.remove("QuestionAnswers");
        viewHolder.editor.commit();

        viewHolder.radioGroup_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(checked_position!=position) {

                    checked_position=position;


                    if (viewHolder.rboption1.isChecked()) {
                        selectedAnswer = "a";
                    } else if (viewHolder.rbOption2.isChecked()) {
                        selectedAnswer = "b";
                    } else if (viewHolder.rbOption3.isChecked()) {
                        selectedAnswer = "c";
                    }
                    questionAnswered=questionAnswered+","+questionIds.get(checked_position)+selectedAnswer;

                    if (selectedAnswer.matches(answer.get(position))) {
                        correctAnswercount = correctAnswercount + 1;

                    }
                    if (!selectedAnswer.isEmpty()) {
                        answeredQuestions = answeredQuestions + 1;
                    }


                    viewHolder.editor.putInt("CorrectAnswersCount", correctAnswercount);
                    viewHolder.editor.putInt("AnsweredQuestions", answeredQuestions);
                    viewHolder.editor.putString("QuestionAnswers",questionAnswered);
                    viewHolder.editor.commit();
                    viewHolder.editor.apply();
                }
        /*
                if(radioGroup.getCheckedRadioButtonId()==-1){
                   selectedAnswer="";

                }else {
                    RadioButton selectedRadioButton=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                    selectedAnswer=selectedRadioButton.getText().toString();

                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_question,tv_result,tv_likes,tv_comments;
        public LinearLayout linearLayout;
        public RelativeLayout relativeLayout;
        RadioButton rboption1,rbOption2,rbOption3;
        RadioGroup radioGroup_answer;
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_question = (TextView) itemView.findViewById(R.id.text_question);
            this.tv_result = (TextView) itemView.findViewById(R.id.text_result);
            this.rboption1 = (RadioButton) itemView.findViewById(R.id.rb_option1);
            this.rbOption2 = (RadioButton) itemView.findViewById(R.id.rb_option2);
            this.rbOption3 = (RadioButton) itemView.findViewById(R.id.rb_option3);
            this.radioGroup_answer = (RadioGroup) itemView.findViewById(R.id.rg_answer);

    }
}
}
