package adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ebda3.sponsorship.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;



public class WorkersListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> Name;
    private final ArrayList<String> Photo;
    private final ArrayList<String> ProfessionInResidence;
    private final ArrayList<String> ActualProfession;




    public WorkersListAdapter(Activity context  , ArrayList<String> Name , ArrayList<String> Photo , ArrayList<String> ProfessionInResidence , ArrayList<String> ActualProfession ) {
        super(context, R.layout.workers_item  ,  Name );
        // TODO Auto-generated constructor stub

        this.context=context;
        this.Name=Name;
        this.Photo=Photo;
        this.ProfessionInResidence=ProfessionInResidence;
        this.ActualProfession=ActualProfession;


    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.workers_item , null,true);

        Log.d("tetttt",Name.get(position));
        TextView name = (TextView) rowView.findViewById(R.id.name);
        ImageView photo = (ImageView) rowView.findViewById(R.id.photo);
        TextView profession_in_residence = (TextView) rowView.findViewById(R.id.profession_in_residence);
        TextView actual_profession = (TextView) rowView.findViewById(R.id.actual_profession);
        name.setText(Name.get(position));
        Picasso.with(this.getContext()).load("http://adc-company.net/kafala/uploads/"+Photo.get(position)  )
                .resize(150, 150)
                .centerCrop()
                .error(R.drawable.logo)
                .into(photo);

        profession_in_residence.setText(ProfessionInResidence.get(position));
        actual_profession.setText(ActualProfession.get(position));


        return rowView;


    };
}
