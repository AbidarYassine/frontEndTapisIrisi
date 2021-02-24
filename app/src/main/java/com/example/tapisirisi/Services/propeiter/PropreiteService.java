package com.example.tapisirisi.Services.propeiter;

import com.example.tapisirisi.logic.model.Motif;
import com.example.tapisirisi.logic.model.Propriete;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PropreiteService {
    @POST("propriete/{id}/")
    Call<Propriete> saveProprite(@Body Propriete postprie, @Path("id") long id);
}
