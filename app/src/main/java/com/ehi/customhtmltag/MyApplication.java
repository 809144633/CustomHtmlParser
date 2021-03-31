package com.ehi.customhtmltag;

import android.app.Application;

/**
 * @author: 37745 <a href="ziju.wang@1hai.cn">Contact me.</a>
 * @date: 2021/3/31 17:34
 * @desc:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HtmlTools.setApplication(this);
    }
}
