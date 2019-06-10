package com.maxwell.csafenet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HSAAchievePhotosFragment extends Fragment {

    RequestQueue queue;
    HttpURLConnection connection;
    InputStream stream;
    BufferedReader reader;

    EditText txt_name, emailid, comments, card_title4, card_title5, card_title6, card_title7;
    Button submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hsa_acheivements_photo, container, false);






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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);

                    return true;

                }

                return false;
            }
        });
    }
}
