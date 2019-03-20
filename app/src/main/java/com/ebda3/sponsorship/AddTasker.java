package com.ebda3.sponsorship;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.ebda3.sponsorship.adapters.CheckBoxProffesionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static com.ebda3.sponsorship.Helpers.Config.Advantages;
import static com.ebda3.sponsorship.Helpers.Config.Country;
import static com.ebda3.sponsorship.Helpers.Config.CountryIDS;
import static com.ebda3.sponsorship.Helpers.Config.Nationalities;
import static com.ebda3.sponsorship.Helpers.Config.Nationalitiescheck;
import static com.ebda3.sponsorship.Helpers.Config.NationalityData;
import static com.ebda3.sponsorship.Helpers.Config.ProfessionData;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions;
import static com.ebda3.sponsorship.Helpers.Config.Nationalities;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions2;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions3;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS2;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS3;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable2;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable3;
import static com.ebda3.sponsorship.Helpers.Config.SpecificationData;

public class AddTasker extends AppCompatActivity {
    ProgressDialog progressDialog;
    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    Context context = this;
    Activity activity = this;
    Spinner tasker_country_spinner, tasker_city_spinner, Features_spinner;
    ArrayList<String> countriesArray;
    ArrayList<String> natioArray;
    ProfessionClass myprofession,mynationality,myspecification;

    public  ArrayList<String> Profession_send_ID =  new ArrayList<String>();
    public  ArrayList<String> nationalities_send_ID =  new ArrayList<String>();
    public  ArrayList<String> specifications_send_ID =  new ArrayList<String>();

    static GridView proffesion_name;
    static GridView proffesion_name_passp;
    CheckBoxProffesionAdapter adapter;
    EditText Add_name;
    EditText Add_phone;
    EditText WorkPlace;
    LinearLayout Add_register;
    String RequiredNationalities;
    String RequiredCities;
    String RequiredFeatures;
    LinearLayout linear;
    LinearLayout lin_add_register;
    ProgressBar pro_add_reg;
    LinearLayout linear_stay, linear_spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasker);

        progressDialog = ProgressDialog.show(AddTasker.this, "", "جارى التحميل", true, true);

        Add_name = (EditText) findViewById(R.id.addTaskerName);
        Add_phone = (EditText) findViewById(R.id.addTaskerPhone);
        WorkPlace = (EditText) findViewById(R.id.workPlace);
        Add_register = (LinearLayout) findViewById(R.id.add_register);
        tasker_city_spinner = (Spinner) findViewById(R.id.nation);


        linear = (LinearLayout) findViewById(R.id.linear);
        linear_stay = (LinearLayout) findViewById(R.id.linear2);
        linear_spec = (LinearLayout) findViewById(R.id.linear_spec);


        lin_add_register = (LinearLayout) findViewById(R.id.add_register);
        pro_add_reg = (ProgressBar) findViewById(R.id.add_progress);





        GetSetting();


        tasker_city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                RequiredNationalities = tasker_city_spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        tasker_city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {
                int c=0;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        Add_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tasker_name = Add_name.getText().toString();
                String tasker_phone = Add_phone.getText().toString();
                String tasker_place = WorkPlace.getText().toString();

                if (tasker_name.length() < 4) {
                    Add_name.setError("من فضلك أدخل الأسم بطريقة صحيحة");
                }
                if (tasker_phone.length() < 10) {
                    Add_phone.setError("من فضلك أدخل رقم الجوال بطريقة صحيحة");
                }
                if (tasker_place.length() < 4) {
                    WorkPlace.setError("من فضلك ادخل مكان العمل بطريقة صحيحة");
                } else {

                    Profession_send_ID = new ArrayList<String>();
                    nationalities_send_ID = new ArrayList<String>();
                    specifications_send_ID = new ArrayList<String>();

                    for (int i = 0; i < Proffesionscheckable.size(); i++)
                    {
                        Log.d("professiondataChecable", Proffesionscheckable.toString());
                        //Log.d("professiondataids", ProfessionData.toString());

                        for (int j = 0; j < ProfessionData.size(); j++)
                        {

                            if (Proffesionscheckable.get(i).equals(ProfessionData.get(j).getPro_name()))
                            {
                                Profession_send_ID.add(ProfessionData.get(j).getPro_id());

                                Log.d("myproids", ProfessionData.get(j).getPro_name());
                                //Toast.makeText(context, Profession_send_ID.toString(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                    Log.d("myproids", Profession_send_ID.toString());


                    for (int i = 0; i < Proffesionscheckable2.size(); i++)
                    {
                        Log.d("professiondataChecable", Proffesionscheckable2.toString());

                        for (int j = 0; j < NationalityData.size(); j++)
                        {

                            if (Proffesionscheckable2.get(i).equals(NationalityData.get(j).getPro_name()))
                            {
                                nationalities_send_ID.add(NationalityData.get(j).getPro_id());

                                Log.d("myproids", NationalityData.get(j).getPro_name());
                                //Toast.makeText(context, Profession_send_ID.toString(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                    Log.d("myproids", nationalities_send_ID.toString());

                    for (int i = 0; i < Proffesionscheckable3.size(); i++)
                    {
                        //Log.d("professiondataChecable", Proffesionscheckable3.toString());
                        //Log.d("professiondataids", ProfessionData.toString());

                        for (int j = 0; j < SpecificationData.size(); j++)
                        {

                            if (Proffesionscheckable3.get(i).equals(SpecificationData.get(j).getPro_name()))
                            {
                                specifications_send_ID.add(SpecificationData.get(j).getPro_id());

                                //Log.d("myproids", SpecificationData.get(j).getPro_name());
                                //Toast.makeText(context, Profession_send_ID.toString(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                    //Log.d("myproids", specifications_send_ID.toString());

                    sendData();
                }
            }
        });

    }



    public void GetSetting() {
        params.weight = 1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://adc-company.net/kafala/include/webService.php", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                Log.d("ressss", response.toString());
                progressDialog.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);
                    if (jObj.has("Country")) {
                        if (Country.size() > 0) {
                            Country.clear();
                            Config.CountryIDS.clear();
                        }
                        JSONArray Countries = new JSONArray(jObj.getString("Country"));
                        if (Countries.length() > 0) {
                            for (int i = 0; i < Countries.length(); i++) {
                                JSONObject jsonObject = Countries.getJSONObject(i);
                                Config.CountryIDS.add(jsonObject.getString("ID"));
                                Country.add(jsonObject.getString("name"));
                            }
                        }

                    }
                    if (jObj.has("nationalities")) {
                        if (Nationalities.size() > 0) {
                            Nationalities.clear();
                            Config.NationalitiesIDS.clear();
                        }
                        JSONArray NationalitiesArr = new JSONArray(jObj.getString("nationalities"));
                        if (NationalitiesArr.length() > 0) {
                            for (int i = 0; i < NationalitiesArr.length(); i++) {
                                JSONObject jsonObject = NationalitiesArr.getJSONObject(i);
                                Config.NationalitiesIDS.add(jsonObject.getString("ID"));
                                Nationalities.add(jsonObject.getString("name"));

                            }
                        }

                    }
                    if (jObj.has("professions")) {
                        if (Proffesions.size() > 0) {
                            Proffesions.clear();
                            ProffesionsIDS.clear();
                        }
                        JSONArray professionsArr = new JSONArray(jObj.getString("professions"));
                        Log.d("professions", jObj.getString("professions"));
                        if (professionsArr.length() > 0) {
                            LinearLayout linearLayout = new LinearLayout(context);
                            int x = 0;
                            for (int i = 0; i < professionsArr.length(); i++) {
                                if (x == 0) {
                                    linearLayout = new LinearLayout(context);
                                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout.setLayoutParams(params);
                                }

                                JSONObject jsonObject = professionsArr.getJSONObject(i);

                                ProffesionsIDS.add(jsonObject.getString("ID"));
                                Proffesions.add(jsonObject.getString("name"));

                                myprofession = new ProfessionClass();
                                myprofession.setPro_id(jsonObject.getString("ID"));
                                myprofession.setPro_name(jsonObject.getString("name"));
                                ProfessionData.add(myprofession);


                                final CheckBox checkBox = new CheckBox(context);
                                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                        if (!b)
                                        {

                                            for (int i = 0; i < Proffesionscheckable.size(); i++) {
                                                if (checkBox.getText().toString().equals(Proffesionscheckable.get(i)))
                                                {
                                                    Proffesionscheckable.remove(i);
                                                }
                                            }

                                        } else {
                                            Proffesionscheckable.add(checkBox.getText().toString());


                                        }
                                    }
                                });

                                checkBox.setLayoutParams(params);
                                checkBox.setWidth(0);
                                checkBox.setText(jsonObject.getString("name"));
                                linearLayout.addView(checkBox);

                                x++;
                                if (x == 3 || professionsArr.length() == (i + 1)) {
                                    if (professionsArr.length() == (i + 1)) {
                                        int Diff = 3 - x;
                                        for (int z = 0; z < Diff; z++) {
                                            TextView textView = new TextView(context);
                                            textView.setLayoutParams(params);
                                            textView.setWidth(0);
                                            linearLayout.addView(textView);
                                        }
                                    }
                                    x = 0;
                                    linear.addView(linearLayout);
                                }

                            }

                        }
                    }


                    if (jObj.has("nationalities")) {
                        if (Nationalitiescheck.size() > 0) {
                            Nationalitiescheck.clear();
                            Nationalitiescheck.clear();
                        }
                        JSONArray professionsArr = new JSONArray(jObj.getString("nationalities"));
                        Log.d("nationalities", jObj.getString("nationalities"));
                        if (professionsArr.length() > 0) {

                            LinearLayout linearLayout2 = new LinearLayout(context);
                            int x = 0;
                            for (int i = 0; i < professionsArr.length(); i++) {
                                if (x == 0) {
                                    linearLayout2 = new LinearLayout(context);
                                    linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout2.setLayoutParams(params);
                                }

                                JSONObject jsonObject = professionsArr.getJSONObject(i);

                                ProffesionsIDS2.add(jsonObject.getString("ID"));
                                Proffesions2.add(jsonObject.getString("name"));

                                mynationality = new ProfessionClass();
                                mynationality.setPro_id(jsonObject.getString("ID"));
                                mynationality.setPro_name(jsonObject.getString("name"));
                                NationalityData.add(mynationality);


                                final CheckBox checkBox2 = new CheckBox(context);
                                checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                        if (!b) {

                                            for (int i = 0; i < Proffesionscheckable2.size(); i++) {
                                                if (checkBox2.getText().toString().equals(Proffesionscheckable2.get(i))) {
                                                    Proffesionscheckable2.remove(i);
                                                }
                                            }

                                        } else {
                                            Proffesionscheckable2.add(checkBox2.getText().toString());


                                        }
                                    }
                                });
                                checkBox2.setLayoutParams(params);
                                checkBox2.setWidth(0);
                                checkBox2.setText(jsonObject.getString("name"));
                                linearLayout2.addView(checkBox2);
                                x++;
                                if (x == 3 || professionsArr.length() == (i + 1)) {
                                    if (professionsArr.length() == (i + 1)) {
                                        int Diff = 3 - x;
                                        for (int z = 0; z < Diff; z++) {

                                            TextView textView3 = new TextView(context);
                                            textView3.setLayoutParams(params);
                                            textView3.setWidth(0);
                                            linearLayout2.addView(textView3);
                                        }
                                    }
                                    x = 0;
                                    linear_stay.addView(linearLayout2);
                                }

                            }

                        }
                    }


                    if (jObj.has("advantages")) {
                        if (Advantages.size() > 0) {
                            Advantages.clear();
                            Advantages.clear();
                        }
                        JSONArray professionsArr = new JSONArray(jObj.getString("advantages"));
                        Log.d("advantages", jObj.getString("advantages"));
                        if (professionsArr.length() > 0) {

                            LinearLayout linearLayout3 = new LinearLayout(context);
                            int x = 0;
                            for (int i = 0; i < professionsArr.length(); i++) {
                                if (x == 0) {
                                    linearLayout3 = new LinearLayout(context);
                                    linearLayout3.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout3.setLayoutParams(params);
                                }

                                JSONObject jsonObject = professionsArr.getJSONObject(i);

                                ProffesionsIDS3.add(jsonObject.getString("ID"));
                                Proffesions3.add(jsonObject.getString("name"));

                                myspecification = new ProfessionClass();
                                myspecification.setPro_id(jsonObject.getString("ID"));
                                myspecification.setPro_name(jsonObject.getString("name"));
                                SpecificationData.add(myspecification);



                                final CheckBox checkBox3 = new CheckBox(context);
                                checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                        if (!b) {

                                            for (int i = 0; i < Proffesionscheckable3.size(); i++) {
                                                if (checkBox3.getText().toString().equals(Proffesionscheckable3.get(i))) {
                                                    Proffesionscheckable3.remove(i);
                                                }
                                            }
                                            Toast.makeText(context, Proffesionscheckable3.toString(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Proffesionscheckable3.add(checkBox3.getText().toString());


                                        }
                                    }
                                });
                                checkBox3.setLayoutParams(params);
                                checkBox3.setWidth(0);
                                checkBox3.setText(jsonObject.getString("name"));
                                linearLayout3.addView(checkBox3);
                                x++;
                                if (x == 3 || professionsArr.length() == (i + 1)) {
                                    if (professionsArr.length() == (i + 1)) {
                                        int Diff = 3 - x;
                                        for (int z = 0; z < Diff; z++) {

                                            TextView textView3 = new TextView(context);
                                            textView3.setLayoutParams(params);
                                            textView3.setWidth(0);
                                            linearLayout3.addView(textView3);
                                        }
                                    }
                                    x = 0;
                                    linear_spec.addView(linearLayout3);
                                }

                            }

                        }
                    }

                    ArrayAdapter<String> nationalities_spinner_adapter = new ArrayAdapter<String>(AddTasker.this,
                            simple_spinner_item, Country);
                    tasker_city_spinner.setAdapter(nationalities_spinner_adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("do", "GetSetting");

                Log.d("xxxxxx", params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void sendData() {


        lin_add_register.setVisibility(View.GONE);
        pro_add_reg.setVisibility(View.VISIBLE);
        //boolean t = isNetworkConnected();
        if (true) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://adc-company.net/kafala/taskmaster-edit-1.html?json=true&ajax_page=true",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            lin_add_register.setVisibility(View.VISIBLE);
                            pro_add_reg.setVisibility(View.GONE);
                            Log.d("res2222",response);
                            Toast.makeText(context,"data send successed",Toast.LENGTH_LONG).show();
                         //   Add_name.
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AddTasker.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("do","insert");
                    map.put("json_email", Config.getJsonEmail(context));
                    map.put("json_password", Config.getJsonPassword(context));
                    map.put("name",Add_name.getText().toString() );
                    map.put("phone",Add_phone.getText().toString());
                    map.put("city",CountryIDS.get(tasker_city_spinner.getSelectedItemPosition()));
                    map.put("Workplace",WorkPlace.getText().toString());
                    for(int i=0 ; i<Profession_send_ID.size() ; i++)
                    {
                        map.put("Professions[]"+i, Profession_send_ID.get(i));
                    }
                    for(int j=0 ; j<nationalities_send_ID.size() ; j++)
                    {
                        map.put("RequiredNationalities[]"+j, nationalities_send_ID.get(j));
                    }
                    for(int k=0 ; k<specifications_send_ID.size() ; k++)
                    {
                        map.put("Advantages[]"+k, specifications_send_ID.get(k));
                    }

                    //map.put("Professions", TextUtils.join(",", Proffesionscheckable));
//                    map.put("RequiredNationalities", TextUtils.join(",",Proffesionscheckable2));
//                    map.put("Advantages", TextUtils.join(",",Proffesionscheckable3));
                    Log.d("map2222", map.toString());
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
