package com.techguy.school.application;

import android.app.Application;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

public class SchoolApp extends Application {
    @Override
    public void onCreate() {

        //Initialize Sugar ORM
        SugarContext.init(this);
        // create table if not exists
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());

        super.onCreate();
    }
}
