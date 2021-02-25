package com.example.tapisirisi.ServiceImpl.UserMotif;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tapisirisi.Common.utils.Consts;
import com.example.tapisirisi.Services.UserMotifService;
import com.example.tapisirisi.model.UserMotif;

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

public class FindByImageService extends Service {

    static Retrofit retrofit;
    static private UserMotifService userMotifService;

    public static void getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Consts.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userMotifService = retrofit.create(UserMotifService.class);
    }

    public void findByImage(File file) {
        getClient();
        Log.i("Motif info", "/////////////////////////");
        final List<UserMotif> fetchedMotifs = new ArrayList();

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
        Call<List<UserMotif>> call = userMotifService.findByImage(description, body);

        call.enqueue(new Callback<List<UserMotif>>() {
            @Override
            public void onResponse(Call<List<UserMotif>> call, Response<List<UserMotif>> response) {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("android.intent.action.CustomReciever");
                Bundle bundle = new Bundle();

                if (response.isSuccessful()) {
                    List userMotifs = response.body();
                    fetchedMotifs.addAll(userMotifs);
                    bundle.putSerializable("motifs", (Serializable) fetchedMotifs);
                    broadcastIntent.putExtras(bundle);
                    sendBroadcast(broadcastIntent);
                    Log.i("resultat requette", userMotifs.toString());

                } else {
                    Log.d("resultat requette", "Boo!");
//                    bundle.putSerializable("motifs", null);
//                    broadcastIntent.putExtras(bundle);
//                    sendBroadcast(broadcastIntent);
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<UserMotif>> call, Throwable t) {
                Log.i("info failure", t.getMessage());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        File picture = (File) intent.getExtras().get("picture");
        Bundle bundle = intent.getExtras();
        File picture = (File) bundle.getSerializable("picture");
        findByImage(picture);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
