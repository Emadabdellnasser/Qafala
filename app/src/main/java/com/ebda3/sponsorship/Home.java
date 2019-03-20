package com.ebda3.sponsorship;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import static com.ebda3.sponsorship.Helpers.Config.comeActivity;
import static com.ebda3.sponsorship.Helpers.Config.getUserName;

public class Home extends AppCompatActivity implements View.OnClickListener {
    LinearLayout TaskmasterSearch,workerSearch,taskmasterRegistration,employee_register,account,welcome_all,head,workers_name,taskers;
    Context context = this;
    Activity activity = this;
    public String u_email,u_password,type = "";
    Boolean Logedin = false;
    TextView register,login,client_name,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        account=(LinearLayout) findViewById(R.id.account);
        EmployeeRegistration om=new EmployeeRegistration();
        employee_register=(LinearLayout) findViewById(R.id.employeeAdd);
        taskmasterRegistration=(LinearLayout) findViewById(R.id.taskmasterAdd);
        workerSearch=(LinearLayout) findViewById(R.id.workerSearch);
        TaskmasterSearch=(LinearLayout) findViewById(R.id.TaskmasterSearch);
        welcome_all=(LinearLayout) findViewById(R.id.welcome_all);
        head=(LinearLayout) findViewById(R.id.head);
        workers_name=(LinearLayout) findViewById(R.id.workers_name);
        taskers=(LinearLayout) findViewById(R.id.taskers);
        login= (TextView) findViewById(R.id.login);
        client_name= (TextView) findViewById(R.id.name_cline);
        register= (TextView) findViewById(R.id.register);
        client_name.setText(getUserName(context));
        logout =(TextView) findViewById(R.id.logout);


        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        u_email = sp1.getString("json_email", " ");
        u_password = sp1.getString("password", " ");

        if (u_email.length() > 2 && u_password.length() > 2) {
            Log.d("checklogin", "checklogin");
            checklogin(u_email, u_password);
        }

        account.setOnClickListener(this);
        employee_register.setOnClickListener(this);
        taskmasterRegistration.setOnClickListener(this);
        TaskmasterSearch.setOnClickListener(this);
        workerSearch.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        logout.setOnClickListener(this);
        taskers.setOnClickListener(this);
        workers_name.setOnClickListener(this);
        LoadActivity();

    }


    public void LoadActivity() {
        // int ID = view.getId();
        if (Logedin) {
            head.setVisibility(View.GONE);
            welcome_all.setVisibility(View.VISIBLE);
            account.setVisibility(View.VISIBLE);


        }
    }

    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    public void checklogin(final String user_email ,final String user_password) {
        Logedin = false;
        boolean t = isNetworkConnected();
        if (t) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ressss", response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                if (jObj.has("ID")) {
                                    Logedin = true;

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            LoadActivity();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("do", "log");
                    map.put("android_key", Config.getFirebaseInstanceId());
                    map.put("email", user_email);
                    map.put("password", user_password);
                    Log.d("params", map.toString());
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

public void Logout()
{
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setMessage("هل تود تسجيل الخروج")
            .setPositiveButton("الغاء", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // FIRE ZE MISSILES!
                }
            })
            .setNegativeButton("تسجيل الخروج", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog

                    SharedPreferences sp=getSharedPreferences("Login", 0);
                    SharedPreferences.Editor Ed=sp.edit();
                    Ed.putString("json_email","");
                    Ed.putString("json_password","");
                    Ed.putString("password","");
                    Ed.putString("ID","");
                    Ed.commit();

                    finishAffinity();

                }
            });
    builder.show();

}

    @Override
    public void onClick(View v) {
      /*  if(!Logedin)
        {
            Intent intent = new Intent(context, Register.class);
            startActivity(intent);
        }
        else {
        */
           // loginDone();

        switch (v.getId()) {
//                case R.id.TaskmasterSearch: {
//                    if (Logedin) {
//                        Intent intent = new Intent(context, Search_employee_true.class);
//                        startActivity(intent);
//                        break;
//                    } else {
//                        Intent intent = new Intent(context, Register.class);
//                        startActivity(intent);
//                        break;
//                    }
//                }

                // do something when the corky is clicked

                case R.id.workerSearch:
                {
                    if (Logedin) {
                        Intent intent = new Intent(context, Workers.class);
                        comeActivity="searchActivity";
                        startActivity(intent);
                        break;
                    } else {
                        Intent intent = new Intent(context, Register.class);
                        startActivity(intent);
                        break;
                    }
                }


                    // do something when the corky2 is clicked

                case R.id.account:
                {
                    if (Logedin) {
                        Intent intent = new Intent(context, MyAccount.class);
                        startActivity(intent);
                        break;
                    } else {
                        Intent intent = new Intent(context, Register.class);
                        startActivity(intent);
                        break;
                    }
                }
                    // do something when the corky3 is clicked

                case R.id.employeeAdd:

                    // do something when the corky3 is clicked
                {
                    if (Logedin) {
                        Intent intent = new Intent(context, Add_employee.class);
                        startActivity(intent);
                        break;
                    } else {
                        Intent intent = new Intent(context, Register.class);
                        startActivity(intent);
                        break;
                    }
                }


                case R.id.taskmasterAdd:
                {
                    if (Logedin) {
                        Intent intent = new Intent(context, AddTasker.class);
                        startActivity(intent);
                        break;
                    } else {
                        Intent intent = new Intent(context, Register.class);
                        startActivity(intent);
                        break;
                    }
                }
                case R.id.taskers:
                {
                    if (Logedin) {
                        Intent intent = new Intent(context, Taskmaster.class);
                        startActivity(intent);
                        break;
                    } else {
                        Intent intent = new Intent(context, Register.class);
                        startActivity(intent);
                        break;
                    }
                }
                case R.id.workers_name:
                {
                    if (Logedin) {

                        Intent intent = new Intent(context, Workers.class);
                        comeActivity="workerActivity";

                        startActivity(intent);
                        break;
                    } else {
                        Intent intent = new Intent(context, Register.class);
                        startActivity(intent);
                        break;
                    }
                }
                    // do something when the corky3 is clicked



                case R.id.login:

                    // do something when the corky3 is clicked
                    Intent in8 = new Intent(context, Login.class);
                    startActivity(in8);
                    break;
                case R.id.register:

                    // do something when the corky3 is clicked
                    Intent in7 = new Intent(context, Register.class);
                    startActivity(in7);
                    break;
                case R.id.logout:

                    // do something when the corky3 is clicked
                    Logout();
                    break;
                default:

                    break;
            //}
        }

    }
    public void finishAffinity(){
       welcome_all.setVisibility(View.GONE);
       head.setVisibility(View.VISIBLE);
        account.setVisibility(View.GONE);
    }




}
