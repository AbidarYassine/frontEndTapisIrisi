package com.example.tapisirisi.activities.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tapisirisi.R;
import com.example.tapisirisi.activities.Admin.Admin;
import com.example.tapisirisi.activities.Login.Login;
import com.example.tapisirisi.activities.Register.Register;
import com.example.tapisirisi.activities.ui.HistoriqueListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}

