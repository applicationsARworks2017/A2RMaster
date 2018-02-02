package com.a2r.a2rmaster.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Activity.AddRestaurant;
import com.a2r.a2rmaster.Adapter.RestaurantAdapter;
import com.a2r.a2rmaster.Pojo.Restaurants;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.APIManager;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    FloatingActionButton add_rest;
    TextView no_rest;
    SwipeRefreshLayout rest_swipe;
    ListView rest_list;
    String user_id;
    ArrayList<Restaurants> rList;
    RestaurantAdapter restaurantAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        user_id = getActivity().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);
        rList=new ArrayList<>();
        add_rest=(FloatingActionButton)view.findViewById(R.id.add_rest);
        add_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AddRestaurant.class);
                startActivity(intent);
            }
        });
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
        return view;
    }

    private void getMyRestaurants() {
        if(CheckInternet.getNetworkConnectivityStatus(getActivity())){
            calltoAPI(user_id,"Y");
        }
        else{
            rest_swipe.setVisibility(View.GONE);
            no_rest.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"No internet",Toast.LENGTH_SHORT).show();
        }
    }
    private void calltoAPI(String added_by, String is_approved) {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading List. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("added_by", added_by);
            jsonObject.put("is_approved", is_approved);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.SHOPLIST,"shops",jsonObject, Restaurants.class,getActivity(),
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                    rList=(ArrayList<Restaurants>) resultObj;
                    restaurantAdapter = new RestaurantAdapter(getActivity(),rList );
                    rest_list.setAdapter(restaurantAdapter);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
