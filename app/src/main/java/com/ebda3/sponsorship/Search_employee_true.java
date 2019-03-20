package com.ebda3.sponsorship;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static com.ebda3.sponsorship.Helpers.Config.Advantages;
import static com.ebda3.sponsorship.Helpers.Config.CountryIDS;
import static com.ebda3.sponsorship.Helpers.Config.CountryW;
import static com.ebda3.sponsorship.Helpers.Config.CountryWIDS;
import static com.ebda3.sponsorship.Helpers.Config.NationalitiesWorker;
import static com.ebda3.sponsorship.Helpers.Config.NationalitiesWorkerIDS;
import static com.ebda3.sponsorship.Helpers.Config.Nationalitiescheckable;
import static com.ebda3.sponsorship.Helpers.Config.NationalityData;
import static com.ebda3.sponsorship.Helpers.Config.ProfessionData;
import static com.ebda3.sponsorship.Helpers.Config.ProfessionData2;
import static com.ebda3.sponsorship.Helpers.Config.ProfessionNationality;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions2;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions3;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS2;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS3;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable2;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable3;
import static com.ebda3.sponsorship.Helpers.Config.SpecificationData;

public class Search_employee_true extends AppCompatActivity {
    Context context = this;
    Activity activity = this;

    ProgressBar pro_add_reg;
    LinearLayout bu_searh;
    EditText  age_txt_from,age_txt_to ;
    Spinner employee_city_spinner;
    LinearLayout linearpro,nations,advantages;
    ProfessionClass myprofession,myspecification,mynationalities;

    public  ArrayList<String> Profession_send_ID =  new ArrayList<String>();
    public  ArrayList<String> nationalities_send_ID =  new ArrayList<String>();
    public  ArrayList<String> specifications_send_ID =  new ArrayList<String>();
    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    String search_name , search_age , search_profession;
    ArrayList<String> res_Name  = new  ArrayList<String>();
    ArrayList<String> res_Photo  = new  ArrayList<String>();
    ArrayList<String> res_City  = new  ArrayList<String>();
    ArrayList<String> res_Phone  = new  ArrayList<String>();
    ArrayList<String> res_Professions  = new  ArrayList<String>();
    ArrayList<String> res_Professions_resedence  = new  ArrayList<String>();
    ArrayList<String> res_Workplace  = new  ArrayList<String>();
    ArrayList<String> res_RequiredNationalities  = new  ArrayList<String>();
    ArrayList<String> res_Advantages  = new  ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__employee);
        pro_add_reg = (ProgressBar) findViewById(R.id.progress_owner) ;
        bu_searh = (LinearLayout) findViewById(R.id.lineSearch);
        advantages= (LinearLayout) findViewById(R.id.AdvantagesSearch);
        age_txt_from = (EditText) findViewById(R.id.AgeFrom);
        age_txt_to = (EditText) findViewById(R.id.AgeTo);
        employee_city_spinner= (Spinner) findViewById(R.id.city_Search);
        linearpro = (LinearLayout) findViewById(R.id.linearProID) ;
        nations = (LinearLayout) findViewById(R.id.NationsSearch) ;
        GetSetting();
        bu_searh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data ();

            }
        });

    }

    public void send_data ()
    {
        bu_searh.setVisibility(View.GONE);
        pro_add_reg.setVisibility(View.VISIBLE);
        //boolean t = isNetworkConnected();
        if (true) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://adc-company.net/kafala/workers-edit-1.html?json=true&ajax_page=true",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            bu_searh.setVisibility(View.VISIBLE);
                            pro_add_reg.setVisibility(View.GONE);
                            Log.d("searchres","res"+response);
                           Toast.makeText(context,"data send successed",Toast.LENGTH_LONG).show();
                           Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                            try {
                                JSONArray array = new JSONArray(response);
                                if (array.length() > 0)
                                {

                                    for (int i = 0; i < array.length(); i++)
                                    {
                                        JSONObject row = array.getJSONObject(i);

                                        res_Name.add(row.getString("name").toString());
                                        res_Photo.add(row.getString("photo").toString());
                                        res_City.add(row.getString("city").toString());
                                        res_Phone.add(row.getString("Phone").toString());
                                        res_Professions.add(row.getString("Professions").toString());
                                        res_Professions_resedence.add(row.getString("professionInResidence").toString());
                                        res_RequiredNationalities.add(row.getString("RequiredNationalities").toString());
                                      //  res_Advantages.add(row.getString("Advantages").toString());
                                    }

                                    Intent intent = new Intent(Search_employee_true.this, Workers.class);
                                    intent.putExtra("activity","Search_Guaranteer_true");
                                    intent.putExtra("res_Name",res_Name);
                                    intent.putExtra("res_Photo",res_Photo);
                                    intent.putExtra("res_City",res_City);
                                    intent.putExtra("res_Phone",res_Phone);
                                    intent.putExtra("res_Professions",res_Professions);
                                    intent.putExtra("res_ProfessionsPresedence",res_Professions_resedence);
                                   // intent.putExtra("res_Workplace",res_Workplace);
                                    intent.putExtra("res_RequiredNationalities",res_RequiredNationalities);
                                   // intent.putExtra("res_Advantages",res_Advantages);
                                    Toast.makeText(context,"activity",Toast.LENGTH_LONG).show();

                                    startActivity(intent);

                                }
                                else
                                {
                                    final Dialog dialog = new Dialog(context);
                                    dialog.setContentView(R.layout.intent_not_found);
                                    dialog.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }





                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pro_add_reg.setVisibility(View.VISIBLE);
                            bu_searh.setVisibility(View.GONE);
                            Toast.makeText(Search_employee_true.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("json_email", Config.getJsonEmail(context));
                    map.put("json_password", Config.getJsonPassword(context));
//                    map.put("word",name_txt.getText().toString() );
//                    map.put("age",age_txt.getText().toString());
//                    map.put("Professions",profession_txt.getText().toString());
                    map.put("city",CountryW.get(employee_city_spinner.getSelectedItemPosition()));
                    for(int i=0 ; i<Proffesionscheckable.size() ; i++)
                    {
                        map.put("actualProfession[]"+i, Proffesionscheckable.get(i));
                    }
                    for(int i=0 ; i<Nationalitiescheckable.size() ; i++)
                    {
                        map.put("nationalities[]"+i, Nationalitiescheckable.get(i));
                    }
                    for(int i=0 ; i<Nationalitiescheckable.size() ; i++)
                    {
                        map.put("nationalities[]"+i, Nationalitiescheckable.get(i));
                    }


                    Log.d("map2222", map.toString());
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }


    public void GetSetting()
    {
        params.weight = 1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST , "http://adc-company.net/kafala/include/webService.php" , new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                Log.d("ressss", response.toString());
                //progressDialog.dismiss();
                try
                {
                    JSONObject jObj = new JSONObject(response);
                    if (jObj.has("Country"))
                    {
                        if (CountryW.size() > 0)
                        {
                            CountryW.clear();
                            CountryWIDS.clear();
                        }
                        JSONArray Countries = new JSONArray(jObj.getString("Country"));
                        if ( Countries.length() > 0 )
                        {
                            for (int i = 0; i < Countries.length(); i++)
                            {
                                JSONObject jsonObject = Countries.getJSONObject(i);
                                CountryWIDS.add(jsonObject.getString("ID"));
                                CountryW.add(jsonObject.getString("name"));
                            }
                        }

                    }
                    ArrayAdapter<String> city_spinner_adapter = new ArrayAdapter<String>(Search_employee_true.this,
                            simple_spinner_item, CountryW);
                    employee_city_spinner.setAdapter(city_spinner_adapter);


                    if (jObj.has("professions"))
                    {
                        if (Proffesions.size() > 0)
                        {
                            Proffesions.clear();
                            ProffesionsIDS.clear();
                        }
                        JSONArray professionsArr = new JSONArray(jObj.getString("professions"));
                        Log.d("professions",jObj.getString("professions"));
                        if ( professionsArr.length() > 0 )
                        {
                            LinearLayout linearLayout = new LinearLayout(context);

                            int x = 0;
                            for (int i = 0; i < professionsArr.length(); i++)
                            {
                                if ( x == 0 )
                                {
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

                                            for (int i = 0 ; i <Proffesionscheckable.size() ; i++)
                                            {
                                                if(checkBox.getText().toString().equals(Proffesionscheckable.get(i)))
                                                {
                                                    Proffesionscheckable.remove(i);
                                                }
                                            }

                                        }
                                        else
                                        {
                                            Proffesionscheckable.add(checkBox.getText().toString());

                                        }
                                    }
                                });


                                checkBox.setLayoutParams(params);
                                checkBox.setWidth(0);

                                checkBox.setText(jsonObject.getString("name"));
                                linearLayout.addView(checkBox);

                                x++;
                                if ( x == 3 || professionsArr.length() == (i+1)  )
                                {
                                    if (  professionsArr.length() == (i+1)  )
                                    {
                                        int Diff = 3-x;
                                        for ( int z = 0 ; z < Diff ; z++)
                                        {
                                            TextView textView = new TextView(context);

                                            textView.setLayoutParams(params);
                                            textView.setWidth(0);
                                            linearLayout.addView(textView);
                                        }
                                    }
                                    x = 0;
                                    linearpro.addView(linearLayout);
//                                        if(linearLayout.getParent()!=null)
//                                            ((ViewGroup)linearLayout.getParent()).removeView(linearLayout); // <- fix
                                }

                            }

                                /*
                                adapter = new CheckBoxProffesionAdapter(activity, Proffesions);
                                Log.d("dddddddd",Proffesions.toString());
                                proffesion_name.setAdapter(adapter);
                                proffesion_name_passp.setAdapter(adapter);
                                */
                        }
                    }
                    if (jObj.has("nationalities"))
                    {
                        if (NationalitiesWorker.size() > 0)
                        {
                            NationalitiesWorker.clear();
                            NationalitiesWorkerIDS.clear();
                        }
                        JSONArray NationalitiesWorkerArr = new JSONArray(jObj.getString("nationalities"));
                        Log.d("nationalities",jObj.getString("nationalities"));
                        if ( NationalitiesWorkerArr.length() > 0 )
                        {
                            LinearLayout linearLayout = new LinearLayout(context);
                            int x = 0;
                            for (int i = 0; i < NationalitiesWorkerArr.length(); i++)
                            {
                                if ( x == 0 )
                                {
                                    linearLayout = new LinearLayout(context);
                                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout.setLayoutParams(params);
                                }

                                JSONObject jsonObject = NationalitiesWorkerArr.getJSONObject(i);
                                NationalitiesWorkerIDS.add(jsonObject.getString("ID"));
                                NationalitiesWorker.add(jsonObject.getString("name"));

                                mynationalities = new ProfessionClass();
                                mynationalities.setPro_id(jsonObject.getString("ID"));
                                mynationalities.setPro_name(jsonObject.getString("name"));
                                ProfessionNationality.add(mynationalities);



                                final CheckBox checkBox3 = new CheckBox(context);
                                checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                        if (!b)
                                        {

                                            for (int i = 0 ; i <Nationalitiescheckable.size() ; i++)
                                            {
                                                if(checkBox3.getText().toString().equals(Nationalitiescheckable.get(i)))
                                                {
                                                    Nationalitiescheckable.remove(i);
                                                }
                                            }

                                        }
                                        else
                                        {
                                            Nationalitiescheckable.add(checkBox3.getText().toString());


                                        }
                                    }
                                });

//                                checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                    @Override
//                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                        if (!b)
//                                        {
//
//                                            for (int i = 0 ; i <Proffesionscheckable2.size() ; i++)
//                                            {
//                                                if(checkBox2.getText().toString().equals(Proffesionscheckable2.get(i)))
//                                                {
//                                                    Proffesionscheckable2.remove(i);
//                                                }
//                                            }
//                                            Toast.makeText(context,Proffesionscheckable2.toString(),Toast.LENGTH_SHORT).show();
//                                        }
//                                        else
//                                        {
//                                            Proffesionscheckable2.add(checkBox2.getText().toString());
//                                            Toast.makeText(context,Proffesionscheckable2.toString(),Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    }
//                                });

                                checkBox3.setLayoutParams(params);
                                checkBox3.setWidth(0);
                                checkBox3.setText(jsonObject.getString("name"));
                                linearLayout.addView(checkBox3);

                                x++;
                                if ( x == 3 || NationalitiesWorkerArr.length() == (i+1)  )
                                {
                                    if (  NationalitiesWorkerArr.length() == (i+1)  )
                                    {
                                        int Diff = 3-x;
                                        for ( int z = 0 ; z < Diff ; z++)
                                        {
                                            TextView textView = new TextView(context);
                                            TextView textView2 = new TextView(context);
                                            textView.setLayoutParams(params);
                                            textView2.setLayoutParams(params);
                                            textView.setWidth(0);
                                            textView2.setWidth(0);
                                            linearLayout.addView(textView);
                                        }
                                    }
                                    x = 0;
                                    nations.addView(linearLayout);
//                                        if(linearLayout.getParent()!=null)
//                                            ((ViewGroup)linearLayout.getParent()).removeView(linearLayout); // <- fix
                                }

                            }


                                /*
                                adapter = new CheckBoxProffesionAdapter(activity, Proffesions);
                                Log.d("dddddddd",Proffesions.toString());
                                proffesion_name.setAdapter(adapter);
                                proffesion_name_passp.setAdapter(adapter);
                                */
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

                                            for (int i = 0; i < Nationalitiescheckable.size(); i++) {
                                                if (checkBox3.getText().toString().equals(Nationalitiescheckable.get(i))) {
                                                    Nationalitiescheckable.remove(i);
                                                }
                                            }
                                            Toast.makeText(context, Nationalitiescheckable.toString(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Nationalitiescheckable.add(checkBox3.getText().toString());


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
                                    advantages.addView(linearLayout3);
                                }

                            }

                        }
                    }

                }
                catch (JSONException e) {
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

                Log.d("xxxxxx",params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}