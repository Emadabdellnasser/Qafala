package com.ebda3.sponsorship.adapters;


import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebda3.sponsorship.R;

import java.util.ArrayList;




public class RequestsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> eID;
    private final ArrayList<String> eName;



    public RequestsListAdapter(Activity context, ArrayList<String> eID, ArrayList<String> eName) {
        super(context, R.layout.request_item  ,  eID);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.eID=eID;
        this.eName=eName;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.request_item, null,true);


        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView date = (TextView) rowView.findViewById(R.id.date);


        if ( ( position % 2 ) == 0 )
        {

        }
        else
        {
        }



        String Name = eName.get(position);
        if ( Name.length() == 0 )
        {
            Name = "طلب استيراد قطعة غيار";
        }

        name.setText(Name);

        return rowView;

    };
}