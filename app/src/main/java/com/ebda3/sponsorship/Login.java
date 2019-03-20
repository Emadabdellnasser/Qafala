package com.ebda3.sponsorship;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.ebda3.sponsorship.R;
import com.ebda3.sponsorship.Helpers.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ebda3.sponsorship.Helpers.Config.LoginUrl;

public class Login extends AppCompatActivity {

    Context context = this;
    LinearLayout login;
    EditText ent_login,pass_login;
    TextView Register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ent_login = (EditText)findViewById(R.id.ent_login);
        pass_login = (EditText)findViewById(R.id.pass_login);
        login = (LinearLayout)findViewById(R.id.login);
        Register = (TextView)findViewById(R.id.Register);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView forget = (TextView)findViewById(R.id.forget);

        ent_login.setFocusableInTouchMode(true);
        ent_login.requestFocus();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext() , Forget.class );
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ent_login.getText().toString().isEmpty() )
                {
                    ent_login.setError("برجاء كتابة رقم الجوال");
                }
                else if ( pass_login.getText().toString().isEmpty() )
                {
                    pass_login.setError("برجاء كتابة كلمة المرور");
                }
                else {
                    Login(ent_login.getText().toString(), pass_login.getText().toString());
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Register.class);
                startActivity(intent);
            }
        });
    }


    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void Login(final String user_email ,final String user_password) {

        boolean t = isNetworkConnected();
        if (t) {

            SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
            String lang = sp1.getString("lang", " ");




            progressBar.setVisibility(View.VISIBLE);
            pass_login.setEnabled(false);
            ent_login.setEnabled(false);
            login.setVisibility(View.GONE);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginUrl,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(String response) {
                            Log.d("response",response);
                            JSONObject jObj;
                            try {
                                jObj = new JSONObject(response);
                                if (jObj.has("ID")) {

                                    SharedPreferences sp=getSharedPreferences("Login", 0);
                                    SharedPreferences.Editor Ed=sp.edit();
                                    Ed.putString("ID",jObj.getString("ID") );
                                    Ed.putString("name",jObj.getString("name") );
                                    Ed.putString("type",jObj.getString("type") );
                                    Ed.putString("active",jObj.getString("active") );
                                    Ed.putString("username",jObj.getString("username") );
                                    Ed.putString("photo",jObj.getString("photo") );
                                    Ed.putString("json_email",jObj.getString("username") );
                                    Ed.putString("json_password",jObj.getString("password") );
                                    Ed.putString("password",user_password);
                                    //Ed.putString("ShareUrl",jObj.getString("ShareUrl") );

                                    Ed.commit();

                                    if ( jObj.getString("active").equals("yes") )
                                    {
                                        Toast.makeText(Login.this, "signed in", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Intent intent = new Intent(context, Activation.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                }
                                else {
                                    Toast.makeText(Login.this, "من فضلك تاكد من بيانات الدخول", Toast.LENGTH_LONG).show();
                                    pass_login.setEnabled(true);
                                    ent_login.setEnabled(true);
                                    pass_login.setText("");
                                    progressBar.setVisibility(View.GONE);
                                    login.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                pass_login.setEnabled(true);
                                ent_login.setEnabled(true);
                                progressBar.setVisibility(View.GONE);
                                pass_login.setVisibility(View.VISIBLE);
                                e.printStackTrace();
                            }
                            //openProfile();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            pass_login.setEnabled(true);
                            ent_login.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("do", "log");
                    map.put("android_key", Config.getFirebaseInstanceId() );
                    map.put("email", "00966"+user_email);
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
            Toast.makeText(Login.this, "please Enable Your Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

}
