package com.ehi.customhtmltag;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;

import org.xml.sax.Attributes;

/**
 * @author: 37745 <a href="ziju.wang@1hai.cn">Contact me.</a>
 * @date: 2020/6/22 13:39
 * @desc:
 */
public abstract class BaseHtmlTag {
    private static final String UNIT_PX = "px";
    protected static final String FONT_SIZE = "font-size";
    protected static final String COLOR = "color";
    protected static final String BACKGROUND_COLOR = "background-color";
    protected static final String FONT_WEIGHT = "font-weight";
    protected static final String BOLD = "bold";
    protected static final String STYLE = "style";

    public abstract void startHandleTag(Editable originEditable, Attributes atts);

    public abstract void endHandleTag(Editable originEditable);

    public int getFontSize(String fontSize) {
        if (TextUtils.isEmpty(fontSize)) {
            return -1;
        }
        fontSize = fontSize.toLowerCase();
        //单位px
        if (fontSize.endsWith(UNIT_PX) && TextUtils.isDigitsOnly(fontSize.substring(0, fontSize.indexOf(UNIT_PX)))) {
            return (int) Float.parseFloat(fontSize.substring(0, fontSize.indexOf(UNIT_PX)));
        }
        //无单位
        if (TextUtils.isDigitsOnly(fontSize)) {
            return (int) Float.parseFloat(fontSize);
        }
        return -1;
    }

    /**
     * 重写Color.parseColor 不希望出现Exception
     *
     * @param colorString
     * @return
     */
    public int parseColor(String colorString) {
        if (TextUtils.isEmpty(colorString)) {
            return -1;
        }
        try {
            return Color.parseColor(colorString);
        } catch (IllegalArgumentException ex) {
            return -1;
        }
    }

    public static <T> T[] getSpansFromEdit(Editable editable, Class<T> kind) {
        T[] objs = editable.getSpans(0, editable.length(), kind);
        if (objs.length == 0) {
            return null;
        } else {
            return objs;
        }
    }
}