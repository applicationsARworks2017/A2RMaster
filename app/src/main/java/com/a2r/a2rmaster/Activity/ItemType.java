package com.a2r.a2rmaster.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Adapter.ItemAdapter;
import com.a2r.a2rmaster.Fragment.ProductFragment;
import com.a2r.a2rmaster.Pojo.ItemList;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.APIManager;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mobileapplication on 2/5/18.
 */

public class ItemType extends AppCompatActivity {

    FloatingActionButton add_rest;
    TextView no_rest;
    SwipeRefreshLayout rest_swipe;
    ListView rest_list;
    String user_id,product_category_id;
    ArrayList<ItemList> iList;
    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_type);

        user_id = getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        iList=new ArrayList<>();
        no_rest=(TextView)findViewById(R.id.no_rest);
        rest_swipe=(SwipeRefreshLayout)findViewById(R.id.rest_swipe);
        rest_list=(ListView)findViewById(R.id.pr_type_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            product_category_id = extras.getString("ID");
        }
        getItemList();
        rest_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rest_swipe.setRefreshing(false);
                getItemList();
            }
        });

    }
    private void getItemList() {
        if(CheckInternet.getNetworkConnectivityStatus(this)){
            calltoAPI(product_category_id,"");
        }
        else{
            rest_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);
            Toast.makeText(this,"No internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void calltoAPI(String added_by, String is_approved) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading List. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("shop_id", ProductFragment.rest_id);
            jsonObject.put("product_category_id", product_category_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.PRODUCTSCATEGORY,"products",jsonObject, ItemList.class,this,
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        iList=(ArrayList<ItemList>) resultObj;
                        itemAdapter = new ItemAdapter(ItemType.this,iList );
                        rest_list.setAdapter(itemAdapter);
                        pd.cancel();
                    }

                    @Override
                    public void onError(String error) {
                        rest_swipe.setVisibility(View.GONE);
                        no_rest.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });
    }

}
