package com.techguy.school.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.techguy.school.R;
import com.techguy.school.adapter.SchoolAdapter;
import com.techguy.school.contracts.SchoolListener;
import com.techguy.school.model.SchoolModel;
import com.techguy.school.utils.DBUtils;
import com.techguy.school.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView schoolList;
    EditText searchBar;
    PullRefreshLayout refreshLayout;
    SchoolListener schoolListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schoolList = findViewById(R.id.school_list);
        //Set Layout Manager And Orientation
        schoolList.setLayoutManager(new LinearLayoutManager(this));

        schoolListener = new SchoolListener() {
            @Override
            public void onSchoolSuccess(List<SchoolModel> model) {
                //Handle Received DB Results
                if (model.size() > 0) {
                    schoolList.setAdapter(new SchoolAdapter(MainActivity.this, model));
                }else{
                    Toast.makeText(MainActivity.this, R.string.no_results, Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, R.string.failed_to_get_data, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        };

        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                //On Search Icon On Keyboard Pressed
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = searchBar.getText().toString();

                    if (query != null && !query.isEmpty()) {
                        DBUtils.queryDB(query, schoolListener);
                    }else{
                        //If No Query Shake Bar
                        YoYo.with(Techniques.Shake).duration(500).playOn(searchBar);
                    }
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Fetching Data
                NetworkUtils.getSchools(schoolListener);
                //Clear Search Bar and Clear Focus If Needed
                searchBar.setText("");
                try {
                    if (searchBar.hasFocus())
                        searchBar.clearFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //Fetching Data
        NetworkUtils.getSchools(schoolListener);

    }


}