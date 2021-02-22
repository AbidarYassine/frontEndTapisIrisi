package com.example.tapisirisi.activities.Register;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.example.tapisirisi.R;

public class CustomSpinner extends Dialog {
    private ProgressBar progressBar;

    public CustomSpinner(@NonNull Context context) {
        super(context);
        setContentView(R.layout.spinner);
        this.progressBar = findViewById(R.id.progressBar);
    }
}
