package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.activity.HeadCounterStatusUpdateActivity;
import com.maxwell.csafenet.model.HeadCountTrackerModel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HeadCountAdapter extends RecyclerView.Adapter<HeadCountAdapter.ViewHolder>{

    List<HeadCountTrackerModel> cevsModelList;
    Context context;

    public HeadCountAdapter(Context mcontext, List<HeadCountTrackerModel> cevsModelList){
        this.context=mcontext;
        this.cevsModelList =cevsModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_head_counter_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final HeadCountTrackerModel headCountTrackerModel= cevsModelList.get(i);


        viewHolder.tv_project.setText(headCountTrackerModel.getProject());
        viewHolder.tv_emergency_type.setText(headCountTrackerModel.getEmergecyType());
        String str = headCountTrackerModel.getDate();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
            viewHolder.tv_date.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            viewHolder.tv_date.setText(headCountTrackerModel.getDate());
        }


            viewHolder.layout.setTag(i);

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=(int) view.getTag();
                    Intent i=new Intent(context, HeadCounterStatusUpdateActivity.class);
                    i.putExtra("HeadCountDetails",headCountTrackerModel);
                    //i.putExtra("employeeList", (Serializable) headCountTrackerModel.getEmployeeStatusModelList());
                    context.startActivity(i);
                }
            });


    }

    @Override
    public int getItemCount() {
        return cevsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_project, tv_emergency_type,tv_date;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            //this.imageView=(ImageView)itemView.findViewById(R.id.image_award);
            this.tv_project =(TextView)itemView.findViewById(R.id.text_project);
            this.tv_emergency_type =(TextView)itemView.findViewById(R.id.text_emergency_type);
            this.tv_date =(TextView)itemView.findViewById(R.id.text_date);
            this.layout=(LinearLayout)itemView.findViewById(R.id.layout_container);



        }
    }


}
