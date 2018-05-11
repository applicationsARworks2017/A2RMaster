package com.a2r.a2rmaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a2r.a2rmaster.Activity.RestruntEdit;
import com.a2r.a2rmaster.Pojo.Restaurants;
import com.a2r.a2rmaster.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Amaresh on 2/2/18.
 */

public class RestaurantAdapter extends BaseAdapter{
    private Context _context;
    ArrayList<Restaurants> myList;
    Holder holder,holder1;

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
        TextView tv_rest_name,details,phone;
        ImageView rest_edit,iv_logo;

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
            holder.phone=(TextView) view.findViewById(R.id.phone);
            holder.rest_edit=(ImageView)view.findViewById(R.id.rest_edit);
            holder.iv_logo=(ImageView)view.findViewById(R.id.iv_logo);
            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
            holder.rest_edit.setTag(holder);
            holder.iv_logo.setTag(holder);
        }
        holder.tv_rest_name.setTag(i);
        holder.details.setTag(i);
        holder.phone.setTag(i);
        holder.tv_rest_name.setText(_pos.getTitle());
        String mobile=_pos.getMobile_no();
        if(mobile==null){
            mobile="Not Given";
        }
        String photo1=_pos.getLogo();
        if(photo1=="" || photo1==null || photo1.isEmpty()) {
        }
        else{
            Picasso.with(_context)
                    .load(photo1)
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error).into(holder.iv_logo);
        }        holder.phone.setText("Phone :"+mobile.trim().toString());
        holder.details.setText("Address :"+_pos.getAddress().trim().toString());


        holder.rest_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder1=(Holder) view.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                builder.setMessage("Do you want to edit the Details?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String res_title=_pos.getTitle();
                                String res_mobile=_pos.getMobile_no();
                                if(res_mobile==null){
                                    res_mobile="Not Given";
                                }
                                String res_address=_pos.getAddress();
                                String res_gst=_pos.getGst();
                                String res_addedby=_pos.getAdded_by();
                                String res_edit_id=_pos.getId();
                                String photoo=_pos.getLogo();
                                Intent i=new Intent(_context,RestruntEdit.class);
                                i.putExtra("title",res_title);
                                i.putExtra("mobile",res_mobile);
                                i.putExtra("address",res_address);
                                i.putExtra("gst",res_gst);
                                i.putExtra("addedby",res_addedby);
                                i.putExtra("editid",res_edit_id);
                                i.putExtra("photo",photoo);
                                _context.startActivity(i);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return view;
    }
}
