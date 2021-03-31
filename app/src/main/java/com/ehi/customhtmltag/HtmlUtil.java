package com.ehi.customhtmltag;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;

/**
 * Created on 2018/12/18.
 * author : HeQing
 * desc : html处理工具类
 */
public class HtmlUtil {

    public static CharSequence fromHtml(String htmlStr) {
        if (TextUtils.isEmpty(htmlStr)) {
            return "";
        }
        try {
            return Html.fromHtml(htmlStr);
        } catch (Exception ignore) {

        }
        return htmlStr;
    }

    public static CharSequence fromHtml(String htmlStr, Context mContext) {
        if (TextUtils.isEmpty(htmlStr)) {
            return "";
        }
        try {
            CustomHtmlTagHandler tagHandler = new CustomHtmlTagHandler();
            tagHandler.registerTag(CustomSpanTag.SPAN, new CustomSpanTag());
            return Html.fromHtml(htmlStr,null, tagHandler);
        } catch (Exception ignore) {
            return Html.fromHtml(htmlStr);
        }
    }
}
