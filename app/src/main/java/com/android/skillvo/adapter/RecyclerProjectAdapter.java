package com.android.skillvo.adapter;


import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.skillvo.R;
import com.android.skillvo.helper.ItemTouchHelperAdapter;
import com.android.skillvo.helper.ItemTouchHelperViewHolder;
import com.android.skillvo.helper.OnStartDragListener;
import com.android.skillvo.model.Portfolio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerProjectAdapter extends RecyclerView.Adapter<RecyclerProjectAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    private List<Portfolio> portfolioList = new ArrayList<>();
    private final OnStartDragListener mDragStartListener;
    private boolean isDragNDropEnable = false;

    public RecyclerProjectAdapter(OnStartDragListener dragStartListener) {
        this.mDragStartListener = dragStartListener;
    }

    public void addPortfolios(List<Portfolio> portfolios) {
        portfolioList.addAll(portfolios);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_project, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.projectTitle.setText(portfolioList.get(position).getProject().getTitle());
        Picasso.with(holder.projectImageView.getContext()).load(portfolioList.get(position).getUrl()).into(holder.projectImageView);
        holder.projectImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isDragNDropEnable = true;
                return true;
            }
        });
        // Start a drag whenever the handle view it touched
        holder.projectImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isDragNDropEnable) {
                    if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return portfolioList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(portfolioList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        isDragNDropEnable = false;
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        isDragNDropEnable = false;
        portfolioList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public final ImageView projectImageView;
        public final TextView projectTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            projectImageView = (ImageView) itemView.findViewById(R.id.portfolioPhoto);
            projectTitle = (TextView) itemView.findViewById(R.id.projectName);
        }

        @Override
        public void onItemSelected() {
            //itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            //itemView.setBackgroundColor(0);
        }
    }
}
