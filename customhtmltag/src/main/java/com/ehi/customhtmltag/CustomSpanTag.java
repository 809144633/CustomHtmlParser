package com.ehi.customhtmltag;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;

import org.xml.sax.Attributes;

/**
 * @author: 37745 <a href="ziju.wang@1hai.cn">Contact me.</a>
 * @date: 2020/6/22 13:38
 * @desc: e.g."<custom><span>普通样式文本</span><br><span style=\"color: #ff7e00; font-size: 20px;\">黄色文字，字号偏大。</span>" +
 * "<span style=\"color: #27ad9a; font-size: 20px;font-weight:bold\">" +
 * "后面的绿色文字，字体加粗的。</span><br><del>中间的删除线文本</del></custom>"
 */
public class CustomSpanTag extends BaseHtmlTag {
    public static final String SPAN = "span";

    @Override
    public void startHandleTag(Editable originEditable, Attributes atts) {
        String style = atts.getValue("", STYLE);
        if (TextUtils.isEmpty(style)) {
            return;
        }
        String textColorStr = getValueFromStyle(style, COLOR);
        String fontSizeStr = getValueFromStyle(style, FONT_SIZE);
        String backgroundColorStr = getValueFromStyle(style, BACKGROUND_COLOR);
        String fontWeight = getValueFromStyle(style, FONT_WEIGHT);
        int fontSize = getFontSize(fontSizeStr);
        if (fontSize != -1) {
            //接收数据为px单位但因为iOS、Android使用单位不同且无法使用px实现适配，暂时采用15px当做15dp进行处理
            setStartStyle(originEditable, new FontSize(fontSize));
        }
        int textColor = parseColor(textColorStr);
        if (textColor != -1) {
            setStartStyle(originEditable, new ForegroundColor(textColor));
        }
        int backgroundColor = parseColor(backgroundColorStr);
        if (backgroundColor != -1) {
            setStartStyle(originEditable, new BackgroundColor(backgroundColor));
        }
        if (fontWeight != null && fontWeight.toLowerCase().equals(BOLD)) {
            setStartStyle(originEditable, new Bold());
        }
    }

    private String getValueFromStyle(String style, String matchAttr) {
        if (TextUtils.isEmpty(style)) {
            return null;
        }
        return getHtmlCssAttrs(style, matchAttr);
    }

    private String getHtmlCssAttrs(@NonNull String style, String matchAttr) {
        if (TextUtils.isEmpty(style)) {
            return null;
        }
        String[] styleAttrs = style.trim().toLowerCase().split(";");
        for (String attr : styleAttrs) {
            attr = attr.trim();
            if (attr.indexOf(matchAttr) == 0) {
                String[] split = attr.split(":");
                if (split.length != 2) {
                    continue;
                }
                return split[1].trim();
            }
        }
        return null;
    }

    @Override
    public void endHandleTag(Editable originEditable) {
        FontSize fontSizeSpan = getLastSpanFromEdit(originEditable, FontSize.class);
        if (fontSizeSpan != null) {
            setEditableSpans(originEditable, fontSizeSpan, new AbsoluteSizeSpan(fontSizeSpan.fontSize, true));
        }
        ForegroundColor foregroundColorSpan = getLastSpanFromEdit(originEditable, ForegroundColor.class);
        if (foregroundColorSpan != null) {
            setEditableSpans(originEditable, foregroundColorSpan, new ForegroundColorSpan(foregroundColorSpan.foregroundColor));
        }
        BackgroundColor backgroundColorSpan = getLastSpanFromEdit(originEditable, BackgroundColor.class);
        if (backgroundColorSpan != null) {
            setEditableSpans(originEditable, backgroundColorSpan, new BackgroundColorSpan(backgroundColorSpan.backgroundColor));
        }
        Bold boldSpans = getLastSpanFromEdit(originEditable, Bold.class);
        if (boldSpans != null) {
            setEditableSpans(originEditable, boldSpans, new CustomFontBoldSpan());
        }
    }

    private void setStartStyle(Editable editable, Object mark) {
        // startHandle阶段 setSpan只做标记位置作用不实现具体效果
        int length = editable.length();
        editable.setSpan(mark, length, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private void setEditableSpans(Editable editable, Object mark, Object... spans) {
        int start = editable.getSpanStart(mark);
        editable.removeSpan(mark);
        int end = editable.length();
        if (start != end) {
            for (Object span : spans) {
                editable.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private static class Bold {

    }

    private static class FontSize {
        int fontSize;

        public FontSize(int fontSize) {
            this.fontSize = fontSize;
        }
    }

    private static class BackgroundColor {
        int backgroundColor;

        public BackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }
    }

    private static class ForegroundColor {
        int foregroundColor;

        public ForegroundColor(int foregroundColor) {
            this.foregroundColor = foregroundColor;
        }
    }

    private static class CustomFontBoldSpan extends StyleSpan {

        public CustomFontBoldSpan() {
            super(Typeface.BOLD);
        }

        public CustomFontBoldSpan(@NonNull Parcel src) {
            super(src);
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setStrokeWidth(1.0F);
            tp.setStyle(Paint.Style.FILL_AND_STROKE);
        }
    }
}
