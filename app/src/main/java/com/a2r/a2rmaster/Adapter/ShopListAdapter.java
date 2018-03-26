package com.a2r.a2rmaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a2r.a2rmaster.Pojo.ShopList;
import com.a2r.a2rmaster.R;

import java.util.ArrayList;

/**
 * Created by Amaresh on 2/9/18.
 */

public class ShopListAdapter extends BaseAdapter {
    Context _context;
    ArrayList<ShopList> mylist;
    Holder holder;

    public ShopListAdapter(FragmentActivity activity, ArrayList<ShopList> rList) {
        this._context=activity;
        this.mylist=rList;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return mylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Holder{
        TextView tv_rest_name,details,textsymbol;
        RelativeLayout name_image;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ShopList _pos=mylist.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) _context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.shop_list, viewGroup, false);
            holder.tv_rest_name=(TextView)view.findViewById(R.id.tv_rest_name);
            holder.details=(TextView) view.findViewById(R.id.details);
            holder.textsymbol=(TextView) view.findViewById(R.id.textsymbol);
            holder.name_image=(RelativeLayout) view.findViewById(R.id.name_image);
            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
            holder.name_image.setTag(holder);
        }
        holder.tv_rest_name.setTag(i);
        holder.details.setTag(i);
        holder.textsymbol.setTag(i);

        holder.tv_rest_name.setText(_pos.getTitle());
        holder.details.setText(_pos.getAddress());
        holder.textsymbol.setText(String.valueOf(_pos.getTitle().charAt(0)).toUpperCase());
        return view;
    }
}
