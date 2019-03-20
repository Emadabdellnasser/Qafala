package com.ebda3.sponsorship;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static com.ebda3.sponsorship.Helpers.Config.Country;
import static com.ebda3.sponsorship.Helpers.Config.CountryIDS;
import static com.ebda3.sponsorship.Helpers.Config.CountryW;
import static com.ebda3.sponsorship.Helpers.Config.CountryWIDS;
import static com.ebda3.sponsorship.Helpers.Config.Nationalities;
import static com.ebda3.sponsorship.Helpers.Config.NationalitiesWorker;
import static com.ebda3.sponsorship.Helpers.Config.NationalitiesWorkerIDS;
import static com.ebda3.sponsorship.Helpers.Config.Nationalitiescheckable;
import static com.ebda3.sponsorship.Helpers.Config.NationalityData;
import static com.ebda3.sponsorship.Helpers.Config.ProfessionData;
import static com.ebda3.sponsorship.Helpers.Config.ProfessionData2;
import static com.ebda3.sponsorship.Helpers.Config.ProfessionNationality;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions;
import static com.ebda3.sponsorship.Helpers.Config.Proffesions2;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS2;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable2;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable3;
import static com.ebda3.sponsorship.Helpers.Config.SpecificationData;
import static com.ebda3.sponsorship.Helpers.Config.cityData;

public class Add_employee extends AppCompatActivity {

    Context context = this;
    Activity activity = this;
    LinearLayout InsertButton;
    public  ArrayList<String> Professionw_send_ID =  new ArrayList<String>();
    public  ArrayList<String> nationalitiesw_send_ID =  new ArrayList<String>();
    public  ArrayList<String> professionw_send_ID2 =  new ArrayList<String>();
    ProfessionClass myprofession,myprofession2,mynationalities;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    ProgressBar pro_add_reg;
    LinearLayout lin_add_register;
    EditText name ;
    EditText age ;
    EditText phone ;
    TextView expirationDateOfResidence ;
    String  Name ,Age, Photo , City , Phone , Professions , Workplace  , Advantages,ExpirationDateOfResidence;

    // Storing server url into String variable.

    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    Spinner employee_city_spinner;
    TextView end_date;
    CheckBoxProffesionAdapter adapter;
    String   RequiredNationalities;
    String   RequiredCities;
    LinearLayout linear, linear_stay,nations,Date_picker;
    final Calendar myCalendar = Calendar.getInstance();
    String my_date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        name = (EditText) findViewById(R.id.add_name);


        //progressDialog = ProgressDialog.show(Add_employee.this, "", "جارى التحميل", true, true);
        lin_add_register = (LinearLayout) findViewById(R.id.add_register_2);
        pro_add_reg = (ProgressBar) findViewById(R.id.add_progress_2);

        age= (EditText) findViewById(R.id.add_age);
        phone= (EditText) findViewById(R.id.add_phone);
        expirationDateOfResidence= (TextView) findViewById(R.id.end_date);
        InsertButton= (LinearLayout) findViewById(R.id.add_register_2);
        nations= (LinearLayout) findViewById(R.id.nations);
        employee_city_spinner= (Spinner) findViewById(R.id.city);
        end_date = (TextView) findViewById(R.id.end_date);

        progressDialog = new ProgressDialog(Add_employee.this);
        linear = (LinearLayout) findViewById(R.id.linearPro) ;
        linear_stay = (LinearLayout) findViewById(R.id.linearpro2) ;
        Date_picker= (LinearLayout) findViewById(R.id.linearpro2) ;
        GetSetting();

        //progressDialog.show();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                end_date.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                my_date = year+"-"+monthOfYear+"-"+dayOfMonth;
            }

        };



        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Add_employee.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();



            }
        });
        employee_city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        // Adding click listener to button.
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tasker_name = name.getText().toString();
                String tasker_phone = phone.getText().toString();
                String tasker_age = age.getText().toString();
                String expired_date = expirationDateOfResidence.getText().toString();

                if (tasker_name.length() < 4) {
                    name.setError("من فضلك أدخل الأسم بطريقة صحيحة");
                }
                if (tasker_phone.length() < 10) {
                    phone.setError("من فضلك أدخل رقم الجوال بطريقة صحيحة");
                }
                if (tasker_age.length() < 1) {
                    age.setError("من فضلك ادخل عمرك");
                }
                if (expired_date.length() < 4) {
                    expirationDateOfResidence.setError("من فضلك ادخل مكان العمل بطريقة صحيحة");
                } else {
                    //sendData(Name,UserName, UserEmail, UserPhone, UserPassword);

                    Professionw_send_ID = new ArrayList<String>();
                    nationalitiesw_send_ID = new ArrayList<String>();


                    for (int i = 0; i < Proffesionscheckable.size(); i++) {
                        Log.d("professiondataChecable", Proffesionscheckable.toString());
                        //Log.d("professiondataids", ProfessionData.toString());

                        for (int j = 0; j < ProfessionData.size(); j++) {

                            if (Proffesionscheckable.get(i).equals(ProfessionData.get(j).getPro_name())) {
                                Professionw_send_ID.add(ProfessionData.get(j).getPro_id());

                                Log.d("myproids", ProfessionData.get(j).getPro_name());
                                //Toast.makeText(context, Profession_send_ID.toString(), Toast.LENGTH_LONG).show();

                            }
                        }
                    }




                    for (int i = 0; i < Proffesionscheckable2.size(); i++) {

                        for (int j = 0; j < ProfessionData2.size(); j++) {

                            if (Proffesionscheckable2.get(i).equals(ProfessionData2.get(j).getPro_name())) {
                                professionw_send_ID2.add(ProfessionData2.get(j).getPro_id());

                            }
                        }
                    }



                    for (int i = 0; i < Nationalitiescheckable.size(); i++) {

                        for (int j = 0; j < ProfessionNationality.size(); j++) {

                            if (Nationalitiescheckable.get(i).equals(ProfessionNationality.get(j).getPro_name())) {
                                nationalitiesw_send_ID.add(ProfessionNationality.get(j).getPro_id());

                            }
                        }
                    }

                    //Log.d("myproids", specifications_send_ID.toString());

                    sendData();
                }

            }
        });
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
                    ArrayAdapter<String> city_spinner_adapter = new ArrayAdapter<String>(Add_employee.this,
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
                            LinearLayout linearLayout2 = new LinearLayout(context);
                            int x = 0;
                            for (int i = 0; i < professionsArr.length(); i++)
                            {
                                if ( x == 0 )
                                {
                                    linearLayout = new LinearLayout(context);
                                    linearLayout2 = new LinearLayout(context);
                                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
                                    linearLayout.setLayoutParams(params);
                                    linearLayout2.setLayoutParams(params);
                                }

                                JSONObject jsonObject = professionsArr.getJSONObject(i);
                                ProffesionsIDS.add(jsonObject.getString("ID"));
                                ProffesionsIDS2.add(jsonObject.getString("ID"));
                                Proffesions.add(jsonObject.getString("name"));
                                Proffesions2.add(jsonObject.getString("name"));

                                myprofession = new ProfessionClass();
                                myprofession.setPro_id(jsonObject.getString("ID"));
                                myprofession.setPro_name(jsonObject.getString("name"));
                                ProfessionData.add(myprofession);

                                myprofession2 = new ProfessionClass();
                                myprofession2.setPro_id(jsonObject.getString("ID"));
                                myprofession2.setPro_name(jsonObject.getString("name"));
                                ProfessionData2.add(myprofession2);


                                final CheckBox checkBox = new CheckBox(context);
                                final CheckBox checkBox2 = new CheckBox(context);

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

                                checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                        if (!b)
                                        {

                                            for (int i = 0 ; i <Proffesionscheckable2.size() ; i++)
                                            {
                                                if(checkBox2.getText().toString().equals(Proffesionscheckable2.get(i)))
                                                {
                                                    Proffesionscheckable2.remove(i);
                                                }
                                            }

                                        }
                                        else
                                        {
                                            Proffesionscheckable2.add(checkBox2.getText().toString());


                                        }
                                    }
                                });

                                checkBox.setLayoutParams(params);
                                checkBox2.setLayoutParams(params);
                                checkBox.setWidth(0);
                                checkBox2.setWidth(0);
                                checkBox.setText(jsonObject.getString("name"));
                                checkBox2.setText(jsonObject.getString("name"));
                                linearLayout.addView(checkBox);
                                linearLayout2.addView(checkBox2);


                                x++;
                                if ( x == 3 || professionsArr.length() == (i+1)  )
                                {
                                    if (  professionsArr.length() == (i+1)  )
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
                                            linearLayout2.addView(textView2);
                                        }
                                    }
                                    x = 0;
                                    linear.addView(linearLayout);
//                                        if(linearLayout.getParent()!=null)
//                                            ((ViewGroup)linearLayout.getParent()).removeView(linearLayout); // <- fix
                                    linear_stay.addView(linearLayout2);
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


    public void sendData() {


        lin_add_register.setVisibility(View.GONE);
        pro_add_reg.setVisibility(View.VISIBLE);
        //boolean t = isNetworkConnected();
        if (true) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://adc-company.net/kafala/workers-edit-1.html?json=true&ajax_page=true",
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
                            Toast.makeText(Add_employee.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("do","insert");
                    map.put("json_email", Config.getJsonEmail(context));
                    map.put("json_password", Config.getJsonPassword(context));
                    map.put("name",name.getText().toString() );
                    map.put("age",age.getText().toString());
                    map.put("phone",phone.getText().toString());
                    Log.d("sesese",CountryWIDS.get(employee_city_spinner.getSelectedItemPosition()));
                    map.put("city",CountryWIDS.get(employee_city_spinner.getSelectedItemPosition()));
                    for(int i=0 ; i<Professionw_send_ID.size() ; i++)
                    {
                        map.put("professionInResidence[]"+i, Professionw_send_ID.get(i));
                    }
                    for(int j=0 ; j<nationalitiesw_send_ID.size() ; j++)
                    {
                        map.put("nationalities[]"+j, nationalitiesw_send_ID.get(j));
                    }
                    for(int k=0 ; k<professionw_send_ID2.size() ; k++)
                    {
                        map.put("actualProfession[]"+k, professionw_send_ID2.get(k));
                    }
                    map.put("expirationDateOfResidence",end_date.getText().toString());
                    Log.d("map2222", map.toString());
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }


}
