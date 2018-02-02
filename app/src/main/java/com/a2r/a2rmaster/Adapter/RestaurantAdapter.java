package com.a2r.a2rmaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a2r.a2rmaster.Pojo.Restaurants;
import com.a2r.a2rmaster.R;

import java.util.ArrayList;

/**
 * Created by Amaresh on 2/2/18.
 */

public class RestaurantAdapter extends BaseAdapter{
    private Context _context;
    ArrayList<Restaurants> myList;
    Holder holder;

    public RestaurantAdapter(FragmentActivity activity, ArrayList<Restaurants> rList) {
    this._context=activity;
    this.myList=rList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return myList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView tv_rest_name,details;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Restaurants _pos=myList.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) _context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.rest_list, viewGroup, false);
            holder.tv_rest_name=(TextView)view.findViewById(R.id.tv_rest_name);
            holder.details=(TextView) view.findViewById(R.id.details);
            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
        }
        holder.tv_rest_name.setTag(i);
        holder.details.setTag(i);
        holder.tv_rest_name.setText(_pos.getTitle());
        holder.details.setText("Phone :"+_pos.getMobile_no()+","+"Address :"+_pos.getAddress());
        return view;
    }
}
