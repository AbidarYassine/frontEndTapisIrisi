package com.example.tapisirisi.UI.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tapisirisi.R;
import com.example.tapisirisi.UI.fragment.AccountFragment;
import com.example.tapisirisi.UI.fragment.AddFragment;
import com.example.tapisirisi.UI.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BottomNavigationView buttom_navigation;
    private Context context;

    public Context getContext() {
        Context applicationContext = getApplicationContext();
        return applicationContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new HomeFragment()).commit();
//        }
        setupView();
        buttom_navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragmnetSelected = null;
            switch (item.getItemId()) {
                case R.id.home:
                    fragmnetSelected = new HomeFragment();
                    break;
                case R.id.add:
                    fragmnetSelected = new AddFragment();
                    break;
                case R.id.account:
                    fragmnetSelected = new AccountFragment();

                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_content, fragmnetSelected)
                    .commit();
            return true;

        });


//        try {
//            th.join();
//        } catch (Exception e) {
//            Log.i(TAG, "onCreate: " + e.getLocalizedMessage());
//        }
//        Intent i = new Intent(this, UserMService.class);
//        startService(i);


    }

    private void setupView() {
        buttom_navigation = findViewById(R.id.buttom_navigation);
    }
}