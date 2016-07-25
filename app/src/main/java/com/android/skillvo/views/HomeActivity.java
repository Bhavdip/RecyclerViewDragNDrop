package com.android.skillvo.views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.skillvo.R;
import com.android.skillvo.databinding.HomeDataBinding;
import com.android.skillvo.viewmodel.HomeViewModel;

public class HomeActivity extends AppCompatActivity implements HomeViewModel.HomeViewModelListener {

    private HomeDataBinding mHomeDataBinding;
    private HomeViewModel mHomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeDataBinding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        mHomeViewModel = new HomeViewModel(this);
        mHomeDataBinding.setViewmodel(mHomeViewModel);
        mHomeViewModel.onCreate(this);
    }

    @Override
    public void loadProjectFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ProjectFragment.getProjectFragment())
                .commit();
    }
}
