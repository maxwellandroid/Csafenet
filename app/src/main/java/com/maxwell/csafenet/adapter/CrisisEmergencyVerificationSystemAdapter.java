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
import com.maxwell.csafenet.activity.CEVSEntryActivity;
import com.maxwell.csafenet.model.AwardDetails;
import com.maxwell.csafenet.model.CEVSModel;

import java.util.List;

public class CrisisEmergencyVerificationSystemAdapter extends RecyclerView.Adapter<CrisisEmergencyVerificationSystemAdapter.ViewHolder>{

    List<CEVSModel> cevsModelList;
    Context context;

    public CrisisEmergencyVerificationSystemAdapter(Context mcontext, List<CEVSModel> cevsModelList){
        this.context=mcontext;
        this.cevsModelList =cevsModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_notifications_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CEVSModel cevsModel= cevsModelList.get(i);


            viewHolder.tv_project.setText(cevsModel.getProject());
            viewHolder.tv_notification.setText(cevsModel.getEmergencyCase());
            viewHolder.layout.setTag(i);

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=(int) view.getTag();
                    Intent i=new Intent(context, CEVSEntryActivity.class);
                    i.putExtra("EmerncyId",cevsModelList.get(position).getId());
                    context.startActivity(i);
                }
            });


    }

    @Override
    public int getItemCount() {
        return cevsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_project, tv_notification;
        LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            //this.imageView=(ImageView)itemView.findViewById(R.id.image_award);
            this.tv_project =(TextView)itemView.findViewById(R.id.text_project);
            this.tv_notification =(TextView)itemView.findViewById(R.id.text_notification);
            this.layout=(LinearLayout)itemView.findViewById(R.id.layout_container);



        }
    }


}
