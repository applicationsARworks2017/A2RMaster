package com.a2r.a2rmaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a2r.a2rmaster.Activity.UsersActivity;
import com.a2r.a2rmaster.Pojo.USERList;
import com.a2r.a2rmaster.R;

import java.util.ArrayList;

/**
 * Created by mobileapplication on 2/12/18.
 */

public class UserAdapter extends BaseAdapter{
    private Context _context;
    ArrayList<USERList> myList;
    Holder holder,holder1;

    public UserAdapter(UsersActivity activity, ArrayList<USERList> userList) {
      this._context=activity;
      this.myList=userList;
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
       TextView tv_rest_name,tv_user_mobile,textsymbol,tv_user_shop;
       RelativeLayout name_image;


   }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final USERList _pos=myList.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) _context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.user_list, viewGroup, false);
            holder.tv_rest_name=(TextView)view.findViewById(R.id.tv_user_name);
            holder.tv_user_mobile=(TextView) view.findViewById(R.id.tv_user_mobile);
            holder.textsymbol=(TextView) view.findViewById(R.id.textsymbol);
            holder.tv_user_shop=(TextView) view.findViewById(R.id.tv_user_shop);
            holder.name_image=(RelativeLayout) view.findViewById(R.id.name_image);
            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
            holder.name_image.setTag(holder);
        }
        holder.tv_rest_name.setTag(i);
        holder.textsymbol.setTag(i);
        holder.tv_user_mobile.setTag(i);
        holder.tv_user_shop.setTag(i);

        holder.tv_rest_name.setText(_pos.getName());
        holder.tv_user_mobile.setText(_pos.getMobile());
        holder.tv_user_shop.setText(_pos.getShop_user_type_title());
        holder.textsymbol.setText(String.valueOf(_pos.getName().charAt(0)).toUpperCase());
        return view;
    }
}
