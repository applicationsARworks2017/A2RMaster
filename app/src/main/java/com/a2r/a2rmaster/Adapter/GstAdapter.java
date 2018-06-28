package com.a2r.a2rmaster.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a2r.a2rmaster.Activity.GstEdit;
import com.a2r.a2rmaster.Activity.RestruntGST;
import com.a2r.a2rmaster.Pojo.GST;
import com.a2r.a2rmaster.R;

import java.util.ArrayList;

public class GstAdapter extends BaseAdapter {

    ArrayList<GST> glist_;
    Context context;
    Holder holder,holder1;
    String shop_name,gst_number;


    public GstAdapter(RestruntGST restruntGST, ArrayList<GST> gList, String shop_name, String res_gst) {
        this.glist_=gList;
        this.context=restruntGST;
        this.shop_name=shop_name;
        this.gst_number=res_gst;
    }

    @Override
    public int getCount() {
        return glist_.size();
    }

    @Override
    public Object getItem(int i) {
        return glist_.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Holder{
        TextView tv_rest_name,tv_gst_number,tv_tax_name,gst_value,effect_from;
        ImageView edit_layout;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final GST _pos=glist_.get(i);
        holder=new Holder();
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.gst_list, viewGroup, false);
            holder.tv_rest_name=(TextView)view.findViewById(R.id.tv_rest_name);
            holder.tv_gst_number=(TextView) view.findViewById(R.id.gst_number);
            holder.tv_tax_name=(TextView) view.findViewById(R.id.tax_name);
            holder.gst_value=(TextView) view.findViewById(R.id.gst_value);
            holder.effect_from=(TextView) view.findViewById(R.id.effect_from);
            holder.edit_layout=(ImageView) view.findViewById(R.id.edit_layout);

            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();

        }
        holder.tv_rest_name.setTag(i);
        holder.tv_gst_number.setTag(i);
        holder.tv_tax_name.setTag(i);
        holder.gst_value.setTag(i);
        holder.effect_from.setTag(i);

        holder.tv_rest_name.setText("Restraunt Name:"+" "+shop_name);
        holder.tv_gst_number.setText("Gst_Number:"+" "+gst_number);
        holder.tv_tax_name.setText("Tax_Name:"+" "+_pos.getTax_name());
        holder.gst_value.setText("Tax_Value:"+" "+_pos.getTax_value());
        holder.effect_from.setText("Effect_from:"+" "+_pos.getEffect_from());

        holder.edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder1=(Holder) view.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to edit the Details?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String taxn=_pos.getTax_name();
                                String shop_id=_pos.getShop_id();
                                String res_edit_id=_pos.getId();
                                String Tax_Value=_pos.getTax_value();
                                String effect_from=_pos.getEffect_from();
                                Intent i=new Intent(context,GstEdit.class);
                                i.putExtra("title",shop_name);
                                i.putExtra("gst_number",gst_number);
                                i.putExtra("tax_name",taxn);
                                i.putExtra("gst_edit_id",res_edit_id);
                                i.putExtra("shop_id",shop_id);
                                i.putExtra("Tax_Value",Tax_Value);
                                i.putExtra("effect_from",effect_from);
                                context.startActivity(i);

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