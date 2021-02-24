package com.example.tapisirisi.activities.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.motif.AddMotifService;
import com.example.tapisirisi.ServiceImpl.propeiter.PropeiterServiceImpl;
import com.example.tapisirisi.activities.Register.CustomSpinner;
import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.utils.ValidationData;

import java.io.Serializable;


public class Ajout_Prop extends AppCompatActivity {
    EditText libelle;
    EditText description;
    Button ajouterPropriet;
    CustomSpinner spinner;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout__prop);

        setupView();
        this.getSupportActionBar().hide();
        context = this;
        spinner = new CustomSpinner(this);
//        returnBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Motif motifSaved =
                (Motif) bundle
                        .getSerializable("value");
        if (motifSaved != null) {
            Toast.makeText(this, "" + motifSaved.getLibelle(), Toast.LENGTH_SHORT).show();
        }



        ajouterPropriet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationData()) {
                    Toast.makeText(Ajout_Prop.this, "commence", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Ajout_Prop.this, PropeiterServiceImpl.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("description", description.getText().toString());
                    bundle.putString("libelle", libelle.getText().toString());
                    bundle.putLong("idMotif", motifSaved.getId());
                    intent.putExtras(bundle);
                    startService(intent);
                }

            }
        });
    }

    private boolean validationData() {

        if (!ValidationData.fieldValidation(libelle.getText().toString())) {
            libelle.setError("Invalid data");
            libelle.requestFocus();
            return false;
        }
        if (!ValidationData.fieldValidation(description.getText().toString())) {
            description.setError("Invalid data");
            description.requestFocus();
            return false;
        }
        return true;
    }

    private void setupView() {
        libelle = findViewById(R.id.libelle);
        description = findViewById(R.id.description);
        ajouterPropriet = findViewById(R.id.ajouterPropriet);

    }
}