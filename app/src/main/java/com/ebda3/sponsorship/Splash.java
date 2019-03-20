package com.ebda3.sponsorship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebda3.sponsorship.Helpers.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ebda3.sponsorship.Helpers.Config.LoginUrl;

public class Splash extends AppCompatActivity {

    Context context = this;
    Activity activity = this;
    public String u_email,u_password,type = "";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        Log.e("tag","1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.e("tag","2");
        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        u_email = sp1.getString("json_email", " ");
        u_password = sp1.getString("password", " ");

        if (u_email.length() > 2 && u_password.length() > 2) {
            Log.d("checklogin", "checklogin");
            checklogin(u_email, u_password);
        } else {
            new CountDownTimer(3000, 1000) {
                public void onFinish() {
                    Intent intent = new Intent(Splash.this, Home.class);
                    startActivity(intent);
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();


        }

    }


    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    public void checklogin(final String user_email ,final String user_password)
    {



        boolean t = isNetworkConnected();
        if(t) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Log.d("ressss",response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                if (jObj.has("ID")) {


                                    if ( jObj.getString("active").equals("yes") )
                                    {
                                        SharedPreferences sp=getSharedPreferences("Login", 0);
                                        SharedPreferences.Editor Ed=sp.edit();
                                        Ed.putString("ID",jObj.getString("ID") );
                                        Ed.putString("name",jObj.getString("name") );
                                        Ed.putString("type",jObj.getString("type") );
                                        Ed.putString("active",jObj.getString("active") );
                                        Ed.putString("username",jObj.getString("username") );
                                        Ed.putString("photo",jObj.getString("photo") );
                                        Ed.putString("json_password",jObj.getString("password") );
                                        //Ed.putString("ShareUrl",jObj.getString("ShareUrl") );

                                        Ed.commit();


                                        finish();
                                    }
                                    else {
                                        Intent intent = new Intent(Splash.this, Activation.class);
                                        startActivity(intent);
                                        finish();
                                    }



                                }
                                else
                                {
                                    Intent intent = new Intent(Splash.this,Home.class);
                                    startActivity(intent);
                                }


                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                            //openProfile();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("do", "log");
                    map.put("android_key", Config.getFirebaseInstanceId() );
                    map.put("email", user_email);
                    map.put("password", user_password);
                    Log.d("params",map.toString());
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(Splash.this, "please Enable Your Internet Connection", Toast.LENGTH_LONG).show();
        }

    }
}
