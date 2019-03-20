package com.ebda3.sponsorship;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Register extends AppCompatActivity {

    Context context = this;
    LinearLayout employee,owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        employee = (LinearLayout)findViewById(R.id.employee);
        owner = (LinearLayout)findViewById(R.id.owner);


        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(context,EmployeeRegistration.class);
                startActivity(myintent);
            }
        });

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,OwnerRegistration.class);
                startActivity(intent);
            }
        });

    }
}
