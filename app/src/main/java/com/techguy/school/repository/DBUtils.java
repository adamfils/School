package com.techguy.school.repository;

import android.util.Log;

import com.techguy.school.contracts.SchoolListener;
import com.techguy.school.model.InfoModel;
import com.techguy.school.model.SchoolModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DBUtils {

    public static List<SchoolModel> getSchoolDatabase() {
        //Returns School Database List
        return SchoolModel.listAll(SchoolModel.class);
    }

    public static List<InfoModel> getInfoDatabase() {
        //Returns School Info Database List
        return InfoModel.listAll(InfoModel.class);
    }

    public static Long getIdForInfo(String dbn) {
        //Returns Database ID for
        Long id = -1L;
        Log.e("TOM", "DBN: "+dbn);
        //Check If Database Contains Values
        if (getInfoDatabase().size() > 0) {
            for (InfoModel info : getInfoDatabase()) {
                //Check Each Database Object for Same DBN Value
                if (info.getDbn().equals(dbn)) {
                    id = info.getId();
                    break;
                }
            }
        }
        return id;
    }

    public static void queryDB(String name, SchoolListener listener) {
        List<SchoolModel> result = new ArrayList<>();
        if (getSchoolDatabase().size() > 0) {
            for (SchoolModel model : getSchoolDatabase()) {
                //Use Pattern To Search For Words
                if (Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(model.getSchool_name()).find()) {
                    result.add(model);
                }
            }
        }
        //Return Results
        listener.onSchoolSuccess(result);
    }

}
