package com.a2r.a2rmaster.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Adapter.PCategoryAdapter;
import com.a2r.a2rmaster.Pojo.CategoryList;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.APIManager;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChooseProduct extends AppCompatActivity {
    TextView no_rest;
    SwipeRefreshLayout rest_swipe;
    ListView rest_list;
    String user_id;
    ArrayList<CategoryList> cList;
    PCategoryAdapter categoryAdapter;
    Toolbar ChooseProduct_tool;
    LinearLayout ChooseProduct_tool_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);
        user_id = ChooseProduct.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        cList=new ArrayList<>();
        no_rest=(TextView)findViewById(R.id.no_rest);
        ChooseProduct_tool=(Toolbar)findViewById(R.id.ChooseProduct_tool);
        ChooseProduct_tool_back=(LinearLayout)findViewById(R.id.ChooseProduct_tool_back);
        rest_swipe=(SwipeRefreshLayout)findViewById(R.id.rest_swipe);
        rest_list=(ListView)findViewById(R.id.cat_list);
        getCategoryList();
        rest_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rest_swipe.setRefreshing(false);
                getCategoryList();
            }
        });
        rest_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryList categoryList=(CategoryList)rest_list.getItemAtPosition(i);
                Intent intent=new Intent(ChooseProduct.this,ItemType.class);
                intent.putExtra("ID",categoryList.getId());
                startActivity(intent);
            }
        });
        ChooseProduct_tool_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getCategoryList() {
        if(CheckInternet.getNetworkConnectivityStatus(ChooseProduct.this)){
            calltoAPI("","");
        }
        else{
            rest_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);
            Toast.makeText(ChooseProduct.this,"No internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void calltoAPI(String added_by, String is_approved) {
        final ProgressDialog pd = new ProgressDialog(ChooseProduct.this);
        pd.setMessage("Loading List. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.PRODUCTCATEGORY,"productCategories",jsonObject, CategoryList.class,ChooseProduct.this,
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        cList=(ArrayList<CategoryList>) resultObj;
                        categoryAdapter = new PCategoryAdapter(ChooseProduct.this,cList );
                        rest_list.setAdapter(categoryAdapter);
                        pd.cancel();
                    }

                    @Override
                    public void onError(String error) {
                        rest_swipe.setVisibility(View.GONE);
                        no_rest.setVisibility(View.VISIBLE);
                        pd.dismiss();
                    }
                });
    }
}
