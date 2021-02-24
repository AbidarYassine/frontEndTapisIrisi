    package com.example.tapisirisi.activities.Admin;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.MotifServiceImpl;
import com.example.tapisirisi.logic.adapter.admin_list_adapter;
import com.example.tapisirisi.logic.adapter.admin_modif_adapter;
import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.logic.model.Propriete;
import com.example.tapisirisi.logic.model.UserMotif;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

public class ModifierMotif extends AppCompatActivity {

    private Button modifier;
    Bitmap bitmap;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.admin_modif);
        Button enregistrer = findViewById(R.id.enrgModifs);
        ListView lv = findViewById(R.id.propModidlv);
        this.getSupportActionBar().hide();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        UserMotif userMotif = (UserMotif) bundle.getSerializable("userMotif");

        EditText lib = findViewById(R.id.modifLibelle);
        lib.setText(userMotif.getMotif().getLibelle());
         imageView = findViewById(R.id.imageModif);
        modifier = findViewById(R.id.buttonImageChange);
        Picasso.get().load(userMotif.getFileUrl()).into(imageView);
        List<Propriete> proprieteList = userMotif.getMotif().getProprietes();
        admin_modif_adapter al = new admin_modif_adapter(this,proprieteList);
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
                Motif m = new Motif();
                m.setLibelle(lib.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MotifServiceImpl.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("updetedMotif", (Serializable) m);
                intent.putExtras(bundle);
                startService(intent);
            }
        });
        lv.setAdapter(al);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri chosenImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
