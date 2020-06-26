package com.techguy.school.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.techguy.school.R;
import com.techguy.school.contracts.InfoListener;
import com.techguy.school.model.SchoolInfoModel;
import com.techguy.school.model.SchoolModel;
import com.techguy.school.utils.DBUtils;
import com.techguy.school.utils.NetworkUtils;

import java.util.List;

public class SchoolInfoActivity extends AppCompatActivity implements InfoListener {

    TextView schoolName, satTotal, mathTotal, readingTotal, writingTotal, noData;
    String dbn="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_info);

        //Get Value From Intent
        dbn = getIntent().getStringExtra("dbn");


        schoolName = findViewById(R.id.school_name);
        satTotal = findViewById(R.id.sat_total);
        readingTotal = findViewById(R.id.reading_total);
        mathTotal = findViewById(R.id.math_total);
        writingTotal = findViewById(R.id.writing_total);
        noData = findViewById(R.id.noData);

        //Listen for Database Values
        NetworkUtils.getInfo(this);
    }

    @Override
    public void onInfoSuccess(List<SchoolModel> model) {
        Long id = DBUtils.getIdForInfo(dbn);
        //Checking is DBN Value is Not Empty and Not -1
        if (model.size() > 0 && !dbn.isEmpty() && id != -1L) {
            SchoolInfoModel info = SchoolInfoModel.findById(SchoolInfoModel.class,id);
            schoolName.setText(info.getSchool_name());
            satTotal.setText(info.getSat_takers());
            readingTotal.setText(info.getReading_score());
            mathTotal.setText(info.getMath_score());
            writingTotal.setText(info.getWriting_score());
            noData.setVisibility(View.GONE);
        }else{
            noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, R.string.failed_to_get_data, Toast.LENGTH_SHORT).show();
    }
}