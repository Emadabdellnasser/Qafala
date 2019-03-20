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

public class TaskmasterDetails extends AppCompatActivity {

    Activity activity = this;
    Context context = this;



    TextView name ;
    ImageView photo ;
    TextView city ;
    TextView phone ;
    TextView professions ;
    TextView workplace ;
    TextView required_nationalities ;
    TextView advantages ;

    String  Name , Photo , City , Phone , Professions , Workplace , RequiredNationalities , Advantages;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskmaster_details);

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
        City = intent.getStringExtra("City");
        Phone = intent.getStringExtra("Phone");
        Professions = intent.getStringExtra("Professions");
        Workplace = intent.getStringExtra("Workplace");
        RequiredNationalities = intent.getStringExtra("RequiredNationalities");
        Advantages = intent.getStringExtra("Advantages");

        //i_d = (TextView) findViewById(R.id.i_d);
        name = (TextView) findViewById(R.id.name);
        photo = (ImageView) findViewById(R.id.photo);
        city = (TextView) findViewById(R.id.city);
        phone = (TextView) findViewById(R.id.phone);
        professions = (TextView) findViewById(R.id.professions);
        workplace = (TextView) findViewById(R.id.workplace);
        required_nationalities = (TextView) findViewById(R.id.required_nationalities);
        advantages = (TextView) findViewById(R.id.advantages);



        name.setText(Name);
        Picasso.with(context).load("http://adc-company.net/kafala/uploads/"+Photo  )
                .resize(360, 280)
                .centerCrop()
                .error(R.drawable.logo);


        city.setText(City);
        phone.setText(Phone);
        professions.setText(Professions);
        workplace.setText(Workplace);
        required_nationalities.setText(RequiredNationalities);
        advantages.setText(Advantages);



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

