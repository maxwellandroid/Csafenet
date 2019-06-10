package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.adapter.QuestionListAdapter;
import com.maxwell.csafenet.model.BulletinModules;
import com.maxwell.csafenet.model.QuestionsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BulletDetailViewActivity extends AppCompatActivity {

    TextView tv_title,tv_short_description,tv_category,tv_document_type,tv_viewer,tv_download;

    String s_title,s_description,s_category,s_document_type,s_viewer,s_downloadurl;
    String filename = "myfile";
    String fileContents = "Hello world!";
    FileOutputStream outputStream;
    String formattedDate;
    BulletinModules bulletinModules=new BulletinModules();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bullet_detail_view);

        initializeViews();
    }
    public  void back(View view){
        onBackPressed();
    }

    public void initializeViews(){

        tv_title=(TextView)findViewById(R.id.text_bulletin_title);
        tv_short_description=(TextView)findViewById(R.id.text_description);
        tv_category=(TextView)findViewById(R.id.text_bullet_in_category);
        tv_document_type=(TextView)findViewById(R.id.text_document_type);
        tv_viewer=(TextView)findViewById(R.id.text_viewers);
        tv_download=(TextView)findViewById(R.id.text_download);

        bulletinModules= (BulletinModules) getIntent().getSerializableExtra("BulletInDetail");
        String str =bulletinModules.getIssuedate();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
             formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            formattedDate=bulletinModules.getIssuedate();

        }
        if(bulletinModules!=null){
            tv_title.setText(bulletinModules.getTitle()+" ( "+formattedDate+" )");
            tv_short_description.setText(bulletinModules.getShortdescription());
            tv_category.setText(bulletinModules.getCategory());
            tv_viewer.setText(bulletinModules.getIssuer());
        }

        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bulletinModules.getFilePath()));
                startActivity(browserIntent);
            }
        });



    }


}
