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

import com.a2r.a2rmaster.Pojo.ItemList;
import com.a2r.a2rmaster.Pojo.Ordersummery;
import com.a2r.a2rmaster.R;

import java.util.ArrayList;

public class OrderSummeryAdapter extends BaseAdapter {
    Context _conext;
    ArrayList<Ordersummery> myList;
    Holder holder;
    public OrderSummeryAdapter(FragmentActivity activity, ArrayList<Ordersummery> iList) {
        this._conext = activity;
        this.myList = iList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Holder{
        TextView bill_date,bill_no,cust_name,total,discount;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Ordersummery _pos=myList.get(i);
        holder=new Holder();

        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) _conext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.summ_list, viewGroup, false);
            holder.bill_date=(TextView)view.findViewById(R.id.bill_date);
            holder.bill_no=(TextView)view.findViewById(R.id.bill_no);
            holder.cust_name=(TextView)view.findViewById(R.id.cust_name);
            holder.total=(TextView) view.findViewById(R.id.total);
            holder.discount=(TextView) view.findViewById(R.id.discount);

            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
            holder.bill_date.setTag(holder);
            holder.bill_no.setTag(holder);
            holder.cust_name.setTag(holder);
            holder.total.setTag(holder);
            holder.discount.setTag(holder);
        }

        holder.bill_no.setText("Bill No : "+_pos.getBill_no());
        holder.bill_date.setText(_pos.getCreated());
        holder.cust_name.setText(_pos.getName()+" ("+_pos.getMobile()+")");
        holder.total.setText(" Total Amount :Rs."+_pos.getTotal_amount());
        holder.discount.setText("Discount : Rs."+_pos.getDiscount()+" ("+_pos.getDiscount_reason()+")");
      //  holder.it_name.setText(_pos.getname());

        return view;
    }
}
