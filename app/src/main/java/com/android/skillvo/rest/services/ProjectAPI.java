package com.android.skillvo.rest.services;


import com.android.skillvo.model.ProjectResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProjectAPI {
    @GET("project/profile/57483ae703ae097808db6ef5?format=json&Page=1&PageSize=15")
    Call<ProjectResponse> getProjectList();
}
