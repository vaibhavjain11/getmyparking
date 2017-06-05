package com.getmyparking.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.getmyparking.DatabaseHandler;
import com.getmyparking.ProjectModel;
import com.getmyparking.R;
import com.getmyparking.adapter.ProjectAdapter;
import com.getmyparking.presenter.ListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectListActivity extends AppCompatActivity implements ListPresenter.ListPresenterInterface, ProjectAdapter.onRecyclerItemClickListener, View.OnClickListener,
        SearchView.OnQueryTextListener {

    ListPresenter listPresenter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.filter)
    ImageView filterImage;
    @BindView(R.id.searchView)
    SearchView mSearchView;

    ProjectAdapter projectAdapter;

    List<ProjectModel> projectModels = new ArrayList<>();

    final String BASE_URL = "https://www.kickstarter.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        ButterKnife.bind(this);

        listPresenter = new ListPresenter(this, this);
        listPresenter.getListOfProject();
        filterImage.setOnClickListener(this);
        setUpSearchView();


    }

    private void setUpSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");

    }

    @Override
    public void getProjectList(Cursor cursor) {

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                ProjectModel model = new ProjectModel();
                model.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHandler.Columns.TITLE)));
                model.setAmtPledged(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.Columns.AMOUNT_PLEGED)));
                model.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseHandler.Columns.URL)));
                model.setNumBackers(cursor.getString(cursor.getColumnIndex(DatabaseHandler.Columns.NUM_BACKERS)));
                model.setEndTime(cursor.getString(cursor.getColumnIndex(DatabaseHandler.Columns.END_TIME)));
                projectModels.add(model);
            }
        }

        projectAdapter = new ProjectAdapter(this, projectModels);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(projectAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ProjectDetailActivity.class);
        intent.putExtra("url", BASE_URL + projectModels.get(position).getUrl());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_filter);
        final EditText etFilter = (EditText) dialog.findViewById(R.id.etFilter);
        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ProjectModel> l = new ArrayList<ProjectModel>();
                for (int i = 0; i < projectModels.size(); i++) {
                    if (etFilter.getText().toString().equals(projectModels.get(i).getNumBackers())) {
                        l.add(projectModels.get(i));
                    }
                }

                projectModels.clear();
                projectModels.addAll(l);
                projectAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        projectAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        projectAdapter.getFilter().filter(newText);
        return true;
    }
}
