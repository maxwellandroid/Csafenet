package com.maxwell.csafenet.adapter;

import android.app.Dialog;
import android.content.Context;
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
import com.maxwell.csafenet.activity.ViewObservationsActivity;
import com.maxwell.csafenet.model.AwardDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class AcheivementsAdapter extends RecyclerView.Adapter<AcheivementsAdapter.ViewHolder>{

    List<AwardDetails> awardDetailsList;
    Context context;

    public AcheivementsAdapter(Context mcontext, List<AwardDetails> awardDetailsList){
        this.context=mcontext;
        this.awardDetailsList =awardDetailsList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem= layoutInflater.inflate(R.layout.layout_awards_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final AwardDetails peoplesModel=awardDetailsList.get(i);
      //  viewHolder.tv_name.setText(peoplesModel.getName());
        /*if(blogsModel.getContent().length()>50){
            String content=blogsModel.getContent().substring(0,50);
            viewHolder.tv_proteins.setText(content+"....Continue Reading");
        }*/
        String str =peoplesModel.getDate();
// parse the String "29/07/2013" to a java.util.Date object

        try {
            Date  date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
            viewHolder.tv_date.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            viewHolder.tv_date.setText(peoplesModel.getDate());
        }
// format the java.util.Date object to the desired format---

        viewHolder.tv_description.setText(peoplesModel.getDescription());
        Glide.with(context)
                .load(peoplesModel.getImageUrl()) // image url
                 .placeholder(R.drawable.csafenet) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                // .override(200, 200); // resizing
               // .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showRadioButtonDialog(peoplesModel.getImageUrl());
                // custom dialog
                final Dialog dialog = new Dialog(view.getContext());
                // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_big_imageview);

                ImageView imageView=(ImageView)dialog.findViewById(R.id.image_big);

                Glide.with(context)
                        .load(peoplesModel.getImageUrl()) // image url
                        // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                        //.error(R.drawable.imagenotfound)  // any image in case of error
                        // .override(200, 200); // resizing
                        //.apply(new RequestOptions().override(100, 100))
                        .into(imageView);

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return awardDetailsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tv_date,tv_description;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView=(ImageView)itemView.findViewById(R.id.image_award);
            this.tv_date=(TextView)itemView.findViewById(R.id.text_date);
            this.tv_description=(TextView)itemView.findViewById(R.id.text_description);


        }
    }
    private void showRadioButtonDialog(String url) {


    }


}
