package com.sunbo.tool;

import com.sunbo.tool.table.OutTable;

import java.util.List;
import java.util.Map;

public interface TableConverter {

    OutTable fillUp(String name, Map<String, String> map);

    Map<String, OutTable> mergeByProfile(Map<String, List<OutTable>> map);
}
