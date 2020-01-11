package com.alison.charset;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.Charset;

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

}
