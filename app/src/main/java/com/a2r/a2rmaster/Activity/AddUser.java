package com.a2r.a2rmaster.Activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.a2r.a2rmaster.Fragment.UsersFragment;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddUser extends AppCompatActivity {

    String user_id;
    Spinner spin_usertype;
    ImageView iv_submit;
    EditText et_name,et_user_name,et_password, et_phone, et_email;
    RelativeLayout addshop_rel;
    List<String> VisitorsType;
    String spin_list,spin_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        user_id = AddUser.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);

        iv_submit = (ImageView) findViewById(R.id.iv_submit);
        et_name = (EditText) findViewById(R.id.et_name);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        addshop_rel=(RelativeLayout)findViewById(R.id.addshop_rel);
        spin_usertype=(Spinner)findViewById(R.id.spin_usertype);
        VisitorsType = new ArrayList<String>();
        VisitorsType.add("-- Select User Type --");
        VisitorsType.add("Admin");
        VisitorsType.add("Cashier");
        VisitorsType.add("Dinning");
        VisitorsType.add("Kitchen");
        ArrayAdapter<String> adapte_visitors = new ArrayAdapter<String>(AddUser.this, android.R.layout.simple_spinner_item, VisitorsType);
        adapte_visitors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spin_usertype.setAdapter(adapte_visitors);
        spin_usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spin_list = spin_usertype.getSelectedItem().toString();
                if(spin_list.contentEquals("Admin")){
                    spin_value="3";
                }
                else if (spin_list.contentEquals("Cashier")){
                    spin_value="5";
                }else if (spin_list.contentEquals("Dinning")){
                    spin_value="4";
                }else if (spin_list.contentEquals("Kitchen")){
                    spin_value="6";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkValidation();

            }
        });
    }

    private void checkValidation() {

        if(et_name.getText().toString().trim().length()<=0){
            showSnackBar("Enter Name");
        }
        else if(et_user_name.getText().toString().trim().length()<0){
            showSnackBar("Enter Username");
        }
        else if(et_password.getText().toString().trim().length()<0){
            showSnackBar("Enter password");
        }
        else if(et_phone.getText().toString().trim().length()<10){
            showSnackBar("Enter Valid phone number");
        } else if(et_email.getText().toString().trim().length()<0){
            showSnackBar("Enter E-mail");
        }else if(spin_list.toString().trim().length()<0){
            showSnackBar("Enter User Type");
        }
        else{
            adduser();
        }
    }

    private void adduser() {
        String name=et_name.getText().toString().trim();
        String user_name=et_user_name.getText().toString().trim();
        String pass=et_password.getText().toString().trim();
        String phone=et_phone.getText().toString().trim();
        String email=et_email.getText().toString().trim();
        if(CheckInternet.getNetworkConnectivityStatus(AddUser.this)){
            //  sendDataroserver(name,phone,address,gst);

            new AddUserdata().execute(name,user_name,pass, phone, email, spin_value, UsersFragment.rest_id,"Y");
        }
        else {
            showSnackBar("No Internet");
        }
    }

    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(addshop_rel, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#ae0a11"));

        snackbar.show();
    }


    private class AddUserdata extends AsyncTask<String,Void,Void> {
        private static final String TAG = "AddShopAsyntask";
        private ProgressDialog progressDialog = null;
        int server_status;
        String server_message;
        String link = Constants.BASEURL+Constants.ADDSHOPS;
        String charset = "UTF-8";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(AddUser.this, "Adding", "Please wait...");
            }
            // onPreExecuteTask();
        }
        @Override
        protected Void doInBackground(String... params) {

            try {

                InputStream in = null;
                int resCode = -1;

                String link =Constants.BASEURL+Constants.ADDUSER;
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setAllowUserInteraction(false);
                conn.setInstanceFollowRedirects(true);
                conn.setRequestMethod("POST");

                Uri.Builder builder = null;
                builder = new Uri.Builder()
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("username", params[1])
                        .appendQueryParameter("password", params[2])
                        .appendQueryParameter("mobile", params[3])
                        .appendQueryParameter("emai", params[4])
                        .appendQueryParameter("user_type_id",params[5])
                        .appendQueryParameter("shop_id", params[6])
                        .appendQueryParameter("is_active", params[7])
                        .appendQueryParameter("save_from", "mobile");

                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    in = conn.getInputStream();
                }
                if (in == null) {
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String res = "", data = "";

                while ((data = reader.readLine()) != null) {
                    res += data + "\n";
                }

                Log.i(TAG, "res : " + res);

                if (res != null && res.length() > 0) {
                    JSONObject ress = new JSONObject(res.trim());
                    JSONObject res1=ress.getJSONObject("res");
                    server_status = res1.optInt("status");
                    if(server_status==1){
                        server_message="Added Successfully";
                    }
                    else {
                        server_message = "Unsuccessfull";
                    }

                }
            }
            catch (Exception e){
                server_message = "Connectivity issues,please try again";
                Log.e( TAG, e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void user) {
            super.onPostExecute(user);
            progressDialog.cancel();
            if (server_status == 1) {
                AddUser.this.finish();
                Toast.makeText(AddUser.this,server_message,Toast.LENGTH_SHORT).show();
            }
            else{
                showSnackBar(server_message);
            }
        }
    }
}