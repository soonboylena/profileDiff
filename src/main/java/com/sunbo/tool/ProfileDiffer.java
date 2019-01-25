package com.sunbo.tool;

import com.sunbo.tool.table.OutTable;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ProfileDiffer {

    private String root;
    private String folderName;
    private int threadCount;

    private IDiffFileReader reader;
    private TableConverter tableConverter;
    private TableOutputFormatter formatter;


    public ProfileDiffer(String root, String folderName, int threadCount) {
        this.root = root;
        this.folderName = folderName;
    }

    public void doScan() {

        File file = new File(root);
        // 指定的地方不对往上挪挪
        if (file.isFile()) {
            root = file.getParent();
            file = file.getParentFile();
        }

        List<File> targetList = new ArrayList<>();
        findSubFolders(file, targetList);

        log.info("待比较文件夹列表： root:{}", root);
        for (File t : targetList) {
            log.info("  --  {}", t.getAbsolutePath());
        }


        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

        Map<String, Map<String, OutTable>> totalMap = new HashMap<>();

        for (File t : targetList) {

            fixedThreadPool.execute(() -> {

                File[] files = t.listFiles(File::isDirectory);

                if (files == null) return;

                Map<String, List<OutTable>> map = new HashMap<>();
                // 各个profile文件夹
                for (File profileFolder : files) {
                    List<OutTable> outTables = readFiles(profileFolder);
                    map.put(profileFolder.getName(), outTables);
                }
                Map<String, OutTable> mergedTable = tableConverter.mergeByProfile(map);
                totalMap.put(t.getAbsolutePath(), mergedTable);
            });
        }
        fixedThreadPool.shutdown();

        try {
            fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            output(totalMap);
        } catch (InterruptedException e) {
            log.error("任务已经完成，但是异常了 ", e);
        }


    }

    private void output(Map<String, Map<String, OutTable>> totalMap) {

        System.out.println("ROOT: " + root);
        for (Map.Entry<String, Map<String, OutTable>> stringMapEntry : totalMap.entrySet()) {

            System.out.println("目录： " + stringMapEntry.getKey().replaceAll(root, ""));

            for (Map.Entry<String, OutTable> stringOutTableEntry : stringMapEntry.getValue().entrySet()) {
                CharSequence format = this.formatter.format(stringOutTableEntry.getValue(), "\t");
                System.out.println(format);
            }

        }

    }

    private List<OutTable> readFiles(File profileFolder) {

        File[] files = profileFolder.listFiles(File::isFile);
        List<OutTable> outTables = new LinkedList<>();
        for (File file : files) {
            if (reader.isSupport(file)) {
                Map<String, String> map = reader.read(file);

                OutTable outTable = tableConverter.fillUp(file.getName(), map);
                outTables.add(outTable);
            } else {
                log.trace("不认识的文件：{}", file.getName());
            }
        }
        return outTables;

    }

    private void findSubFolders(File parentFolder, List<File> targetList) {

        File[] files = parentFolder.listFiles((dir, name) -> folderName.equals(name));
        if (files != null)
            targetList.addAll(Arrays.asList(files));

        File[] subFolders = parentFolder.listFiles();

        if (subFolders != null) {
            for (File subFolder : subFolders) {
                findSubFolders(subFolder, targetList);
            }
        }
    }

    public void setReader(IDiffFileReader reader) {
        this.reader = reader;
    }


    public TableConverter getTableConverter() {
        return tableConverter;
    }

    public void setTableConverter(TableConverter tableConverter) {
        this.tableConverter = tableConverter;
    }

    public TableOutputFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(TableOutputFormatter formatter) {
        this.formatter = formatter;
    }
}
