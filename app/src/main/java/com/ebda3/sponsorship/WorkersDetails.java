package com.ebda3.sponsorship;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class WorkersDetails extends AppCompatActivity {

    Activity activity = this;
    Context context = this;


    //TextView i_d ;
    TextView name ;
    ImageView photo ;
    TextView age ;
    TextView phone ;
    TextView nationalities ;
    TextView profession_in_residence ;
    //TextView actual_profession ;
    TextView city ;
    TextView expiration_date_of_residence ;

    String  Name , Photo , Age , Phone , Nationalities , ProfessionInResidence , ActualProfession , City , ExpirationDateOfResidence;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
       // ID = intent.getStringExtra("ID");
        Name = intent.getStringExtra("Name");
        Photo = intent.getStringExtra("Photo");
        Age = intent.getStringExtra("Age");
        Phone = intent.getStringExtra("Phone");
        Nationalities = intent.getStringExtra("Nationalities");
        ProfessionInResidence = intent.getStringExtra("ProfessionInResidence");
        //ActualProfession = intent.getStringExtra("ActualProfession");
        City = intent.getStringExtra("City");
        ExpirationDateOfResidence = intent.getStringExtra("ExpirationDateOfResidence");

       // i_d = (TextView) findViewById(R.id.i_d);
        name = (TextView) findViewById(R.id.name);

        photo = (ImageView) findViewById(R.id.photo);
        age = (TextView) findViewById(R.id.age);
        phone = (TextView) findViewById(R.id.phone);
        nationalities = (TextView) findViewById(R.id.nationalities);
        profession_in_residence = (TextView) findViewById(R.id.profession_in_residence);
        //actual_profession = (TextView) findViewById(R.id.actual_profession);
        city = (TextView) findViewById(R.id.city);
        expiration_date_of_residence = (TextView) findViewById(R.id.expiration_date_of_residence);


       // i_d.setText(ID);
        name.setText(Name);
//        Picasso.with(context).load("http://adc-company.net/kafala/uploads/"+Photo  )
//                .resize(360, 280)
//                .centerCrop()
//                .error(R.drawable.logo)
//                .into(photo);

        age.setText(Age);
        phone.setText(Phone);
        nationalities.setText(Nationalities);
        profession_in_residence.setText(ProfessionInResidence);
       // actual_profession.setText(ActualProfession);
        city.setText(City);
        expiration_date_of_residence.setText(ExpirationDateOfResidence);



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


