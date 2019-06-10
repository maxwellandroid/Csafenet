package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.adapter.ViewPagerAdapter;
import com.maxwell.csafenet.model.OnlineTrainingDetails;
import com.viewpagerindicator.CirclePageIndicator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HSEOnlineTrainingSystemActivity extends AppCompatActivity {
    private Integer[] images = {R.drawable.csafebanner1, R.drawable.csafebanner2, R.drawable.csafebanner3};
    ViewPager viewPager;
    private static int currentPage = 0;
    private static int NUM_PAGES =4;

    OnlineTrainingDetails onlineTrainingDetails;

    Gson gson = new Gson();
    List<String> questionsList,option1List,option2List,option3List,imageUrlList,answers,questionIds,titles;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hseonline_training_system);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        questionsList=new ArrayList<>();
        option1List=new ArrayList<>();
        option2List=new ArrayList<>();
        option3List=new ArrayList<>();
        imageUrlList=new ArrayList<>();
        answers=new ArrayList<>();
        questionIds=new ArrayList<>();
        titles=new ArrayList<>();

        preferences= PreferenceManager.getDefaultSharedPreferences(HSEOnlineTrainingSystemActivity.this);
        String string = preferences.getString("listQuestions", "");
        Type type = new TypeToken<List<String>>() {
        }.getType();
        questionsList = gson.fromJson(string, type);
        String stringOption1 = preferences.getString("listOption1", "");
        Type type1 = new TypeToken<List<String>>() {
        }.getType();
        option1List = gson.fromJson(stringOption1, type1);
        String stringOption2 = preferences.getString("listOption2", "");
        Type type2 = new TypeToken<List<String>>() {
        }.getType();
        option2List = gson.fromJson(stringOption2, type2);
        String stringOption3 = preferences.getString("listOption3", "");
        Type type3 = new TypeToken<List<String>>() {
        }.getType();
        option3List = gson.fromJson(stringOption3, type3);
        String stringImages = preferences.getString("listImages", "");
        Type type4 = new TypeToken<List<String>>() {
        }.getType();
        imageUrlList = gson.fromJson(stringImages, type4);
  String stringAnswers = preferences.getString("listAnswers", "");
        Type type5 = new TypeToken<List<String>>() {
        }.getType();
        answers = gson.fromJson(stringAnswers, type5);
        String stringQuestionIds = preferences.getString("listQuestionIds", "");
        Type type6 = new TypeToken<List<String>>() {
        }.getType();
        questionIds = gson.fromJson(stringQuestionIds, type6);
        String stringTitles = preferences.getString("listTitles", "");
        Type type7 = new TypeToken<List<String>>() {
        }.getType();
        titles = gson.fromJson(stringTitles, type7);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(),imageUrlList);
        viewPager.setAdapter(viewPagerAdapter);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
      /*  Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
*/
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

    public  void back(View view){

       Intent i =new Intent(getApplicationContext(),MainActivity.class);
       startActivity(i);
    }


}
