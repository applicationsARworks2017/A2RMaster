package com.a2r.a2rmaster.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddGst extends AppCompatActivity {

    EditText et_tax_efct_from, et_tax,et_tax_value;
    ImageView iv_submit;
    String res_title,gst_number,tax_name,gst_edit_id,shop_id,Tax_Value,effect_from;
    RelativeLayout addshop_rel;
    Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_add_gst);


        et_tax_efct_from = (EditText) findViewById(R.id.et_tax_efct_from);
        et_tax = (EditText) findViewById(R.id.et_tax);
        et_tax_value = (EditText) findViewById(R.id.et_tax_value);
        iv_submit=(ImageView)findViewById(R.id.iv_submit);
        addshop_rel=(RelativeLayout)findViewById(R.id.addshop_rel);
        myCalendar= Calendar.getInstance();
        et_tax_efct_from.setText(effect_from);
        et_tax.setText(tax_name);
        et_tax_value.setText(Tax_Value);

        et_tax_efct_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar= Calendar.getInstance();
                DatePickerDialog datepickerDialog= new DatePickerDialog(AddGst.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datepickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datepickerDialog.show();
            }
        });

        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

    }
    DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            // myCalendar1.roll(Calendar.DAY_OF_YEAR, -30);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_tax_efct_from.setText(sdf.format(myCalendar.getTime()));
    }
    private void checkValidation() {
        if(et_tax.getText().toString().trim().length()<=0){
            showSnackBar("Enter Tax Type");
        }
        else if(et_tax_value.getText().toString().trim().length()<=0){
            showSnackBar("Enter Tax Value");
        }
        else if(et_tax_efct_from.getText().toString().trim().length()<=0){
            showSnackBar("Enter Effect From");
        }/*else if(et_gst.getText().toString().trim().length()<0){
            showSnackBar("Enter Gst");
        }*/
        else{
            Addgst();
        }
    }

    private void Addgst() {
        String tax=et_tax.getText().toString().trim();
        String tax_value=et_tax_value.getText().toString().trim();
        String efct_from=et_tax_efct_from.getText().toString().trim();
        if(CheckInternet.getNetworkConnectivityStatus(AddGst.this)){
           shop_id=RestruntGST.shop_id;
            Addgst gst_add = new Addgst();
            gst_add.execute(shop_id,tax, tax_value, efct_from);
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

    private class Addgst extends AsyncTask<String,Void,Void> {
        private static final String TAG = "AddShopAsyntask";
        private ProgressDialog progressDialog = null;
        int server_status;
        String server_message;
        String link = Constants.BASEURL + Constants.ADDSHOPS;
        String charset = "UTF-8";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(AddGst.this, "Processing", "Please wait...");
            }
            // onPreExecuteTask();
        }

        @Override
        protected Void doInBackground(String... params) {

            try {

                InputStream in = null;
                int resCode = -1;

                String link = Constants.BASEURL + Constants.GSTADD;
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
                        .appendQueryParameter("shop_id", params[0])
                        .appendQueryParameter("tax_name", params[1])
                        .appendQueryParameter("tax_value", params[2])
                        .appendQueryParameter("effect_from", params[3]);

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
                    JSONObject res1 = ress.getJSONObject("res");
                    server_status = res1.optInt("status");
                    if (server_status == 1) {
                        server_message = "Added Successfully";
                    } else {
                        server_message = "Unsuccessfull";
                    }

                }
            } catch (Exception e) {
                server_message = "Connectivity issues,please try again";
                Log.e(TAG, e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void user) {
            super.onPostExecute(user);
            progressDialog.cancel();
            if (server_status == 1) {
                AddGst.this.finish();
                Toast.makeText(AddGst.this, server_message, Toast.LENGTH_SHORT).show();
            } else {
                showSnackBar(server_message);
            }
        }
    }
}
