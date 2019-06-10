package com.maxwell.csafenet.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.model.DropDownDetails;
import java.util.List;

public class CustomGridAdapter extends BaseAdapter {

    private Context context;

    private List<DropDownDetails> dropDownList;

    public CustomGridAdapter(Context context, List<DropDownDetails> dropDownDetailsList){

        this.context=context;
        this.dropDownList=dropDownDetailsList;



    }
    @Override
    public int getCount() {
        return dropDownList.size();
    }

    @Override
    public Object getItem(int position) {
        return dropDownList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_spinner_item, parent, false);
        }

        CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.checkbox);
        checkBox.setText(dropDownList.get(position).getRiskCategory());

      //  ImageView iv_menu_image=(ImageView)convertView.findViewById(R.id.image_offer);


        return convertView;
    }

}
