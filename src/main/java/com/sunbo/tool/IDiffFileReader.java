package com.sunbo.tool;

import java.io.File;
import java.util.Map;

public interface IDiffFileReader {
    boolean isSupport(File file);

    Map<String, String> read(File file);
}
