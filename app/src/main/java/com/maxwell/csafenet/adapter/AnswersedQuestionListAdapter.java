package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.model.AnswersDetailsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnswersedQuestionListAdapter extends BaseAdapter {

    private Context context;

    private List<AnswersDetailsModel> answersDetailsModelList;

    public AnswersedQuestionListAdapter(Context context, List<AnswersDetailsModel> answersDetailsModelList1){

        this.context=context;
        this.answersDetailsModelList =answersDetailsModelList1;



    }
    @Override
    public int getCount() {
        return answersDetailsModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return answersDetailsModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_answered_question_by_location_row, parent, false);
        }

        TextView tv_item=(TextView)convertView.findViewById(R.id.text_item);
        TextView tv_question=(TextView)convertView.findViewById(R.id.text_question);
        TextView tv_reg_date=(TextView)convertView.findViewById(R.id.text_reg_date);
        TextView tv_remarks=(TextView)convertView.findViewById(R.id.text_remark);

        tv_item.setText(String.valueOf(position+1));
        tv_question.setText(answersDetailsModelList.get(position).getQuestion());
       /* String reg_date=answersDetailsModelList.get(position).getRegDate();

        try {
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = spf.parse(reg_date);
            spf= new SimpleDateFormat("dd-MM-yyyy");
            reg_date = spf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
      //  tv_reg_date.setText(reg_date);
        tv_remarks.setText(answersDetailsModelList.get(position).getAnswer());

        tv_question.setTag(position);

        return convertView;
    }

}
