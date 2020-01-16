package com.yhz.yhz.util;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.MenuRes;

import com.yhz.yhz.interfaces.MenuInterface;

/**
 * @description: MenuUtils (菜单工具类)
 * @author: Y.hz
 * @time: 2020/01/16 15:27
 */
public class MenuUtils implements PopupMenu.OnMenuItemClickListener, PopupMenu.OnDismissListener {

    private MenuInterface menuInterface;
    private PopupMenu popupMenu;

    public void showMenu(Context context, View view, @MenuRes int menuRes,MenuInterface menuInterface){
        this.menuInterface = menuInterface;
        // View当前PopupMenu显示的相对View的位置
        popupMenu = new PopupMenu(context, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(this);
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        menuInterface.MenuItemClick(item);
        popupMenu.dismiss();
        return false;
    }

    @Override
    public void onDismiss(PopupMenu menu) {
        menuInterface.MenuDismiss();
    }
}
