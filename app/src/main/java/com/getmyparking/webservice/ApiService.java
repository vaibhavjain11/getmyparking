package com.getmyparking.webservice;

import com.getmyparking.ProjectModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vaibhavjain on 04/06/17.
 */

public interface ApiService {

    @GET("kickstarter")
    Call<List<ProjectModel>> getProjectList();
}
