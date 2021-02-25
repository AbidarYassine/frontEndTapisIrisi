package com.example.tapisirisi.UI.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.motif.MotifServiceImpl;
import com.example.tapisirisi.UI.adapter.admin_modif_adapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.model.Motif;
import com.example.tapisirisi.model.Propriete;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Modifier extends AppCompatActivity {
    String[] libelles = {"l1","l2","l3"};
    String[] props = {"p1","p2,","p3"};
    Bitmap bitmap;
    private ListView lv;
    private Button modifier;
    private TextView lib;
    ImageView iv;
    private static final List<Propriete> motifs = new ArrayList<Propriete>() {{
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_modif);
        Log.i("info","onCreateModifier");
        ListView lv = findViewById(R.id.propModidlv);
        this.getSupportActionBar().hide();

        admin_modif_adapter a = new admin_modif_adapter(this, motifs);
        lv.setAdapter(a);
        Button enregistrer = findViewById(R.id.enrgModifs);
        EditText lib = findViewById(R.id.modifLibelle);
         iv = findViewById(R.id.imageModif);
        EditText libPro = findViewById(R.id.libellePropMotif);
        EditText descPro = findViewById(R.id.descPropMotif);
        modifier = findViewById(R.id.buttonImageChange);

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info","helelelzae$zadlâ");
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });


        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info","helleazeaezae");
                Motif m = new Motif();
                m.setLibelle(libPro.getText().toString());
                m.setDescription(descPro.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MotifServiceImpl.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("updetedMotif", (Serializable) m);
                intent.putExtras(bundle);
                startService(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri chosenImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
