package com.example.tapisirisi.activities.Admin;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.adapter.admin_modif_adapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.logic.model.Propriete;

import java.util.ArrayList;
import java.util.List;

public class Modifier extends AppCompatActivity {
    String libelles[] = {"l1","l2","l3"};
    String props[] = {"p1","p2,","p3"};

    private ListView lv;
    private TextView lib;

    private static List<Propriete> motifs = new ArrayList<Propriete>() {{
        add(new Propriete(1, "test1", "test1"));
        add(new Propriete(2, "test1", "test2"));
        add(new Propriete(3, "test1", "test3"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.admin_modif);
        ListView lv = findViewById(R.id.propModidlv);
        this.getSupportActionBar().hide();

        admin_modif_adapter a = new admin_modif_adapter(this, motifs);
        lv.setAdapter(a);

    }

    /*public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String libelles[];
        String proprties[];

        public MyAdapter(Context c, String libs[], String props[]){
            super(c, R.layout.admin_row_list);
            this.context = c;
            this.libelles = libs;
            this.proprties = props;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.admin_row_prp_modif, parent, false);
            EditText lib = row.findViewById(R.id.libelle);
            EditText desc = row.findViewById(R.id.desc);

            lib.setText(libelles[position]);
            desc.setText(proprties[position]);
            return  row;
        }
    }*/


}
