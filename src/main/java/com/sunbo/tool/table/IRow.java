package com.sunbo.tool.table;


import java.util.Set;

public interface IRow {

    String getByKey(String key);

    Set<String> getKeys();

    void add(String otherKey, String byKey);

    void changeKey(String oldColName, String newColName);
}
