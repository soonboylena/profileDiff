package com.sunbo.tool.table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Row implements IRow {

    protected Map<String, String> row = new LinkedHashMap<>();

    @Override
    public String getByKey(String key) {
        return row.get(key);
    }

    @Override
    public Set<String> getKeys() {
        return row.keySet();
    }

    @Override
    public void add(String key, String value) {
        row.put(key, value);
    }

    @Override
    public void changeKey(String oldColName, String newColName) {
        String s = row.get(oldColName);
        row.remove(oldColName);
        row.put(newColName, s);
    }
}
