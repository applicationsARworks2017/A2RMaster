package com.a2r.a2rmaster.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.a2r.a2rmaster.R;

/**
 * Created by mobileapplication on 3/26/18.
 */

public class UsersListShopWise extends AppCompatActivity {

    FloatingActionButton add_rest;
    RelativeLayout framelayout;
    Toolbar user_tool;
    LinearLayout user_tool_back;
    FloatingActionButton add_user;
    RelativeLayout manager, cashier, waiter, cook;
    public static String user_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_users);


        manager = (RelativeLayout) findViewById(R.id.manager);
        cashier = (RelativeLayout) findViewById(R.id.cashier);
        waiter = (RelativeLayout) findViewById(R.id.dining);
        cook = (RelativeLayout) findViewById(R.id.kitchen);
        user_tool = (Toolbar) findViewById(R.id.user_tool);
        user_tool_back = (LinearLayout) findViewById(R.id.user_tool_back);
        framelayout = (RelativeLayout) findViewById(R.id.linn);
        add_rest=(FloatingActionButton)findViewById(R.id.add_rest) ;

        add_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UsersListShopWise.this, AddUser.class);
                startActivity(intent);
            }
        });
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 user_type="Manager";
                Intent i = new Intent(UsersListShopWise.this, UsersActivity.class);
                startActivity(i);
            }
        });
        cashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 user_type="Cashier";

                Intent i = new Intent(UsersListShopWise.this, UsersActivity.class);
                startActivity(i);
            }
        });
        waiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 user_type="Waiter";

                Intent i = new Intent(UsersListShopWise.this, UsersActivity.class);
                startActivity(i);
            }
        });
        cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 user_type="Cook";

                Intent i = new Intent(UsersListShopWise.this, UsersActivity.class);
                startActivity(i);
            }
        });

        user_tool_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

