package com.sunbo.tool.table;

import java.util.*;

public class IndexedOutTable extends OutTable {

    Map<String, IndexedRow> rowMap = new HashMap<>();

    public IndexedOutTable(String tableName, List<IndexedRow> rows, String... titles) {

        super(tableName, Arrays.asList(titles));
        for (IndexedRow row : rows) {
            addRow(row);
        }
    }

    public IndexedOutTable(String tableName) {
        super(tableName);
    }

    @Override
    public IRow addRow(IRow iRow) {

        IndexedRow row = (IndexedRow) iRow;
        if (rowMap.containsKey(row.getIndexKey())) {
            IndexedRow orgi = rowMap.get(row.getIndexKey());
            if (orgi == null) {
                orgi = new IndexedRow();
                rowMap.put(row.getIndexKey(), orgi);
            }
            orgi.merge(row);
        } else {
            super.addRow(iRow);
            rowMap.put(row.getIndexKey(), row);
        }
        return row;
    }

    public void appendIndex(Set<String> keySet) {
        for (String s : keySet) {
            if (!rowMap.containsKey(s)) {
                IndexedRow row = new IndexedRow();
                row.setIndexKey(s);
                addRow(row);
            }
        }
    }
}
