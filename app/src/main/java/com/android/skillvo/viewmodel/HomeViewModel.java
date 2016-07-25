package com.android.skillvo.viewmodel;

import android.app.Activity;
import android.content.Intent;


public class HomeViewModel implements ActivityViewModel {

    private HomeViewModelListener modelListener;

    public interface HomeViewModelListener {
        void loadProjectFragment();
    }

    public HomeViewModel(HomeViewModelListener modelListener) {
        this.modelListener = modelListener;
    }

    @Override
    public void onCreate(Activity activity) {
        modelListener.loadProjectFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onResumeActivity(Activity activity) {
    }

    @Override
    public void onPauseActivity(Activity activity) {
    }

    @Override
    public void onBackPressed(Activity activity) {
    }

    @Override
    public void onDestroy() {
    }
}
