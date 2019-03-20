package com.ebda3.sponsorship.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.ebda3.sponsorship.AddTasker;
import com.ebda3.sponsorship.R;

import java.util.ArrayList;

import static com.ebda3.sponsorship.Helpers.Config.Proffesions;
import static com.ebda3.sponsorship.Helpers.Config.ProffesionsIDS;
import static com.ebda3.sponsorship.Helpers.Config.Proffesionscheckable;

/**
 * Created by work on 28/08/2017.
 */

public class CheckBoxProffesionAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> OfferName;

    public CheckBoxProffesionAdapter(Activity context, ArrayList<String> OfferName) {
        super(context, R.layout.check_box_profession_item,OfferName);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.OfferName=OfferName;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.check_box_profession_item, null,true);
        CheckBox damage_name = (CheckBox) rowView.findViewById(R.id.checkBox_damage_name);
        damage_name.setText(OfferName.get(position));


        return rowView;
    }
}
