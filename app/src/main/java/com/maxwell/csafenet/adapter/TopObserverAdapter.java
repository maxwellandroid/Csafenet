package com.maxwell.csafenet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.activity.TopObserverActivity;
import com.maxwell.csafenet.model.ManageObservationModel;
import com.maxwell.csafenet.model.TopObserverModule;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopObserverAdapter extends RecyclerView.Adapter<TopObserverAdapter.MyViewHolder> {

    private List<TopObserverModule> homeList;
    Context context;


    public TopObserverAdapter(Context mcontext, List<TopObserverModule> journalsModelList){
        this.context=mcontext;
        this.homeList =journalsModelList;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_top_observer_row, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final  TopObserverModule myobservationModel = homeList.get(position);
        holder.myobservationModel = myobservationModel;
        holder.name.setText(holder.myobservationModel.getName());
        holder.company.setText(holder.myobservationModel.getCompanyName());
        holder.count.setText("Total Observations : "+holder.myobservationModel.getTotalObservations());

        Glide.with(context)
                .load(holder.myobservationModel.getImage()) // image url
                // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                .error(R.drawable.csafenet)  // any image in case of error
                // .override(200, 200); // resizing
                //.apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {

        CircleImageView circleImageView;
        private TopObserverModule myobservationModel;
        public TextView  company, name, count;
        public MyViewHolder(View view) {
            super(view);
            //userId = (TextView) view.findViewById(R.id.userid);
            circleImageView=(CircleImageView)view.findViewById(R.id.imageTopObserver);

            name = (TextView) view.findViewById(R.id.text_name);
            company = (TextView) view.findViewById(R.id.text_company);
            count=(TextView)view.findViewById(R.id.text_total_observations);

        }
    }


}