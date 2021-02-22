package com.example.tapisirisi.activities.Admin;

import android.os.Bundle;
import android.widget.ListView;

import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.adapter.admin_list_adapter;

import androidx.appcompat.app.AppCompatActivity;



import com.example.tapisirisi.logic.model.Motif;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    private ListView lv;

    private static List<Motif> motifs = new ArrayList<Motif>() {{
        add(new Motif(1, R.drawable.ic_launcher_background, "test1"));
        add(new Motif(2, R.drawable.ic_launcher_background, "test2"));
        add(new Motif(2, R.drawable.ic_launcher_background, "test3"));
    }};

    //int images[] = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.admin_list);
        this.getSupportActionBar().hide();

        admin_list_adapter al = new admin_list_adapter(this,motifs);
       // MyAdapter a = new MyAdapter(this, images);
        ListView lv = findViewById(R.id.adminlv);
        lv.setAdapter(al);



    }

    /*public class MyAdapter extends BaseAdapter {
        private  Context context;
        private int rImgs[];

        public MyAdapter(Context c, int imgs[]){
            //super(c, R.layout.admin_row_list);
            this.context = c;
            this.rImgs = imgs;
        }


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.admin_row_list, parent, false);
            }

            //LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          //  View row = layoutInflater.inflate(R.layout.admin_row_list, parent, false);
            ImageView imageView = convertView.findViewById(R.id.image);
            imageView.setImageResource(rImgs[position]);
            return  convertView;
        }
    }*/
}
