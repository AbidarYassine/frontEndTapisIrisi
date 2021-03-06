    package com.example.tapisirisi.UI.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.example.tapisirisi.ServiceImpl.motif.MotifServiceImpl;
import com.example.tapisirisi.UI.utilUiOpenCv.OpenCVCameraActivity;
import com.example.tapisirisi.UI.adapter.admin_modif_adapter;
import com.example.tapisirisi.model.Motif;
import com.example.tapisirisi.model.Propriete;
import com.example.tapisirisi.model.UserMotif;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

    public class ModifierMotif extends AppCompatActivity {

    private Button modifier;
    Bitmap bitmap;
    ImageView imageView;
    List<Propriete> proprietes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.admin_modif);
        Button enregistrer = findViewById(R.id.enrgModifs);
        ListView lv = findViewById(R.id.propModidlv);
        this.getSupportActionBar().hide();
        proprietes = new ArrayList<>();
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
        lv.setAdapter(al);
        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Motif m = new Motif();
                m.setId(userMotif.getMotif().getId());
                m.setLibelle(lib.getText().toString());
                for (int i=0;i<lv.getCount();i++){
                  Propriete propriete = (Propriete) lv.getItemAtPosition(i);
                    proprietes.add(propriete);
                }
                Bundle bundle = new Bundle();
                if(bitmap != null){
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
                    bundle.putSerializable("picture", (Serializable) pictureFile);
                    bundle.putLong("idUserMotif",userMotif.getId());
                }else  {
                    Picasso.get().load(userMotif.getFileUrl()).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap1, Picasso.LoadedFrom from) {
                            // Set it in the ImageView
                            bitmap = bitmap1;
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
                            bundle.putSerializable("picture", (Serializable) pictureFile);
                            bundle.putLong("idUserMotif",userMotif.getId());
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
                }
                 Intent intent = new Intent(getApplicationContext(), MotifServiceImpl.class);
                m.setProprietes(proprietes);
                bundle.putSerializable("updatedMotif", (Serializable) m);

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
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
