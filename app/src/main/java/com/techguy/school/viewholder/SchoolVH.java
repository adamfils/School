package com.techguy.school.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techguy.school.R;

public class SchoolVH extends RecyclerView.ViewHolder {
    private TextView schoolName,schoolLocation;
    public SchoolVH(@NonNull View itemView) {
        super(itemView);

        schoolName = itemView.findViewById(R.id.school_name);
        schoolLocation = itemView.findViewById(R.id.school_location);
        schoolLocation.setSelected(true);
    }
    //Helper Method To Check and Set School Name
    public void setSchoolName(String name){
        if(name != null && !name.isEmpty()){
            schoolName.setText(name);
        }
    }
    //Helper Method To Check and Set School Location
    public void setSchoolLocation(String name){
        if(name != null && !name.isEmpty()){
            schoolLocation.setText(name);
        }
    }
}
