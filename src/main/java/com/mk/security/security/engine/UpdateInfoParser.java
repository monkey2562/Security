package com.mk.security.security.engine;

import android.util.Xml;

import com.mk.security.security.domain.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by Administrator on 2014/5/9.
 */
public class UpdateInfoParser {

    public static UpdateInfo getUpdateInfo(InputStream is) throws  Exception{
        UpdateInfo info = new UpdateInfo();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(is,"utf-8");
        int type = xmlPullParser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if (xmlPullParser.getName().equals("version")) {
                        info.setVersion(xmlPullParser.nextText());
                    }else if (xmlPullParser.getName().equals("description")) {
                        info.setDescription(xmlPullParser.nextText());
                    }else if (xmlPullParser.getName().equals("apkurl")) {
                        info.setUrl(xmlPullParser.nextText());
                    }
                    break;
                default:
                    break;
            }
            type = xmlPullParser.next();
        }
        return info;
    }
}
