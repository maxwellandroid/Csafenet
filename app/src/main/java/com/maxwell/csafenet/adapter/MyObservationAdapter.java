package com.maxwell.csafenet.adapter;

import android.content.Context;
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

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.model.ManageObservationModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyObservationAdapter extends RecyclerView.Adapter<MyObservationAdapter.MyViewHolder> {

    private List<ManageObservationModel> homeList;
    Context context;


    public MyObservationAdapter setUsers(Context context, List<ManageObservationModel> user) {
        this.homeList = user;
        this.context = context;
        return this;
    }
    /*public HomefragmentAdapter(List<Home> homeList) {
        this.homeList = homeList;
    }*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myobsevationrow, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ManageObservationModel myobservationModel = homeList.get(position);
        holder.myobservationModel = myobservationModel;
        holder.sNo.setText(holder.myobservationModel.getRegcode());
        holder.dateOf.setText(holder.myobservationModel.getReg_date());
        holder.name.setText(holder.myobservationModel.getUser_name());
        holder.company.setText(holder.myobservationModel.getCompany_name());
        holder.type.setText(holder.myobservationModel.getDesc_type());
        holder.actionBy.setText(holder.myobservationModel.getAction_by());

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView statename, cityName, flex, amount, titile;
        CircleImageView circleImageView;
        private LinearLayout userFullDetail;
        private String tweedId;
        private CircleImageView userImg;
        private CardView member;
        private LinearLayout viewUser, imagecircle;
        private ManageObservationModel myobservationModel;
        private ImageView imageView;
        Animation rotate, downtoBottom, right;
        Button buttonDetail;

        public TextView note;
        public TextView dot, title;
        ImageView minusadult, minusChild;
        public TextView timestamp, userId, sNo, dateOf, company, name, actionBy, type,adultAmount,
                adultToalAmout,childToalAmount;
        public MyViewHolder(View view) {
            super(view);
            //userId = (TextView) view.findViewById(R.id.userid);
            sNo = (TextView) view.findViewById(R.id.s_no);
            dateOf = (TextView) view.findViewById(R.id.dateofinput);
            name = (TextView) view.findViewById(R.id.name);
            company = (TextView) view.findViewById(R.id.company);
            actionBy = (TextView) view.findViewById(R.id.actioby);
            type = (TextView) view.findViewById(R.id.type);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
//                case R.id.like_button:
//                    updateLikes(tweedId);
//                    likePost.setEnabled(false);
//                    break;


            }

        }
    }
}