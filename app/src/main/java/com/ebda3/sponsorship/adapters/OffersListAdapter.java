package com.ebda3.sponsorship.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ebda3.sponsorship.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import static com.ebda3.sponsorship.Helpers.Config.imageupload;


public class OffersListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> eID;
    private final ArrayList<String> Date;
    private final ArrayList<String> Price;
    private final ArrayList<String> DeliveryTime;
    private final ArrayList<String> DeliveryLocation;
    private final ArrayList<String> Specifications;
    private final ArrayList<String> UserName;
    private final ArrayList<String> Photo;



    public OffersListAdapter(Activity context, ArrayList<String> eID, ArrayList<String> Date, ArrayList<String> Price, ArrayList<String> DeliveryTime, ArrayList<String> DeliveryLocation, ArrayList<String> Specifications, ArrayList<String> UserName, ArrayList<String> Photo ) {
        super(context, R.layout.offer_item  ,  eID);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.eID=eID;
        this.Date=Date;
        this.Price=Price;
        this.DeliveryTime=DeliveryTime;
        this.DeliveryLocation=DeliveryLocation;
        this.Specifications=Specifications;
        this.UserName=UserName;
        this.Photo=Photo;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.offer_item, null,true);


        TextView username = (TextView) rowView.findViewById(R.id.username);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        ImageView photo = (ImageView) rowView.findViewById(R.id.photo);





        username.setText(UserName.get(position));
        date.setText( "تاريخ العرض : "  + Date.get(position));
        price.setText( Price.get(position) + " ريال" );

        Picasso.with(this.getContext()).load(imageupload+Photo.get(position)  )
                .resize(150, 150)
                .centerCrop()
                .error(R.drawable.logo)
                .into(photo);

        return rowView;

    };
}