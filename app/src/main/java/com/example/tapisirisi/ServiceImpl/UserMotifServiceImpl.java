package com.example.tapisirisi.ServiceImpl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tapisirisi.Services.UserMotifService;
import com.example.tapisirisi.activities.Admin.Admin;
import com.example.tapisirisi.logic.model.UserMotif;
import com.example.tapisirisi.utils.Consts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserMotifServiceImpl extends Service {
    private static final String TAG = "UserMotifSerice";
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Consts.API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static private UserMotifService userMotifService = retrofit.create(UserMotifService.class);

    public void getAllUserMotifs() {
        Call<List<UserMotif>> call = userMotifService.getAllUserMotif();
        final List<UserMotif> fetchedUserMotif = new ArrayList<UserMotif>();
        call.enqueue(new Callback<List<UserMotif>>() {
            @Override
            public void onResponse(Call<List<UserMotif>> call, Response<List<UserMotif>> response) {
                Log.i(TAG, "getAllUserMotif: " + response.body());
                if (response.isSuccessful()) {
                    List userMotifs = response.body();
                    fetchedUserMotif.addAll(userMotifs);
                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("value", (Serializable) fetchedUserMotif);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Log.d("Yo", "Boo!");
                }
            }

            @Override
            public void onFailure(Call<List<UserMotif>> call, Throwable t) {
                Log.i("TAGerr", t.getMessage());
            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getAllUserMotifs();
        return START_NOT_STICKY;
    }

    public void talk() {
        Intent i = new Intent();
//        i.putExtras("usermotifs", getAllUserMotif());
//        https://stackoverflow.com/questions/3539300/how-do-i-get-data-from-a-running-service/3539370
        i.setAction("FILTER");
        sendBroadcast(i);
    }
}
