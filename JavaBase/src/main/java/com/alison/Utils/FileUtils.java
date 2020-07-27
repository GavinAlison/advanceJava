package com.alison.Utils;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @program: collectormergeCode
 * @description: 文件工具类
 **/
public class FileUtils {


    /***
     * 按照headers 顺序 生产对应的文件
     * @param data
     * @param headers
     * @return
     */
    public static File createTmpCsvFile( List<Map<String,Object>> data,List<String> headers){

        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        File tempFile;
        CsvWriter csvWriter = null;
        try {
            tempFile = File.createTempFile(fileName, ".csv");
            csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
            if(null != data && data.size()>0){
                String[] content = new String[headers.size()];
                for(Map<String,Object> item:data){
                    for(int i=0;i<headers.size();i++){
                         if(item.containsKey(headers.get(i))){
                             content[i] = item.get(headers.get(i)).toString();
                         }else{
                             content[i] = "null string";
                         }
                    }
                    csvWriter.writeRecord(content);
                }
            }
            } catch (IOException e) {
                throw new RuntimeException("数据读取异常");
            } finally {
                if (csvWriter != null){
                    csvWriter.close();
                }
            }
        return tempFile;

    }

    /**
     * 删除单个文件
     *
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile( File file) {
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


}
