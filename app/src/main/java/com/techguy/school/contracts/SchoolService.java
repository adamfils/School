package com.techguy.school.contracts;

import com.techguy.school.model.InfoModel;
import com.techguy.school.model.SchoolModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SchoolService {
    @GET("/resource/s3k6-pzi2.json/")
    Call<List<SchoolModel>> getSchools();

    @GET("/resource/f9bf-2cp4.json/")
    Call<List<InfoModel>> getInfo();
}
