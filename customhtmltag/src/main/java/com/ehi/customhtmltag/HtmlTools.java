package com.ehi.customhtmltag;

import android.app.Application;

/**
 * @author: 37745 <a href="ziju.wang@1hai.cn">Contact me.</a>
 * @date: 2021/3/31 17:06
 * @desc:
 */
public class HtmlTools {
    private static Application mApplication;

    public static Application getApplication() {
        return mApplication;
    }

    public static void setApplication(Application mApplication) {
        HtmlTools.mApplication = mApplication;
    }
}
