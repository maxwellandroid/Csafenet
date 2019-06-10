package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.activity.BulletDetailViewActivity;
import com.maxwell.csafenet.activity.QuestionDetailsActivity;
import com.maxwell.csafenet.model.BulletinModules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BulletinAdapter extends BaseAdapter {

    private Context context;

    private List<BulletinModules> bulletinModulesList;

    public BulletinAdapter(Context context, List<BulletinModules> bulletinModulesList){

        this.context=context;
        this.bulletinModulesList =bulletinModulesList;



    }
    @Override
    public int getCount() {
        return bulletinModulesList.size();
    }

    @Override
    public Object getItem(int position) {
        return bulletinModulesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_bulletin_row, parent, false);
        }

        TextView tv_date=(TextView)convertView.findViewById(R.id.text_date);
        TextView tv_title=(TextView)convertView.findViewById(R.id.text_title);
        TextView tv_issuer=(TextView)convertView.findViewById(R.id.text_issuer);

        String str =bulletinModulesList.get(position).getIssuedate();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
            tv_date.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            tv_date.setText(bulletinModulesList.get(position).getIssuedate());
        }
        //tv_project.setText(bulletinModulesList.get(position).getIssuedate());
        tv_title.setText(bulletinModulesList.get(position).getTitle());
        tv_issuer.setText(bulletinModulesList.get(position).getIssuer());

        tv_title.setTag(position);

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int viewPosition=(int)view.getTag();
                Intent i=new Intent(context, BulletDetailViewActivity.class);
                i.putExtra("BulletInDetail",  bulletinModulesList.get(viewPosition));
                /*i.putExtra("Question", bulletinModulesList.get(viewPosition).getTitle());
                i.putExtra("QuestionId", bulletinModulesList.get(viewPosition).getId());
                i.putExtra("FieldId", bulletinModulesList.get(viewPosition).getField_id());*/
                context.startActivity(i);

            }
        });



       // CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.checkbox);
       // checkBox.setText(bulletinModulesList.get(position).getRiskCategory());

      //  ImageView iv_menu_image=(ImageView)convertView.findViewById(R.id.image_offer);


        return convertView;
    }

}
