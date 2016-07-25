package com.shiki.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by Maik on 2016/2/14.
 */
public abstract class FGORecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected View customLoadMoreView = null;
    protected View customHeaderView = null;

    protected class VIEW_TYPES {
        public static final int NORMAL = 0;
        public static final int HEADER = 1;
        public static final int FOOTER = 2;
        public static final int CHANGED_FOOTER = 3;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.FOOTER) {
            VH viewHolder = getViewHolder(customLoadMoreView);
            if (getAdapterItemCount() == 0)
                viewHolder.itemView.setVisibility(View.GONE);
            return viewHolder;
        } else if (viewType == VIEW_TYPES.HEADER) {
            if (customHeaderView != null)
                return getViewHolder(customHeaderView);
        } else if (viewType == VIEW_TYPES.CHANGED_FOOTER) {
            VH viewHolder = getViewHolder(customLoadMoreView);
            if (getAdapterItemCount() == 0)
                viewHolder.itemView.setVisibility(View.GONE);
            return viewHolder;
        }
        return onCreateViewHolder(parent);

    }

    public void setCustomLoadMoreView(View customview) {
        customLoadMoreView = customview;
    }

    public boolean isLoadMoreChanged = false;

    public void swipeCustomLoadMoreView(View customview) {
        customLoadMoreView = customview;
        isLoadMoreChanged = true;
    }

    public View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && customLoadMoreView != null) {
            if (isLoadMoreChanged) {
                return VIEW_TYPES.CHANGED_FOOTER;
            } else {
                return VIEW_TYPES.FOOTER;
            }
        } else if (position == 0 && customHeaderView != null) {
            return VIEW_TYPES.HEADER;
        } else
            return VIEW_TYPES.NORMAL;
    }

    @Override
    public int getItemCount() {
        int headerOrFooter = 0;
        if (customHeaderView != null) headerOrFooter++;
        if (customLoadMoreView != null) headerOrFooter++;
        return getAdapterItemCount() + headerOrFooter;
    }

    public abstract VH getViewHolder(View view);
    public abstract VH onCreateViewHolder(ViewGroup parent);
    public abstract int getAdapterItemCount();

    public void toggleSelection(int pos) {
        notifyItemChanged(pos);
    }

    public void clearSelection(int pos) {
        notifyItemChanged(pos);
    }

    public void setSelected(int pos) {
        notifyItemChanged(pos);
    }

    public void swapPositions(List<?> list, int from, int to) {
        if (customHeaderView != null) {
            from--;
            to--;
        }
        Collections.swap(list, from, to);
    }

    public <T> void insert(List<T> list, T object, int position) {
        list.add(position, object);
        if (customHeaderView != null) position++;
        notifyItemInserted(position);
    }

    public void remove(List<?> list, int position) {
        if (list.size() > 0) {
            list.remove(customHeaderView != null ? position - 1 : position);
            notifyItemRemoved(position);
        }
    }

    public void clear(List<?> list) {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }
}
