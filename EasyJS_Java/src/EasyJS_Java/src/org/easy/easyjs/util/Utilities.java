/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easy.easyjs.util;

import java.util.HashMap;
import java.util.Map;
import org.easy.easyjs.annotation.JsonElement;

/**
 *
 * @author mm29950
 */
public class Utilities {

    private static Map<String, String[]> keyTypeList = new HashMap<String, String[]>();

    public static void isNull(Object obj) {
        check(obj != null);
    }

    public static void check(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException("condition failed: " + condition);
        }
    }

    public static Map<String, String[]> getKeyTypeList() {
        keyTypeList.clear();
        keyTypeList.put(JsonElement.HTML_BUTTON,        new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_CHECKBOX,      new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_H1,            new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_H2,            new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_H3,            new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_H4,            new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_HIDDEN,        new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_LABEL,         new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_LEGEND,        new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_ORDERED_LIST,  new String[]{"type", "values", "id"});
        keyTypeList.put(JsonElement.HTML_SPAN,          new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_SUBMIT,        new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_TEXT,          new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_TEXT_AREA,     new String[]{"type", "value", "id"});
        keyTypeList.put(JsonElement.HTML_UNORDERED_LIST,new String[]{"type", "values", "id"});
        keyTypeList.put(JsonElement.HTML_RADIO,         new String[]{"type", "value", "id", "name", "text", "checked"});
        keyTypeList.put(JsonElement.HTML_SELECT,        new String[]{"type", "values", "id", "select"});
        keyTypeList.put(JsonElement.HTML_IMAGE,         new String[]{"type", "src", "id", "height", "width"});
        keyTypeList.put(JsonElement.HTML_LINK,          new String[]{"type", "value", "id", "href"});
        
        return keyTypeList;
    }
}
