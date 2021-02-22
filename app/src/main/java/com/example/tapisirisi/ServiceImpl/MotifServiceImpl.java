package com.example.tapisirisi.ServiceImpl;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tapisirisi.Services.MotifService;
import com.example.tapisirisi.activities.Admin.Admin;
import com.example.tapisirisi.logic.adapter.admin_list_adapter;
import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.logic.model.UserMotif;
import com.example.tapisirisi.utils.Consts;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MotifServiceImpl  extends Service {

    static Retrofit retrofit;
    static private MotifService motifService;

    public static void getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Consts.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        motifService = retrofit.create(MotifService.class);
    }

    public void findByImage(File file) {
        getClient();
        Log.i("Moitf info","/////////////////////////");
        final List returnedMotifs = new ArrayList();

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/jpg"),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<List> call = motifService.findByImage(description, body);

        call.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                if (response.isSuccessful()) {
                    List motifs = response.body();
//                    Intent intent = new Intent(getApplicationContext(), Admin.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("value", (Serializable) motifs);
//                    intent.putExtras(bundle);
//                    startActivity(intent);

                    Log.i("resultat requette", motifs.toString());
                    returnedMotifs.addAll(motifs);
                } else {
                    Log.d("resultat requette", "Boo!");
                    return;
                }
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                Log.i("info", t.getMessage());

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        File picture = (File) intent.getExtras().get("picture");
        Bundle bundle = intent.getExtras();
        File picture = (File) bundle.getSerializable("picture");
        findByImage(picture);
        Log.d("resultat requette 11111", "Boo!");
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}