package com.example.tapisirisi.ServiceImpl.propriete;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.tapisirisi.Services.MotifService;
import com.example.tapisirisi.Services.PropreiteService;
import com.example.tapisirisi.UI.Main.MainActivity;
import com.example.tapisirisi.Common.utils.Consts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class deleteProprieteServiceImpl extends Service {
    static Retrofit retrofit;
    static private MotifService motifService;
    static private PropreiteService propreiteService;
    public static void getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Consts.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        motifService = retrofit.create(MotifService.class);
        propreiteService = retrofit.create(PropreiteService.class);
    }

    public void deletePropriete(Long id) {
        getClient();
        Log.i("info",String.valueOf(id));
        Call<Void> call = propreiteService.deletePropriete(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("info",t.getMessage());
            }
        });
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        File picture = (File) intent.getExtras().get("picture");
        Bundle bundle = intent.getExtras();
        long idPropriete = (long) bundle.getLong("idPropriete");
        deletePropriete(idPropriete);
        Log.d("resultat requette 11111", "Boo!");
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}