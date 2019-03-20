package com.ebda3.sponsorship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.ebda3.sponsorship.Helpers.MarshMallowPermission;
import com.ebda3.sponsorship.Helpers.MultiPartUtility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.ebda3.sponsorship.Helpers.Config.WebService;
import static com.ebda3.sponsorship.Helpers.Config.imageupload;


public class MyAccount extends AppCompatActivity {

    Activity activity = this;
    Context context = this;
    public Toolbar toolbar;
    public ImageView user_photo,upload;
    LinearLayout MainLinearLayout,LoadingLinearLayout,edit,save,account_id,edit_password_linear;
    TextView name,code;
    EditText edit_name,edit_password;
    Button retry;
    ProgressBar progressBar;

    String imageURL = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;


    public String URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        String UserType = Config.getUserType(context);
        if ( UserType.equals("user") )
        {
            URL = Config.SignUpCustomer ;
        }
        else if ( UserType.equals("workshop") )
        {
            URL = Config.SignUpWorkshop ;
        }
        else if ( UserType.equals("trader") )
        {
            URL = Config.SignUpTrader ;
        }
        else if ( UserType.equals("importer") )
        {
            URL = Config.SignUpImporter ;
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        retry = (Button) findViewById(R.id.retry);
        edit_password_linear = (LinearLayout) findViewById(R.id.edit_password_linear);
        account_id = (LinearLayout) findViewById(R.id.account_id);
        edit = (LinearLayout) findViewById(R.id.edit);
        save = (LinearLayout) findViewById(R.id.save);
        MainLinearLayout = (LinearLayout) findViewById(R.id.MainLinearLayout);
        LoadingLinearLayout = (LinearLayout) findViewById(R.id.LoadingLinearLayout);
        name = (TextView) findViewById(R.id.name);

        code = (TextView) findViewById(R.id.code);
        user_photo = (ImageView) findViewById(R.id.user_photo);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_password = (EditText) findViewById(R.id.edit_password);

        upload = (ImageView) findViewById(R.id.upload);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetProfile();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_name.setVisibility(View.VISIBLE);

                edit_password_linear.setVisibility(View.VISIBLE);
                upload.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);

                account_id.setVisibility(View.GONE);
                name.setVisibility(View.GONE);

                edit.setVisibility(View.GONE);


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = edit_name.getText().toString().trim();
                String UserPassword = edit_password.getText().toString().trim();

                if (Name.length() < 4 ) {
                    edit_name.setError( "من فضلك اكتب الاسم" );
                }

                else if ( UserPassword.length() > 0 && UserPassword.length() < 4  ) {
                    edit_password.setError("كلمة السر صغيرة جدا");
                }
                else {
                    sendData(Name , UserPassword);
                }
            }
        });



        final MarshMallowPermission marshMallowPermission = new MarshMallowPermission(activity);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!marshMallowPermission.checkPermissionForRead()) {
                    marshMallowPermission.requestPermissionForRecord();
                } else {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
                    /*
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                    */

                }

            }
        });

        GetProfile();

    }



    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void sendData(final String name, final String password) {

        boolean t = isNetworkConnected();
        if(t) {
            progressBar.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
            edit_name.setEnabled(false);
            edit_password.setEnabled(false);

            upload.setEnabled(false);
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String charset = "UTF-8";
                        MultiPartUtility multipart = new MultiPartUtility(charset);
                        HashMap<String, String> params = new HashMap<>();
                        params.put("do", "updates");
                        params.put("id", Config.getUserID(context));
                        params.put("name", name);

                        if ( password.length() > 0 ) {
                            params.put("password", password);
                        }

                        params.put("json_email", Config.getJsonEmail(context));
                        params.put("json_password", Config.getJsonPassword(context));
                        Pair<String, String> imag = new Pair<>("image", imageURL);
                        multipart.SetParams(params, imag);
                        multipart.execute(URL);
                        final String Response = multipart.get();
                        Log.e("Response", params.toString());
                        Log.e("Response", Response);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject object = new JSONObject(Response);
                                    if ( object.has("ID") )
                                    {
                                        SharedPreferences sp=getSharedPreferences("Login", 0);
                                        SharedPreferences.Editor Ed=sp.edit();
                                        Ed.putString("name",object.getString("name").toString() );
                                        Ed.putString("username",object.getString("username").toString() );
                                        Ed.putString("photo",object.getString("photo").toString() );
                                        if ( edit_password.getText().toString().length() > 0 ) {
                                            Ed.putString("password", edit_password.getText().toString());
                                        }
                                        Ed.putString("json_password",object.getString("password") );
                                        Ed.putString("json_email",object.getString("username") );
                                        Ed.commit();
                                        GetProfile();
                                    }
                                }catch (Exception e)
                                {
                                    edit_name.setEnabled(true);
                                    edit_password.setEnabled(true);

                                    upload.setEnabled(true);
                                    save.setVisibility(View.VISIBLE);
                                    JSONArray array = null;
                                    try {
                                        array = new JSONArray(Response);
                                        if ( array.getString(0).equals("Field password  From 6 To 20") ) {
                                            edit_password.setError("كلمة السر صعيفة جدا");
                                            edit_password.setText("");
                                        }

                                        //Toast.makeText(activity, array.getString(0), Toast.LENGTH_SHORT).show();
                                        Log.d("response",  array.getString(0));
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        });

                    }
                    catch (Exception e)
                    {
                        Log.e("Response", e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "برجاء المحاولة مرة اخرى", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }



                }
            });
            th.start();
        }
        else
        {
            Toast.makeText(context, "please Enable Your Internet Connection", Toast.LENGTH_LONG).show();
        }












    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, s);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            user_photo.setImageBitmap(imageBitmap);
            Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);
            imageURL = getRealPathFromURI(tempUri);
        }
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                user_photo.setImageBitmap(bitmap);
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                imageURL = cursor.getString(idx);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void GetProfile()
    {
        edit_name.setEnabled(true);
        edit_password.setEnabled(true);

        upload.setEnabled(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST , WebService , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("xxxxxx", response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    if (jObj.has("ID"))
                    {
                        Log.d("xxxxxx", response);
                        MainLinearLayout.setVisibility(View.VISIBLE);
                        LoadingLinearLayout.setVisibility(View.GONE);
                        retry.setVisibility(View.GONE);

                        code.setText(jObj.getString("ID"));
                        name.setText(jObj.getString("name"));


                        edit_name.setText(jObj.getString("name"));

                        Picasso.with(context).load(imageupload + jObj.getString("photo"))
                                .resize(360,256)
                                .centerCrop()
                                .transform(new CropCircleTransformation())
                                .error(R.drawable.ic_account_circle_white_48dp)
                                .into(user_photo);

                        edit_name.setVisibility(View.GONE);

                        edit_password_linear.setVisibility(View.GONE);
                        upload.setVisibility(View.GONE);
                        save.setVisibility(View.GONE);

                        account_id.setVisibility(View.VISIBLE);
                        name.setVisibility(View.VISIBLE);

                        edit.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    retry.setVisibility(View.VISIBLE);
                    MainLinearLayout.setVisibility(View.GONE);
                    LoadingLinearLayout.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                retry.setVisibility(View.VISIBLE);
                MainLinearLayout.setVisibility(View.GONE);
                LoadingLinearLayout.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("do", "MyInfo");
                params.put("json_email", Config.getJsonEmail(context) );
                params.put("json_password", Config.getJsonPassword(context) );

                Log.d("xxxxxx",params.toString());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();

        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}