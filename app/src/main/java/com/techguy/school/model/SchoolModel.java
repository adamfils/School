package com.techguy.school.model;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table(name="SchoolModel")
public class SchoolModel extends SugarRecord {
    private String dbn;
    private String school_name;
    private String location;

    public SchoolModel() {
    }

    public SchoolModel(String dbn, String school_name, String school_location) {
        this.dbn = dbn;
        this.school_name = school_name;
        this.location = school_location;
    }

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
