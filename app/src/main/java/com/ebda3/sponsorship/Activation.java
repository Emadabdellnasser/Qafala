package com.ebda3.sponsorship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import com.android.volley.DefaultRetryPolicy;
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

import static com.ebda3.sponsorship.Helpers.Config.WebService;
import static com.ebda3.sponsorship.Helpers.Config.WebService;

public class Activation extends AppCompatActivity {

    Context context = this;
    Activity activity = this;
    LinearLayout confirm;
    EditText activation_code;
    ProgressBar progressBar;
    TextView resend,relogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        resend = (TextView) findViewById(R.id.resend);
        relogin = (TextView) findViewById(R.id.relogin);

        confirm = (LinearLayout) findViewById(R.id.confirm);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        activation_code = (EditText) findViewById(R.id.activation_code);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( activation_code.getText().toString().length() > 3 )
                {
                    SendCode();
                }
                else
                {
                    activation_code.setError("من فضلك اكتب رمز التأكيد");
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resend.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                ResendCode();
            }
        });

        relogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Login.class);
                startActivity(intent);
            }
        });

    }

    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void ResendCode() {
        boolean t = isNetworkConnected();
        if (t) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("xxxxxx", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if ( jsonObject.getString("Result").length() > 0 )
                        {
                            Toast.makeText(context, "تم ارسال رمز التحقق", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(context, "يوجد مشكلة فى الحساب من فضلك قم بمراسلة الادارة", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {

                    }

                    progressBar.setVisibility(View.GONE);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("do", "reactivation_code");
                    params.put("json_email", Config.getJsonEmail(context));
                    params.put("json_password", Config.getJsonPassword(context));
                    Log.d("xxxxxx", params.toString());
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(stringRequest);
        }
    }
    public void SendCode() {
        boolean t = isNetworkConnected();
        if (t) {
            progressBar.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.GONE);
            activation_code.setEnabled(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("xxxxxx", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if ( jsonObject.getString("Result").equals("Success") )
                        {
                            Toast.makeText(context, "تم تفعيل عضويتك بنجاح", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp=getSharedPreferences("Login", 0);
                            SharedPreferences.Editor Ed=sp.edit();
                            Ed.putString("active","yes" );
                            Ed.commit();

                            finish();
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            confirm.setVisibility(View.VISIBLE);
                            activation_code.setEnabled(true);
                            activation_code.setText("");
                            activation_code.setError("من فضلك تأكد من رمز التحقق");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        confirm.setVisibility(View.VISIBLE);
                        activation_code.setEnabled(true);
                        activation_code.setError("من فضلك تأكد من رمز التحقق");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    confirm.setVisibility(View.VISIBLE);
                    activation_code.setEnabled(true);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("do", "activation_code");
                    params.put("activation_code", activation_code.getText().toString());
                    params.put("json_email", Config.getJsonEmail(context));
                    params.put("json_password", Config.getJsonPassword(context));
                    Log.d("xxxxxx", params.toString());
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(stringRequest);
        }
    }
}
