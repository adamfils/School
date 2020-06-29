package com.techguy.school.view;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.techguy.school.R;
import com.techguy.school.adapter.SchoolAdapter;
import com.techguy.school.viewmodel.SchoolVM;

public class MainActivity extends AppCompatActivity {

    RecyclerView schoolList;
    EditText searchBar;
    PullRefreshLayout refreshLayout;
    SchoolVM schoolVM;
    SchoolAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schoolVM = ViewModelProviders.of(this).get(SchoolVM.class);
        //Fetch School Info From VM
        schoolVM.getSchool().observe(this, model -> {
            adapter = new SchoolAdapter(MainActivity.this, model);
            //Handle Received DB Results
            if (model.size() > 0) {
                schoolList.setAdapter(adapter);
            } else {
                Toast.makeText(MainActivity.this, R.string.no_results, Toast.LENGTH_SHORT).show();
            }
            refreshLayout.setRefreshing(false);
        });
        //Listen for Errors
        schoolVM.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });


        schoolList = findViewById(R.id.school_list);
        //Set Layout Manager And Orientation
        schoolList.setLayoutManager(new LinearLayoutManager(this));

        searchBar = findViewById(R.id.search_bar);
        //Listen for When Search Icon is Tapped on Keyboard
        searchBar.setOnEditorActionListener((v, actionId, keyEvent) -> {
            //On Search Icon On Keyboard Pressed
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchBar.getText().toString().trim();

                if (!query.isEmpty()) {
                    schoolVM.querySchool(query);
                } else {
                    //If No Query Shake Bar
                    YoYo.with(Techniques.Shake).duration(500).playOn(searchBar);
                }
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });

        refreshLayout = findViewById(R.id.refresh);
        //Listen to Refresh Events
        refreshLayout.setOnRefreshListener(() -> {
            //Fetching Data
            schoolVM.getSchool();
            //Clear Search Bar and Clear Focus If Needed
            searchBar.setText("");
            try {
                if (searchBar.hasFocus())
                    searchBar.clearFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    protected void onDestroy() {
        if (schoolVM != null) {
            schoolVM.getSchool().removeObservers(this);
            schoolVM.getError().removeObservers(this);
        }
        super.onDestroy();


    }
}