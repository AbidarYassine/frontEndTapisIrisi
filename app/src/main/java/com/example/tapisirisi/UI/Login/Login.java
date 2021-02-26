package com.example.tapisirisi.UI.Login;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapisirisi.Common.database.DatabaseHelper;
import com.example.tapisirisi.R;
import com.example.tapisirisi.ServiceImpl.User.UserServiceImpl;
import com.example.tapisirisi.ServiceImpl.UserMotif.UserMotifServiceImpl;
import com.example.tapisirisi.UI.Main.MainActivity;
import com.example.tapisirisi.UI.Register.CustomPopup;
import com.example.tapisirisi.UI.Register.CustomSpinner;
import com.example.tapisirisi.UI.Register.Register;
import com.example.tapisirisi.model.Role;
import com.example.tapisirisi.model.User;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    private TextInputEditText login, password;
    private Button btn;
    CustomPopup popup;
    CustomSpinner spinner;
    private TextView textView,forgettenPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // hide the action bar
        this.getSupportActionBar().hide();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        User user = databaseHelper.getCurrentUser();


        if (user != null) {
            Log.i("TAG", "onCreate: user id" + user.getRole());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
        } else {

            btn = findViewById(R.id.loginButton);
            login = findViewById(R.id.loginInput);
            password = findViewById(R.id.passwordInput);
            forgettenPass= findViewById(R.id.forgettenPass);
            forgettenPass.setOnClickListener(v -> {
                Intent intent = new Intent(this,Register.class);
                startActivity(intent);
            });
            textView = findViewById(R.id.forgettenPass);
            popup = new CustomPopup(this);
            spinner = new CustomSpinner(this);
            Intent registerIntent = new Intent(this, Register.class);
            Intent intent1 = new Intent(Login.this, MainActivity.class);
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
                    UserServiceImpl.login(loginValue, passwordValue, spinner, popup, intent1, this);
                    finish();
                }
            });


        }


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
                Log.i("TAG", "onCreate: dflkdsfdslfndskfndslfdsfks");
                UserServiceImpl.login(loginValue, passwordValue, spinner, popup, intent, this);
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