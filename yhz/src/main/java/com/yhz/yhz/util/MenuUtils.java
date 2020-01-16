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
public class MenuUtils{

    private static PopupMenu popupMenu;
    private static MenuInterface mMenuInterface;

    public static void showMenu(Context context, View view, @MenuRes int menuRes, MenuInterface menuInterface){
        mMenuInterface = menuInterface;
        // View当前PopupMenu显示的相对View的位置
        popupMenu = new PopupMenu(context, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mMenuInterface.MenuItemClick(item);
                popupMenu.dismiss();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                mMenuInterface.MenuDismiss();
            }
        });
        popupMenu.show();
    }
}
