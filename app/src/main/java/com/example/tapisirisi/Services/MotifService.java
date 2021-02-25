package com.example.tapisirisi.Services;

import com.example.tapisirisi.model.Motif;
import com.example.tapisirisi.model.UserMotif;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MotifService {
    @Multipart
    @POST("motif/save/")
    Call<Motif> saveMotif(@Query("libelle") String libelle, @Query("desc") String desc, @Part MultipartBody.Part file, @Query("userId") long userId);

    @Multipart
    @POST("motif/update/")
    Call<UserMotif> updateMotif(@Query("idMotif") long idMotif, @Query("libelle") String libelle, @Part MultipartBody.Part file, @Query("idUserMotif") long id);

    @DELETE("motif/delete/{id}")
    Call<Void> delteMotif(@Path("id") long id);
}
