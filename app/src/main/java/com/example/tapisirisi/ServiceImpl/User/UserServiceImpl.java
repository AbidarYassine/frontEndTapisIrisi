package com.example.tapisirisi.ServiceImpl.User;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;

import com.example.tapisirisi.ServiceImpl.UserMotif.UserMotifServiceImpl;
import com.example.tapisirisi.Services.UserService;
import com.example.tapisirisi.UI.Register.CustomPopup;
import com.example.tapisirisi.UI.Register.CustomSpinner;
import com.example.tapisirisi.Common.database.DatabaseHelper;
import com.example.tapisirisi.model.User;
import com.example.tapisirisi.Common.utils.Consts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserServiceImpl {
    static Retrofit retrofit;
    static private UserService userService;

    public static void getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Consts.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }

    public static void getUsers() {
        getClient();
        Log.i("info", "/////////////////////////");
        Call<List> call = userService.getUsers();
        call.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                if (response.isSuccessful()) {
                    List users = response.body();
                    Log.i("info", users.get(0).toString());
                } else {
                    Log.d("Yo", "Boo!");
                    return;
                }
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                Log.i("info", t.getMessage());

            }
        });
    }

    public static User register(User user, CustomSpinner spinner, CustomPopup popup, Context context) {
        getClient();
        Call<User> call = userService.register(user);
        final User[] fetchedUser = new User[1];
        spinner.show();
        Window window = spinner.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popup.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    spinner.dismiss();
                    fetchedUser[0] = response.body();
                    if (fetchedUser[0].getLogin() == null) {
                        popup.setTitle("Erreur");
                        popup.setContent("Utilisateur déjà existant");
                        popup.build();
                    } else {
                        // rediriger vers l'espace utilisateur

                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("info", "error register");
                spinner.dismiss();
                popup.setTitle("Ouuups");
                popup.setContent("Erreur 505");
                popup.build();
            }
        });
        return fetchedUser[0];
    }
    public static void update(User user,CustomPopup popup,CustomSpinner spinner){
        getClient();
        Call<User> call = userService.update(user);
        final User[] fetchedUser = new User[1];
        Log.i("info","eeeeeeeeeeeee");
        spinner.show();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                spinner.dismiss();
                if(response.isSuccessful()){
                    Log.i("info","bbvdvd");
                    popup.setTitle("Succès");
                    popup.setContent("Modification bien enregistrées");
                    popup.build();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                spinner.dismiss();
                popup.setTitle("Ouups");
                popup.setContent("Erreur 505");
            }
        });
    }
    public static User login(String login, String password, CustomSpinner spinner, CustomPopup popup, Intent intent, Context context) {
        getClient();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Log.i("info", "//////////////////////");
        Call<User> call = userService.login(login, password);
        final User[] fetchedUser = new User[1];
        spinner.show();
        Window window = spinner.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        popup.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    spinner.dismiss();
                    fetchedUser[0] = response.body();
                    if (fetchedUser[0].getLogin() == null) {
                        popup.setTitle("Erreur");
                        popup.setContent("Utilisateur Inexistant");
                        popup.build();
                    } else {
                       // context.startActivity(intent);
                        spinner.dismiss();
                       databaseHelper.insertUser(fetchedUser[0]);
                     //context.startActivity(intent);
                        Intent i = new Intent(context, UserMotifServiceImpl.class);
                        i.putExtra("idUser", databaseHelper.getCurrentUser().getId());
                        context.startService(i);

                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                spinner.dismiss();
                popup.setTitle("Ouuups");
                popup.setContent("Erreur 505");
                popup.build();
            }
        });
        return fetchedUser[0];
    }
}
