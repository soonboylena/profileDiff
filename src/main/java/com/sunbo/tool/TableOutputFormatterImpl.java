package com.sunbo.tool;

import com.sunbo.tool.table.IRow;
import com.sunbo.tool.table.IndexedOutTable;
import com.sunbo.tool.table.IndexedRow;
import com.sunbo.tool.table.OutTable;

import java.util.List;

public class TableOutputFormatterImpl implements TableOutputFormatter {

    @Override
    public CharSequence format(OutTable outTable, String prefix) {

        StringBuilder sbd = new StringBuilder();

        IndexedOutTable indexedOutTable = (IndexedOutTable) outTable;
        sbd.append("\r\n").append(prefix);
        sbd.append(outTable.getTableName());
        sbd.append("\r\n").append(prefix).append("\t");
        for (String tableTitle : outTable.getTableTitles()) {
            sbd.append("\t");
            sbd.append(tableTitle);
        }
        sbd.append("\r\n").append(prefix).append("\t");

        List<IRow> rows = indexedOutTable.getRows();
        if (rows == null) return sbd;
        for (IRow row : rows) {
            IndexedRow indexedRow = (IndexedRow) row;
            sbd.append(indexedRow.getIndexKey());
            sbd.append("\t");

            for (String tableTitle : outTable.getTableTitles()) {
                sbd.append(row.getByKey(tableTitle) == null ? "": row.getByKey(tableTitle));
                sbd.append("\t");
            }
            sbd.append("\r\n").append(prefix).append("\t");
        }
        return sbd;
    }
}
