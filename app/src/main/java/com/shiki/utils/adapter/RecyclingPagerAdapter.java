package com.shiki.utils.adapter;

import android.os.Build.VERSION;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.AccessibilityDelegate;

/**
 * Created by Maik on 2016/2/26.
 */
public abstract class RecyclingPagerAdapter extends PagerAdapter {
    static final int IGNORE_ITEM_VIEW_TYPE = -1;
    private final RecyclingPagerAdapter.RecycleBin recycleBin;

    public RecyclingPagerAdapter() {
        this(new RecyclingPagerAdapter.RecycleBin());
    }

    RecyclingPagerAdapter(RecyclingPagerAdapter.RecycleBin recycleBin) {
        this.recycleBin = recycleBin;
        recycleBin.setViewTypeCount(this.getViewTypeCount());
    }

    public void notifyDataSetChanged() {
        this.recycleBin.scrapActiveViews();
        super.notifyDataSetChanged();
    }

    public final Object instantiateItem(ViewGroup container, int position) {
        int viewType = this.getItemViewType(position);
        View view = null;
        if (viewType != -1) {
            view = this.recycleBin.getScrapView(position, viewType);
        }

        view = this.getView(position, view, container);
        container.addView(view);
        return view;
    }

    public final void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        int viewType = this.getItemViewType(position);
        if (viewType != -1) {
            this.recycleBin.addScrapView(view, position, viewType);
        }

    }

    public final boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public abstract View getView(int var1, View var2, ViewGroup var3);

    public static class RecycleBin {
        private View[] activeViews = new View[0];
        private int[] activeViewTypes = new int[0];
        private SparseArray<View>[] scrapViews;
        private int viewTypeCount;
        private SparseArray<View> currentScrapViews;

        public RecycleBin() {
        }

        public void setViewTypeCount(int viewTypeCount) {
            if (viewTypeCount < 1) {
                throw new IllegalArgumentException("Can\'t have a viewTypeCount < 1");
            } else {
                SparseArray[] scrapViews = new SparseArray[viewTypeCount];

                for (int i = 0; i < viewTypeCount; ++i) {
                    scrapViews[i] = new SparseArray();
                }

                this.viewTypeCount = viewTypeCount;
                this.currentScrapViews = scrapViews[0];
                this.scrapViews = scrapViews;
            }
        }

        protected boolean shouldRecycleViewType(int viewType) {
            return viewType >= 0;
        }

        View getScrapView(int position, int viewType) {
            return this.viewTypeCount == 1 ? retrieveFromScrap(this.currentScrapViews, position) : (viewType >= 0 && viewType < this.scrapViews.length ? retrieveFromScrap(this.scrapViews[viewType], position) : null);
        }

        void addScrapView(View scrap, int position, int viewType) {
            if (this.viewTypeCount == 1) {
                this.currentScrapViews.put(position, scrap);
            } else {
                this.scrapViews[viewType].put(position, scrap);
            }

            if (VERSION.SDK_INT >= 14) {
                scrap.setAccessibilityDelegate((AccessibilityDelegate) null);
            }

        }

        void scrapActiveViews() {
            View[] activeViews = this.activeViews;
            int[] activeViewTypes = this.activeViewTypes;
            boolean multipleScraps = this.viewTypeCount > 1;
            SparseArray scrapViews = this.currentScrapViews;
            int count = activeViews.length;

            for (int i = count - 1; i >= 0; --i) {
                View victim = activeViews[i];
                if (victim != null) {
                    int whichScrap = activeViewTypes[i];
                    activeViews[i] = null;
                    activeViewTypes[i] = -1;
                    if (this.shouldRecycleViewType(whichScrap)) {
                        if (multipleScraps) {
                            scrapViews = this.scrapViews[whichScrap];
                        }

                        scrapViews.put(i, victim);
                        if (VERSION.SDK_INT >= 14) {
                            victim.setAccessibilityDelegate((AccessibilityDelegate) null);
                        }
                    }
                }
            }

            this.pruneScrapViews();
        }

        private void pruneScrapViews() {
            int maxViews = this.activeViews.length;
            int viewTypeCount = this.viewTypeCount;
            SparseArray[] scrapViews = this.scrapViews;

            for (int i = 0; i < viewTypeCount; ++i) {
                SparseArray scrapPile = scrapViews[i];
                int size = scrapPile.size();
                int extras = size - maxViews;
                --size;

                for (int j = 0; j < extras; ++j) {
                    scrapPile.remove(scrapPile.keyAt(size--));
                }
            }

        }

        static View retrieveFromScrap(SparseArray<View> scrapViews, int position) {
            int size = scrapViews.size();
            if (size > 0) {
                int index;
                for (index = 0; index < size; ++index) {
                    int r = scrapViews.keyAt(index);
                    View view = (View) scrapViews.get(r);
                    if (r == position) {
                        scrapViews.remove(r);
                        return view;
                    }
                }

                index = size - 1;
                View var6 = (View) scrapViews.valueAt(index);
                scrapViews.remove(scrapViews.keyAt(index));
                return var6;
            } else {
                return null;
            }
        }
    }
}