package com.example.tapisirisi.Services;

import com.example.tapisirisi.model.UserMotif;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserMotifService {
    @GET("user-motif/{id}")
    Call<List<UserMotif>> getAllUserMotif(@Path("id") Long id);

    @Multipart
    @POST("user-motif/findByImage")
    Call<List<UserMotif>> findByImage(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);
}
