package com.ebda3.sponsorship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebda3.sponsorship.Helpers.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ebda3.sponsorship.Helpers.Config.SignUpCustomer;

public class EmployeeRegistration extends AppCompatActivity {

    Activity activity = this;
    Context context = this;
    TextView Login;
    EditText ent_name,ent_login,pass_login;
    LinearLayout Register,LinearLogin;
    ProgressBar progressBar;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_registration);
        ent_name = (EditText)findViewById(R.id.ent_name);
        ent_login = (EditText)findViewById(R.id.ent_phone);
        pass_login = (EditText)findViewById(R.id.pass_login);
        Login = (TextView) findViewById(R.id.Login);
        Register = (LinearLayout)findViewById(R.id.Register);
        LinearLogin = (LinearLayout)findViewById(R.id.LinearLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ent_name.getText().toString().isEmpty() )
                {
                    ent_name.setError("????? ????? ?????");
                }
                else if ( ent_login.getText().toString().isEmpty() )
                {
                    ent_login.setError("????? ????? ??? ??????");
                }
                else if ( pass_login.getText().toString().isEmpty() )
                {
                    pass_login.setError("????? ????? ???? ??????");
                }
                else {
                    name=ent_name.getText().toString();
                    Login(ent_name.getText().toString(),ent_login.getText().toString(), pass_login.getText().toString());


                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Workers.class);
                startActivity(intent);
            }
        });

    }

    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void Login(final String user_name ,final String user_email ,final String user_password) {

        boolean t = isNetworkConnected();
        if (t) {
            progressBar.setVisibility(View.VISIBLE);
            pass_login.setEnabled(false);
            ent_name.setEnabled(false);
            ent_login.setEnabled(false);
            Register.setVisibility(View.GONE);
            LinearLogin.setVisibility(View.GONE);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, SignUpCustomer,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(String response) {
                            Log.d("response",response);
                            try {
                                JSONObject jObj = new JSONObject(response);
                                if (jObj.has("ID")) {
                                   // Toast.makeText(activity, "?? ??????? ?????", Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);

                                    SharedPreferences sp=getSharedPreferences("Login", 0);
                                    SharedPreferences.Editor Ed=sp.edit();
                                    Ed.putString("ID",jObj.getString("ID") );
                                    Ed.putString("name",jObj.getString("name").toString() );
                                    Ed.putString("type",jObj.getString("type").toString() );
                                    Ed.putString("active",jObj.getString("active").toString() );
                                    Ed.putString("json_email",jObj.getString("username").toString() );
                                    Ed.putString("photo",jObj.getString("photo").toString() );
                                    Ed.putString("password", pass_login.getText().toString()  );
                                    Ed.putString("json_password",jObj.getString("password") );
                                    Ed.commit();

                                    if ( jObj.getString("active").toString().equals("no") )
                                    {
                                        Intent intent = new Intent(context,Activation.class);
                                        startActivity(intent);

                                    }
                                    else
                                    {
                                    }
                                    finish();


                                }
                            } catch (JSONException e) {
                                pass_login.setEnabled(true);
                                ent_name.setEnabled(true);
                                ent_login.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                Register.setVisibility(View.VISIBLE);
                                LinearLogin.setVisibility(View.VISIBLE);

                                JSONArray array = null;
                                try {
                                    array = new JSONArray(response);
                                    if ( array.getString(0).equals("Field password  From 6 To 20") ) {
                                        pass_login.setError(" كلمة السر يجب ان لا تقل عن 6 احرف ولا تزيد عن 20 حرف");
                                        pass_login.setFocusable(true);
                                    }
                                    else if ( array.getString(0).equals("Field username  Already exist") ) {
                                        ent_login.setError("اسم المستخدم موجود بالفعلش  ");
                                        ent_login.setText("");
                                        ent_login.setFocusable(true);
                                    }
                                    //Toast.makeText(activity, array.getString(0), Toast.LENGTH_SHORT).show();
                                    Log.d("response",  array.getString(0));
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pass_login.setEnabled(true);
                            ent_name.setEnabled(true);
                            ent_login.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            Register.setVisibility(View.VISIBLE);
                            LinearLogin.setVisibility(View.VISIBLE);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("do", "insert");
                    map.put("android_key", Config.getFirebaseInstanceId() );
                    map.put("name", user_name);
                    map.put("username", "00966"+user_email);
                    map.put("password", user_password);
                    Log.d("map",map.toString());
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(context, "please Enable Your Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


}

