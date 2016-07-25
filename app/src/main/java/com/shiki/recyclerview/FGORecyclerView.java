package com.shiki.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;
import com.shiki.recyclerview.ui.VerticalSwipeRefreshLayout;
import com.std.ramsoapp.R;

/**
 * Created by Maik on 2016/2/14.
 */
public class FGORecyclerView extends FrameLayout {

    private RecyclerView mRecyclerView;
    private VerticalSwipeRefreshLayout mSwipeRefreshLayout;

    private static final int SCROLLBARS_NONE = 0;
    private static final int SCROLLBARS_VERTICAL = 1;
    private static final int SCROLLBARS_HORIZONTAL = 2;
    private int mScrollbarsStyle;

    private int mVisibleItemCount = 0;
    private int mTotalItemCount = 0;
    private int previousTotal = 0;
    private int mFirstVisibleItem;
    private int lastVisibleItemPosition;
    private boolean isLoadingMore = false;
    private OnLoadMoreListener onLoadMoreListener;

    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected boolean mClipToPadding;
    protected boolean mDispatch;
    protected int[] defaultSwipeToDismissColors;
    protected RecyclerView.OnScrollListener mOnScrollListener;
    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    private boolean mIsLoadMoreWidgetEnabled;
    private FGORecyclerViewAdapter mAdapter;
    private int showLoadMoreItemNum = 3;

    protected ViewStub mEmpty;
    protected View mEmptyView;
    protected int mEmptyId;

    private long duration;

    private enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    private int findMin(int[] lastPositions) {
        int min = Integer.MAX_VALUE;
        for (int value : lastPositions) {
            if (value != RecyclerView.NO_POSITION && value < min)
                min = value;
        }
        return min;
    }

    public FGORecyclerView(Context context) {
        super(context);
        initViews();
    }

    public FGORecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initViews();
    }

    public FGORecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initViews();
    }

    protected void initViews() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fgo_recycler_view_layout, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fgo_list);
        mSwipeRefreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        setScrollbars();
        mSwipeRefreshLayout.setEnabled(false);
        if (mRecyclerView != null) {
            mRecyclerView.setClipToPadding(mClipToPadding);
            if (mPadding != -1.1f) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
        }
        setDefaultScrollListener();
        mEmpty = (ViewStub) view.findViewById(R.id.emptyview);
        mEmpty.setLayoutResource(mEmptyId);
        if (mEmptyId != 0)
            mEmptyView = mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(@LayoutRes int emptyResourceId) {
        mEmptyId = emptyResourceId;
        mEmpty.setLayoutResource(mEmptyId);
        if (mEmptyId != 0)
            mEmptyView = mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);
    }

    public void showEmptyView() {
        if (mEmptyId != 0)
            mEmpty.setVisibility(View.VISIBLE);
    }

    public void hideEmptyView() {
        if (mEmptyId != 0)
            mEmpty.setVisibility(View.GONE);
    }

    protected void setScrollbars() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (mScrollbarsStyle) {
            case SCROLLBARS_VERTICAL:
                mSwipeRefreshLayout.removeView(mRecyclerView);
                View verticalView = inflater.inflate(R.layout.fgo_vertical_recycler_view, mSwipeRefreshLayout, true);
                mRecyclerView = (RecyclerView) verticalView.findViewById(R.id.fgo_list);
                break;
            case SCROLLBARS_HORIZONTAL:
                mSwipeRefreshLayout.removeView(mRecyclerView);
                View horizontalView = inflater.inflate(R.layout.fgo_horizontal_recycler_view, mSwipeRefreshLayout, true);
                mRecyclerView = (RecyclerView) horizontalView.findViewById(R.id.fgo_list);
                break;
            default:
                break;
        }
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FGORecyclerView);
        try {
            mPadding = (int) typedArray.getDimension(R.styleable.FGORecyclerView_recyclerviewPadding, -1.1f);
            mPaddingTop = (int) typedArray.getDimension(R.styleable.FGORecyclerView_recyclerviewPaddingTop, 0.0f);
            mPaddingBottom = (int) typedArray.getDimension(R.styleable.FGORecyclerView_recyclerviewPaddingBottom, 0.0f);
            mPaddingLeft = (int) typedArray.getDimension(R.styleable.FGORecyclerView_recyclerviewPaddingLeft, 0.0f);
            mPaddingRight = (int) typedArray.getDimension(R.styleable.FGORecyclerView_recyclerviewPaddingRight, 0.0f);
            mClipToPadding = typedArray.getBoolean(R.styleable.FGORecyclerView_recyclerviewClipToPadding, false);
            mDispatch = typedArray.getBoolean(R.styleable.FGORecyclerView_recycleviewDispatch, false);
            mEmptyId = typedArray.getResourceId(R.styleable.FGORecyclerView_recyclerviewEmptyView, 0);
            mScrollbarsStyle = typedArray.getInt(R.styleable.FGORecyclerView_recyclerviewScrollbars, SCROLLBARS_NONE);
            int colorList = typedArray.getResourceId(R.styleable.FGORecyclerView_recyclerviewDefaultSwipeColor, 0);
            if (colorList != 0) {
                defaultSwipeToDismissColors = getResources().getIntArray(colorList);
            }
        } finally {
            typedArray.recycle();
        }
    }

    protected void setDefaultScrollListener() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    public void enableLoadmore() {
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = new RecyclerView.OnScrollListener() {
            private int[] lastPositions;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManagerType == null) {
                    if (layoutManager instanceof GridLayoutManager) {
                        layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
                    } else {
                        throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                    }
                }
                switch (layoutManagerType) {
                    case LINEAR:
                        mVisibleItemCount = layoutManager.getChildCount();
                        mTotalItemCount = layoutManager.getItemCount();
                    case GRID:
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        mFirstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                        break;
                    case STAGGERED_GRID:
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        if (lastPositions == null)
                            lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                        lastVisibleItemPosition = findMax(lastPositions);
                        staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions);
                        mFirstVisibleItem = findMin(lastPositions);
                        break;
                }
                if (isLoadingMore) {
                    if (mTotalItemCount > previousTotal) {
                        isLoadingMore = false;
                        previousTotal = mTotalItemCount;
                    }
                }
                if (mVisibleItemCount == mTotalItemCount || mTotalItemCount <= showLoadMoreItemNum) {
                    mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
                } else if (!isLoadingMore && (mTotalItemCount - mVisibleItemCount) <= mFirstVisibleItem) {
                    if (System.currentTimeMillis() - duration >= 1000) {
                        duration = System.currentTimeMillis();
                        isLoadingMore = true;
                        onLoadMoreListener.loadMore(mRecyclerView.getAdapter().getItemCount(), lastVisibleItemPosition);
                        previousTotal = mTotalItemCount;
                    } else {
                        int scrolldy = mAdapter.customLoadMoreView.getHeight();
                        mRecyclerView.scrollBy(0, scrolldy * -1);
                    }
                }
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        if (mAdapter != null && mAdapter.getCustomLoadMoreView() == null)
            mAdapter.setCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.fgo_bottom_progressbar, null));
        mIsLoadMoreWidgetEnabled = true;

    }

    public boolean isLoadMoreEnabled() {
        return mIsLoadMoreWidgetEnabled;
    }

    public void disableLoadmore() {
        setDefaultScrollListener();
        if (mAdapter != null)
            mAdapter.swipeCustomLoadMoreView(LayoutInflater.from(getContext())
                    .inflate(R.layout.fgo_empty_progressbar, null));
        mIsLoadMoreWidgetEnabled = false;
    }

    public interface OnLoadMoreListener {
        void loadMore(int itemsCount, final int maxLastVisiblePosition);
    }

    public void setAdapter(FGORecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter != null)
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    updateHelperDisplays();
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    updateHelperDisplays();
                }
            });
        if ((adapter == null || mAdapter.getAdapterItemCount() == 0) && mEmptyId != 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void updateHelperDisplays() {
        isLoadingMore = false;
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (mAdapter == null)
            return;
        if (mAdapter.getAdapterItemCount() == 0) {
            mEmpty.setVisibility(mEmptyId != 0 ? View.VISIBLE : View.GONE);
        } else if (mEmptyId != 0) {
            mEmpty.setVisibility(View.GONE);
        }
        if (mAdapter.getCustomLoadMoreView() == null) return;
        if (mAdapter.getAdapterItemCount() >= showLoadMoreItemNum && mAdapter.getCustomLoadMoreView().getVisibility() == View.GONE) {
            mAdapter.getCustomLoadMoreView().setVisibility(View.VISIBLE);
        }
        if (mAdapter.getAdapterItemCount() < showLoadMoreItemNum) {
            mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
        }
    }

    public void noMoreData() {
        updateHelperDisplays();
        duration = System.currentTimeMillis();
        int scrolldy = mAdapter.customLoadMoreView.getHeight();
        mRecyclerView.scrollBy(0, scrolldy * -1);
    }

    @Deprecated
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                update();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                update();
            }

            @Override
            public void onChanged() {
                super.onChanged();
                update();
            }

            private void update() {
                isLoadingMore = false;
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void setDefaultOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setEnabled(true);
        if (defaultSwipeToDismissColors != null && defaultSwipeToDismissColors.length > 0) {
            mSwipeRefreshLayout.setColorSchemeColors(defaultSwipeToDismissColors);
        } else {
            mSwipeRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }

    public void setDefaultSwipeToRefreshColorScheme(int... colors) {
        mSwipeRefreshLayout.setColorSchemeColors(colors);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public RecyclerView.Adapter getAdapter() {
        return mRecyclerView.getAdapter();
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    public void enableDefaultSwipeRefresh(boolean isSwipeRefresh) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setEnabled(isSwipeRefresh);
    }

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecyclerView.addOnItemTouchListener(listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mDispatch)
            getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
