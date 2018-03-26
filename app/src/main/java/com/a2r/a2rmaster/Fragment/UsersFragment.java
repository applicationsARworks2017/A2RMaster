package com.a2r.a2rmaster.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Activity.UsersListShopWise;
import com.a2r.a2rmaster.Adapter.ShopListAdapter;
import com.a2r.a2rmaster.Pojo.ShopList;
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
    TextView no_rest;
    SwipeRefreshLayout rest_swipe;
    ListView rest_list;
    String user_id;
    ArrayList<ShopList> rList;
    ShopListAdapter shopAdapter;
    public static String rest_id, shop_id;

    public UsersFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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
        View view=inflater.inflate(R.layout.fragment_shops, container, false);
        user_id = getActivity().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        rList=new ArrayList<>();
        no_rest=(TextView)view.findViewById(R.id.no_rest);
        rest_swipe=(SwipeRefreshLayout)view.findViewById(R.id.rest_swipe);
        rest_list=(ListView)view.findViewById(R.id.rest_list);
        getMyRestaurants();
        rest_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rest_swipe.setRefreshing(false);
                getMyRestaurants();
            }
        });
        rest_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShopList shops=(ShopList)rList.get(i);
                rest_id=shops.getId();
                Intent intent=new Intent(getActivity(), UsersListShopWise.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void getMyRestaurants() {
        if(CheckInternet.getNetworkConnectivityStatus(getActivity())){
            new calltoAPI().execute(user_id,"Y");
        }
        else{
            rest_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"No internet",Toast.LENGTH_SHORT).show();
        }
    }
    private class  calltoAPI extends AsyncTask<String, Void, Void>

    {

        String id,title,address,logo,gst,mobile_no,added_by,created,modified,is_approved,no_of_table,
                bill_prefix,frequency;
        private static final String TAG = "GetUserDetails";
        private ProgressDialog progressDialog = null;
        int server_status;
        String server_message;

        @Override
        protected void onPreExecute() {
        super.onPreExecute();
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait...");
        }
    }
        @Override
        protected Void doInBackground(String... params) {

        try {

            InputStream in = null;
            int resCode = -1;

            String link =Constants.BASEURL+Constants.SHOPLIST;
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
                    .appendQueryParameter("added_by", params[0])
                    .appendQueryParameter("is_approved", params[1]);

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
             "{
             "shops": [
             {
             "id": 11,
             "title": "shop1",
             "address": "MIG-102, Udaygiri Vihar",
             "logo": "file15170172681254705673.jpg",
             "gst": "1234",
             "mobile_no": null,
             "added_by": 5,
             "is_approved": "Y",
             "no_of_table": 10,
             "bill_prefix": null,
             "frequency": "Monthly",
             "created": "2018-01-27T01:41:08+00:00",
             "modified": "2018-02-06T00:55:59+00:00"
             },
             * */


            if (response != null && response.length() > 0) {
                JSONObject res = new JSONObject(response.trim());
                rList=new ArrayList<>();
                JSONArray jarry = res.getJSONArray("shops");
                if (jarry.length() > 0) {
                    server_status=1;
                    for (int i = 0; i < jarry.length(); i++) {
                        JSONObject j_obj = jarry.getJSONObject(i);

                        id = j_obj.optString("id");
                        title = j_obj.optString("title");
                        address = j_obj.optString("address");
                        logo = j_obj.optString("logo");
                        gst = j_obj.optString("gst");
                        mobile_no = j_obj.optString("mobile_no");
                        added_by = j_obj.optString("added_by");
                        created = j_obj.optString("created");
                        modified = j_obj.optString("modified");
                        is_approved = j_obj.optString("is_approved");
                        no_of_table = j_obj.optString("no_of_table");
                        bill_prefix = j_obj.optString("bill_prefix");
                        frequency = j_obj.optString("frequency");



                        ShopList shop = new ShopList(id,title,address,logo,gst,mobile_no,added_by,created,modified,is_approved,no_of_table,
                                bill_prefix,frequency);
                        rList.add(shop);
                    }
                } else {
                    server_status = 0;
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
            Toast.makeText(getActivity(),server_message,Toast.LENGTH_SHORT).show();

        }
        else{
            shopAdapter = new ShopListAdapter(getActivity(),rList );
            rest_list.setAdapter(shopAdapter);

        }
        progressDialog.cancel();
    }
    }
}


