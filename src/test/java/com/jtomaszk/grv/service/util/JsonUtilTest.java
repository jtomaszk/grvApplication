package com.jtomaszk.grv.service.util;

import com.jtomaszk.grv.domain.InputPattern;
import com.jtomaszk.grv.domain.PatternColumn;
import com.jtomaszk.grv.domain.enumeration.ColumnEnum;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonUtilTest {

    @Test
    public void shouldParseJson() throws Exception {
        String jsonString = getFile("test.json");

        InputPattern pattern = new InputPattern();
        pattern.addColumns(new PatternColumn().column(ColumnEnum.EXTERNAL_ID).value("${id}"));
        pattern.addColumns(new PatternColumn().column(ColumnEnum.LAST_NAME).value("${surname}"));

        List<Map<ColumnEnum, String>> result = JsonUtil.parse(pattern, jsonString);

        assertEquals(1, result.size());
        Map<ColumnEnum, String> values = new HashMap<>();
        values.put(ColumnEnum.EXTERNAL_ID, "512");
        values.put(ColumnEnum.LAST_NAME, "AAA");
        assertEquals(values, result.get(0));
    }

    private String getFile(String fileName) {

        String result = "";

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}
