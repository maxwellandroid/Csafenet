package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.model.EmployeeStatusModel;
import com.maxwell.csafenet.model.HeadCountTrackerModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HeadCountStatusAdapter extends RecyclerView.Adapter<HeadCountStatusAdapter.ViewHolder>{

    List<EmployeeStatusModel> employeeStatusModelList;
    Context context;

    public HeadCountStatusAdapter(Context mcontext, List<EmployeeStatusModel> employeeStatusModelList){
        this.context=mcontext;
        this.employeeStatusModelList =employeeStatusModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_head_counter_status_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final EmployeeStatusModel employeeStatusModel= employeeStatusModelList.get(i);


        viewHolder.tv_project.setText(employeeStatusModel.getEmployeeName());
        viewHolder.tv_emergency_type.setText(employeeStatusModel.getDesignation());

        if(!employeeStatusModel.getVerifyId().equals("-")&&!employeeStatusModel.getVerifyId().isEmpty()){
            viewHolder.tv_date.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv_date.setVisibility(View.INVISIBLE);
        }

       /*     viewHolder.layout.setTag(i);

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=(int) view.getTag();
                    Intent i=new Intent(context, HeadCounterStatusUpdateActivity.class);
                    i.putExtra("HeadCountDetails",headCountTrackerModel);
                    context.startActivity(i);
                }
            });
*/

    }

    @Override
    public int getItemCount() {
        return employeeStatusModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_project, tv_emergency_type;
               ImageView tv_date;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            //this.imageView=(ImageView)itemView.findViewById(R.id.image_award);
            this.tv_project =(TextView)itemView.findViewById(R.id.text_name);
            this.tv_emergency_type =(TextView)itemView.findViewById(R.id.text_designation);
            this.tv_date =(ImageView) itemView.findViewById(R.id.text_status);
            this.layout=(LinearLayout)itemView.findViewById(R.id.layout_container);



        }
    }


}
