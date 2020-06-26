package com.techguy.school.contracts;

import com.techguy.school.model.SchoolModel;

import java.util.ArrayList;
import java.util.List;

public interface SchoolListener {
    void onSchoolSuccess(List<SchoolModel> model);
    void onFailure();
}
