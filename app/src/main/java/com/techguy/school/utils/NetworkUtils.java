package com.techguy.school.utils;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.techguy.school.contracts.InfoListener;
import com.techguy.school.contracts.SchoolListener;
import com.techguy.school.model.SchoolInfoModel;
import com.techguy.school.model.SchoolModel;

import org.json.JSONArray;
import org.json.JSONException;

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
                            new SchoolInfoModel(response.getJSONObject(i).getString("dbn"), response.getJSONObject(i).getString("school_name"), response.getJSONObject(i).getString("num_of_sat_test_takers"),response.getJSONObject(i).getString("sat_math_avg_score"),response.getJSONObject(i).getString("sat_writing_avg_score"),response.getJSONObject(i).getString("sat_critical_reading_avg_score")).save();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //Check Again
                    getInfo(listener);
                }

                @Override
                public void onError(ANError anError) {
                    listener.onFailure();
                }
            });
        } else {
            //Return Database Values
            listener.onInfoSuccess(DBUtils.getSchoolDatabase());
        }
    }
}
