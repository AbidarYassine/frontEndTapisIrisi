package com.example.tapisirisi.activities.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.R;
import com.example.tapisirisi.logic.adapter.admin_list_adapter;
import com.example.tapisirisi.logic.adapter.admin_modif_adapter;
import com.example.tapisirisi.logic.model.Propriete;
import com.example.tapisirisi.logic.model.UserMotif;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Properties;

public class ModifierMotif extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.admin_modif);
        ListView lv = findViewById(R.id.propModidlv);
        this.getSupportActionBar().hide();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        UserMotif userMotif = (UserMotif) bundle.getSerializable("userMotif");

        EditText lib = findViewById(R.id.modifLibelle);
        lib.setText(userMotif.getMotif().getLibelle());

        List<Propriete> proprieteList = userMotif.getMotif().getProprietes();
        admin_modif_adapter al = new admin_modif_adapter(this,proprieteList);

        lv.setAdapter(al);

    }

}
