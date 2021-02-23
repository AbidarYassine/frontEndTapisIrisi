package com.example.tapisirisi.activities.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.UserServiceImpl;
import com.example.tapisirisi.activities.Main.MainActivity;
import com.example.tapisirisi.activities.Register.CustomPopup;
import com.example.tapisirisi.activities.Register.CustomSpinner;
import com.example.tapisirisi.database.DatabaseHelper;
import com.example.tapisirisi.logic.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AccountFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    View rootView;
    private TextInputEditText nom, prenom, password, newPassword, cNewPassword;
    private TextInputLayout nomInput;
    private Button enregister;
    private TextView login;
    private Button update_prf;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.account_fragment, container, false);
        databaseHelper = new DatabaseHelper(getContext());
        User user;
        user = databaseHelper.getCurrentUser();
        Log.i("info", user.getLogin().toString());
        nom = rootView.findViewById(R.id.nom);
        prenom = rootView.findViewById(R.id.prenom);
        login = rootView.findViewById(R.id.login);
        newPassword = rootView.findViewById(R.id.newPassword);
        cNewPassword = rootView.findViewById(R.id.cNewPassword);
        password = rootView.findViewById(R.id.password);
        enregister = rootView.findViewById(R.id.save);
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        login.setText(user.getLogin());
        String passwordValue = password.getText().toString();
        CustomPopup popup = new CustomPopup(getContext());
        CustomSpinner spinner = new CustomSpinner(getContext());
        popup.getButton().setOnClickListener(v -> {
            popup.dismiss();
        });
        enregister.setOnClickListener(v -> {
            String nomValue, prenomValue;
            nomValue = nom.getText().toString();
            prenomValue = prenom.getText().toString();
            if (!nomValue.equals(user.getNom()) || !prenomValue.equals(user.getPrenom())) {
                user.setNom(nomValue);
                user.setPrenom(prenomValue);

                // save user in the remote database
            }
            if (!password.getText().toString().equals("")) {
                if (!password.getText().toString().equals(user.getPassword())) {
                    popup.setTitle("Ouuuups");
                    popup.setContent("Mot de passe actuel erroné");
                    popup.build();
                } else {
                    if (!newPassword.getText().toString().equals(cNewPassword.getText().toString()) && !newPassword.getText().equals("")) {
                        popup.setTitle("Ouuuups");
                        popup.setContent("Nouveau mot de passe et mot de passe de confirmation sont différents ");
                        popup.build();
                    } else {
                        // traitement
                        user.setPassword(newPassword.getText().toString());

                    }
                }
            }
           databaseHelper.update(user);
            UserServiceImpl.update(user,popup,spinner);
            Log.i("user info",databaseHelper.getCurrentUser().getPassword());
            // traitement backend
        });
        if (!passwordValue.equals(user.getPassword())) {

        }
        return rootView;
    }
}
