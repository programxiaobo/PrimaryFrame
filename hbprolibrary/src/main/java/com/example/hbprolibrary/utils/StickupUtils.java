package com.example.hbprolibrary.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * 复制粘贴板utils
 */

public class StickupUtils {
    private static StickupUtils utils;
    private StickupUtils(){
    }
    public static StickupUtils getInstance() {
        synchronized (StickupUtils.class) {
            if (utils == null) {
                utils = new StickupUtils();
            }
            return utils;
        }
    }

    /**
     * 赋值到粘贴板
     * @param context
     * @param content
     */
    public void copy(Context context,String content){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple",content);
        clipboard.setPrimaryClip(clip);
    }
}
