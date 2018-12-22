package com.example.hbprolibrary.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * recyclerView模式类utils
 */
public class RecyclerViewUtil {
    public final static int LIST_VERTICAL = 0x001;
    public final static int LIST_VERTICAL_REVERSE = 0X002;
    public final static int LIST_HORIZONTAL = 0x003;
    public final static int LIST_HORIZONTAL_REVERSE = 0x004;

    public final static int GRID_VERTICAL = 0x005;
    public final static int GRID_VERTICAL_REVERSE = 0x006;
    public final static int GRID_HORIZONTAL = 0x007;
    public final static int GRID_HORIZONTAL_REVERSE = 0x008;

    public final static int STAGGER_VERTICAL = 0x009;
    public final static int STAGGER_HORIZONTAL = 0x010;

    /**
     * 把recyclerView设置成ListView模式
     * @param context
     * @param ss LIST_VERTICAL 垂直的ListVIew
     *           LIST_VERTICAL_REVERSE 反向垂直的ListVIew
     *           LIST_HORIZONTAL 横向的ListView
     *           LIST_HORIZONTAL_REVERSE 反向横向的ListView
     */
    public static void getRecyclerListViewType(Context context, RecyclerView recyclerView, int ss){
        LinearLayoutManager linearLayoutManager=null;
        if (ss == LIST_VERTICAL){
            linearLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        }else if (ss == LIST_VERTICAL_REVERSE){
            linearLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
        }else if (ss == LIST_HORIZONTAL){
            linearLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }else if (ss == LIST_HORIZONTAL_REVERSE){
            linearLayoutManager=new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * 把recyclerView设置成GridView模式
     * @param context
     * @param ss GRID_VERTICAL 垂直的GirdVIew
     *           GRID_VERTICAL_REVERSE 反向垂直的GirdVIew
     *           GRID_HORIZONTAL 横向的GirdView
     *           GRID_HORIZONTAL_REVERSE 反向横向的GirdView
     * @param count  垂直的时候 代表设置列数
     *                横向的时候 代表行数
     */
    public static void getRecyclerGridViewType(Context context, RecyclerView recyclerView, int ss, int count){
        GridLayoutManager gridLayoutManager = null;
        if (ss == GRID_VERTICAL){
            gridLayoutManager = new GridLayoutManager(context, count, GridLayoutManager.VERTICAL, false);
        }else if (ss == GRID_VERTICAL_REVERSE){
            gridLayoutManager = new GridLayoutManager(context, count, GridLayoutManager.VERTICAL, true);
        }else if (ss == GRID_HORIZONTAL){
            gridLayoutManager = new GridLayoutManager(context, count, GridLayoutManager.HORIZONTAL, false);
        }else if (ss == GRID_HORIZONTAL_REVERSE){
            gridLayoutManager = new GridLayoutManager(context, count, GridLayoutManager.HORIZONTAL, true);
        }
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    /**
     * 把recyclerView设置成瀑布流模式
     * @param ss STAGGER_VERTICAL 垂直的瀑布流
     *           STAGGER_HORIZONTAL 横向的瀑布流
     *@param count 垂直的时候 代表设置列数
     *                横向的时候 代表行数
     */
    public static void getRecyclerStaggerViewType(RecyclerView recyclerView, int ss, int count){
        StaggeredGridLayoutManager staggeredGridLayoutManager = null;
        if (ss == STAGGER_VERTICAL){
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(count, StaggeredGridLayoutManager.VERTICAL);
        }else if (ss == STAGGER_HORIZONTAL){
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(count, StaggeredGridLayoutManager.HORIZONTAL);
        }
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }
}
