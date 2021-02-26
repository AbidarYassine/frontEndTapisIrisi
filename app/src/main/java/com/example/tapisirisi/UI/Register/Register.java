package com.example.tapisirisi.UI.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.User.UserServiceImpl;
import com.example.tapisirisi.model.Role;
import com.example.tapisirisi.model.User;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {
    Button register;
    TextInputEditText login, password, cPassword;
    CustomPopup popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.register);
        login = findViewById(R.id.loginIn);
        register = findViewById(R.id.save);
        password = findViewById(R.id.passwordInput);
        cPassword = findViewById(R.id.confirmPasswordIn);
        popup = new CustomPopup(this);
        popup.getButton().setOnClickListener(v1 -> {
            popup.dismiss();
            login.setText("");
            password.setText("");
            cPassword.setText("");
        });
        CustomSpinner spinner = new CustomSpinner(this);
        register.setOnClickListener(v -> {
            String loginValue = login.getText().toString();
            String passwordValue = password.getText().toString();
            String cPasswordValue = cPassword.getText().toString();
            if (loginValue.equals("") || passwordValue.equals("") || cPasswordValue.equals("")) {
                Toast.makeText(this, "malawa mok", Toast.LENGTH_SHORT).show();
                showPopup("Ouups", "tous les champs sont obligatoires");
            } else if (!passwordValue.equals(cPasswordValue)) {
                showPopup("Ouups", "mot de passe et mot de passe de confirmation sont différents");
            } else {
                User user = new User(loginValue, passwordValue);
                user.setRole(Role.USER);
                UserServiceImpl.register(user, spinner, popup, this);
            }

        });
    }

    public void showPopup(String title, String content) {
        popup.setTitle(title);
        popup.setContent(content);

        popup.build();
        popup.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }
}