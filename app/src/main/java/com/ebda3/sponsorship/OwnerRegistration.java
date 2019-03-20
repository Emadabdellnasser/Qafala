package com.ebda3.sponsorship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.ebda3.sponsorship.Helpers.Config;
import com.ebda3.sponsorship.Helpers.MarshMallowPermission;
import com.ebda3.sponsorship.Helpers.MultiPartUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static com.ebda3.sponsorship.R.id.upload;

public class OwnerRegistration extends AppCompatActivity {

    Context context = this;
    Activity activity = this;
    LinearLayout register;
    EditText phone,password;
    TextView location,locationName;
    ProgressBar progressBar;

    String Location = "";
    TextView location_name;

    int REQUEST_PLACE_PICKER = 200;

    String imageURL = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_registration);

        location = (TextView) findViewById(R.id.location);
        register = (LinearLayout) findViewById(R.id.register);

        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final MarshMallowPermission marshMallowPermission = new MarshMallowPermission(activity);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( phone.getText().toString().isEmpty() )
                {
                    phone.setError("برجاء كتابة رقم الجوال");
                }
                else if ( password.getText().toString().isEmpty() )
                {
                    password.setError("برجاء كتابة كلمة المرور");
                }
else  {
Intent Myintent = new Intent(context,Activation.class);
                    startActivity(Myintent);
                }
            }
        });


    }


    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void Login(final String user_phone ,final String user_password ,final String user_name,final String user_description) {

        boolean t = isNetworkConnected();
        if (t) {

            SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
            String lang = sp1.getString("lang", " ");




            progressBar.setVisibility(View.VISIBLE);
            password.setEnabled(false);
            phone.setEnabled(false);
            register.setVisibility(View.GONE);

            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String charset = "UTF-8";
                        MultiPartUtility multipart = new MultiPartUtility(charset);
                        HashMap<String, String> params = new HashMap<>();
                        params.put("do", "insert");
                        params.put("name", user_name);
                        params.put("username", "00966"+user_phone);
                        params.put("password", user_password);
                        multipart.execute(Config.SignUpWorkshop);
                        final String Response = multipart.get();
                        Log.e("Response", params.toString());
                        Log.e("Response", Response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject object = new JSONObject(Response);
                                    if ( object.has("ID") )
                                    {
                                        SharedPreferences sp=getSharedPreferences("Login", 0);
                                        SharedPreferences.Editor Ed=sp.edit();
                                        Ed.putString("ID",object.getString("ID").toString() );
                                        Ed.putString("active",object.getString("active").toString() );
                                        Ed.putString("type",object.getString("type").toString() );
                                        Ed.putString("name",object.getString("name").toString() );
                                        Ed.putString("email","00966"+object.getString("email").toString() );
                                        Ed.putString("photo",object.getString("photo").toString() );
                                        Ed.putString("password", user_password);
                                        Ed.putString("json_password",object.getString("password") );
                                        Ed.putString("json_email",object.getString("username") );
                                        Ed.commit();
                                        Intent intent;
                                        if ( object.getString("active").toString().equals("no") )
                                        {
                                            intent = new Intent(context,Activation.class);
                                        }
                                        else
                                        {
                                        }
                                        finish();
                                    }
                                }catch (Exception e)
                                {
                                    phone.setEnabled(true);
                                    register.setVisibility(View.VISIBLE);
                                    JSONArray array = null;
                                    try {
                                        array = new JSONArray(Response);
                                        if ( array.getString(0).equals("Field password  From 6 To 20") ) {
                                            password.setError("كلمة السر صعيفة جدا");
                                            password.setText("");
                                        }
                                        else if ( array.getString(0).equals("Field username  Already exist") ) {
                                            phone.setError("رقم الموبيل مستخدم مسبقا");
                                            phone.setText("");
                                        }
                                        //Toast.makeText(activity, array.getString(0), Toast.LENGTH_SHORT).show();
                                        Log.d("response",  array.getString(0));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        });

                    }
                    catch (Exception e)
                    {
                        Log.e("Response", e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "برجاء المحاولة مرة اخرى", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                }
            });
            th.start();
        }
        else
        {
            Toast.makeText(context, "please Enable Your Internet Connection", Toast.LENGTH_LONG).show();
        }
    }




}
