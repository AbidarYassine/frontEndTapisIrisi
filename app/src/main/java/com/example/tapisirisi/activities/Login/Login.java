package com.example.tapisirisi.activities.Login;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.UserServiceImpl;
import com.example.tapisirisi.activities.Register.CustomPopup;
import com.example.tapisirisi.activities.Register.CustomSpinner;
import com.example.tapisirisi.activities.Register.Register;
import com.example.tapisirisi.activities.utilUiOpenCv.CameraStartupActivity;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    private TextInputEditText login, password;
    private Button btn;
    CustomPopup popup;
    CustomSpinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // hide the action bar
//        this.getSupportActionBar().hide();
        btn = findViewById(R.id.loginButton);
        login = findViewById(R.id.loginInput);
        password = findViewById(R.id.passwordInput);
        textView = findViewById(R.id.forgettenPass);
        popup = new CustomPopup(this);
        spinner = new CustomSpinner(this);
        Intent registerIntent = new Intent(this, Register.class);
        // redirection après la connexion
        Intent intent = new Intent(this, Register.class);
        popup.getButton().setOnClickListener(v1 -> {
            popup.dismiss();
            login.setText("");
            password.setText("");
        });
        textView.setOnClickListener(v -> {
            startActivity(registerIntent);
        });
        btn.setOnClickListener(v -> {
            String loginValue = login.getText().toString();
            String passwordValue = password.getText().toString();
            if (loginValue.equals("") || passwordValue.equals("")) {

                showPopup("Ouups", "tous les champs sont obligatoires");
            } else {
                UserServiceImpl.login(loginValue, passwordValue, spinner, popup, intent, this);
            }
        });

        Intent intent1 = new Intent(Login.this, CameraStartupActivity.class);
        startActivity(intent1);
    }

    public void showPopup(String title, String content) {
        popup.setTitle(title);
        popup.setContent(content);

        popup.build();
        popup.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }
}