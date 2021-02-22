package com.example.tapisirisi.activities.utilUiOpenCv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.model.Motif;

public class HistoriqueMotifDetailsActivity extends AppCompatActivity {

    private ImageView motifDetailsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_motif_details);
        this.getSupportActionBar().hide();

        motifDetailsImageView = findViewById(R.id.motifDetailsImageView);

        Intent intent = getIntent();
//        Motif motif = new Motif(intent.getIntExtra("element_id", 0), intent.getIntExtra("element_drawable", 0), intent.getStringExtra("element_libelle"));
//        motifDetailsImageView.setImageResource(motif.getDrawable());
    }
}