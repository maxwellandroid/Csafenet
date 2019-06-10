package com.maxwell.csafenet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.activity.OnlineTestActivity;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.csafebanner1, R.drawable.csafebanner2, R.drawable.csafebanner3,R.drawable.startnowbanner};
    List<String> imageList;

    public ViewPagerAdapter(Context context, List<String> imageList ) {

        this.context = context;
        this.imageList=imageList;
    }

    @Override
    public int getCount() {
        return imageList.size()+1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_image_slide, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        //imageView.setImageResource(images[position]);

        if(position<=imageList.size()-1){
            Glide.with(context)
                    .load(imageList.get(position)) // image url
                    // .placeholder(R.drawable.placeholder) // any placeholder to load at start
                    //.error(R.drawable.imagenotfound)  // any image in case of error
                    // .override(200, 200); // resizing
                    //.apply(new RequestOptions().placeholder(R.drawable.loading))
                    .into(imageView);

        }else {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.startnowbanner));
           // imageView.setImageResource(context.getResources().getDrawable(R.drawable.startnowbanner));
        }



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==imageList.size()){

                    Intent i=new Intent(context, OnlineTestActivity.class);
                    context.startActivity(i);

                }
            }
        });




        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

}
