package com.techguy.school.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.techguy.school.R;
import com.techguy.school.model.InfoModel;
import com.techguy.school.viewmodel.SchoolVM;

import java.util.List;

public class SchoolInfoActivity extends AppCompatActivity {

    TextView schoolName, satTotal, mathTotal, readingTotal, writingTotal, noData;
    String dbn = "";
    SchoolVM schoolViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);

        //Get Value From Intent
        dbn = getIntent().getStringExtra("dbn");

        schoolViewModel = ViewModelProviders.of(this).get(SchoolVM.class);
        //Listen for Database Values
        schoolViewModel.getSchoolInfo().observe(this, new Observer<List<InfoModel>>() {
            @Override
            public void onChanged(List<InfoModel> model) {
                Long id = schoolViewModel.getDBId(dbn).getValue();
                //Checking is DBN Value is Not Empty and Not -1

                if (model.size() > 0 && !dbn.isEmpty() && id != null && id != -1L) {
                    InfoModel info = InfoModel.findById(InfoModel.class, id);
                    schoolName.setText(info.getSchool_name());
                    satTotal.setText(info.getNum_of_sat_test_takers());
                    readingTotal.setText(info.getSat_critical_reading_avg_score());
                    mathTotal.setText(info.getSat_math_avg_score());
                    writingTotal.setText(info.getSat_writing_avg_score());
                    noData.setVisibility(View.GONE);
                } else {
                    noData.setVisibility(View.VISIBLE);
                }
            }
        });
        //Listen for Database Errors
        schoolViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(SchoolInfoActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });


        schoolName = findViewById(R.id.school_name);
        satTotal = findViewById(R.id.sat_total);
        readingTotal = findViewById(R.id.reading_total);
        mathTotal = findViewById(R.id.math_total);
        writingTotal = findViewById(R.id.writing_total);
        noData = findViewById(R.id.noData);

    }


    @Override
    protected void onDestroy() {
        if (schoolViewModel != null) {
            schoolViewModel.getSchoolInfo().removeObservers(this);
            schoolViewModel.getError().removeObservers(this);
        }
        super.onDestroy();
    }
}