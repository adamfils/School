package com.techguy.school.contracts;

import com.techguy.school.model.InfoModel;
import com.techguy.school.model.SchoolModel;

import java.util.ArrayList;
import java.util.List;

public interface InfoListener {
    void onInfoSuccess(List<InfoModel> model);
    void onFailure();
}
