package com.sunbo.tool;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Main {

    public static void main(String[] args) {

        RunParams runParams = analysis(args);

        log.info("参数： {}", runParams);
        ProfileDiffer differ = new ProfileDiffer(runParams.getPath(), runParams.getFolder(), runParams.getThreadCount());
        differ.setReader(new PropertiesReader());
        differ.setFormatter(new TableOutputFormatterImpl());
        differ.setTableConverter(new TableConverterImpl());
        differ.doScan();
    }

    private static RunParams analysis(String[] args) {


        Map<String, String> argMap = new HashMap<>();

        String tempKey = null;
        for (String arg : args) {
            if (arg.startsWith("-")) {
                tempKey = arg.substring(1);
            } else if (tempKey != null) {
                argMap.put(tempKey, arg);
            } else {
                log.warn("抛弃的参数: {}", arg);
            }
        }

        String path;
        if (argMap.get("p") == null) {
            log.debug("没有指定Root路径，使用当前路径.如果要指定，请使用 -p 参数");
            path = new File("").getAbsolutePath();
        } else {
            path = argMap.get("p");
        }


        String folder;
        if (argMap.get("f") == null) {
            log.debug("没有指定文件夹名，使用 \"config\"。如果要指定，请使用 -f");
            folder = "config";
        } else {
            folder = argMap.get("f");
        }

        Integer threadNum = 3;
        if (argMap.get("t") == null) {
            log.debug("没有指定线程数。默认值为3.如果要指定，请使用 -t");
        } else {
            try {
                threadNum = Integer.valueOf(argMap.get("t"));
            } catch (NumberFormatException e) {
                log.debug("线程数设置错误，请使用 -t. 使用默认值3");
            }
        }


        RunParams params = new RunParams();
        params.setPath(path);
        params.setFolder(folder);
        params.setThreadCount(threadNum);
        return params;

    }
}
