package com.maxwell.csafenet.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.activity.CrisisManagement;
import com.maxwell.csafenet.activity.EnvironmentActivity;
import com.maxwell.csafenet.activity.HSERiskManagmentActivity;
import com.maxwell.csafenet.activity.HealthAndWelfareActivity;
import com.maxwell.csafenet.activity.IncidentInvesticationActivity;
import com.maxwell.csafenet.activity.OccupationalSafteyActivity;
import com.maxwell.csafenet.activity.OffshoreActivity;
import com.maxwell.csafenet.activity.SecurityManagmentActivity;
import com.maxwell.csafenet.activity.TechnicalSafetyActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HomeTab extends Fragment {

    RequestQueue queue;
    HttpURLConnection connection;
    InputStream stream;
    BufferedReader reader;

    EditText txt_name, emailid, comments, card_title4, card_title5, card_title6, card_title7;
    Button submit;

    LinearLayout l_health_welfare,l_operational_saftey,l_environment,l_technical_process,l_incident_investication,l_security,l_offshore,l_risk_mgmt,l_crisis_mgmt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_tab, container, false);

        l_health_welfare=(LinearLayout)view.findViewById(R.id.layout_health_welfare);
        l_operational_saftey=(LinearLayout)view.findViewById(R.id.layout_organisational_saftey);
        l_environment=(LinearLayout)view.findViewById(R.id.layout_environment);
        l_technical_process=(LinearLayout)view.findViewById(R.id.layout_technical_process);
        l_incident_investication=(LinearLayout)view.findViewById(R.id.layout_incident_investication);
        l_security=(LinearLayout)view.findViewById(R.id.layout_security);
        l_offshore=(LinearLayout)view.findViewById(R.id.layout_offshore);
        l_risk_mgmt=(LinearLayout)view.findViewById(R.id.layout_risk_managment);
        l_crisis_mgmt=(LinearLayout)view.findViewById(R.id.layout_crisis_managment);

        l_health_welfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), HealthAndWelfareActivity.class);
                i.putExtra("TitleName","Occupational Health and Welfare");
                startActivity(i);
            }
        });
        l_operational_saftey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), OccupationalSafteyActivity.class);
                startActivity(i);
            }
        });
 l_environment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), EnvironmentActivity.class);
                startActivity(i);
            }
        });
l_technical_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), TechnicalSafetyActivity.class);
                startActivity(i);
            }
        });
        l_incident_investication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), IncidentInvesticationActivity.class);
                startActivity(i);
            }
        });
 l_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), SecurityManagmentActivity.class);
                startActivity(i);
            }
        });
 l_offshore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), OffshoreActivity.class);
                startActivity(i);
            }
        });
 l_risk_mgmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), HSERiskManagmentActivity.class);
                startActivity(i);
            }
        });
 l_crisis_mgmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getActivity(), CrisisManagement.class);
                startActivity(i);
            }
        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                /*if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure you want to exit?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    getActivity().finish();
                                    android.os.Process.killProcess(android.os.Process.myPid());

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    android.app.AlertDialog alert = builder.create();
                    alert.show();

                    return true;
                }*/

                return false;
            }
        });
    }
}
