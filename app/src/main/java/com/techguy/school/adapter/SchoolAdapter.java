package com.techguy.school.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.techguy.school.R;
import com.techguy.school.model.SchoolModel;
import com.techguy.school.view.SchoolInfoActivity;
import com.techguy.school.viewholder.SchoolVH;

import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolVH> {

    Context context;
    List<SchoolModel> model;

    public SchoolAdapter(Context context, List<SchoolModel> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public SchoolVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_layout, parent, false);
        return new SchoolVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolVH holder, final int position) {
        //Handles IndexOutOfBounds Errors
        if (position < model.size()) {
            holder.setSchoolName(model.get(position).getSchool_name());
            holder.setSchoolLocation(model.get(position).getLocation());
            //When One Recyclerview Item Is Clicked
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Animation
                    YoYo.with(Techniques.RubberBand).duration(500).playOn(view);
                    //Go To Info Activity
                    Intent intent = new Intent(context, SchoolInfoActivity.class);
                    intent.putExtra("dbn",model.get(position).getDbn());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
