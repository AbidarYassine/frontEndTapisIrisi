package com.example.tapisirisi.ServiceImpl.motif;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tapisirisi.Common.utils.Consts;
import com.example.tapisirisi.Services.MotifService;
import com.example.tapisirisi.Services.PropreiteService;
import com.example.tapisirisi.UI.Main.MainActivity;
import com.example.tapisirisi.model.Motif;
import com.example.tapisirisi.model.UserMotif;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MotifServiceImpl extends Service {
    private static final String TAG = "MotifSerice";
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

    public void delteMotif(Long id) {
        getClient();
        Call<Motif> call = motifService.delteMotif(id);
        final Motif[] fetchedMotif = {new Motif()};
        call.enqueue(new Callback<Motif>() {
            @Override
            public void onResponse(Call<Motif> call, Response<Motif> response) {
                Log.i(TAG, "updateMotif: " + response.body());
                if (response.isSuccessful()) {
                    Motif motif1 = response.body();
                    Log.i("DELETE", motif1.getDescription());
                    Intent intent = new Intent(getApplicationContext(), MotifServiceImpl.class);
                    startService(intent);

                } else {
                    Log.d("Yo", "Boo!");
                }
            }

            @Override
            public void onFailure(Call<Motif> call, Throwable t) {

            }
        });
    }

    public void updateMotif(Motif motif, File file, Long id) {
        getClient();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Log.i("id user motif ", String.valueOf(id));
        Log.i("mitif id", String.valueOf(motif.getId()));
        Call<UserMotif> call = motifService.updateMotif(motif.getId(), motif.getLibelle(), body, id);
        Call<Void> call1 = propreiteService.update(motif);
        final UserMotif[] fetchedMotif = {new UserMotif()};
        call1.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("info", "propriete executed");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        call.enqueue(new Callback<UserMotif>() {
            @Override
            public void onResponse(Call<UserMotif> call, Response<UserMotif> response) {
                Log.i(TAG, "updateMotif: " + response.body());
                if (response.isSuccessful()) {
                    UserMotif motif1 = response.body();
                    fetchedMotif[0] = motif1;
                    Log.i("UPDATE", String.valueOf(motif1.getId()));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("updatedMotif", (Serializable) fetchedMotif);
//                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    Log.d("Yo", "Boo!");
                }
            }

            @Override
            public void onFailure(Call<UserMotif> call, Throwable t) {
                Log.i("info", t.getMessage());
            }

        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        File picture = (File) intent.getExtras().get("picture");
        Bundle bundle = intent.getExtras();
        Motif m = (Motif) bundle.getSerializable("updatedMotif");
        File file = (File) bundle.getSerializable("picture");
        long idUserMotif = (long) bundle.getLong("idUserMotif");
        updateMotif(m, file, idUserMotif);
        Log.d("resultat requette 11111", "Boo!");
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}