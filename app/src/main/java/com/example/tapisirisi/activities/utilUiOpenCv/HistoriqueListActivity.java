package com.example.tapisirisi.activities.utilUiOpenCv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.adapter.HistoriqueListAdapter;
import com.example.tapisirisi.logic.model.Motif;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueListActivity extends AppCompatActivity {

    private ListView historiqueListView;

    private static List<Motif> motifs = new ArrayList<Motif>() {{
//        add(new Motif(1, R.drawable.ic_launcher_background, "test1"));
//        add(new Motif(2, R.drawable.ic_launcher_background, "test2"));
//        add(new Motif(2, R.drawable.ic_launcher_background, "test3"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_list);
        this.getSupportActionBar().hide();

        HistoriqueListAdapter historiqueListAdapter = new HistoriqueListAdapter(this, motifs);

        historiqueListView = findViewById(R.id.historiqueListView);
        historiqueListView.setAdapter(historiqueListAdapter);

        historiqueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoriqueListActivity.this, HistoriqueMotifDetailsActivity.class);
                Motif selectedMotif = motifs.get(position);
                intent.putExtra("element_id", selectedMotif.getId());
                intent.putExtra("element_libelle", selectedMotif.getLibelle());
                startActivity(intent);
            }
        });

    }
}