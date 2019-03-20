package adapters;

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


public class TaskmasterListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> Name;
    private final ArrayList<String> Photo;
    private final ArrayList<String> Professions;




    public TaskmasterListAdapter(Activity context  , ArrayList<String> Name , ArrayList<String> Photo , ArrayList<String> Professions ) {
        super(context, R.layout.taskmaster_item  ,  Name );
        // TODO Auto-generated constructor stub

        this.context=context;
        this.Name=Name;
        this.Photo=Photo;
        this.Professions=Professions;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.taskmaster_item , null,true);


        TextView name = (TextView) rowView.findViewById(R.id.name);
        ImageView photo = (ImageView) rowView.findViewById(R.id.photo);
        TextView professions = (TextView) rowView.findViewById(R.id.professions);
        name.setText(Name.get(position));
        Picasso.with(this.getContext()).load("http://adc-company.net/kafala/uploads/"+Photo.get(position)  )
                .resize(150, 150)
                .centerCrop()
                .error(R.drawable.logo)
                .into(photo);

        professions.setText(Professions.get(position));


        return rowView;


    };
}