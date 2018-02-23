package com.jtomaszk.grv.service.util;

import com.google.common.collect.Maps;
import com.jtomaszk.grv.domain.InputPattern;
import com.jtomaszk.grv.domain.PatternColumn;
import com.jtomaszk.grv.domain.enumeration.ColumnEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonUtil {

    public static List<Map<ColumnEnum, String>> parse(InputPattern inputPattern, String jsonString) throws JSONException {
        final Set<PatternColumn> columns = inputPattern.getColumns();
        final JSONArray json = new JSONArray(jsonString);

        return IntStream.range(0, json.length())
            .boxed()
            .map(i -> getJsonObject(json, i))
            .map(JsonUtil::getMapValues)
            .map(i -> transformPatterns(columns, i))
            .collect(Collectors.toList());
    }

    private static Map<ColumnEnum, String> transformPatterns(Set<PatternColumn> columns, Map<String, String> values) {
        return columns.stream()
            .collect(Collectors.toMap(
                PatternColumn::getColumn,
                j -> StrSubstitutor.replace(j.getValue(), values)
            ));
    }

    private static JSONObject getJsonObject(JSONArray json, int i) {
        try {
            return json.getJSONObject(i);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, String> getMapValues(JSONObject jsonObject) {
        final Iterator keys = jsonObject.keys();
        final Map<String, String> values = Maps.newHashMap();

        while (keys.hasNext()) {
            Object key = keys.next();
            if (key instanceof String && getObject(jsonObject, getKey(key)) instanceof String) {
                final String keyString = getKey(key);
                final String value = getString(jsonObject, keyString);

                values.put(keyString, value);
            }
        }
        return values;
    }

    private static String getKey(Object key) {
        return (String) key;
    }

    private static String getString(JSONObject jsonObject, String keyString) {
        try {
            return StringUtils.upperCase(StringUtils.trimToNull(jsonObject.getString(keyString)));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.get(key);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
