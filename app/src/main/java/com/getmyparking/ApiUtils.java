package com.getmyparking;

import com.getmyparking.webservice.ApiService;

/**
 * Created by vaibhavjain on 04/06/17.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://starlord.hackerearth.com/";

    public static ApiService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
