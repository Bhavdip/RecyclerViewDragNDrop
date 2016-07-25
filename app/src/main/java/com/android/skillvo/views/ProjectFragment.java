package com.android.skillvo.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.skillvo.R;
import com.android.skillvo.adapter.RecyclerProjectAdapter;
import com.android.skillvo.databinding.ProjectDataBinding;
import com.android.skillvo.helper.OnStartDragListener;
import com.android.skillvo.helper.SimpleItemTouchHelperCallback;
import com.android.skillvo.model.PagedList;
import com.android.skillvo.utils.DialogUtils;
import com.android.skillvo.utils.ItemOffsetDecoration;
import com.android.skillvo.viewmodel.ProjectViewModel;

import java.util.List;


public class ProjectFragment extends Fragment implements ProjectViewModel.ProjectViewModelListener, OnStartDragListener {

    private ProjectDataBinding projectDataBinding;
    private ProjectViewModel mProjectViewModel;
    private RecyclerProjectAdapter mRecyclerProjectAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public static Fragment getProjectFragment() {
        ProjectFragment projectFragment = new ProjectFragment();
        return projectFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        projectDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false);
        mProjectViewModel = new ProjectViewModel(this);
        projectDataBinding.setViewmodel(mProjectViewModel);
        mProjectViewModel.onCreateView(this);
        return projectDataBinding.getRoot();
    }

    @Override
    public void setUpRecyclerView() {
        mRecyclerProjectAdapter = new RecyclerProjectAdapter(this);
        projectDataBinding.projectList.setHasFixedSize(true);
        projectDataBinding.projectList.setAdapter(mRecyclerProjectAdapter);

        final int spanCount = 2;
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        projectDataBinding.projectList.addItemDecoration(itemOffsetDecoration);
        projectDataBinding.projectList.setLayoutManager(layoutManager);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mRecyclerProjectAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(projectDataBinding.projectList);
    }

    @Override
    public void onLoadProjectsList(List<PagedList> projectList) {
        mRecyclerProjectAdapter.addPortfolios(projectList.get(0).getPortfolio());
        mRecyclerProjectAdapter.notifyDataSetChanged();
    }

    @Override
    public void failToLoadProjectList(String failMessage) {
        DialogUtils.showToast(getActivity(), failMessage);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
