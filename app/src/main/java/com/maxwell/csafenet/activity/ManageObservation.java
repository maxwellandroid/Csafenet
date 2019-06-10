package com.maxwell.csafenet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;


import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.adapter.MyObservationAdapter;
import com.maxwell.csafenet.model.ManageObservationModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ManageObservation extends AppCompatActivity {
    HttpPost httppost;
    HttpResponse response;
    DefaultHttpClient httpclient;
    String urls;
    private RecyclerView recyclerView2;
    String use, mob;
    ProgressDialog Asycdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_observation);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);
        use = "mousashat";
        mob = "55330610";
        Asycdialog = new ProgressDialog(ManageObservation.this);
       // new Mylogin(use, mob).execute();
    }

    public void back(View view) {
        onBackPressed();
    }


    public class Mylogin extends AsyncTask<Void, Void, Void> {

        String responseBody;
        JSONObject jobj = null;
        JSONArray arrays;
        HttpEntity httpentity = null;
        HttpResponse httpResponse = null;
        String response = null;
        int wchecker;
        String imobile, ipass;
        String userID;
        Bundle bundle = new Bundle();
        List<ManageObservationModel> modelList = new ArrayList<>();
        String id, userid, user_name, regcode, dept_name, company_name, emp_no, designation,
                observed_date, observed_time, project_name, areaname, description, desc_type, action_description, action_by,
                action_type, risk, explain_text, image, reg_date;

        Mylogin(String mobile, String pass) {

            this.imobile = mobile;
            this.ipass = pass;

        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setMessage("Please wait...");
            Asycdialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                httpclient = new DefaultHttpClient();

                String d = "1";
                // urls = "http://naveenbalaji.com/api/login.php?mobile=" + imobile + "&password=" + ipass;
                urls = "http://www.goalzero.qa/main/api/login.php?user_name=" + imobile + "&pswd=" + ipass;
                URI uri = null;
                uri = new URI(urls.replace(" ", "%20"));
                System.out.println(uri);
                httppost = new HttpPost(uri);
                httpResponse = httpclient.execute(httppost);
                httpentity = httpResponse.getEntity();
                response = EntityUtils.toString(httpentity);

            } catch (Exception e) {
                // TODO: handle exception

                System.out.println("Exception : " + e.getMessage());
                e.printStackTrace();

            }
            try {
                JSONObject jobject = new JSONObject(response);
                JSONArray array = jobject.getJSONArray("login");
                for (int i = 0; i < array.length(); i++) {
                    wchecker = 0;
                    JSONObject user = array.getJSONObject(i);
                    userID = user.getString("userid");
                    id = user.getString("id");
                    regcode = user.getString("regcode");
                    user_name = user.getString("user_name");
                    dept_name = user.getString("dept_name");
                    company_name = user.getString("company_name");
                    emp_no = user.getString("emp_no");
                    designation = user.getString("designation");
                    observed_date = user.getString("observed_date");
                    observed_time = user.getString("observed_time");
                    project_name = user.getString("project_name");
                    areaname = user.getString("areaname");
                    description = user.getString("description");
                    desc_type = user.getString("desc_type");
                    action_description = user.getString("action_description");
                    action_type = user.getString("action_type");
                    risk = user.getString("risk");
                    image = user.getString("image");
                    explain_text = user.getString("explain_text");
                    reg_date = user.getString("reg_date");
/*
                    MyobservationModel model=new MyobservationModel();
                    model.setId(id);
                    model.setUserid(userID);
                    model.setUser_name(user_name);
                    model.setRegcode(regcode);
                    model.setDept_name(dept_name);
                    model.setCompany_name(company_name);
                    model.setEmp_no(emp_no);
                    model.setDesignation(designation);
                    model.setObserved_date(observed_date);
                    model.setObserved_time(observed_time);
                    model.setProject_name(project_name);
                    model.setAreaname(areaname);
                    model.setDescription(description);
                    model.setDesc_type(desc_type);
                    model.setAction_description(action_description);
                    model.setAction_type(action_type);
                    model.setRisk(risk);
                    model.setExplain_text(explain_text);
                    model.setImage(image);
                    model.setReg_date(reg_date);
                    modelList.add(model);


                    MyObservationAdapter memberListAdapter = new MyObservationAdapter().setUsers(getActivity(), modelList);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView2.setLayoutManager(mLayoutManager);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
                    recyclerView2.setItemAnimator(new DefaultItemAnimator());
                    recyclerView2.setAdapter(memberListAdapter);
                    recyclerView2.setNestedScrollingEnabled(false);*/

                }


                if (!userID.equals("")) {

                    wchecker = 0;

                } else {
                    wchecker = 1;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            if (Asycdialog.isShowing())
                Asycdialog.dismiss();


            try {
                JSONObject jobject = new JSONObject(response);
                JSONArray array = jobject.getJSONArray("login");
                for (int i = 0; i < array.length(); i++) {
                    wchecker = 0;
                    JSONObject user = array.getJSONObject(i);
                    userID = user.getString("userid");
                    id = user.getString("id");
                    regcode = user.getString("regcode");
                    user_name = user.getString("user_name");
                    dept_name = user.getString("dept_name");
                    company_name = user.getString("company_name");
                    emp_no = user.getString("emp_no");
                    designation = user.getString("designation");
                    observed_date = user.getString("observed_date");
                    observed_time = user.getString("observed_time");
                    project_name = user.getString("project_name");
                    areaname = user.getString("areaname");
                    description = user.getString("description");
                    desc_type = user.getString("desc_type");
                    action_description = user.getString("action_description");
                    action_type = user.getString("action_type");
                    risk = user.getString("risk");
                    image = user.getString("image");
                    explain_text = user.getString("explain_text");
                    reg_date = user.getString("reg_date");
                    action_by = user.getString("action_by");
                    ManageObservationModel model = new ManageObservationModel();
                    model.setId(id);
                    model.setUserid(userID);
                    model.setUser_name(user_name);
                    model.setRegcode(regcode);
                    model.setDept_name(dept_name);
                    model.setCompany_name(company_name);
                    model.setEmp_no(emp_no);
                    model.setDesignation(designation);
                    model.setObserved_date(observed_date);
                    model.setObserved_time(observed_time);
                    model.setProject_name(project_name);
                    model.setAreaname(areaname);
                    model.setDescription(description);
                    model.setDesc_type(desc_type);
                    model.setAction_description(action_description);
                    model.setAction_type(action_type);
                    model.setRisk(risk);
                    model.setExplain_text(explain_text);
                    model.setImage(image);
                    model.setReg_date(reg_date);
                    model.setAction_by(action_by);
                    modelList.add(model);
                    MyObservationAdapter memberListAdapter = new MyObservationAdapter().setUsers(getApplicationContext(), modelList);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView2.setLayoutManager(mLayoutManager);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
                    recyclerView2.setItemAnimator(new DefaultItemAnimator());
                    recyclerView2.setAdapter(memberListAdapter);
                    recyclerView2.setNestedScrollingEnabled(false);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*Intent i = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(i);*/

        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) *
                // spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /
                // spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
