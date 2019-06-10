package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.activity.CEVSEntryActivity;
import com.maxwell.csafenet.model.CEVSModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CEVSAdapter extends RecyclerView.Adapter<CEVSAdapter.ViewHolder>{

    List<CEVSModel> cevsModelList;
    Context context;

    public CEVSAdapter(Context mcontext, List<CEVSModel> cevsModelList){
        this.context=mcontext;
        this.cevsModelList =cevsModelList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_cevs_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final CEVSModel cevsModel= cevsModelList.get(i);
        viewHolder.tv_emergencyCase.setText(cevsModel.getEmergencyCase());
        viewHolder.tv_project.setText(cevsModel.getProject());
        viewHolder.linearLayout.setTag(i);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(context, CEVSEntryActivity.class);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cevsModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_emergencyCase,tv_project;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_emergencyCase=(TextView)itemView.findViewById(R.id.text_crisis_emergency_case);
            this.tv_project=(TextView)itemView.findViewById(R.id.text_project);
            this.linearLayout=(LinearLayout)itemView.findViewById(R.id.layout_container);



        }
    }


}
