package com.example.tapisirisi.Services;

import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.logic.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MotifService {
    @Multipart
    @POST("motif/findByImage")
    Call<List> findByImage(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("motif/save/")
    Call<Motif> saveMotif(@Query("libelle") String libelle, @Query("desc") String desc, @Part MultipartBody.Part file, @Query("userId") long userId);
}
