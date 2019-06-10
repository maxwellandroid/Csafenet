package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.activity.ViewAnsweredQuestionsByLocationActivty;
import com.maxwell.csafenet.model.ComplianceModuleViewModel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComplianceViewAdapter extends BaseAdapter {

    private Context context;

    private List<ComplianceModuleViewModel> complianceModuleViewModelList;

    public ComplianceViewAdapter(Context context, List<ComplianceModuleViewModel> complianceModuleViewModelList){

        this.context=context;
        this.complianceModuleViewModelList =complianceModuleViewModelList;



    }
    @Override
    public int getCount() {
        return complianceModuleViewModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return complianceModuleViewModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_view_compliance_row, parent, false);
        }

        TextView tv_location=(TextView)convertView.findViewById(R.id.text_location);
        TextView tv_specific_location=(TextView)convertView.findViewById(R.id.text_specific_location);
        TextView tv_reg_date=(TextView)convertView.findViewById(R.id.text_reg_date);
        LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.layout_container) ;

        tv_location.setText(complianceModuleViewModelList.get(position).getLocation());
        tv_specific_location.setText(complianceModuleViewModelList.get(position).getSpecificLocation());
        String reg_date= complianceModuleViewModelList.get(position).getRegDate();
        try {
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = spf.parse(reg_date);
            spf= new SimpleDateFormat("dd-MM-yyyy");
            reg_date = spf.format(newDate);

            tv_reg_date.setText(reg_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context, ViewAnsweredQuestionsByLocationActivty.class);
                i.putExtra("answersList", (Serializable) complianceModuleViewModelList.get(position).getAnswersDetailsModelList());
                context.startActivity(i);
            }
        });





        return convertView;
    }

}
