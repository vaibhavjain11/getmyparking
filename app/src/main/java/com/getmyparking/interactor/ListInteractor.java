package com.getmyparking.interactor;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.getmyparking.ApiUtils;
import com.getmyparking.DatabaseHandler;
import com.getmyparking.ProjectModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaibhavjain on 04/06/17.
 */

public class ListInteractor {

    private final Context context;
    public ListInteractorInterface listInteractorInterface;
    public List<ProjectModel> projectModels;

    public void getMyProjectList() {
        Call<List<ProjectModel>> call = ApiUtils.getAPIService().getProjectList();
        call.enqueue(new Callback<List<ProjectModel>>() {
            @Override
            public void onResponse(Call<List<ProjectModel>> call, Response<List<ProjectModel>> response) {
                projectModels = (List<ProjectModel>) response.body();

                DatabaseHandler databaseHandler = DatabaseHandler.getInstance(context);
                databaseHandler.saveProject(projectModels);
                Cursor cursor = databaseHandler.getAllProjectList();
                listInteractorInterface.listProject(cursor);

            }

            @Override
            public void onFailure(Call<List<ProjectModel>> call, Throwable t) {
                Log.i("vaibhav", t.getLocalizedMessage());
            }
        });
    }

    public interface ListInteractorInterface{
        void listProject(Cursor cursor);
    }

    public ListInteractor(Context context, ListInteractorInterface listInteractorInterface){
        this.context = context;
        this.listInteractorInterface = listInteractorInterface;
    }
}
