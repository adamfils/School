package com.techguy.school.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.techguy.school.contracts.InfoListener;
import com.techguy.school.contracts.SchoolListener;
import com.techguy.school.model.InfoModel;
import com.techguy.school.model.SchoolModel;
import com.techguy.school.repository.DBUtils;
import com.techguy.school.repository.NetworkUtils;

import java.util.List;

public class SchoolVM extends ViewModel implements SchoolListener, InfoListener {

    MutableLiveData<List<SchoolModel>> schoolData;
    MutableLiveData<List<InfoModel>> infoData;
    MutableLiveData<String> errorMessage;
    MutableLiveData<Long> dbId;

    public SchoolVM() {
        schoolData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        infoData = new MutableLiveData<>();
        dbId = new MutableLiveData<>();
    }

    public LiveData<List<SchoolModel>> getSchool(){
        Log.e("TOM", "Get School");
        NetworkUtils.getRetroSchools(this);
        return schoolData;
    }

    public void querySchool(String name){
        Log.e("TOM", "Query School");
        DBUtils.queryDB(name,this);
    }

    public LiveData<List<InfoModel>> getSchoolInfo(){
        Log.e("TOM", "Get Info");
        NetworkUtils.getRetroInfo(this);
        return infoData;
    }

    public LiveData<Long> getDBId(String dbn){
        Log.e("TOM", "Get DBId");
        dbId.setValue(DBUtils.getIdForInfo(dbn));
        return dbId;
    }

    public LiveData<String> getError(){
        return errorMessage;
    }

    @Override
    public void onSchoolSuccess(List<SchoolModel> model) {
        schoolData.setValue(model);
    }

    @Override
    public void onInfoSuccess(List<InfoModel> model) {
        infoData.setValue(model);
    }

    @Override
    public void onFailure() {
        errorMessage.setValue("Error Occurred");
    }
}
