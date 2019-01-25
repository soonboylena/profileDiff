package com.sunbo.tool.table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class IndexedRow extends Row {

    private String indexKey;

    public void merge(IndexedRow row) {

        Set<String> otherKeys = row.getKeys();

        for (String otherKey : otherKeys) {
            this.add(otherKey, row.getByKey(otherKey));
        }
    }
}
