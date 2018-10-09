package com.a2r.a2rmaster.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a2r.a2rmaster.Activity.Home;
import com.a2r.a2rmaster.Activity.ItemType;
import com.a2r.a2rmaster.Adapter.ItemAdapter;
import com.a2r.a2rmaster.Adapter.OrderSummeryAdapter;
import com.a2r.a2rmaster.Pojo.ItemList;
import com.a2r.a2rmaster.Pojo.Ordersummery;
import com.a2r.a2rmaster.Pojo.Restaurants;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.APIManager;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountsReport.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountsReport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountsReport extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Double totalamount = 0.0;
    private OnFragmentInteractionListener mListener;
    LinearLayout g_img3,g_img4;
    Calendar myCalendar;
    Spinner shopnames;
    TextView s_date,e_date,tv_totalamount;
    Button bt_ok;
    String id_rest="00",name_rest;
    ArrayList<Ordersummery> iList;
    ListView lv_accounts;
    OrderSummeryAdapter itemAdapter;
    SwipeRefreshLayout account_refresh;

    public AccountsReport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountsReport.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountsReport newInstance(String param1, String param2) {
        AccountsReport fragment = new AccountsReport();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accounts_report, container, false);
        g_img3=(LinearLayout)v.findViewById(R.id.g_img3);
        g_img4=(LinearLayout)v.findViewById(R.id.g_img4);
        s_date = (TextView)v.findViewById(R.id.s_date);
        tv_totalamount = (TextView)v.findViewById(R.id.totalamount);
        e_date = (TextView)v.findViewById(R.id.e_date);
        shopnames=(Spinner)v.findViewById(R.id.shopnames);
        lv_accounts=(ListView)v.findViewById(R.id.lv_accounts);
        account_refresh = (SwipeRefreshLayout)v.findViewById(R.id.account_refresh);
        bt_ok = (Button)v.findViewById(R.id.bt_ok);


        final ArrayList<String> restaurantsList = new ArrayList<>();
        restaurantsList.add("-- Select Shop --");
        final ArrayList<String> restaurantsIds = new ArrayList<>();
        restaurantsIds.add("00");

        for(int i=0;i<HomeFragment.rList.size();i++){
            restaurantsList.add(HomeFragment.rList.get(i).getTitle());
            restaurantsIds.add(HomeFragment.rList.get(i).getId());
        }

        ArrayAdapter<String> adapte_visitors = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,restaurantsList );
        adapte_visitors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        shopnames.setAdapter(adapte_visitors);

        shopnames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 id_rest = restaurantsIds.get(position);//This will be the student id.
                 name_rest=restaurantsList.get(position);
               // Toast.makeText(getActivity(),name_rest+id_rest,Toast.LENGTH_LONG).show();
                /*if(!id_rest.contentEquals("00")){
                    getTransactions(id_rest);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        account_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                account_refresh.setRefreshing(false);
                getupdate();
            }
        });
        g_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateStartDate();


                    }

                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();

            }
        });
        g_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        updateEndDate();
                    }

                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getupdate();
            }
        });
        return v;
    }

    private void getupdate() {
        iList = new ArrayList<>();

        if(id_rest.contentEquals("00")){
            Toast.makeText(getActivity(),"Select Shop for report",Toast.LENGTH_LONG).show();
        }
        else if(s_date.getText().toString().trim().contentEquals("Start Date")){
            Toast.makeText(getActivity(),"Select Start Date",Toast.LENGTH_LONG).show();

        }
        else if(e_date.getText().toString().trim().contentEquals("End Date")){
            Toast.makeText(getActivity(),"Select End Date",Toast.LENGTH_LONG).show();

        }
        else {
            getTransactions(id_rest,s_date.getText().toString().trim(),e_date.getText().toString().trim());
        }
    }

    private void updateStartDate() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        s_date.setText(sdf.format(myCalendar.getTime()));
        //findlist();
    }
    private void updateEndDate() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        e_date.setText(sdf.format(myCalendar.getTime()));
       // findlist();

    }
    private void getTransactions(String id_rest, String s_date, String e_date) {
         //Toast.makeText(getActivity(),id_rest,Toast.LENGTH_LONG).show();
        if(CheckInternet.getNetworkConnectivityStatus(getActivity())){
            calltoAPI(id_rest,s_date,e_date);
        }
        else{
            Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();
        }
    }

    private void calltoAPI(String idRest, String s_date, String e_date) {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading List. Please wait...");
        pd.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("shop_id", id_rest);
            jsonObject.put("start_date", s_date);
            jsonObject.put("end_date", e_date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIManager().postJSONArrayAPI(Constants.BASEURL+Constants.ORDER_SUMMERY,"orderSummaries",jsonObject, Ordersummery.class,getActivity(),
                new APIManager.APIManagerInterface() {
                    @Override
                    public void onSuccess(Object resultObj) {
                        iList=(ArrayList<Ordersummery>) resultObj;
                        itemAdapter = new OrderSummeryAdapter(getActivity(),iList );
                        lv_accounts.setAdapter(itemAdapter);
                        pd.cancel();
                       updatetotal();
                    }

                    @Override
                    public void onError(String error) {
                        account_refresh.setVisibility(View.GONE);
                        //no_rest.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),"No Data Found",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                });


    }

    private void updatetotal() {
        for(int i =0;i<iList.size();i++){
            String temp_amount = iList.get(i).getTotal_amount();
            if(!temp_amount.isEmpty()|| temp_amount!=null || !temp_amount.contentEquals("")) {
                totalamount = totalamount + Double.valueOf(temp_amount);
            }
        }
        tv_totalamount.setText("Rs.: "+String.valueOf(totalamount));

        //Toast.makeText(getActivity(),"Hi",Toast.LENGTH_LONG).show();
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
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
