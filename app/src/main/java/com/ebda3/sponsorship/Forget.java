package com.ebda3.sponsorship;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.ebda3.sponsorship.Helpers.Config.WebService;

public class Forget extends AppCompatActivity {

    Context context = this;
    EditText email;
    String EmailText;
    LinearLayout Confirm,ConfirmProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        Intent intent =  getIntent() ;
        EmailText = intent.getStringExtra("email");

        Confirm = (LinearLayout) findViewById(R.id.Confirm);
        ConfirmProgress = (LinearLayout) findViewById(R.id.ConfirmProgress);

        email = (EditText) findViewById(R.id.full_name_log);
        email.setText(EmailText);

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean t = isNetworkConnected();
                if(t) {
                    if ( email.getText().toString().isEmpty() )
                    {
                        email.setError(getString(R.string.Please_type_your_email_address));
                    }
                    else
                    {
                        Confirm.setVisibility(View.GONE);
                        ConfirmProgress.setVisibility(View.VISIBLE);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebService+"&do=sendPassword",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("response",response);
                                        Log.d("response","fdfdf");
                                        try {
                                            JSONObject jsonObject = new JSONObject ( response );
                                            if ( jsonObject.has("ID") )
                                            {
                                                Toast.makeText(context, getString(R.string.The_new_password_has_been_sent_to_your_email), Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                            else
                                            {
                                                sendAgain();
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            sendAgain();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        sendAgain();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("AppID",  "54344776543345");
                                map.put("email",  "00966"+email.getText().toString());
                                Log.d("params",map.toString());
                                return map;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);
                    }

                }
                else
                {
                    Toast.makeText(Forget.this, "please Enable Your Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void sendAgain()
    {
        email.setError(getString(R.string.Please_make_sure_your_email_address));
        Confirm.setVisibility(View.VISIBLE);
        ConfirmProgress.setVisibility(View.GONE);
    }

    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
