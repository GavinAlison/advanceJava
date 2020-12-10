package com.alison.Utils;

import com.csvreader.CsvReader;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author alison
 * @Date 2020/5/19
 * @Version 1.0
 * @Description
 */
public class FileUtilTest {
    @Test
    public void test1() throws Exception {
        String name = "C:\\Users\\hy\\Desktop\\dataModel.csv";
        byte[]  content = new byte[1024];
        new FileInputStream(name).read(content, 0, 1024);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(name, content);
        Charset charset = FileUtil.detectCharset(mockMultipartFile);
        List<Map<String,Object>> data = FileUtil.uploadCsv(mockMultipartFile);
        System.out.println(data);
        System.out.println(charset);
        File file = new File(name);
        InputStream in= new FileInputStream(file);
        byte[] b = new byte[3];
        in.read(b);
        in.close();
        if (b[0] == -17 && b[1] == -69 && b[2] == -65)
            System.out.println(file.getName() + "：编码为UTF-8");
        else
            System.out.println("GBK");
    }
    @Test
    public   void readCSV() {
        try {
            // 用来保存数据
            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
            // 定义一个CSV路径
            String csvFilePath = "C:\\Users\\hy\\Desktop\\dataModel.csv";
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));
            // 跳过表头 如果需要表头的话，这句可以忽略
//            reader.readHeaders();
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
                System.out.println(reader.getRawRecord());
                csvFileList.add(reader.getValues());
            }
            reader.close();

            // 遍历读取的CSV文件
            for (int row = 0; row < csvFileList.size(); row++) {
                // 取得第row行第0列的数据
                String cell = csvFileList.get(row)[0];
                System.out.println("------------>"+cell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
