package com.a2r.a2rmaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a2r.a2rmaster.Activity.ItemType;
import com.a2r.a2rmaster.Pojo.ItemList;
import com.a2r.a2rmaster.R;

import java.util.ArrayList;

/**
 * Created by mobileapplication on 2/5/18.
 */

public class ItemAdapter extends BaseAdapter{
    private Context _context;
    ArrayList<ItemList> myList;
    Holder holder,holder1;

    public ItemAdapter(ItemType activity, ArrayList<ItemList> iList) {
        this._context=activity;
        this.myList=iList;
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
        TextView it_name,cat_cost,cat_name;
        ImageView sub_category;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ItemList _pos=myList.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) _context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.item_list, viewGroup, false);
            holder.it_name=(TextView)view.findViewById(R.id.it_name);
            holder.cat_cost=(TextView)view.findViewById(R.id.cat_cost);
            holder.cat_name=(TextView)view.findViewById(R.id.cat_name);
            holder.sub_category=(ImageView)view.findViewById(R.id.idntyf_v_n);

            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
            holder.it_name.setTag(holder);
            holder.sub_category.setTag(holder);
            holder.cat_cost.setTag(holder);
            holder.cat_name.setTag(holder);
        }
        holder.it_name.setText(_pos.getTitle());
        holder.cat_name.setText(_pos.getProduct_category_id());
        holder.cat_cost.setText(_pos.getPrice());
        String product_id=_pos.getProduct_type_id();
        if(product_id.contentEquals("1")){
         holder.sub_category.setImageResource(R.drawable.veg);
        }
        else{
            holder.sub_category.setImageResource(R.drawable.nonveg);

        }

        return view;
    }
}
