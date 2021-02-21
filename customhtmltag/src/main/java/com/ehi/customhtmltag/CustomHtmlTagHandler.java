package com.ehi.customhtmltag;

import android.text.Editable;
import android.text.Html;
import android.util.ArrayMap;
import android.util.Log;

import androidx.collection.ArraySet;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.util.Arrays;

/**
 * @author: 37745 <a href="ziju.wang@1hai.cn">Contact me.</a>
 * @date: 2020/6/19 16:39
 * @desc: 使用自定义标签最外层需包裹<custom></custom>
 */
public class CustomHtmlTagHandler implements Html.TagHandler, ContentHandler {
    private static final String TAG = "CustomHtmlTagHandler";
    private final String CUSTOM_TAG = "custom";
    private XMLReader originXmlReader;
    private ContentHandler originContentHandler;
    private Editable originEditable;
    private int count;
    private final ArraySet<String> ORIGIN_TAGS = new ArraySet<>(Arrays.asList(
            "br", "p", "ul", "li", "div", "span", "strong", "b", "em", "cite", "dfn", "i",
            "big", "small", "font", "blockquote", "tt", "a", "u", "del", "s", "strike",
            "sup", "sub", "h1", "h2", "h3", "h4", "h5", "h6", "img"
    ));

    private ArrayMap<String, BaseHtmlTag> tagMaps = new ArrayMap<>();

    public void registerTag(String tag, BaseHtmlTag htmlTag) {
        tagMaps.put(tag.toLowerCase(), htmlTag);
    }

    public BaseHtmlTag removeTag(String tag) {
        tag = tag.toLowerCase();
        if (tagMaps.containsKey(tag)) {
            return tagMaps.remove(tag);
        }
        return null;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (opening) {
            startHandleTag(tag.toLowerCase(), output, xmlReader);
        } else {
            endHandleTag(tag.toLowerCase(), output, xmlReader);
        }
    }

    private void startHandleTag(String tag, Editable output, XMLReader xmlReader) {
        switch (tag) {
            case CUSTOM_TAG:
                if (originContentHandler == null) {
                    originContentHandler = xmlReader.getContentHandler();
                    originXmlReader = xmlReader;
                    xmlReader.setContentHandler(this);
                    originEditable = output;
                }
                count++;
                break;
            default:
        }
    }

    private void endHandleTag(String tag, Editable output, XMLReader xmlReader) {
        switch (tag) {
            case CUSTOM_TAG:
                count--;
                if (count == 0) {
                    for (String key : tagMaps.keySet()) {
                        tagMaps.get(key).finishHandleTag(originEditable);
                    }
                    originXmlReader.setContentHandler(originContentHandler);
                    originXmlReader = null;
                    originContentHandler = null;
                    originEditable = null;
                }
                break;
            default:
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        //忽略大小写
        localName = localName.toLowerCase();
        if (localName.equalsIgnoreCase(CUSTOM_TAG)) {
            handleTag(true, localName, originEditable, originXmlReader);
        } else if (canHandleTag(localName)) {
            tagMaps.get(localName).startHandleTag(originEditable, atts);
        } else if (ORIGIN_TAGS.contains(localName)) {
            originContentHandler.startElement(uri, localName, qName, atts);
        } else {
            Log.e(TAG, "startElement: <" + localName + ">标签不可被解析");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //忽略大小写
        localName = localName.toLowerCase();
        if (localName.equalsIgnoreCase(CUSTOM_TAG)) {
            handleTag(false, localName, originEditable, originXmlReader);
        } else if (canHandleTag(localName)) {
            tagMaps.get(localName).endHandleTag(originEditable);
        } else if (ORIGIN_TAGS.contains(localName)) {
            originContentHandler.endElement(uri, localName, qName);
        } else {
            Log.e(TAG, "endElement: </" + localName + ">标签不可被解析");
        }
    }

    public boolean canHandleTag(String tagName) {
        if (!tagMaps.containsKey(tagName)) {
            return false;
        }
        BaseHtmlTag baseHtmlTag = tagMaps.get(tagName);
        return baseHtmlTag != null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        originContentHandler.characters(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        originContentHandler.ignorableWhitespace(ch, start, length);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        originContentHandler.processingInstruction(target, data);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        originContentHandler.skippedEntity(name);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        originContentHandler.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        originContentHandler.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        originContentHandler.endDocument();
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        originContentHandler.startPrefixMapping(prefix, uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        originContentHandler.endPrefixMapping(prefix);
    }
}