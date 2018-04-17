package com.a2r.a2rmaster.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.a2r.a2rmaster.Pojo.User;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.APIManager;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button lin_signin;
    EditText u_mobile,user_password;
    RelativeLayout login_rel;
    List <User> list;
    String  fcm_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fcm_id = LoginActivity.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.FCM_ID, null);

        //list=new ArrayList<>();
        lin_signin=(Button) findViewById(R.id.lin_signin);
        login_rel=(RelativeLayout)findViewById(R.id.login_rel);
        u_mobile=(EditText)findViewById(R.id.et_username);
        user_password=(EditText)findViewById(R.id.et_password);
        lin_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile=u_mobile.getText().toString().trim();
                String password=user_password.getText().toString().trim();

                if(u_mobile.getText().toString().trim().length()<=0){
                    showSnackBar("Invalid Mobile Number");
                }
                else if(user_password.getText().toString().trim().length()<=0){
                    showSnackBar("Invalid password");
                }
                else{
                    if(CheckInternet.getNetworkConnectivityStatus(LoginActivity.this)){
                        calltoAPI(mobile, password);
                    }
                    else{
                        showSnackBar("No Internet");
                    }
                }
            }
        });
        /*lin_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,Home.class);
                startActivity(intent);
            }
        });*/
    }

    private void calltoAPI(String mobl, String password) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Validating. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mobile", mobl);
            jsonObject.put("password", password);
            jsonObject.put("user_type_id", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIManager().PostAPI(Constants.BASEURL + Constants.LOGIN, "users",jsonObject, User.class, this, new APIManager.APIManagerInterface() {
            @Override
            public void onSuccess(Object resultObj) {
                pd.dismiss();
                User user= (User) resultObj;
                String name=user.getName();
                String phone=user.getMobile();
                String user_id=user.getId();
                String user_email=user.getEmail();

                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0); // 0 - for private mode
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.USER_ID, user_id);
                editor.putString(Constants.USER_NAME, name);
                editor.putString(Constants.USER_PHONE, phone);
                editor.putString(Constants.USER_MAIL, user_email);
                editor.commit();

                Intent i=new Intent(LoginActivity.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);

            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                showSnackBar(error);
            }
        });
    }

    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(login_rel, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#ae0a11"));

        snackbar.show();
    }
}
