package com.sunbo.tool;

import com.sunbo.tool.table.OutTable;

import java.util.List;

public interface TableOutputFormatter {
    CharSequence format(OutTable outTable, String prefix);
}
