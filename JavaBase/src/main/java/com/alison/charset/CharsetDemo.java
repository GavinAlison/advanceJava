package com.alison.charset;

import com.csvreader.CsvReader;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author alison
 * @Date 2020/1/4  22:45
 * @Version 1.0
 * @Description 关于字符编码的问题
 * @link https://www.jianshu.com/p/49f0e0423b41
 */
@Slf4j
public class CharsetDemo {
    @Test
    public void test() {
        String str = "哈哈";
        log.info(this.getEncode(str.getBytes(Charset.forName("GBK"))));
    }

    // detect what is charset of byte[]
    public String getEncode(byte[] bytes) {
        CharsetDetector detector = new CharsetDetector();
        detector.setText(bytes);
        CharsetMatch match = detector.detect();
        String encode = match.getName();
        return encode;
    }

    public static Charset detectCharset(MultipartFile f) {
        Charset charset = Charset.forName("GBK");
        try (InputStream in = f.getInputStream()) {
            byte[] b = new byte[3];
            in.read(b);
            in.close();
            if (b[0] == -17 && b[1] == -69 && b[2] == -65)
                charset = StandardCharsets.UTF_8;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return charset;
    }

    /**
     * 上传csv文件
     *
     * @param file
     * @return
     */
    public static List<Map<String, Object>> uploadCsv(MultipartFile file) {

        List<Map<String, Object>> result = Lists.newArrayList();
        String[] firstField = null;
        // 判断文件流的编码格式
        Charset charset = detectCharset(file);
        try (InputStreamReader ir = new InputStreamReader(file.getInputStream(), charset);
             BufferedReader br = new BufferedReader(ir);) {
            String fields;
            int i = 0;
            while ((fields = br.readLine()) != null) {
                String[] field = fields.split(",");
                if (i == 0) {
                    if (field[0].startsWith("\uFEFF")) {
                        field[0] = field[0].replaceFirst("\uFEFF", "");
                    }
                    firstField = field;
                    i++;
                } else {
                    Map<String, Object> map = Maps.newHashMap();
                    for (int j = 0; j < field.length; j++) {
                        map.put(firstField[j], field[j]);
                    }
                    result.add(map);
                }
            }
        } catch (IOException e) {
        }
        return result;
    }

    @Test
    public void test1() throws Exception {
        String name = "C:\\Users\\hy\\Desktop\\dataModel.csv";
        byte[] content = new byte[1024];
        new FileInputStream(name).read(content, 0, 1024);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(name, content);
        Charset charset = detectCharset(mockMultipartFile);
        List<Map<String, Object>> data = uploadCsv(mockMultipartFile);
        System.out.println(data);
        System.out.println(charset);
        File file = new File(name);
        InputStream in = new java.io.FileInputStream(file);
        byte[] b = new byte[3];
        in.read(b);
        in.close();
        if (b[0] == -17 && b[1] == -69 && b[2] == -65)
            System.out.println(file.getName() + "：编码为UTF-8");
        else
            System.out.println("GBK");
    }

    @Test
    public void readCSV() {
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
                System.out.println("------------>" + cell);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
