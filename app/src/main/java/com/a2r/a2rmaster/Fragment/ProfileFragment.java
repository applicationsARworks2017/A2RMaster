package com.a2r.a2rmaster.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a2r.a2rmaster.Activity.LoginActivity;
import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.Constants;

/**
 * Created by mobileapplication on 3/21/18.
 */

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String user_name, user_email, user_phone;
    TextView tv_profileName, tv_address, phone;
    Button logout_button;
    RelativeLayout linn;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.a2r_profile, container, false);
        user_name = getContext().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_NAME, null);
        user_phone = getContext().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_PHONE, null);
        user_email = getContext().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_MAIL, null);

        tv_profileName = (EditText) v.findViewById(R.id.tv_profileName);
        tv_address = (TextView) v.findViewById(R.id.tv_address);
        phone = (EditText) v.findViewById(R.id.user_phone);

        logout_button = (Button) v.findViewById(R.id.logout_button);
        linn = (RelativeLayout) v.findViewById(R.id.linn);
        tv_profileName.setEnabled(false);
        phone.setEnabled(false);
        tv_profileName.setText(user_name);
        tv_address.setText(user_email);
        phone.setText(user_phone);


        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return v;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0); // 0 - for private mode
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        dialog.dismiss();

                        Intent intent=new Intent(getContext(),LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}