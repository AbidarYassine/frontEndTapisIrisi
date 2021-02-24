package com.example.tapisirisi.ServiceImpl.Motif;

import android.app.ActionBar;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.tapisirisi.R;
import com.example.tapisirisi.Services.MotifService;
import com.example.tapisirisi.activities.Admin.Admin;
import com.example.tapisirisi.activities.Admin.Ajout_Prop;
import com.example.tapisirisi.activities.Admin.Ajouter;
import com.example.tapisirisi.activities.Register.CustomPopup;
import com.example.tapisirisi.activities.Register.CustomSpinner;
import com.example.tapisirisi.activities.utilUiOpenCv.BitmapToFile;
import com.example.tapisirisi.activities.utilUiOpenCv.OpenCVCameraActivity;
import com.example.tapisirisi.database.DatabaseHelper;
import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.logic.model.User;
import com.example.tapisirisi.utils.Consts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMotifService extends Service {
    private static MotifService motifService;
    private static final String TAG = "AddMotifService";
    CustomSpinner spinner;
    CustomPopup popup;

    static Retrofit retrofit;


    public static void getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Consts.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        motifService = retrofit.create(MotifService.class);
    }

    public Motif saveMotif(Motif motif, File file) {
        getClient();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        User u = new DatabaseHelper(getApplicationContext()).getCurrentUser();
        Call<Motif> call = motifService.saveMotif(motif.getLibelle(), motif.getDescription(), body, 1);
        spinner.show();
        Window window = spinner.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        call.enqueue(new Callback<Motif>() {
            @Override
            public void onResponse(Call<Motif> call, Response<Motif> response) {
                if (response.isSuccessful()) {
                    spinner.dismiss();
                    Motif motifSaved = (Motif) response.body();
                    Intent intent = new Intent(getApplicationContext(), Ajout_Prop.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("value", (Serializable) motifSaved);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Log.d("resultat requette", "Boo!");
                    return;
                }
            }

            @Override
            public void onFailure(Call<Motif> call, Throwable t) {
                Log.i("info", t.getMessage());
                spinner.dismiss();
                popup.setTitle("Ouuups");
                popup.setContent("Erreur 500");
                popup.build();

            }
        });
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: start");
        Bundle bundle = intent.getExtras();
        String libelle = bundle.getString("libelle");
        String description = bundle.getString("description");
        File picture = (File) bundle.getSerializable("picture");
        popup = new CustomPopup(Ajouter.context);
        popup.getButton().setOnClickListener((v) ->
        {
            popup.dismiss();
        });
//        Log.i(TAG, "onStartCommand: " + getApplicationContext().);
        spinner = new CustomSpinner(Ajouter.context);
        saveMotif(new Motif(libelle, description), picture);
        return START_NOT_STICKY;
    }
}
