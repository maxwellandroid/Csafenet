package com.maxwell.csafenet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.maxwell.csafenet.activity.AddInitialIncidentNotification;
import com.maxwell.csafenet.activity.CAHSAPolicies;
import com.maxwell.csafenet.activity.CEVSActivity;
import com.maxwell.csafenet.activity.ChangePasswordActivity;
import com.maxwell.csafenet.activity.ClientWiseProcedureActivity;
import com.maxwell.csafenet.activity.ConfinedSpaceWorkActivity;
import com.maxwell.csafenet.activity.CorrectiveActionsReportGeneratorActivity;
import com.maxwell.csafenet.activity.EmergencyAndCrisisNotificationActivity;
import com.maxwell.csafenet.activity.EquipmentsAssignedToYouActivity;
import com.maxwell.csafenet.activity.HSAAhivementActivity;
import com.maxwell.csafenet.activity.HSAAhivementLettersActivity;
import com.maxwell.csafenet.activity.HSEActionTrackerActivity;
import com.maxwell.csafenet.activity.HSEAlertsActivity;
import com.maxwell.csafenet.activity.HSEBulletin;
import com.maxwell.csafenet.activity.HSEFitnessToWork;
import com.maxwell.csafenet.activity.HSEManualsAbdSubManuals;
import com.maxwell.csafenet.activity.HSEOrganisationChart;
import com.maxwell.csafenet.activity.HSEProceduresActivity;
import com.maxwell.csafenet.activity.HSETrainingPassport;
import com.maxwell.csafenet.activity.HeadCountTrackerActivity;
import com.maxwell.csafenet.activity.ImmediateNotificationSystemActivity;
import com.maxwell.csafenet.activity.IncidentNotificationReport;
import com.maxwell.csafenet.activity.JobCategoryQuestions;
import com.maxwell.csafenet.activity.LoginActivity;
import com.maxwell.csafenet.activity.MyProfileActivity;
import com.maxwell.csafenet.activity.NewObservationActivity;
import com.maxwell.csafenet.activity.NoItemsActivity;
import com.maxwell.csafenet.activity.ObservationForActionActivity;
import com.maxwell.csafenet.activity.ObservationStatisticsActivity;
import com.maxwell.csafenet.activity.OnlineTrainingListActivity;
import com.maxwell.csafenet.activity.SecurityIncidentReportActivity2;
import com.maxwell.csafenet.activity.SecurityProgramAuditActivity;
import com.maxwell.csafenet.activity.TopObserverActivity;
import com.maxwell.csafenet.activity.ViewObservationsActivity;
import com.maxwell.csafenet.adapter.DrawerListAdapter;
import com.maxwell.csafenet.fragment.GoldenRules;
import com.maxwell.csafenet.fragment.HomeTab;
import com.maxwell.csafenet.fragment.IntegratedManagementTab;
import com.maxwell.csafenet.fragment.LifeSavingRuls;
import com.maxwell.csafenet.model.LifeSavingDetailsModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout myprofile,genral, tools,profile, general_list, tools_list, incident_management,profile_list;
    //General
    LinearLayout empMgmt,aciveMents, managmentSysytem, organizationChart, fitnewssToWork, onlineTraining, onlineTrainingSysytem, pasport, employeeCommAssesments,
            communication, newObservation, manageObservation;
    //Tools
    LinearLayout cSafeObservationCard, incidentMangemnet, complianceInspectionModule, auditModule, permitManagement, jobSafteyAnalysis, fatigueManagement,
            actionItemTracking, riskManagemnet, statistics, equpmentRegister, contractorMangement, employeeSaftyClimate, disciplinaryActioRegistery,
            processSaftyElements, logout, liear_csafeobservartion;
    TextView tv_selected_navigation;
    View empMgmtView,achivementView, managementSysView, onlineTrainingView, onlineTrainSysView, trainingPassportView, employeeHSEView, communicationView,crisisManagemntView,equipmentMgmtView;
    View cSafeObserView, incidentmangementView, permitView, fatigueManagementView, actionItemTrackingView, riskMangementView, staticsView,
            contaroctorView, include_hsa_compliency, view_csafeobservation,auditView;
    ArrayList<String> navigation_items;
    private DrawerListAdapter drawerListAdapter;
    private ListView lv_drawer;
    View includedLayout;
    int firsr =0;
    int second = 1;
    int third = 0;
    int empMgmtId=0;
    int hseacheivmentId=0;
    int managesysId = 0, onlineTrainigId = 0, onlineSystId = 0, passporId = 0, emploeeId = 0, communicationId = 0, safeId = 0, incidentId = 0,trainingpasspoprtId=0,
            permitId = 0, jobsaftiId = 0, fatiguId = 0, actioonId = 0, riskId = 0, staticId = 0, contracoterId = 0, hsa_compliency_id = 0,auditId=0,crisisId=0,equipmentId=0;
    int csafe = 0;
    TabLayout tabLayout;
    FragmentAdapterClass fragmentAdapter;
    ViewPager viewPager;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String s_user_id;

    TextView tv_view_my_observation,tv_observatio_for_action;
    List<LifeSavingDetailsModule> goldenImageList =new ArrayList<>();
    List<LifeSavingDetailsModule> imssImages =new ArrayList<>();
    List<LifeSavingDetailsModule> lifeSavingDetailsModuleList=new ArrayList<>();
    LifeSavingDetailsModule lifeSavingDetailsModule;
    String TAG="FireBaseResponse";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        genral = (LinearLayout) findViewById(R.id.general_linear);
        myprofile = (LinearLayout) findViewById(R.id.linear_my_profile);
        tools = (LinearLayout) findViewById(R.id.tools_linear);
        general_list = (LinearLayout) findViewById(R.id.general_list);
        tools_list = (LinearLayout) findViewById(R.id.tools_list);
        profile_list = (LinearLayout) findViewById(R.id.profile_list);
        //General
        empMgmt = (LinearLayout) findViewById(R.id.linear_emp_mgmt);
        aciveMents = (LinearLayout) findViewById(R.id.linear_hsa_achivements);
        managmentSysytem = (LinearLayout) findViewById(R.id.linear_hsa_management);
        organizationChart = (LinearLayout) findViewById(R.id.linear_hsa_organiztion_chart);
        fitnewssToWork = (LinearLayout) findViewById(R.id.linear_hsa_fitnesstowork);
        onlineTraining = (LinearLayout) findViewById(R.id.linear_hsa_online_training);
        onlineTrainingSysytem = (LinearLayout) findViewById(R.id.linear_hsa_online_training_system);
        pasport = (LinearLayout) findViewById(R.id.linear_hse_passport);
        employeeCommAssesments = (LinearLayout) findViewById(R.id.linear_hsa_employee_comptency_assesment);
        communication = (LinearLayout) findViewById(R.id.linear_hsa_communication);
        // newObservation = (LinearLayout) findViewById(R.id.linear_hsa_new_observation);
        manageObservation = (LinearLayout) findViewById(R.id.linear_hsa_manage_observation);
        logout = (LinearLayout) findViewById(R.id.logout);
        //Tools
        cSafeObservationCard = (LinearLayout) findViewById(R.id.linear_c_safe);
        incident_management = (LinearLayout) findViewById(R.id.liner_incident_management);
        complianceInspectionModule = (LinearLayout) findViewById(R.id.linear_hsa_inspection_module);
        auditModule = (LinearLayout) findViewById(R.id.liner_audit_modul);
        permitManagement = (LinearLayout) findViewById(R.id.linear_hsa_permit);
        jobSafteyAnalysis = (LinearLayout) findViewById(R.id.linear_hsa_job_safty);
        fatigueManagement = (LinearLayout) findViewById(R.id.linear_fartigu_management);
        actionItemTracking = (LinearLayout) findViewById(R.id.linear_action_item_tracking);
        riskManagemnet = (LinearLayout) findViewById(R.id.linear_risk_management);
        statistics = (LinearLayout) findViewById(R.id.linear_hsa_satics);
        equpmentRegister = (LinearLayout) findViewById(R.id.linear_hsa_equipmnet);
        contractorMangement = (LinearLayout) findViewById(R.id.linear_cotract_management);
        employeeSaftyClimate = (LinearLayout) findViewById(R.id.linear_employee_safty);
        disciplinaryActioRegistery = (LinearLayout) findViewById(R.id.linear_disciplinary_action);
        processSaftyElements = (LinearLayout) findViewById(R.id.linear_hsa_process_safty);
        liear_csafeobservartion = (LinearLayout) findViewById(R.id.linear_csafe_observation);
//includ layout general
        empMgmtView = findViewById(R.id.emp_mgmt);
        achivementView = findViewById(R.id.hsa_achivement);
        managementSysView = findViewById(R.id.hsa_managemnet_sysytem);
        onlineTrainingView = findViewById(R.id.hsa_online_training);
        onlineTrainSysView = findViewById(R.id.hsa_online_trini_sys);
        trainingPassportView = findViewById(R.id.hsa_passport);
        employeeHSEView = findViewById(R.id.hsa_employee_competency);
        communicationView = findViewById(R.id.hsa__communication);
        crisisManagemntView = findViewById(R.id.crisis_management);
        equipmentMgmtView = findViewById(R.id.equipment_management);
        view_csafeobservation = findViewById(R.id.hsa__csafeobservation);
//includ layout tools
        cSafeObserView = findViewById(R.id.c_safe_observation);
        incidentmangementView = findViewById(R.id.incident_managemnet);
        permitView = findViewById(R.id.permit_managemnet);
        fatigueManagementView = findViewById(R.id.fatigue_managmnet);
        actionItemTrackingView = findViewById(R.id.action_item_tracking);
        riskMangementView = findViewById(R.id.risk_management);
        staticsView = findViewById(R.id.hsa_statics);
        contaroctorView = findViewById(R.id.controcter_managment);
        include_hsa_compliency = findViewById(R.id.include_hsa_compliency);
        auditView = findViewById(R.id.include_hsa_audit_module);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        tabLayout = (TabLayout) findViewById(R.id.tab_layout1);
        viewPager = (ViewPager) findViewById(R.id.pager1);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Integrated Management System Statement"));
        tabLayout.addTab(tabLayout.newTab().setText("Golden Rules"));
        tabLayout.addTab(tabLayout.newTab().setText("Life Saving Rules"));


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        editor = preferences.edit();

        // new ViewMyObservation().execute();
        viewMyOservation();
        viewObservationForActon();
      //  new ViewObservationForAction().execute();
       /* new GetIntegratedManagmentimage().execute();
          new GetGoldenRulesimage().execute();
          new GetLifeSavingRulesOperation().execute();
        */

       myprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

              /* Intent i=new Intent(getApplicationContext(), MyProfileActivity.class);
               startActivity(i);*/
           }
       });
        aciveMents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hseacheivmentId == 0) {
                    hseacheivmentId = 1;
                    achivementView.setVisibility(View.VISIBLE);
                } else {
                    hseacheivmentId = 0;
                    achivementView.setVisibility(View.GONE);
                }

            }
        });
        empMgmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(empMgmtId==0){
                    empMgmtId=1;
                    empMgmtView.setVisibility(View.VISIBLE);
                }else {
                    empMgmtId=0;
                    empMgmtView.setVisibility(View.GONE);
                }

            }
        });
        managmentSysytem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (managesysId == 0) {
                    managesysId = 1;
                    managementSysView.setVisibility(View.VISIBLE);
                } else {
                    managesysId = 0;
                    managementSysView.setVisibility(View.GONE);
                }
            }
        });
        organizationChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), HSEOrganisationChart.class);
                startActivity(i);
            }
        });
        fitnewssToWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), HSEFitnessToWork.class);
                startActivity(i);
            }
        });
        onlineTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* if (onlineTrainigId == 0) {
                    onlineTrainigId = 1;
                    onlineTrainingView.setVisibility(View.VISIBLE);
                } else {
                    onlineTrainigId = 0;
                    onlineTrainingView.setVisibility(View.GONE);
                }
                */
              Intent i=new Intent(getApplicationContext(),OnlineTrainingListActivity.class);
              startActivity(i);
            }
        });
        onlineTrainingSysytem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onlineSystId == 0) {
                    onlineSystId = 1;
                    onlineTrainSysView.setVisibility(View.VISIBLE);
                } else {
                    onlineSystId = 0;
                    onlineTrainSysView.setVisibility(View.GONE);
                }
            }
        });
        pasport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passporId == 0) {
                    passporId = 1;
                    onlineTrainingView.setVisibility(View.VISIBLE);
                } else {
                    passporId = 0;
                    onlineTrainingView.setVisibility(View.GONE);
                }

            }
        });
        employeeCommAssesments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emploeeId == 0) {
                    emploeeId = 1;
                    employeeHSEView.setVisibility(View.VISIBLE);
                } else {
                    emploeeId = 0;
                    employeeHSEView.setVisibility(View.GONE);
                }

            }
        });
        communication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (communicationId == 0) {
                    communicationId = 1;
                    communicationView.setVisibility(View.VISIBLE);
                } else {
                    communicationId = 0;
                    communicationView.setVisibility(View.GONE);
                }
            }
        });
        liear_csafeobservartion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewMyOservation();
                viewObservationForActon();
                if (csafe == 0) {
                    csafe = 1;
                    view_csafeobservation.setVisibility(View.VISIBLE);
                } else {
                    view_csafeobservation.setVisibility(View.GONE);
                    csafe = 0;
                }
            }
        });

        TextView csafe = (TextView) view_csafeobservation.findViewById(R.id.new_observation);
        csafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewObservationActivity.class);
                intent.putExtra("ScreenName","New Observation");

                startActivity(intent);

                drawer.closeDrawer(GravityCompat.START);
            }
        });
        cSafeObservationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (safeId == 0) {
                    safeId = 1;
                    cSafeObserView.setVisibility(View.VISIBLE);
                } else {
                    safeId = 0;
                    cSafeObserView.setVisibility(View.GONE);
                }

            }
        });
        incident_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incidentId == 0) {
                    incidentId = 1;
                    incidentmangementView.setVisibility(View.VISIBLE);
                } else {
                    incidentId = 0;
                    incidentmangementView.setVisibility(View.GONE);
                }

            }
        });
        complianceInspectionModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hsa_compliency_id == 0) {
                    hsa_compliency_id = 1;
                    include_hsa_compliency.setVisibility(View.VISIBLE);
                } else {
                    hsa_compliency_id = 0;
                    include_hsa_compliency.setVisibility(View.GONE);
                }


            }
        });
        auditModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(auditId==0){
                    auditId=1;
                    auditView.setVisibility(View.VISIBLE);
                }else {
                    auditId=0;
                    auditView.setVisibility(View.GONE);
                }
            }
        });
        permitManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permitId == 0) {
                    permitId = 1;
                    permitView.setVisibility(View.VISIBLE);
                } else {
                    permitId = 0;
                    permitView.setVisibility(View.GONE);
                }

            }
        });
        jobSafteyAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (jobsaftiId == 0) {
                    jobsaftiId = 1;

                } else {
                    jobsaftiId = 0;

                }*/
                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Job Saftey Analysis");
                startActivity(i);

            }
        });
        fatigueManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fatiguId == 0) {
                    fatiguId = 1;
                    fatigueManagementView.setVisibility(View.VISIBLE);
                } else {
                    fatiguId = 0;
                    fatigueManagementView.setVisibility(View.GONE);
                }

            }
        });
        actionItemTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actioonId == 0) {
                    actioonId = 1;
                    actionItemTrackingView.setVisibility(View.VISIBLE);
                } else {
                    actioonId = 0;
                    actionItemTrackingView.setVisibility(View.GONE);
                }

            }
        });
        riskManagemnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (riskId == 0) {
                    riskId = 1;
                    riskMangementView.setVisibility(View.VISIBLE);
                } else {
                    riskId = 0;
                    riskMangementView.setVisibility(View.GONE);
                }

            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (staticId == 0) {
                    staticId = 1;
                    staticsView.setVisibility(View.VISIBLE);
                } else {
                    staticId = 0;
                    staticsView.setVisibility(View.GONE);
                }

            }
        });
        equpmentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),HSAAhivementActivity.class);
                i.putExtra("Screen","HSE Equipment Register");
                startActivity(i);

            }
        });
        contractorMangement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contracoterId == 0) {
                    contracoterId = 1;
                    contaroctorView.setVisibility(View.VISIBLE);
                } else {
                    contracoterId = 0;
                    contaroctorView.setVisibility(View.GONE);
                }

            }
        });
        employeeSaftyClimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Employee Safety Climate Survey");
                startActivity(i);

            }
        });
        disciplinaryActioRegistery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Disciplinary Actions Register");
                startActivity(i);
            }
        });
        processSaftyElements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Process Safety Elements");
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        try {
            fragmentAdapter = new FragmentAdapterClass(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(fragmentAdapter);
            //  viewPager.setPageTransformer(true, new CubeOutTransformer());
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        int limit = (fragmentAdapter.getCount() > 1 ? fragmentAdapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab LayoutTab) {



                viewPager.setCurrentItem(LayoutTab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab LayoutTab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab LayoutTab) {


            }
        });


        View includedLayout = findViewById(R.id.hsa_achivement);


        TextView insideTheIncludedLayout = (TextView) includedLayout.findViewById(R.id.award_photos);
        insideTheIncludedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HSAAhivementActivity.class);
                startActivity(intent);
                /*HSAAchievePhotosFragment fragment = new HSAAchievePhotosFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.commit();*/

             //   drawer.closeDrawer(GravityCompat.START);
            }
        });

        LinearLayout ll_hse_home=(LinearLayout)findViewById(R.id.linear_hse_home);
        ll_hse_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }

            }
        });
   LinearLayout ll_profile=(LinearLayout)findViewById(R.id.linear_profile);
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent i=new Intent(getApplicationContext(),MyProfileActivity.class);
              startActivity(i);
            }
        });
        LinearLayout ll_change_password=(LinearLayout)findViewById(R.id.linear_change_password);
        ll_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent i=new Intent(getApplicationContext(), ChangePasswordActivity.class);
              startActivity(i);
            }
        });

        LinearLayout ll_acheviments_photos=(LinearLayout)achivementView.findViewById(R.id.linear_photos);
        ll_acheviments_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","HSE Acheivements Phots");
                startActivity(i);

            }
        });
        LinearLayout ll_contractor_evaluations=(LinearLayout)contaroctorView.findViewById(R.id.linear_assessment_system);
        ll_contractor_evaluations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Contactor Evaluations –\n" +
                        "HSE Capability Assessment System");
                startActivity(i);

            }
        });

        LinearLayout ll_crisis=(LinearLayout)findViewById(R.id.linear_crisis_management) ;
        ll_crisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (crisisId == 0) {
                    crisisId = 1;
                    crisisManagemntView.setVisibility(View.VISIBLE);
                } else {
                    crisisId = 0;
                    crisisManagemntView.setVisibility(View.GONE);
                }

              /*  Intent i=new Intent(getApplicationContext(), CrisisManagement.class);
                startActivity(i);*/
            }
        });
        LinearLayout ll_equipment=(LinearLayout)findViewById(R.id.linear_equipment_management) ;
        ll_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (equipmentId == 0) {
                    equipmentId = 1;
                    equipmentMgmtView.setVisibility(View.VISIBLE);
                } else {
                    equipmentId = 0;
                    equipmentMgmtView.setVisibility(View.GONE);
                }

            }
        });

        LinearLayout equipementsAssigned=(LinearLayout)equipmentMgmtView.findViewById(R.id.linear_equpment_assigned_to_you);
        equipementsAssigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String action;
                Intent i=new Intent(getApplicationContext(), EquipmentsAssignedToYouActivity.class);
                startActivity(i);
            }
        });
        LinearLayout headCountTracker=(LinearLayout)crisisManagemntView.findViewById(R.id.linear_head_count_tracker);
        headCountTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(getApplicationContext(), HeadCountTrackerActivity.class);
                startActivity(i);
            }
        });
        LinearLayout ll_cevs=(LinearLayout)crisisManagemntView.findViewById(R.id.linear_cevs);
        ll_cevs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), CEVSActivity.class);
                startActivity(i);
            }
        });
        LinearLayout ll_emergency_crisis_notification=(LinearLayout)crisisManagemntView.findViewById(R.id.linear_crisis_notification);
        ll_emergency_crisis_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), EmergencyAndCrisisNotificationActivity.class);
                startActivity(i);
            }
        });
        LinearLayout ll_hse_action_tracker=(LinearLayout)actionItemTrackingView.findViewById(R.id.linear_project_hse_action_tracker);
        ll_hse_action_tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSEActionTrackerActivity.class);
                //i.putExtra("Screen","Project HSE Action Tracker");
                startActivity(i);

            }
        });
        LinearLayout ll_letters=(LinearLayout)achivementView.findViewById(R.id.linear_letters);
        ll_letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementLettersActivity.class);
              //  i.putExtra("Screen","HSE Acheivements Appreciation Lettters");
                startActivity(i);

            }
        });
        LinearLayout ll_bullet=(LinearLayout)communicationView.findViewById(R.id.linear_hse_bulletin);
        ll_bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSEBulletin.class);
             //   i.putExtra("Screen","HSE Bulletin");
                startActivity(i);

            }
        });
        LinearLayout ll_hse_alert=(LinearLayout)communicationView.findViewById(R.id.linear_hse_alerts);
        ll_hse_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSEAlertsActivity.class);
                //i.putExtra("Screen","HSE Alerts");
                startActivity(i);

            }
        });
        LinearLayout ll_imediate_notify=(LinearLayout)incidentmangementView.findViewById(R.id.linear_immediate_notification);
        ll_imediate_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), AddInitialIncidentNotification.class);

                startActivity(i);

            }
        });
        LinearLayout ll_inr=(LinearLayout)incidentmangementView.findViewById(R.id.linear_inr);
        ll_inr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), IncidentNotificationReport.class);
                startActivity(i);

            }
        });
        LinearLayout ll_iir=(LinearLayout)incidentmangementView.findViewById(R.id.linear_iir);
        ll_iir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityIncidentReportActivity2.class);
               // i.putExtra("Screen","Security Incident Report");
                startActivity(i);

            }
        });
        LinearLayout ll_corrective_action=(LinearLayout)incidentmangementView.findViewById(R.id.linear_corrective_action);
        ll_corrective_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), CorrectiveActionsReportGeneratorActivity.class);

                startActivity(i);

            }
        });
        LinearLayout ll_pws_green=(LinearLayout)permitView.findViewById(R.id.linear_pws_green);
        ll_pws_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Permit to Work System (Green Field)");
                startActivity(i);

            }
        });
        LinearLayout ll_pws_brown=(LinearLayout)permitView.findViewById(R.id.linear_pws_brown);
        ll_pws_brown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Permit to Work System (Brown Field)");
                startActivity(i);

            }
        });
        LinearLayout ll_pwts=(LinearLayout)permitView.findViewById(R.id.linear_pwts);
        ll_pwts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Permit to Work Tracking Systems");
                startActivity(i);

            }
        });
        LinearLayout ll_fatigue_monitor=(LinearLayout)fatigueManagementView.findViewById(R.id.linear_fatigue_managment);
        ll_fatigue_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Fatigue Monitoring System");
                startActivity(i);

            }
        });
        LinearLayout ll_online_passport=(LinearLayout)trainingPassportView.findViewById(R.id.linear_online_passport);
        ll_online_passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Online Passport");
                startActivity(i);

            }
        });
        LinearLayout ll_online_trainging_register=(LinearLayout)trainingPassportView.findViewById(R.id.linear_online_training_register);
        ll_online_trainging_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Online Training register");
                startActivity(i);

            }
        });
        LinearLayout ll_project_statistics=(LinearLayout)staticsView.findViewById(R.id.linear_project_statistics);
        ll_project_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Project HSE Statistics");
                startActivity(i);

            }
        });
        LinearLayout ll_corporate_statistics=(LinearLayout)staticsView.findViewById(R.id.linear_corporate_statistics);
        ll_corporate_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSAAhivementActivity.class);
                i.putExtra("Screen","Corporate HSE Statistics");
                startActivity(i);

            }
        });
        LinearLayout ll_competencyassesment=(LinearLayout)empMgmtView.findViewById(R.id.linear_competency_assessment);
        ll_competencyassesment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), NoItemsActivity.class);
                i.putExtra("Screen","Competency Assessment");
                startActivity(i);

            }
        });
          LinearLayout ll_mobilisation=(LinearLayout)empMgmtView.findViewById(R.id.linear_mobilisation);
        ll_mobilisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), NoItemsActivity.class);
                i.putExtra("Screen","De-Mobillisation of Employee");
                startActivity(i);

            }
        });
     LinearLayout ll_online_training=(LinearLayout) onlineTrainingView.findViewById(R.id.linear_hse_online_passport);
        ll_online_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSETrainingPassport.class);

                startActivity(i);

            }
        });
  LinearLayout ll_job_category_questions=(LinearLayout)employeeHSEView.findViewById(R.id.linear_questions);
        ll_job_category_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), JobCategoryQuestions.class);

                startActivity(i);

            }
        });

 LinearLayout ll_ca_hse_policies=(LinearLayout)managementSysView.findViewById(R.id.linear_ca_hse_policies);
        ll_ca_hse_policies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), CAHSAPolicies.class);
                startActivity(i);

            }
        });

  LinearLayout ll_ca_hse_letters=(LinearLayout)managementSysView.findViewById(R.id.linear_ca_hse_letters);
        ll_ca_hse_letters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSEManualsAbdSubManuals.class);
                startActivity(i);

            }
        });
  LinearLayout ll_ca_hse_procedures=(LinearLayout)managementSysView.findViewById(R.id.linear_ca_hse_procedures);
        ll_ca_hse_procedures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), HSEProceduresActivity.class);
                startActivity(i);

            }
        });
  LinearLayout ll_client_procedure=(LinearLayout)managementSysView.findViewById(R.id.linear_client_wise_procedure);
        ll_client_procedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), ClientWiseProcedureActivity.class);
                startActivity(i);

            }
        });

        LinearLayout ll_new_observation=(LinearLayout)cSafeObserView.findViewById(R.id.linear_new_observation) ;

        ll_new_observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),NewObservationActivity.class);
                i.putExtra("ScreenName","New Observation");
                i.putExtra("Platform","observation");
                startActivity(i);
            }
        });
  LinearLayout ll_top_observation=(LinearLayout)cSafeObserView.findViewById(R.id.linear_top_observe) ;

        ll_top_observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), TopObserverActivity.class);
                startActivity(i);
            }
        });
  LinearLayout ll_view_observation=(LinearLayout)cSafeObserView.findViewById(R.id.linear_view_observation) ;

        ll_view_observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ViewObservationsActivity.class);
              //  i.putExtra("Screen","My Observations");
                startActivity(i);
            }
        });
  LinearLayout ll_observation_action=(LinearLayout)cSafeObserView.findViewById(R.id.linear_observation_action) ;

        ll_observation_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), ObservationForActionActivity.class);
                startActivity(i);
            }
        });

        tv_view_my_observation=(TextView)findViewById(R.id.textview_view_observation) ;
        tv_observatio_for_action=(TextView)findViewById(R.id.textview_obser_for_action) ;
  LinearLayout ll_observer_statistics=(LinearLayout)cSafeObserView.findViewById(R.id.linear_observer_statistics) ;

        ll_observer_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), ObservationStatisticsActivity.class);
                i.putExtra("Screen","Observation Statistics");
                startActivity(i);
            }
        });
 LinearLayout ll_project_risk=(LinearLayout)riskMangementView.findViewById(R.id.linear_risk_register) ;

        ll_project_risk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),HSAAhivementActivity.class);
                i.putExtra("Screen","Project Risk Register");
                startActivity(i);
            }
        });
  LinearLayout ll_hazard_register=(LinearLayout)riskMangementView.findViewById(R.id.linear_hazard_register) ;

        ll_hazard_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),HSAAhivementActivity.class);
                i.putExtra("Screen","Construction Hazard Register");
                startActivity(i);
            }
        });
  LinearLayout ll_hse_tracking_register=(LinearLayout)riskMangementView.findViewById(R.id.linear_hse_tracking_register) ;

        ll_hse_tracking_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),HSAAhivementActivity.class);
                i.putExtra("Screen","Tech HSE Action Tracking Register");
                startActivity(i);
            }
        });

        LinearLayout ll_confinedSpace=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_confinedspace);
        ll_confinedSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Confined Space Work");
                startActivity(intent);
            }
        });

        LinearLayout ll_excavation=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_excavations);
        ll_excavation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Excavations");
                startActivity(intent);
            }
        });
        LinearLayout ll_heavy_equipment=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_heavy_equipment);
        ll_heavy_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Heavy Equipment and Vehicle Operations");
                startActivity(intent);
            }
        });
  LinearLayout ll_hot_works=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_hot_works);
        ll_hot_works.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Hot Works");
                startActivity(intent);
            }
        });
        LinearLayout ll_lifting=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_lifting_hoisting);
        ll_lifting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Lifting and Hoisting");
                startActivity(intent);
            }
        });
        LinearLayout ll_dropped_object_prevention=(LinearLayout)include_hsa_compliency.findViewById(R.id.dropped_object_prevention);
        ll_dropped_object_prevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Dropped Object Prevention");
                startActivity(intent);
            }
        });
 LinearLayout ll_safe_isolation=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_safe_isolation);
        ll_safe_isolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Safe Isolation of Energy");
                startActivity(intent);
            }
        });
LinearLayout ll_scaffolding=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_scaffolding);
        ll_scaffolding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Scaffolding and other forms of access");
                startActivity(intent);
            }
        });
LinearLayout ll_simops=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_simops);
        ll_simops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Simultaneous Operations");
                startActivity(intent);
            }
        });
LinearLayout ll_working_height=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_working_height);
        ll_working_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Working at Height");
                startActivity(intent);
            }
        });
LinearLayout ll_house_keeping=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_house_keeping);
        ll_house_keeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Housekeeping");
                startActivity(intent);
            }
        });
LinearLayout ll_lof=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_line_of_fire);
        ll_lof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Line of Fire");
                startActivity(intent);
            }
        });
LinearLayout ll_personal_protective=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_personal_protective);
        ll_personal_protective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Personal protective Equipment");
                startActivity(intent);
            }
        });
    LinearLayout ll_routine_task=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_routine_task);
        ll_routine_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Routine Life Task");
                startActivity(intent);
            }
        });
    LinearLayout ll_barricades=(LinearLayout)include_hsa_compliency.findViewById(R.id.linear_barricades);
        ll_barricades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Barricades and Open Holes");
                startActivity(intent);
            }
        });
  LinearLayout ll_welfare_and_welness_audit=(LinearLayout)auditView.findViewById(R.id.linear_welfare_welness_audit);
        ll_welfare_and_welness_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","Welfare and Welness Audit");
                startActivity(i);
            }
        });
        LinearLayout ll_camp_audit=(LinearLayout)auditView.findViewById(R.id.linear_camp_audit);
        ll_camp_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","Camp Audit");
                startActivity(i);
            }
        });
        LinearLayout ll_hse_mgmt_audit=(LinearLayout)auditView.findViewById(R.id.linear_hse_mgmt_audit);
        ll_hse_mgmt_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","HSE Managemnet System Element Audit");
                startActivity(i);
            }
        });
    LinearLayout ll_high_risk_activity=(LinearLayout)auditView.findViewById(R.id.linear_high_risk_activity);
        ll_high_risk_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","High Risk Activity");
                startActivity(i);
            }
        });
 LinearLayout ll_environmental_audir=(LinearLayout)auditView.findViewById(R.id.linear_environmental_audit);
        ll_environmental_audir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","Environmental Audit");
                startActivity(i);
            }
        });
 LinearLayout ll_waste_mgmt=(LinearLayout)auditView.findViewById(R.id.linear_wase_mgmt_audit);
        ll_waste_mgmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","Waste Management Audit");
                startActivity(i);
            }
        });
LinearLayout ll_bbs_audit=(LinearLayout)auditView.findViewById(R.id.linear_bbs_audit);
        ll_bbs_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","BBS Audit");
                startActivity(i);
            }
        });
LinearLayout ll_permit_to_work_audit=(LinearLayout)auditView.findViewById(R.id.linear_permit_to_work_audit);
        ll_permit_to_work_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","Permit to Work Audit");
                startActivity(i);
            }
        });

        LinearLayout ll_security_program_audit=(LinearLayout)auditView.findViewById(R.id.linear_security_program_audit);
        ll_security_program_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","Security Program Audit");
                startActivity(i);
            }
        });
LinearLayout ll__audit_subcontractors=(LinearLayout)auditView.findViewById(R.id.linear_audit_subcontractors);
        ll__audit_subcontractors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","SHES Audit of Subcontractors");
                startActivity(i);
            }
        });
LinearLayout ll__hear_stress_audit=(LinearLayout)auditView.findViewById(R.id.linear_hear_stress_audit);
        ll__hear_stress_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","Heat Stress Audit");
                startActivity(i);
            }
        });
LinearLayout ll_action_card_audit=(LinearLayout)auditView.findViewById(R.id.linear_action_card_audit);
        ll_action_card_audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), SecurityProgramAuditActivity.class);
                i.putExtra("Screen","PTST LMRA and IIF Action Card Audit");
                startActivity(i);
            }
        });

        TextView onvinedspace = (TextView) include_hsa_compliency.findViewById(R.id.onvinedspace);
        onvinedspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfinedSpaceWorkActivity.class);
                intent.putExtra("Screen","Confined Space Work");
                startActivity(intent);
            }
        });


        genral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firsr == 0) {

                    general_list.setVisibility(View.VISIBLE);
                    tools_list.setVisibility(View.GONE);
                    profile_list.setVisibility(View.GONE);
                    firsr = 1;
                } else {

                    general_list.setVisibility(View.GONE);
                    tools_list.setVisibility(View.GONE);
                    profile_list.setVisibility(View.GONE);
                    firsr = 0;
                }

            }
        });

        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMyOservation();
                viewObservationForActon();

                if (second == 0) {

                    general_list.setVisibility(View.GONE);
                    tools_list.setVisibility(View.VISIBLE);
                    profile_list.setVisibility(View.GONE);
                    second = 1;
                } else {

                    general_list.setVisibility(View.GONE);
                    tools_list.setVisibility(View.GONE);
                    profile_list.setVisibility(View.GONE);
                    second = 0;
                }

            }
        });
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (third == 0) {

                    general_list.setVisibility(View.GONE);
                    profile_list.setVisibility(View.VISIBLE);
                    tools_list.setVisibility(View.GONE);
                    third = 1;
                } else {

                    general_list.setVisibility(View.GONE);
                    tools_list.setVisibility(View.GONE);
                    profile_list.setVisibility(View.GONE);
                    third = 0;
                }

            }
        });


    }
    public void viewObservationForActon(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.viewObservationForAction, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                if(!response.matches("")){

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("Data")){

                            JSONArray jsonArray=new JSONArray();
                            jsonArray=jsonObject.getJSONArray("Data");
                            String size= String.valueOf(jsonArray.length());
                            if(jsonArray.length()>0){
                                tv_observatio_for_action.setText("Observation for Action ( "+size+" )" );
                            }



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    tv_observatio_for_action.setText("Observation for Action ( 0 )" );
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put(StringConstants.inputactionbyId, s_user_id);
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }

    public void viewMyOservation(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.viewMyObservation, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("Response",response);

                if(!response.matches("")){

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("Data")){

                            JSONArray jsonArray=new JSONArray();
                            jsonArray=jsonObject.getJSONArray("Data");
                            String size= String.valueOf(jsonArray.length());

                            tv_view_my_observation.setText("View My Observation ( "+size+" )" );


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {

                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put(StringConstants.inputEnterbyId, s_user_id);
                return MyData;
            }
        };

        requestQueue.add(MyStringRequest);

    }

    public class FragmentAdapterClass extends FragmentStatePagerAdapter {

        int TabCount;

        public FragmentAdapterClass(FragmentManager fragmentManager, int CountTabs) {

            super(fragmentManager);

            this.TabCount = CountTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    HomeTab tab1 = new HomeTab();
                    return tab1;
                case 1:
                    IntegratedManagementTab tab2 = new IntegratedManagementTab();
                    return tab2;
                case 2:
                    GoldenRules tab3 = new GoldenRules();
                    return tab3;
                case 3:
                    LifeSavingRuls business_tab = new LifeSavingRuls();
                    return business_tab;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TabCount;
        }
    }


    @Override
    public void onBackPressed() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        finish();
                        moveTaskToBack(true);
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
    }


}