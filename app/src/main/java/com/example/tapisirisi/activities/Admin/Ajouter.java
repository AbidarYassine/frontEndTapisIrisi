package com.example.tapisirisi.activities.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.model.Motif;

import java.util.ArrayList;
import java.util.List;

public class Ajouter extends AppCompatActivity {
    Button bntAjout;

    private static List<Motif> motifs = new ArrayList<Motif>() {{
        add(new Motif(1, R.drawable.ic_launcher_background, "test1"));
        add(new Motif(2, R.drawable.ic_launcher_background, "test2"));
        add(new Motif(2, R.drawable.ic_launcher_background, "test3"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.admin_ajout);
        ListView lv = findViewById(R.id.propModidlv);
        this.getSupportActionBar().hide();

        Button btn1 = findViewById(R.id.ajouterPropriete);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ajouter.this, Ajouter.class);

                startActivity(intent);
            }
        });

    }

}
