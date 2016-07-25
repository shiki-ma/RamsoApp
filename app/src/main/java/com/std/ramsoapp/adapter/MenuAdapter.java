package com.std.ramsoapp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiki.utils.DeviceUtils;
import com.shiki.utils.StringUtils;
import com.shiki.utils.adapter.ViewHolderAdapter;
import com.std.ramsoapp.R;
import com.std.ramsoapp.domain.MenuInfo;

import java.util.List;

/**
 * Created by Maik on 2016/3/8.
 */
public class MenuAdapter extends ViewHolderAdapter<MenuAdapter.MenuHolder, MenuInfo> {

    public MenuAdapter(Activity activity, List<MenuInfo> list) {
        super(activity, list);
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.gridview_item, parent);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        MenuInfo menuInfo = getDatas().get(position);
        if (StringUtils.isEmpty(menuInfo.getTitle())) {
            holder.ivImage.setPadding(0, 0, 0, DeviceUtils.dip2px(getContext(), 30));
            holder.tvTitle.setVisibility(View.GONE);
        } else {
            holder.tvTitle.setText(menuInfo.getTitle());
        }
        holder.ivImage.setImageResource(menuInfo.getImgResource());
    }

    public class MenuHolder extends ViewHolderAdapter.ViewHolder {
        public TextView tvTitle;
        public ImageView ivImage;

        public MenuHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_item);
            ivImage = (ImageView) view.findViewById(R.id.iv_item);
        }
    }
}
