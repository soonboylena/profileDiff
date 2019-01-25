package com.sunbo.tool;

import com.sunbo.tool.table.IndexedOutTable;
import com.sunbo.tool.table.IndexedRow;
import com.sunbo.tool.table.OutTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableConverterImpl implements TableConverter {

    @Override
    public OutTable fillUp(String name, Map<String, String> map) {

        IndexedOutTable table = new IndexedOutTable(name);

        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            IndexedRow row = new IndexedRow();
            row.setIndexKey(stringStringEntry.getKey());
            row.add("value", stringStringEntry.getValue());
            table.addRow(row);
        }
        return table;
    }

    @Override
    public Map<String, OutTable> mergeByProfile(Map<String, List<OutTable>> map) {


        Map<String, OutTable> tableNameMap = new HashMap<>();
        for (Map.Entry<String, List<OutTable>> stringListEntry : map.entrySet()) {

            String profile = stringListEntry.getKey();
            List<OutTable> value = stringListEntry.getValue();
            for (OutTable outTable : value) {

                outTable.changeColumn("value", profile);
                boolean b = tableNameMap.containsKey(outTable.getTableName());
                if (b) {
                    OutTable sameNameTable = tableNameMap.get(outTable.getTableName());
                    sameNameTable.appendColumn(outTable);
                } else {
                    tableNameMap.put(outTable.getTableName(), outTable);
                }
//                if (outTableTemp == null) {
//                    outTableTemp = outTable;
//                    outTableTemp.changeColumn("value", profile);
//                } else {
//                    outTable.changeColumn("value", profile);
//                    outTableTemp.appendColumn(outTable);
//                }
            }
        }
        return tableNameMap;
    }
}
