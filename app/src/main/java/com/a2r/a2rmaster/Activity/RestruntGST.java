package com.a2r.a2rmaster.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Adapter.GstAdapter;
import com.a2r.a2rmaster.Pojo.GST;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.APIManager;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestruntGST extends AppCompatActivity {
    ListView gst_list;
    SwipeRefreshLayout gst_swipe;
    RelativeLayout add_gst_layout;
    TextView no_rest;
   public static String shop_id,res_gst,shop_name;
    ArrayList<GST> gList;
    GstAdapter gstAdapter;
    RelativeLayout add_gst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_gst);

        gst_list = (ListView) findViewById(R.id.gst_list);
        gst_swipe = (SwipeRefreshLayout) findViewById(R.id.gst_swipe);
        add_gst_layout = (RelativeLayout) findViewById(R.id.add_gst);
        no_rest=(TextView)findViewById(R.id.no_rest);
        add_gst=(RelativeLayout)findViewById(R.id.add_gst);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            res_gst = extras.getString("gst");
            shop_id = extras.getString("shop_id");
            shop_name = extras.getString("shop_name");

        }
        gst_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gst_swipe.setRefreshing(false);
                getGst();

            }
        });
        add_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RestruntGST.this,AddGst.class);
                startActivity(i);
            }
        });
        getGst();

    }

    private void getGst() {
        if (CheckInternet.getNetworkConnectivityStatus(this)) {
            calltoAPI(shop_id);
        } else {
            gst_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void calltoAPI(String shop) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading List. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("shop_id", shop);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.GSTLIST,"taxs",jsonObject, GST.class,this,
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        gList=(ArrayList<GST>) resultObj;
                        gstAdapter = new GstAdapter( RestruntGST.this,gList,shop_name,res_gst );
                        gst_list.setAdapter(gstAdapter);
                        pd.cancel();
                    }

                    @Override
                    public void onError(String error) {
                        gst_swipe.setVisibility(View.GONE);
                        no_rest.setVisibility(View.VISIBLE);
                        pd.dismiss();
                    }
                });
    }
}
