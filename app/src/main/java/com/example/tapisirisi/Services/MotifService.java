package com.example.tapisirisi.Services;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MotifService {
    @Multipart
    @POST("motif/findByImage")
    Call<List> findByImage(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);
}
