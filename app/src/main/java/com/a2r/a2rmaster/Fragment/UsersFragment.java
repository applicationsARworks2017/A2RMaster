package com.a2r.a2rmaster.Fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Adapter.UserAdapter;
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

/**
 * Created by mobileapplication on 2/5/18.
 */

public class UsersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HomeFragment.OnFragmentInteractionListener mListener;

    FloatingActionButton add_rest;
    TextView no_rest;
    SwipeRefreshLayout rest_swipe;
    ListView rest_list;
    String user_id;
    ArrayList<USERList> userList;
    UserAdapter userAdapter;
    FrameLayout framelayout;

    public UsersFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static UsersFragment newInstance(String param1, String param2) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_users, container, false);
        user_id = getActivity().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        userList=new ArrayList<>();
        framelayout=(FrameLayout)view.findViewById(R.id.linn);
        no_rest=(TextView)view.findViewById(R.id.no_rest);
        rest_swipe=(SwipeRefreshLayout)view.findViewById(R.id.rest_swipe);
        rest_list=(ListView)view.findViewById(R.id.user_list);
        getUser();
        rest_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rest_swipe.setRefreshing(false);
                getUser();
            }
        });
        return view;
    }

    private void getUser() {
        if(CheckInternet.getNetworkConnectivityStatus(getActivity())){
           new calltogetUserDetails().execute(user_id);
        }
        else{
            rest_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"No internet",Toast.LENGTH_SHORT).show();
        }
    }
    private class calltogetUserDetails extends AsyncTask<String, Void, Void>{

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
            progressDialog = ProgressDialog.show(getContext(), "Loading", "Please wait...");
        }
    }
    @Override
    protected Void doInBackground(String... params) {

        try {
            String userid = params[0];

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
                builder = new Uri.Builder()
                        .appendQueryParameter("user_id", userid);;

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
             * {
             "users": [
             {
             "id": 6,
             "name": "admin1",
             "mobile": "7205674061",
             "email": "a@gmail.com",
             "user_type_id": 3,
             "added_by": 5,
             "shop_id": 11,
             "created": "2018-01-27T01:10:10+00:00",
             "modified": "2018-01-27T01:10:10+00:00",
             "shop": {
             "id": 11,
             "title": "shop1",
             "address": "MIG-102, Udaygiri Vihar",
             "logo": "file15170172681254705673.jpg",
             "gst": "1234",
             "mobile_no": null,
             "added_by": 5,
             "is_approved": "Y",
             "no_of_table": null,
             "created": "2018-01-27T01:41:08+00:00",
             "modified": "2018-02-06T00:55:59+00:00"
             },
             "user_type": {
             "id": 3,
             "title": "Admin",
             "is_active": "Y",
             "created": "2018-01-19T00:31:44+00:00",
             "modified": "2018-01-21T02:37:08+00:00"
             }
             }
             ]
             * */


            if (response != null && response.length() > 0) {
                JSONObject res = new JSONObject(response.trim());
                server_status = res.optInt("status");
                    JSONArray jarry=res.getJSONArray("users");
                    for(int i=0;i<jarry.length();i++){
                        JSONObject j_obj=jarry.getJSONObject(i);
                        JSONObject shop_data=j_obj.getJSONObject("shop");
                        if(shop_data==null){

                        }
                        JSONObject user_type_data=j_obj.getJSONObject("user_type");

                        id = j_obj.optString("id");
                        name = j_obj.optString("name");
                        mobile = j_obj.optString("mobile");
                        email  = j_obj.optString("email");
                        user_type_id = j_obj.optString("user_type_id");
                        added_by = j_obj.optString("added_by");
                        shop_id = j_obj.optString("shop_id");
                        created = j_obj.optString("created");
                        modified = j_obj.optString("modified");

                         shop_id=shop_data.getString("id");
                         shop_title=shop_data.getString("title");
                         address=shop_data.getString("address");

                        shop_user_type_id=user_type_data.getString("id");
                        shop_user_type_title=user_type_data.getString("title");
                        shop_user_is_active=user_type_data.getString("is_active");

                        USERList userlist = new USERList(id,name,mobile,email,user_type_id,added_by,shop_id,created,modified,shop_title,address,
                                shop_user_type_id,shop_user_type_title,shop_user_is_active);
                        userList.add(userlist);
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
        if(server_status==1) {
            userAdapter = new UserAdapter(getActivity(),userList );
            rest_list.setAdapter(userAdapter);
            rest_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);

        }
        else{

            Snackbar snackbar = Snackbar
                    .make(framelayout, server_message, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        progressDialog.cancel();
    }
}
}

