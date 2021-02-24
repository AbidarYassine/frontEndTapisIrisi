package com.example.tapisirisi.Services;

import com.example.tapisirisi.model.Motif;
import com.example.tapisirisi.model.Propriete;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PropreiteService {
    @POST("propriete/{id}/")
    Call<Propriete> saveProprite(@Body Propriete postprie, @Path("id") long id);
    @PUT("propriete/update")
    Call<Void> update(@Body Motif motif);
    @DELETE("propriete/delete/{id}")
    Call<Void> deletePropriete(@Path("id") long id);
}
