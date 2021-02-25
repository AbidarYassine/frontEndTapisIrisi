package com.example.tapisirisi.ServiceImpl.Motif;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.tapisirisi.Common.utils.Consts;
import com.example.tapisirisi.Services.MotifService;
import com.example.tapisirisi.Services.PropreiteService;
import com.example.tapisirisi.UI.Main.MainActivity;
import com.example.tapisirisi.model.Motif;
import com.example.tapisirisi.model.Propriete;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupprimerMotif extends Service {
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
    public void deleteMotif(Long id) {
        getClient();
        Log.i("info",String.valueOf(id));
        Call<Void> call = motifService.delteMotif(id);
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
        Bundle bundle = intent.getExtras();
        long idPropriete = bundle.getLong("idMotif");
        deleteMotif(idPropriete);
        Log.d("resultat requette 11111", "Boo!");
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
         return  null;
    }
}