package com.example.tapisirisi.Services;

import com.example.tapisirisi.logic.model.Motif;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MotifService {
    @Multipart
    @POST("motif/findByImage")
    Call<List> findByImage(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

    @PUT("update/{id}")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<Motif> updateMotif(@Body Motif motif, @Path("id") Long id);

    @DELETE("/delete/{id}")
    Call<Motif>  delteMotif(@Path("id") long id);

}
