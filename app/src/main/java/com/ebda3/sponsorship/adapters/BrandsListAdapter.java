package com.ebda3.sponsorship.adapters;

import android.app.Activity;
import android.content.Context;
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


public class BrandsListAdapter  extends ArrayAdapter<com.ebda3.sponsorship.adapters.ItemData> {
    int groupid;
    Activity context;
    ArrayList<com.ebda3.sponsorship.adapters.ItemData> list;
    LayoutInflater inflater;
    public BrandsListAdapter(Activity context, int groupid, int id, ArrayList<com.ebda3.sponsorship.adapters.ItemData>
            list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
        TextView textView=(TextView)itemView.findViewById(R.id.txt);
        textView.setText(list.get(position).getText());


        Picasso.with(this.getContext()).load(imageupload+list.get(position).getImageId()  )
                .resize(64, 64)
                .centerCrop()
                .error(R.drawable.logo)
                .into(imageView);

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        return getView(position,convertView,parent);

    }
}