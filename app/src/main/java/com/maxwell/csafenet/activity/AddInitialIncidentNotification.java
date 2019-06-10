package com.maxwell.csafenet.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;
import com.maxwell.csafenet.StringConstants;
import com.maxwell.csafenet.model.DropDownDetails;
import com.maxwell.csafenet.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddInitialIncidentNotification extends AppCompatActivity {

    Spinner  location, project, actionBy;
    EditText et_what_happened,et_action_going,et_corrective_action;
    TextView daate, time;
    ArrayList<String> locationrrayList = new ArrayList<String>();
    ArrayList<String> projectrrayList = new ArrayList<String>();
    String companyid, projctId, locationId, actionById, filePath = "",safetyCategoryID;
    ArrayList<String> actionbyList = new ArrayList<String>();
    Button button_submit;
    DropDownDetails dropDownDetails;
    ArrayAdapter<String> dataAdapter;
    String s_user_id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView tv_view_iin;
    Map<String, String> notifyPersonsMap = null;
    TextView text_selected_action_by_persons;
    private CharSequence[] selected_persons;
    String educatioPosi;
    ArrayList<String> selectedPersonsList=new ArrayList<>();
    Button SelectGlarry;
    TextView   imageUriName;
    private Bitmap bitmap;
    private static final int SELECT_PICTURE = 100,REQUEST_CAMERA = 1888;
    final int PERMISSION_REQUEST_CODE=123;
    List<DropDownDetails> dropDownDetailsList,dropDownDetailsList1,dropDownDetailsList2,dropDownDetailsList3,dropDownDetailsList4,dropDownDetailsList5,dropDownDetailsList6,dropDownDetailsList7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_initial_incident_notification);

        tv_view_iin=(TextView)findViewById(R.id.text_view_initial_incident_notification);
        text_selected_action_by_persons = (TextView) findViewById(R.id.selected_text);

        location = (Spinner) findViewById(R.id.location);
        project = (Spinner) findViewById(R.id.project);
        actionBy = (Spinner) findViewById(R.id.actionby);
        et_what_happened=(EditText)findViewById(R.id.edit_what_happened);
        et_action_going=(EditText)findViewById(R.id.edit_what_actions_going);
        et_corrective_action=(EditText)findViewById(R.id.edt_imm_corre_action);
        daate = (TextView) findViewById(R.id.datee);
        time = (TextView) findViewById(R.id.edt_time);
        SelectGlarry = (Button) findViewById(R.id.chooseFile);
        imageUriName = (TextView) findViewById(R.id.image_uri);
        button_submit=(Button)findViewById(R.id.submit);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        s_user_id=preferences.getString(StringConstants.prefEmployeeId,"");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = sdf.format(new Date());
        DateFormat df = new SimpleDateFormat(" hh:mm a");
        String currentTime = df.format(Calendar.getInstance().getTime());
        Log.i("StringDate", currentTime);
        daate.setText(currentDate);
        time.setText(currentTime);

        SelectGlarry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }
        });

        tv_view_iin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(AddInitialIncidentNotification.this,ImmediateNotificationSystemActivity.class);
                startActivity(i);
            }
        });
        locationrrayList.add("-Select Location-");
        projectrrayList.add("-Select Project-");

        new ProjectsListOperation().execute();
        new LocationListOperation().execute();
        new ActionByOperation().execute();

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!project.getSelectedItem().toString().equals("-Select Project-") && !project.getSelectedItem().toString().equals("")) {
                    int selectedProgram_id = project.getSelectedItemPosition();
                    projctId = dropDownDetailsList.get(selectedProgram_id - 1).getProjectsId();
                    if (!location.getSelectedItem().toString().equals("-Select Location-") && !location.getSelectedItem().toString().equals("")) {
                        int selectedLocation_id = location.getSelectedItemPosition();
                        locationId = dropDownDetailsList2.get(selectedLocation_id - 1).getLocationId();
                        if(!et_what_happened.getText().toString().isEmpty()){
                            if(!et_action_going.getText().toString().isEmpty()){
                                if(!et_corrective_action.getText().toString().isEmpty()){
                                    if (!actionBy.getSelectedItem().toString().equals("-Select Action-") && !actionBy.getSelectedItem().toString().equals("")) {
                                        int selectedActionby_id = actionBy.getSelectedItemPosition();
                                       // actionById = dropDownDetailsList6.get(selectedActionby_id - 1).getActionById();
                                        actionById=educatioPosi;

                                        new InsertNewObservationOperation().execute();

                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddInitialIncidentNotification.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(AddInitialIncidentNotification.this);
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                    //  dispatchTakePictureIntent();

                } else if (items[item].equals("Choose from Library")) {
                    openImageChooser();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public  void back(View view){
        onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("AddInitialNotification", "Image Path : " + path);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    filePath = encodedImage;
                    // Set the image in ImageView
                    if (path != null) {
                        File f = new File(path);
                        String imageName = f.getName();
                        imageUriName.setText(imageName);

                    }
                    //  Toast.makeText(getApplicationContext(),filePath, Toast.LENGTH_SHORT).show();

                }
            } else if (requestCode == REQUEST_CAMERA){

              /*  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                byte[] imageBytes = bytes.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                filePath = encodedImage;
*/
                onCaptureImageResult(data);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] imageBytes = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        filePath = encodedImage;
        File destination = new File(getApplicationContext().getFilesDir().getPath(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Uri fileUri=data.getData();
       /*Uri fileUri=getImageUri(DriverJobListActivity.this,thumbnail);
        cameraImagePath=getPath(getApplicationContext(),fileUri);*/


        Uri fileUri=Uri.fromFile(destination);
        String  cameraImagePath=getPath(getApplicationContext(),fileUri);
        //cameraImagePath=destination.getParent();

        String filename=cameraImagePath.substring(cameraImagePath.lastIndexOf(File.separator)+1);
        if(cameraImagePath!=null&&cameraImagePath!=""&&cameraImagePath.length()>0){
            imageUriName.setText(filename);
        }
        //  Toast.makeText(getApplicationContext(),filePath, Toast.LENGTH_SHORT).show();

       /* }else {

            Toast.makeText(getApplicationContext(),"Storage Permission Denied", Toast.LENGTH_SHORT).show();
        }
*/

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    private class ProjectsListOperation extends AsyncTask<String, Void,String> {
        ProgressDialog pDialog=new ProgressDialog(AddInitialIncidentNotification.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(AddInitialIncidentNotification.this);

        @Override
        protected String doInBackground(String... strings) {
            dropDownDetailsList=new ArrayList<>();
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.projectsListUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("project")){

                            JSONArray jsonArray=jsonObject.getJSONArray("project");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject dataObject=jsonArray.getJSONObject(i);
                                dropDownDetails=new DropDownDetails();
                                dropDownDetails.setProject(dataObject.getString("project"));
                                dropDownDetails.setProjectsId(dataObject.getString("id"));

                                projectrrayList.add(dataObject.getString("project"));
                                dropDownDetailsList.add(dropDownDetails);
                            }

                            dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, projectrrayList);
                            project.setAdapter(dataAdapter);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        
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
                    return MyData;
                }
            };

            requestQueue.add(MyStringRequest);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading..");
            pDialog.setTitle("");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    private class LocationListOperation extends AsyncTask<String, Void,String> {
        ProgressDialog pDialog=new ProgressDialog(AddInitialIncidentNotification.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(AddInitialIncidentNotification.this);
        @Override
        protected String doInBackground(String... strings) {
            dropDownDetailsList2=new ArrayList<>();
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.locationListUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("location")){

                            JSONArray jsonArray=jsonObject.getJSONArray("location");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject dataObject=jsonArray.getJSONObject(i);
                                dropDownDetails=new DropDownDetails();
                                dropDownDetails.setLocation(dataObject.getString("location"));
                                dropDownDetails.setLocationId(dataObject.getString("id"));
                                locationrrayList.add(dataObject.getString("location"));
                                dropDownDetailsList2.add(dropDownDetails);
                            }

                            dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, locationrrayList);
                            location.setAdapter(dataAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    return MyData;
                }
            };

            requestQueue.add(MyStringRequest);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading..");
            pDialog.setTitle("");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog.isShowing())
                pDialog.dismiss();
        }
    }
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    private class ActionByOperation extends AsyncTask<String, Void,String> {
        ProgressDialog pDialog=new ProgressDialog(AddInitialIncidentNotification.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(AddInitialIncidentNotification.this);
        @Override
        protected String doInBackground(String... strings) {
            dropDownDetailsList6=new ArrayList<>();
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.actionByListUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("actionby")){

                            JSONArray jsonArray=jsonObject.getJSONArray("actionby");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject dataObject=jsonArray.getJSONObject(i);
                                dropDownDetails=new DropDownDetails();
                                dropDownDetails.setActionBy(dataObject.getString("fname"));
                                dropDownDetails.setActionById(dataObject.getString("id"));
                                //listRootCause.add(dataObject.getString("name"));
                                dropDownDetailsList6.add(dropDownDetails);

                                actionbyList.add(dataObject.getString("fname"));
                                //notifyPersonsMap.put(dataObject.getString("id"), dataObject.getString("fname"));;
                            }
                            dataAdapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_spinner_item, actionbyList);
                            actionBy.setAdapter(dataAdapter);

                            text_selected_action_by_persons.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    selected_persons=actionbyList.toArray(new CharSequence[actionbyList.size()]);
                                    showSelectColoursDialog(dropDownDetailsList6);
                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    MyData.put(StringConstants.inputUserID, s_user_id);
                    return MyData;
                }
            };

            requestQueue.add(MyStringRequest);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading..");
            pDialog.setTitle("");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog.isShowing())
                pDialog.dismiss();
        }
    }


    protected void onChangeSelectedColours() {

        StringBuilder stringBuilder = new StringBuilder();
        educatioPosi="";

        for (Map.Entry<String, String> entry : notifyPersonsMap.entrySet()) {


            String  educatioPosition=entry.getKey().toString();
            educatioPosi+=educatioPosition+",";




            // selectedPosition = position;
        }
        // educatioPosi=educatioPosi.substring(1);
       // educatioPosi=educatioPosi.replaceAll(" ","%20");


        for(CharSequence colour : selectedPersonsList)

            stringBuilder.append(colour + ",");

        //  Toast.makeText(getActivity(),stringBuilder.toString(),Toast.LENGTH_LONG).show();

        text_selected_action_by_persons.setText(stringBuilder.toString());

        String[] education_array = stringBuilder.toString().split(",");

        if(education_array.length>=4){
            text_selected_action_by_persons.setText(education_array[0]+", "+education_array[1]+", "+education_array[2]+" + "+String.valueOf(education_array.length-3)+"More Items");
        }else {
            text_selected_action_by_persons.setText(stringBuilder.toString());
        }

        //selectColoursButton.setText(stringBuilder.toString());

    }



    private class InsertNewObservationOperation extends AsyncTask<String, Void,String> {

        ProgressDialog pDialog=new ProgressDialog(AddInitialIncidentNotification.this);
        String result="";
        RequestQueue requestQueue = Volley.newRequestQueue(AddInitialIncidentNotification.this);
        @Override
        protected String doInBackground(String... strings) {
            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, StringConstants.mainUrl + StringConstants.addInitialIncidentNotificationUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //This code is executed if the server responds, whether or not the response contains data.
                    //The String 'response' contains the server's response.
                    Log.d("Response",response);

                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());

                        if(jsonObject.has("Register")){
                            JSONObject object=jsonObject.getJSONObject("Register");
                            result=object.getString("response");
                            if(result.matches("success")){
                                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(AddInitialIncidentNotification.this, MainActivity.class);
                                startActivity(i);
                            }else {
                                showAlertDialog("Failed");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
/*
                    try {
                        JSONObject jsonObject=new JSONObject(response.trim());
                        if(jsonObject.has("Data")){

                            JSONArray jsonArray=jsonObject.getJSONArray("Data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject dataObject=jsonArray.getJSONObject(i);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
*/
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    //This code is executed if there is an error.
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put(StringConstants.inputProject, projctId);
                    MyData.put(StringConstants.inputLocation,locationId);
                    MyData.put(StringConstants.inputDate,daate.getText().toString());
                    MyData.put(StringConstants.inputTime, time.getText().toString());
                   MyData.put(StringConstants.inputHappen,et_what_happened.getText().toString().trim());
                   MyData.put(StringConstants.inputActionTaken,et_action_going.getText().toString().trim());
                   MyData.put(StringConstants.inputActionCorrection,et_corrective_action.getText().toString().trim());
                    MyData.put(StringConstants.inputPostBy, s_user_id);
                    MyData.put(StringConstants.inputUserID, actionById);
                    MyData.put(StringConstants.inputIncidentImage,filePath);

                    return MyData;
                }
            };

            requestQueue.add(MyStringRequest);
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Loading..");
            pDialog.setTitle("");
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    public void showAlertDialog(String message){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
        // requestPermissions( new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_REQUEST_CODE);
    }

    private void cameraIntent()
    {
        if(checkPermission()){
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(AddInitialIncidentNotification.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    protected void showSelectColoursDialog(final List<DropDownDetails> dropDownDetails ) {

        boolean[] checkedColours = new boolean[selected_persons.length];
        notifyPersonsMap=new HashMap<>();


        int count = selected_persons.length;

        for(int i = 0; i < count; i++)

            checkedColours[i] = selectedPersonsList.contains(selected_persons[i]);



        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            @SuppressLint("NewApi")
            @Override

            public void onClick(DialogInterface dialog, int which, boolean isChecked) {


                if(isChecked){
                    selectedPersonsList.add(String.valueOf(selected_persons[which]));
                    notifyPersonsMap.put(dropDownDetails.get(which).getActionById(),dropDownDetails.get(which).getActionBy());
                }
                else
                {
                    selectedPersonsList.remove(selected_persons[which]);
                    notifyPersonsMap.remove(dropDownDetails.get(which).getActionById(),dropDownDetails.get(which).getActionBy());
                }


                onChangeSelectedColours();

            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddInitialIncidentNotification.this);

        builder.setTitle("Select Persons");

        builder.setMultiChoiceItems(selected_persons, checkedColours, coloursDialogListener);

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
