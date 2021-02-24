package com.example.tapisirisi.UI.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.User.UserServiceImpl;
import com.example.tapisirisi.UI.Login.Login;
import com.example.tapisirisi.UI.Register.CustomPopup;
import com.example.tapisirisi.UI.Register.CustomSpinner;
import com.example.tapisirisi.Common.database.DatabaseHelper;
import com.example.tapisirisi.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class AccountFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    View rootView;
    private TextInputEditText nom, prenom, password, newPassword, cNewPassword;
    private TextInputLayout nomInput;
    private Button enregister,logout;
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
        logout = rootView.findViewById(R.id.logout);
        nom.setText(user.getNom());
        prenom.setText(user.getPrenom());
        login.setText(user.getLogin());
        logout.setOnClickListener(v -> {
            databaseHelper.delete(user);
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);

        });
        String passwordValue = password.getText().toString();
        CustomPopup popup = new CustomPopup(getContext());
        CustomSpinner spinner = new CustomSpinner(getContext());
        popup.getButton().setOnClickListener(v -> {
            popup.dismiss();
            password.setText("");
            newPassword.setText("");
            cNewPassword.setText("");
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
