package com.example.tapisirisi.activities.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tapisirisi.R;
import com.example.tapisirisi.database.DatabaseHelper;
import com.example.tapisirisi.logic.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AccountFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    View rootView;
    private TextInputEditText nom,prenom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.account_fragment, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        User user;
        user = databaseHelper.getCurrentUser();
        Log.i("info",user.getLogin());
        nom = rootView.findViewById(R.id.nom);
        prenom = rootView.findViewById(R.id.prenom);
        if(!user.getNom().toString().equals("") || !user.getPrenom().toString().equals("")){
            nom.setText(user.getNom().toString());
            prenom.setText(user.getPrenom().toString());
        }
        return inflater.inflate(R.layout.account_fragment, container, false);
    }
}
