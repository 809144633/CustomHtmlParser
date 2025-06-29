package com.ehi.customhtmltag;

import android.os.Build;
import android.text.Html;
import android.text.TextUtils;

/**
 * Created on 2018/12/18.
 * author : HeQing
 * desc : html处理工具类
 */
public class HtmlUtil {

    /**
     * Convert an HTML string into a {@link CharSequence}.
     *
     * @param htmlStr html content to parse
     * @return styled text parsed from the provided HTML
     */
    public static CharSequence fromHtml(String htmlStr) {
        if (TextUtils.isEmpty(htmlStr)) {
            return "";
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                CustomHtmlTagHandler tagHandler = new CustomHtmlTagHandler();
                tagHandler.registerTag(CustomSpanTag.SPAN, new CustomSpanTag());
                return Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_LEGACY, null, tagHandler);
            } else {
                return Html.fromHtml(htmlStr);
            }

        } catch (Exception ignore) {

        }
        return htmlStr;
    }
}
