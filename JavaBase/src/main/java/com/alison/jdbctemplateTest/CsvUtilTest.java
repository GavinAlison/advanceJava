package com.alison.jdbctemplateTest;

import com.csvreader.CsvReader;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CsvUtilTest {
    @Test
    public ArrayList<String[]> readCSV() {
        // 用来保存数据
        ArrayList<String[]> csvFileList = Lists.newArrayList();
        try {
            // 定义一个CSV路径
            List<String> strs = Lists.newArrayList();
//            String csvFilePath = "D:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\function2.csv";
            String arithmeticfunction3 = "d:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\arithmeticfunction3.csv";
            String conditionalfunction = "D:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\Conditionalfunction.csv";
            String typeconversionfunction = "D:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\TypeConversionfunction.csv";
            String collectionfunction = "D:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\Collectionfunction.csv";
            String valueconstructionfunction = "D:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\ValueConstructionfunction.csv";
            String groupingfunction = "D:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\Groupingfunction.csv";
            String hashfunction = "D:\\workspace\\any-data-hub\\any-data-hub-common\\src\\test\\resources\\Hashfunction.csv";
            strs.add(arithmeticfunction3);
            strs.add(conditionalfunction);
            strs.add(typeconversionfunction);
            strs.add(collectionfunction);
            strs.add(valueconstructionfunction);
            strs.add(groupingfunction);
            strs.add(hashfunction);
            for (String str : strs) {
                CsvReader reader = null;
                try {
                    // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
                    reader = new CsvReader(str, '\t', Charset.forName("UTF-8"));
                    // 跳过表头 如果需要表头的话，这句可以忽略
//            reader.readHeaders();
                    // 逐行读入除表头的数据
                    while (reader.readRecord()) {
//                        System.out.println(reader.getRawRecord());
                        csvFileList.add(reader.getValues());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
//            CsvReader reader = new CsvReader(csvFilePath, '\t', Charset.forName("GBK"));
            // 跳过表头 如果需要表头的话，这句可以忽略
//            reader.readHeaders();
            // 逐行读入除表头的数据
//            while (reader.readRecord()) {
//                System.out.println(reader.getRawRecord());
//                csvFileList.add(reader.getValues());
//            }
//            reader.close();

            // 遍历读取的CSV文件
//            for (int row = 0; row < csvFileList.size(); row++) {
//                // 取得第row行第0列的数据
//                String cell = csvFileList.get(row)[0];
//                System.out.println("------------>" + cell);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvFileList;
    }

}
