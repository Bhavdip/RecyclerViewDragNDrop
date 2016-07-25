package com.android.skillvo.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.skillvo.model.PagedList;
import com.android.skillvo.model.ProjectResponse;
import com.android.skillvo.rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProjectViewModel implements FragmentViewModel {

    private ProjectViewModelListener viewModelListener;
    public ObservableField<Integer> visibilityProgressBar = new ObservableField<>();

    public interface ProjectViewModelListener {
        void onLoadProjectsList(List<PagedList> projectList);

        void failToLoadProjectList(String failMessage);

        void setUpRecyclerView();
    }

    public ProjectViewModel(ProjectViewModelListener viewModelListener) {
        this.viewModelListener = viewModelListener;
    }

    @Override
    public void onCreateView(Fragment fragment) {
        viewModelListener.setUpRecyclerView();
        loadProjectList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onResume(Fragment activity) {

    }

    @Override
    public void onPause(Fragment activity) {

    }

    @Override
    public void onDestroy() {

    }

    private void loadProjectList() {
        visibilityProgressBar.set(View.VISIBLE);
        Call<ProjectResponse> requestForProjectList = RestClient.getProjectAPI().getProjectList();
        requestForProjectList.enqueue(new Callback<ProjectResponse>() {
            @Override
            public void onResponse(Call<ProjectResponse> call, Response<ProjectResponse> response) {
                if (response != null && response.body() != null) {
                    if (viewModelListener != null) {
                        viewModelListener.onLoadProjectsList(response.body().getPagedList());
                    }
                } else {
                    if (viewModelListener != null) {
                        viewModelListener.failToLoadProjectList("");
                    }
                }
                visibilityProgressBar.set(View.GONE);
            }

            @Override
            public void onFailure(Call<ProjectResponse> call, Throwable t) {
                visibilityProgressBar.set(View.GONE);
                if (viewModelListener != null) {
                    viewModelListener.failToLoadProjectList(t.getMessage());
                }
            }
        });
    }
}
