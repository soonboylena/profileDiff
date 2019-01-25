package com.sunbo.tool;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class PropertiesReader implements IDiffFileReader {

    @Override
    public boolean isSupport(File file) {
        String name = file.getName();
        return file.isFile() && (name.endsWith(".properties") || name.endsWith(".properties.zh"));
    }

    @Override
    public Map<String, String> read(File file) {

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
            HashMap<String, String> stringStringHashMap = new HashMap<>();

            Set<String> strings = properties.stringPropertyNames();
            for (String string : strings) {
                stringStringHashMap.put(string, properties.getProperty(string));
            }
            return stringStringHashMap;

        } catch (IOException e) {
            log.error("error:{}", file.getName(), e);
        }
        return Collections.emptyMap();
    }
}
