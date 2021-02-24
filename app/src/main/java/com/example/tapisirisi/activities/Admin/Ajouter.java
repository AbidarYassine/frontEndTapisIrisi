package com.example.tapisirisi.activities.Admin;

import android.content.Context;
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
import com.example.tapisirisi.ServiceImpl.Motif.AddMotifService;
import com.example.tapisirisi.activities.Register.CustomPopup;
import com.example.tapisirisi.activities.Register.CustomSpinner;
import com.example.tapisirisi.activities.utilUiOpenCv.OpenCVCameraActivity;
import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.utils.ValidationData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ajouter extends AppCompatActivity {
    private static final String TAG = "Ajouter";
    Button bntAjout;
    EditText libelle_motif;
    EditText description;
    ListView lv;
    Button imageChange;
    ImageView image2;
    Bitmap bitmap;
    CustomSpinner spinner;
    public static Context context;
    CustomPopup popup;
    private static List<Motif> motifs = new ArrayList<Motif>() {{
//        add(new Motif(1, R.drawable.ic_launcher_background, "test1"));
//        add(new Motif(2, R.drawable.ic_launcher_background, "test2"));
//        add(new Motif(2, R.drawable.ic_launcher_background, "test3"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.admin_ajout);
        setupView();
        context = this;
        popup = new CustomPopup(this);
        spinner = new CustomSpinner(this);
        popup.getButton().setOnClickListener((v) ->
        {
            popup.dismiss();
        });
        ListView lv = findViewById(R.id.propModidlv);
        this.getSupportActionBar().hide();
        bntAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationData()) {
                    Intent i = new Intent(Ajouter.this, AddMotifService.class);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                    byte[] bytes = stream.toByteArray();
                    File pictureFile = OpenCVCameraActivity.getOutputMediaFile(OpenCVCameraActivity.MEDIA_TYPE_IMAGE);
                    Log.i("TAG", "onCameraFrame: " + pictureFile.toString());
                    try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        fos.write(bytes);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(Ajouter.this, AddMotifService.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("description", description.getText().toString());
                    bundle.putString("libelle", libelle_motif.getText().toString());
                    bundle.putSerializable("picture", (Serializable) pictureFile);
                    intent.putExtras(bundle);
                    startService(intent);
                }


            }
        });
        imageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

    }

    private boolean validationData() {
        if (!ValidationData.fieldValidation(libelle_motif.getText().toString())) {
            libelle_motif.setError("Invalid data");
            libelle_motif.requestFocus();
            return false;
        }
        if (!ValidationData.fieldValidation(description.getText().toString())) {
            description.setError("Invalid data");
            description.requestFocus();
            return false;
        }
        if (bitmap == null) {
            popup.setTitle("Ouuups");
            popup.setContent("Merci de Choisir une photo");
            popup.build();
            return false;
        }
        return true;
    }

    private void setupView() {
        bntAjout = findViewById(R.id.bntAjout);
        libelle_motif = findViewById(R.id.libelle_motif);
//        lv = findViewById(R.id.lv);
        imageChange = findViewById(R.id.buttonImageChange);
        image2 = findViewById(R.id.image2);
        description = findViewById(R.id.description);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri chosenImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                image2.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
