package com.a2r.a2rmaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a2r.a2rmaster.Activity.ItemType;
import com.a2r.a2rmaster.Pojo.CategoryList;
import com.a2r.a2rmaster.R;

import java.util.ArrayList;

/**
 * Created by mobileapplication on 2/5/18.
 */

public class PCategoryAdapter extends BaseAdapter {
    private Context _context;
    ArrayList<CategoryList> myList;
    Holder holder,holder1;
    public PCategoryAdapter(FragmentActivity activity, ArrayList<CategoryList> cList) {
        this._context=activity;
        this.myList=cList;
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
        TextView tv_category_name,details;
        ImageView sub_category;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final CategoryList _pos=myList.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) _context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.category_list, viewGroup, false);
            holder.tv_category_name=(TextView)view.findViewById(R.id.tv_category_name);
            holder.sub_category=(ImageView) view.findViewById(R.id.sub_category);
            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
            holder.tv_category_name.setTag(holder);
            holder.sub_category.setTag(holder);
        }
        holder.tv_category_name.setText(_pos.getTitle());
        holder.sub_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder1=(Holder) view.getTag();

                Intent i=new Intent(_context,ItemType.class);
               _context.startActivity(i);
            }
        });
        return view;
    }
}
