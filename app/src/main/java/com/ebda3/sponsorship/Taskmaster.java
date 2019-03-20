package com.ebda3.sponsorship;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebda3.sponsorship.Helpers.Config;

import adapters.TaskmasterListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Taskmaster extends AppCompatActivity {

    Activity activity = this;
    Context context = this;

    public ListView listView;
    public TextView no_data;

    public int StartFrom = 0;
    public int LastStartFrom = 0;
    public int VolleyCurrentConnection = 0;
    public int LimitBerRequest = 5;

    View footerView;

    public Boolean setAdapterStatus = false;


    private ArrayList<String> Name  = new  ArrayList<String>();
    private ArrayList<String> Photo  = new  ArrayList<String>();
    private ArrayList<String> City  = new  ArrayList<String>();
    private ArrayList<String> Phone  = new  ArrayList<String>();
    private ArrayList<String> Professions  = new  ArrayList<String>();
    private ArrayList<String> Workplace  = new  ArrayList<String>();
    private ArrayList<String> RequiredNationalities  = new  ArrayList<String>();
    private ArrayList<String> Advantages  = new  ArrayList<String>();
    TaskmasterListAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskmaster);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        final String activity_name = intent.getStringExtra("activity");


        listView = (ListView) findViewById(R.id.listview);
        footerView = ((LayoutInflater)   getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_footer, null, false);
        no_data = (TextView) findViewById(R.id.no_data);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (activity_name.equals("Home")) {
                    if (setAdapterStatus) {

                        Name.clear();
                        Photo.clear();
                        City.clear();
                        Phone.clear();
                        Professions.clear();
                        Workplace.clear();
                        RequiredNationalities.clear();
                        Advantages.clear();
                        adapter.clear();
                    }
                    listView.setVisibility(View.GONE);
                    VolleyCurrentConnection = 0;
                    StartFrom = 0;
                    loadData();
                }
                else
                {
                    listView.removeFooterView(footerView);
                    swipeRefreshLayout.setRefreshing(false);

                }
            }
        });



        if (activity_name.equals("Home"))
        {
            loadData();
        }
        else
        {
            listView.removeFooterView(footerView);
            swipeRefreshLayout.setRefreshing(false);
            Name = (ArrayList<String>) getIntent().getSerializableExtra("res_Name");
            Photo = (ArrayList<String>) getIntent().getSerializableExtra("res_Photo");
            City = (ArrayList<String>) getIntent().getSerializableExtra("res_City");
            Phone = (ArrayList<String>) getIntent().getSerializableExtra("res_Phone");
            Professions = (ArrayList<String>) getIntent().getSerializableExtra("res_Professions");
            Workplace = (ArrayList<String>) getIntent().getSerializableExtra("res_Workplace");
            RequiredNationalities = (ArrayList<String>) getIntent().getSerializableExtra("res_RequiredNationalities");
            Advantages = (ArrayList<String>) getIntent().getSerializableExtra("res_Advantages");

            adapter = new TaskmasterListAdapter(activity , Name , Photo , Professions  );
            listView.setAdapter(adapter);

        }

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                //Algorithm to check if the last item is visible or not
                final int lastItem = firstVisibleItem + visibleItemCount;
                Log.d("lastItem",String.valueOf(visibleItemCount));
                if(lastItem == totalItemCount){

                    // loadData();

                }
            }
            @Override
            public void onScrollStateChanged(AbsListView view,int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                        listView.getFooterViewsCount()) >= (adapter.getCount() - 3)) {

                    loadData();
                }
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                Intent intent = new Intent(context,TaskmasterDetails.class);

                intent.putExtra("Name",Name.get(position));
                intent.putExtra("Photo",Photo.get(position));
                intent.putExtra("City",City.get(position));
                intent.putExtra("Phone",Phone.get(position));
                intent.putExtra("Professions",Professions.get(position));
                intent.putExtra("Workplace",Workplace.get(position));
                intent.putExtra("RequiredNationalities",RequiredNationalities.get(position));
                intent.putExtra("Advantages",Advantages.get(position));
                startActivity(intent);
            }
        });

    }

    public void loadData() {
        Log.d("loadData","loadData");
        if (VolleyCurrentConnection == 0) {
            VolleyCurrentConnection = 1;
            String VolleyUrl = "http://adc-company.net/kafala/taskmaster-edit-1.html?json=true&ajax_page=true" + "&start=" + String.valueOf(StartFrom) + "&end=" + String.valueOf(LimitBerRequest);
            Log.d("responser", String.valueOf(VolleyUrl));
            listView.addFooterView(footerView);
            try
            {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, VolleyUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", "response");
                        response = fixEncoding (response);
                        listView.removeFooterView(footerView);
                        listView.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            JSONArray array = new JSONArray(response);
                            if (array.length() > 0) {

                                VolleyCurrentConnection = 0;
                                StartFrom += LimitBerRequest;
                                LastStartFrom = StartFrom;
                                for (int i = 0; i < array.length(); i++)
                                {
                                    JSONObject row = array.getJSONObject(i);

                                    Name.add(row.getString("name").toString());
                                    Photo.add(row.getString("photo").toString());
                                    City.add(row.getString("city").toString());
                                    Phone.add(row.getString("Phone").toString());
                                    Professions.add(row.getString("Professions").toString());
                                    Workplace.add(row.getString("Workplace").toString());
                                    RequiredNationalities.add(row.getString("RequiredNationalities").toString());
                                    Advantages.add(row.getString("Advantages").toString());
                                }

                                if (!setAdapterStatus) {
                                    adapter = new TaskmasterListAdapter(activity , Name , Photo , Professions  );
                                    listView.setAdapter(adapter);
                                    setAdapterStatus = true;
                                } else {
                                    adapter.notifyDataSetChanged();
                                }



                                no_data.setVisibility(View.GONE);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);


                        new CountDownTimer(3000, 1000) {
                            public void onFinish() {
                                VolleyCurrentConnection = 0;
                                listView.removeFooterView(footerView);
                                loadData();
                            }

                            public void onTick(long millisUntilFinished) {
                                // millisUntilFinished    The amount of time until finished.
                            }
                        }.start();

                        //Log.d("ErrorResponse", error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map paramas = new HashMap();
                        paramas.put("json_email", Config.getJsonEmail(context) );
                        paramas.put("json_password", Config.getJsonPassword(context));
                        Log.d("params",paramas.toString());
                        return paramas;
                    }
                };

                int socketTimeout = 10000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue queue = Volley.newRequestQueue( context );

                queue.add(stringRequest);
            }
            catch (Exception e)
            {
                swipeRefreshLayout.setRefreshing(false);
                //Log.d("ffffff",e.getMessage());
                VolleyCurrentConnection = 0;
                loadData();
            }
        }
    }

    public static String fixEncoding(String response) {
        try {
            byte[] u = response.toString().getBytes(
                    "ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


