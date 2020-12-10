package com.alison.Utils;

import com.csvreader.CsvWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author 梁慧琴
 * @date 2019/12/9
 * 导出文件成csv格式工具类
 */
@Slf4j
public class CsvUtil {

    public static void writeCsv(OutputStream out,String[] strings){
        CsvWriter csvWriter = new CsvWriter(out,',',Charset.forName("UTF-8"));

        try{
            csvWriter.writeRecord(strings);
            csvWriter.close();
        }catch (IOException e){
            log.error("write csv error, ", e);
        }


    }
}
