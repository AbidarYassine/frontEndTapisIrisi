package com.example.tapisirisi.Services;

import com.example.tapisirisi.model.UserMotif;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserMotifService {
    @GET("user-motif/{id}")
    Call<List<UserMotif>> getAllUserMotif(@Path("id") Long id);

}
