package com.example.tapisirisi.Services;

import com.example.tapisirisi.logic.model.UserMotif;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserMotifService {
    @GET("user-motif/")
    Call<List<UserMotif>> getAllUserMotif();

}
