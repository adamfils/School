package com.techguy.school.repository;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.techguy.school.contracts.InfoListener;
import com.techguy.school.contracts.SchoolListener;
import com.techguy.school.contracts.SchoolService;
import com.techguy.school.model.InfoModel;
import com.techguy.school.model.SchoolInfoModel;
import com.techguy.school.model.SchoolModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    public static void getSchools(final SchoolListener listener) {

        //Check If Database Values Exist
        if (DBUtils.getSchoolDatabase().size() == 0) {
            Log.e("TOM", "No Values Fetching");
            AndroidNetworking.get(Constants.SCHOOL_URL).build().getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            //Saving Values To Database
                            new SchoolModel(response.getJSONObject(i).getString("dbn"), response.getJSONObject(i).getString("school_name"), response.getJSONObject(i).getString("location")).save();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //Check Again
                    getSchools(listener);
                }

                @Override
                public void onError(ANError anError) {
                    listener.onFailure();
                }
            });
        } else {
            //Return Database Values
            listener.onSchoolSuccess(DBUtils.getSchoolDatabase());
        }
    }

    public static void getInfo(final InfoListener listener) {
        //Check If Database Values Exist
        if (DBUtils.getInfoDatabase().size() == 0) {
            AndroidNetworking.get(Constants.INFO_URL).build().getAsJSONArray(new JSONArrayRequestListener() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            //Saving Values To Database
                            new SchoolInfoModel(response.getJSONObject(i).getString("dbn"), response.getJSONObject(i).getString("school_name"), response.getJSONObject(i).getString("num_of_sat_test_takers"), response.getJSONObject(i).getString("sat_math_avg_score"), response.getJSONObject(i).getString("sat_writing_avg_score"), response.getJSONObject(i).getString("sat_critical_reading_avg_score")).save();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //Check Again
                    getRetroInfo(listener);
                }

                @Override
                public void onError(ANError anError) {
                    listener.onFailure();
                }
            });
        } else {
            //Return Database Values
            //listener.onInfoSuccess(DBUtils.getSchoolDatabase());
        }
    }

    public static void getRetroSchools(final SchoolListener listener) {

        if (DBUtils.getSchoolDatabase().size() == 0) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SCHOOL_URL_RETRO).addConverterFactory(GsonConverterFactory.create()).build();

            SchoolService schoolService = retrofit.create(SchoolService.class);
            schoolService.getSchools().enqueue(new Callback<List<SchoolModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<SchoolModel>> call, @NotNull Response<List<SchoolModel>> response) {
                    Log.e("TOM", "Retrofit Response: " + response.body());
                    listener.onSchoolSuccess(response.body());
                    assert response.body() != null;
                    for (int i = 0; i < response.body().size(); i++) {
                        new SchoolModel(response.body().get(i).getDbn(), response.body().get(i).getSchool_name(), response.body().get(i).getLocation()).save();
                    }
                    getRetroSchools(listener);
                }

                @Override
                public void onFailure(@NotNull Call<List<SchoolModel>> call, @NotNull Throwable t) {
                    Log.e("TOM", "Retrofit Error: " + t.getLocalizedMessage());
                    Log.e("TOM", "Retrofit Error: " + call.request());
                    listener.onFailure();

                }
            });
        } else {
            //Return Database Values
            listener.onSchoolSuccess(DBUtils.getSchoolDatabase());
        }
    }

    public static void getRetroInfo(final InfoListener listener) {
        if (DBUtils.getInfoDatabase().size() == 0) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SCHOOL_URL_RETRO).addConverterFactory(GsonConverterFactory.create()).build();

            SchoolService infoService = retrofit.create(SchoolService.class);
            infoService.getInfo().enqueue(new Callback<List<InfoModel>>() {
                @Override
                public void onResponse(@NotNull Call<List<InfoModel>> call, @NotNull Response<List<InfoModel>> response) {
                    for (int i = 0; i < response.body().size(); i++) {
                        new InfoModel(response.body().get(i).getDbn(), response.body().get(i).getSchool_name(), response.body().get(i).getNum_of_sat_test_takers(), response.body().get(i).getSat_math_avg_score(), response.body().get(i).getSat_writing_avg_score(), response.body().get(i).getSat_critical_reading_avg_score()).save();
                    }

                    getRetroInfo(listener);

                }

                @Override
                public void onFailure(@NotNull Call<List<InfoModel>> call, @NotNull Throwable t) {
                    listener.onFailure();
                }
            });
        }else{
            listener.onInfoSuccess(DBUtils.getInfoDatabase());
        }
    }
}
