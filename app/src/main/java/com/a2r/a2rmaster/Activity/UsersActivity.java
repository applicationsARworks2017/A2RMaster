package com.a2r.a2rmaster.Activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Adapter.UserAdapter;
import com.a2r.a2rmaster.Fragment.UsersFragment;
import com.a2r.a2rmaster.Pojo.USERList;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONArray;
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

public class UsersActivity extends AppCompatActivity {


    UserAdapter userAdapter;
    RelativeLayout framelayout;
    TextView no_rest;
    SwipeRefreshLayout rest_swipe;
    ListView rest_list;
    String user_id,shop_id;
    ArrayList<USERList> userList;
    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager);

        user_id = getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        shop_id= UsersFragment.rest_id;
        user_type= UsersListShopWise.user_type;
        userList=new ArrayList<>();
        framelayout=(RelativeLayout)findViewById(R.id.framelayout);
        no_rest=(TextView)findViewById(R.id.no_rest);
        rest_swipe=(SwipeRefreshLayout)findViewById(R.id.rest_swipe);
        rest_list=(ListView)findViewById(R.id.user_list);
        getManagerList();

        rest_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rest_swipe.setRefreshing(false);
               getManagerList();
            }
        });
    }

    private void getManagerList() {
        if(CheckInternet.getNetworkConnectivityStatus(this)){
            new ManagerList().execute(shop_id);
        }
        else{
            rest_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);
            Toast.makeText(this,"No internet",Toast.LENGTH_SHORT).show();
        }
    }

    private class ManagerList extends AsyncTask<String, Void, Void> {

        String id,name,mobile,email,user_type_id,added_by,shop_id,created,modified,shop_title,address,
                shop_user_type_id,shop_user_type_title,shop_user_is_active;
        private static final String TAG = "GetUserDetails";
        private ProgressDialog progressDialog = null;
        int server_status;
        String server_message;
        String photo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(UsersActivity.this, "Loading", "Please wait...");
            }
        }
        @Override
        protected Void doInBackground(String... params) {

            try {
                String shop_id = params[0];

                InputStream in = null;
                int resCode = -1;

                String link =Constants.BASEURL+Constants.USERLIST;
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
                if(user_type.contentEquals("Manager")) {
                    builder = new Uri.Builder()
                            .appendQueryParameter("shop_id", shop_id)
                            .appendQueryParameter("user_type_id", "2");
                }else if(user_type.contentEquals("Waiter")){
                    builder = new Uri.Builder()
                            .appendQueryParameter("shop_id", shop_id)
                            .appendQueryParameter("user_type_id", "4");
                }else if(user_type.contentEquals("Cashier")){
                    builder = new Uri.Builder()
                            .appendQueryParameter("shop_id", shop_id)
                            .appendQueryParameter("user_type_id", "5");
                }else if(user_type.contentEquals("Cook")){
                    builder = new Uri.Builder()
                            .appendQueryParameter("shop_id", shop_id)
                            .appendQueryParameter("user_type_id", "6");
                }

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
                String response = "", data = "";

                while ((data = reader.readLine()) != null) {
                    response += data + "\n";
                }

                Log.i(TAG, "Response : " + response);

                /**
                 *  "users": [
                 {
                 "id": 1,
                 "name": "Superadmin",
                 "mobile": "9090403050",
                 "email": "sadsadsad@dsad.dsf",
                 "user_type_id": 1,
                 "added_by": 1,
                 "shop_id": 0,
                 "created": "2018-01-21T16:22:23+00:00",
                 "modified": "2018-01-21T16:27:38+00:00",
                 "shop": null,
                 "user_type": {
                 "id": 1,
                 "title": "Superadmin",
                 "is_active": "Y",
                 "created": "2018-01-21T16:16:10+00:00",
                 "modified": "2018-01-21T16:16:10+00:00"
                 }
                 },
                 * */


                if (response != null && response.length() > 0) {
                    JSONObject res = new JSONObject(response.trim());
                    JSONArray jarry = res.getJSONArray("users");
                    if (jarry.length() > 0) {
                        server_status=1;
                        for (int i = 0; i < jarry.length(); i++) {
                            JSONObject j_obj = jarry.getJSONObject(i);
                            JSONObject user_type_data = j_obj.getJSONObject("user_type");
                            JSONObject shop_type_data = j_obj.getJSONObject("shop");

                            id = j_obj.optString("id");
                            name = j_obj.optString("name");
                            mobile = j_obj.optString("mobile");
                            email = j_obj.optString("email");
                            user_type_id = j_obj.optString("user_type_id");
                            added_by = j_obj.optString("added_by");
                            shop_id = j_obj.optString("shop_id");
                            created = j_obj.optString("created");
                            modified = j_obj.optString("modified");

                            shop_id=shop_type_data.getString("id");
                            shop_title=shop_type_data.getString("title");
                            address=shop_type_data.getString("address");

                            shop_user_type_id = user_type_data.getString("id");
                            shop_user_type_title = user_type_data.getString("title");
                            shop_user_is_active = user_type_data.getString("is_active");

                            USERList userlist = new USERList(id, name, mobile, email, user_type_id, added_by, shop_id, created, modified, shop_title, address,
                                    shop_user_type_id, shop_user_type_title, shop_user_is_active);
                            userList.add(userlist);
                        }
                    } else {
                        server_status = 0;
                        server_message = "No User Found";

                    }
                }
                return null;
            } catch (Exception exception) {
                server_message = "Network Error";
                Log.e(TAG, "SynchMobnum : doInBackground", exception);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void user) {
            super.onPostExecute(user);
            if(server_status==0) {
                no_rest.setVisibility(View.VISIBLE);
                rest_swipe.setVisibility(View.GONE);

                Snackbar snackbar = Snackbar
                        .make(framelayout, server_message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else{
                userAdapter = new UserAdapter(UsersActivity.this,userList );
                rest_list.setAdapter(userAdapter);

            }
            progressDialog.cancel();
        }
    }
}
