package com.techguy.school.model;

import com.orm.SugarRecord;

public class SchoolInfoModel extends SugarRecord {
    private String dbn;
    private String school_name;
    private String sat_takers;
    private String math_score;
    private String writing_score;
    private String reading_score;

    public SchoolInfoModel() {
    }

    public SchoolInfoModel(String dbn, String school_name, String sat_takers, String math_score, String writing_score, String reading_score) {
        this.dbn = dbn;
        this.school_name = school_name;
        this.sat_takers = sat_takers;
        this.math_score = math_score;
        this.writing_score = writing_score;
        this.reading_score = reading_score;
    }

    public String getDbn() {
        return dbn;
    }

    public String getSchool_name() {
        return school_name;
    }

    public String getSat_takers() {
        return sat_takers;
    }

    public String getMath_score() {
        return math_score;
    }

    public String getWriting_score() {
        return writing_score;
    }

    public String getReading_score() {
        return reading_score;
    }
}
