package com.std.ramsoapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.shiki.utils.ActivityManager;
import com.std.ramsoapp.adapter.MenuAdapter;
import com.std.ramsoapp.base.BaseActivity;
import com.std.ramsoapp.domain.MenuInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maik on 2016/3/8.
 */
public class MenuActivity extends BaseActivity {
    private List<MenuInfo> menuInfos;
    private GridView gvMenu;

    private long timeMillis;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_menu);
        gvMenu = (GridView) findViewById(R.id.gv_menu);
        gvMenu.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gvMenu.setAdapter(new MenuAdapter(this, menuInfos));
        gvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                Bundle menuBundle = new Bundle();
                menuBundle.putInt("menuId", position);
                intent.putExtras(menuBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void preData() {
        menuInfos = new ArrayList<>();
        MenuInfo info = new MenuInfo();
        info.setTitle("业务");
        info.setImgResource(R.drawable.ic_business_normal);
        menuInfos.add(info);
        info = new MenuInfo();
        info.setTitle("客户");
        info.setImgResource(R.drawable.ic_customer_normal);
        menuInfos.add(info);
        info = new MenuInfo();
        info.setTitle("我");
        info.setImgResource(R.drawable.ic_my_normal);
        menuInfos.add(info);
        info = new MenuInfo();
        info.setTitle("百宝箱");
        info.setImgResource(R.drawable.ic_box_normal);
        menuInfos.add(info);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                timeMillis = System.currentTimeMillis();
            } else {
                ActivityManager.getActivityManager().finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
