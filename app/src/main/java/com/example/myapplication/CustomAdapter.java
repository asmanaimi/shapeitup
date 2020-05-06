package com.example.myapplication;

import android.app.Activity;
import android.content.Context;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CustomAdapter extends PagerAdapter {

    Context context;
    int[] imageId = {R.drawable.tipa, R.drawable.tipsleep1, R.drawable.tipd,R.drawable.tipm,R.drawable.tipn,R.drawable.tipyoga1};

    public CustomAdapter(Context context){
        this.context = context;

    }

    public static int cposition=0;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub

        position=cposition;
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        Log.d("ffffffffffffffffffff",""+cposition);
        View viewItem = inflater.inflate(R.layout.imageitem, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
        imageView.setImageResource(imageId[cposition%imageId.length]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        cposition++;

        Animation rotateimage = AnimationUtils.loadAnimation(context, R.anim.slidingimages);
        imageView.startAnimation(rotateimage);

        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub

        return view == ((View)object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }

}
