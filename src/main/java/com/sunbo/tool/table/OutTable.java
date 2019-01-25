package com.sunbo.tool.table;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OutTable {

    private String tableName;
    private List<String> tableTitles = new LinkedList<>();

    private List<IRow> rows;


    public OutTable(String tableName, List<IRow> rows, String... titles) {
        this.tableName = tableName;
        tableTitles.addAll(Arrays.asList(titles));
        this.rows = rows;
    }

    public OutTable(String tableName, List<String> tableTitles) {
        this.tableName = tableName;
        this.tableTitles = tableTitles;
    }

    public OutTable(String tableName) {
        this.tableName = tableName;
    }

    public IRow addRow(IRow row) {
        if (row != null) {
            if (rows == null) rows = new LinkedList<>();
            rows.add(row);
        }
        return row;
    }

    public void addColumn(String key) {
        tableTitles.add(key);
    }

    public void changeColumn(String oldColName, String newColName) {
        boolean remove = tableTitles.remove(oldColName);
        tableTitles.add(newColName);
        if (rows != null) {
            for (IRow row : rows) {
                row.changeKey(oldColName, newColName);
            }
        }
    }

    public void appendColumn(OutTable outTable) {
        this.tableTitles.addAll(outTable.tableTitles);
        List<IRow> rows = outTable.rows;

        if (rows == null) return;
        for (IRow row : rows) {
            addRow(row);
        }
    }

    public List<String> getTableTitles() {
        return tableTitles;
    }

    public String getTableName() {
        return tableName;
    }

    public List<IRow> getRows() {
        return rows;
    }
}
