package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;

public class HSESSunContractorAuditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsessun_contractor_audit);
    }
    public  void back(View view){
        onBackPressed();
    }

}
