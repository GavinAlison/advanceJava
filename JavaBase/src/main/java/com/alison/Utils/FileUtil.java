package com.alison.Utils;

import com.csvreader.CsvWriter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author peidong.meng
 * @date 2018/11/13
 */
@Slf4j
public class FileUtil {

    private static final String UTF8 = "UTF-8";

    /**
     * 缓存文件数据
     * key为存入时生成的时间戳
     * value为字符串形式的文件名称+文件内容
     */
    private static TimeMap<String, Map<String,String>> fileData = new TimeMap<>();
    /**
     * 缓存上传校验后的数据
     */
    private static TimeMap<String, String> checkData = new TimeMap<>();


    public static Boolean containsFileData(String key){
        return fileData.containsKey(key);
    }

    public static String setFileData(Map<String,String> data){
        String id = UUID.randomUUID().toString().replaceAll("-","");
        fileData.put(id,data);

        return id;
    }

    public static  Map<String,String> getFileData(String key){
        return fileData.get(key);
    }

    public static void removeFileData(String key){
        fileData.remove(key);
    }

    public static String getCheckData(String key){
        String data = checkData.get(key);

       // checkData.remove(key);
        return data;
    }

    public static void setCheckData(String key, String value){
        checkData.put(key,value);
    }

    /**
     * 下载单个文件
     * @param response
     * @param data
     */
    public static void downloadOne(HttpServletResponse response, Map<String,String> data){
        BufferedOutputStream buff = null;

        try {
            setFileHeader(response);

            for(Map.Entry<String,String> entry : data.entrySet()) {

                response.setHeader("Content-Disposition", "attachment;filename=" + getFileName(entry.getKey()));

                buff = new BufferedOutputStream(response.getOutputStream());
                buff.write(getCrypt(entry.getValue()).getBytes(StandardCharsets.UTF_8));
                buff.flush();
                break;
            }
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }finally{
            try {
                if(buff != null){
                    buff.close();
                }
            }catch (IOException ex){

            }
        }
    }

    /**
     * 下载zip文件
     * @param response
     * @param data
     * @param zipName
     */
    public static void downloadZip(HttpServletResponse response, Map<String,String> data, String zipName){

        ZipOutputStream zipOut = null;
        ByteArrayInputStream bin = null;
        try {
            setFileHeader(response);
            response.setHeader("Content-Disposition", "attachment;filename=" + getFileName(zipName));

            zipOut = new ZipOutputStream(response.getOutputStream());

            for(Map.Entry<String,String> entry : data.entrySet()) {

                //bin = new ByteArrayInputStream(getCrypt(entry.getValue()).getBytes(UTF8));
                bin = new ByteArrayInputStream(entry.getValue().getBytes(StandardCharsets.UTF_8));
                zipOut.putNextEntry(new ZipEntry(entry.getKey()));

                byte[] buf = new byte[1024];
                int len;
                while ( (len = bin.read(buf)) > 0) {
                    zipOut.write(buf, 0, len);
                }
                bin.close();
            }
            response.flushBuffer();

        }catch (IOException e){
            log.error(e.getMessage(), e);
        }finally{
            try {
                if(bin != null){
                    bin.close();
                }
                if(zipOut != null){
                    zipOut.closeEntry();
                    zipOut.close();
                }
            }catch (IOException ex){

            }
        }
    }


    /**
     * 下载zip文件
     * @param data
     */
    public static void downloadZip(FileOutputStream outputStream, Map<String,String> data){

        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(outputStream);
            for(Map.Entry<String,String> entry : data.entrySet()) {
                zipOut.putNextEntry(new ZipEntry(entry.getKey()));
                    zipOut.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
            }

        }catch (IOException e){
            log.error(e.getMessage(), e);
        }finally{
            try {
                if(zipOut != null){
                    zipOut.closeEntry();
                    zipOut.close();
                }
                if(null!=outputStream){
                    outputStream.flush();
                    outputStream.close();
                }
            }catch (IOException ex){

            }
        }
    }


    private static String getFileName(String name) throws UnsupportedEncodingException{
        return new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }

    private static void setFileHeader(HttpServletResponse response){
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static String uploadFile (MultipartFile file,String dirName,String fileName) throws Exception {

        Integer fileCount = 0;
        //设置文件存储路径
        String pathName = FileUtil.class.getClassLoader().getResource("").getPath() + dirName+"/";

        //判断文件夹是否存在
        File f = new File(pathName);
        if (!f.exists()) {
            f.mkdirs();
        }

        File fileToLocal = new File(pathName + fileName);
        file.transferTo(fileToLocal);

//        List<Object> fileContent = ExcelUtil.loadExcel(fileToLocal,false);
        List<Object> fileContent = Collections.emptyList();

        for(int i=0;i<fileContent.size();i++){
            fileCount++;
        }

        return fileName+"_"+fileCount;
    }

    /**
     * 上传zip文件
     * @param file
     * @return
     */
    public static List<String> uploadZip(MultipartFile file,String fileEnd){

        List<String> result = Lists.newArrayList();
        ZipInputStream zipIn = null;
        BufferedInputStream bs = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if(!file.getOriginalFilename().toLowerCase().endsWith(Constants.ZIP_FILE)){
            throw new RuntimeException("文件格式错误！");
        }

        try {

            zipIn = new ZipInputStream(file.getInputStream(), Charset.forName(UTF8));
            bs = new BufferedInputStream(zipIn);
            ZipEntry zipEntry;
            byte[] bytes = null;
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                if( !zipEntry.getName().endsWith(fileEnd)){
                    throw new RuntimeException("文件格式错误");
                }

                long size = zipEntry.getSize();
                if (size == -1) {
                    baos.reset();
                    while (true) {
                        int data = zipIn.read();
                        if (data == -1) {
                            break;
                        }
                        baos.write(data);
                    }
                    result.add(getDecrypt(new String(baos.toByteArray(), StandardCharsets.UTF_8)));
                } else {
                    bytes = new byte[(int) zipEntry.getSize()];
                    zipIn.read(bytes, 0, (int) zipEntry.getSize());
                    result.add(getDecrypt(new String(bytes, StandardCharsets.UTF_8)));
                }
            }
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }finally {
            try {
                if (zipIn != null) {
                    zipIn.close();
                }
                if (bs != null){
                    bs.close();
                }
                baos.close();
            }catch (IOException e){

            }
        }

        return result;
    }

    /**
     * 上传zip
     * @param file
     * @return
     */
    public static Map<String,List<String>> uploadZip(MultipartFile file){

        List<String> functionList = Lists.newArrayList();
        List<String> eventList = Lists.newArrayList();
        List<String> modelList = Lists.newArrayList();
        List<String> dataList = Lists.newArrayList();

        Map<String,List<String>> result = Maps.newHashMap();

        ZipInputStream zipIn = null;
        BufferedInputStream bs = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if(!file.getOriginalFilename().toLowerCase().endsWith(Constants.ZIP_FILE)){
            throw new RuntimeException("文件格式错误！");
        }

        try {
            zipIn = new ZipInputStream(file.getInputStream(), Charset.forName(UTF8));
            bs = new BufferedInputStream(zipIn);
            ZipEntry zipEntry;
            byte[] bytes = null;
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                long size = zipEntry.getSize();
                if (size == -1) {
                    baos.reset();
                    while (true) {
                        int data = zipIn.read();
                        if (data == -1) {
                            break;
                        }
                        baos.write(data);
                    }
                    //暂时不解密，导入时未加密
                    if(zipEntry.getName().endsWith(Constants.FUNCTION_FILE)) {
                        functionList.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.EVENT_OBJECT_FILE)){
                        eventList.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.MODEL_OBJECT_FILE)){
                        modelList.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.DATA_OBJECT_FILE)){
                        dataList.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }
                } else {
                    bytes = new byte[(int) zipEntry.getSize()];
                    zipIn.read(bytes, 0, (int) zipEntry.getSize());
                    //暂时不解密，导入时未加密
                    if(zipEntry.getName().endsWith(Constants.FUNCTION_FILE)) {
                        functionList.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.EVENT_OBJECT_FILE)){
                        eventList.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.MODEL_OBJECT_FILE)){
                        modelList.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.DATA_OBJECT_FILE)){
                        dataList.add(new String(bytes, StandardCharsets.UTF_8));
                    }
                }
            }
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }finally {
            try {
                if (zipIn != null) {
                    zipIn.close();
                }
                if (bs != null){
                    bs.close();
                }
                baos.close();
            }catch (IOException e){
                log.debug("上传失败:{}",e);
            }
        }

        result.put("ResourceType.FUNCTION",functionList);
        result.put("ResourceType.EVENT_OBJECT",eventList);
        result.put("ResourceType.MODEL_OBJECT",modelList);
        result.put("ResourceType.DATA_OBJECT",dataList);
        return result;
    }

    /**
     * 上传zip
     * @param file
     * @return
     */
    public static Map<String,List<String>> uploadResourceZip(MultipartFile file){

        List<String> rule = Lists.newArrayList();
        List<String> ruleSet = Lists.newArrayList();
        List<String> ruleTree = Lists.newArrayList();
        List<String> scoreCard = Lists.newArrayList();
        List<String> script = Lists.newArrayList();
        List<String> matrix = Lists.newArrayList();
        List<String> strategy = Lists.newArrayList();
        List<String> resource = Lists.newArrayList();

        Map<String,List<String>> result = Maps.newHashMap();

        ZipInputStream zipIn = null;
        BufferedInputStream bs = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if(!file.getOriginalFilename().toLowerCase().endsWith(Constants.ZIP_FILE)){
            throw new RuntimeException("文件格式错误！");
        }

        try {
            zipIn = new ZipInputStream(file.getInputStream(), Charset.forName(UTF8));
            bs = new BufferedInputStream(zipIn);
            ZipEntry zipEntry;
            byte[] bytes = null;
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                long size = zipEntry.getSize();
                if (size == -1) {
                    baos.reset();
                    while (true) {
                        int data = zipIn.read();
                        if (data == -1) {
                            break;
                        }
                        baos.write(data);
                    }
                    //暂时不解密，导入时未加密
                    if(zipEntry.getName().endsWith(Constants.RULE_FILE)) {
                        rule.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.RULE_SET_FILE)){
                        ruleSet.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.RULE_TREE_FILE)){
                        ruleTree.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.SCORE_CARD_FILE)){
                        scoreCard.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.SCRIPT_FILE)){
                        script.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.MATRIX_FILE)){
                        matrix.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.STRATEGY_FILE)){
                        strategy.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.RESOURCE_FILE)){
                        resource.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
                    }
                } else {
                    bytes = new byte[(int) zipEntry.getSize()];
                    zipIn.read(bytes, 0, (int) zipEntry.getSize());
                    //暂时不解密，导入时未加密
                    if(zipEntry.getName().endsWith(Constants.RULE_FILE)) {
                        rule.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.RULE_SET_FILE)){
                        ruleSet.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.RULE_TREE_FILE)){
                        ruleTree.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.SCORE_CARD_FILE)){
                        scoreCard.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.SCRIPT_FILE)){
                        script.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.MATRIX_FILE)){
                        matrix.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.STRATEGY_FILE)){
                        strategy.add(new String(bytes, StandardCharsets.UTF_8));
                    }else if (zipEntry.getName().endsWith(Constants.RESOURCE_FILE)){
                        strategy.add(new String(bytes, StandardCharsets.UTF_8));
                    }
                }
            }
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }finally {
            try {
                if (zipIn != null) {
                    zipIn.close();
                }
                if (bs != null){
                    bs.close();
                }
                baos.close();
            }catch (IOException e){
                log.debug("上传失败: {}",e);
            }
        }

        result.put("ResourceType.RULE",rule);
        result.put("ResourceType.RULESET",ruleSet);
        result.put("ResourceType.RULETREE",ruleTree);
        result.put("ResourceType.SCORECARD",scoreCard);
        result.put("ResourceType.SCRIPT",script);
        result.put("ResourceType.MATRIX",matrix);
        result.put("ResourceType.STRATEGY",strategy);
        //暂时用事件对象类型，代表所有的资源类型
        result.put("ResourceType.EVENT_OBJECT",resource);

        return result;
    }

    private static String getCrypt(String data){

        return TokenAES.encrypt(data.replaceAll("-","@.@"));
    }

    private static String getDecrypt(String data){

        return TokenAES.decrypt(data).replaceAll("@.@","-");
    }

    /**
     * 删除单个文件
     *
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile( File file) {
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 导出csv文件
     * @param data 要导出的数据
     */
    public static void downloadCsv(HttpServletResponse response, List<String> data, String name){

        //创建临时csv文件
        File tempFile;
        CsvWriter csvWriter = null;
        try {
            tempFile = File.createTempFile(name, ".csv");
            csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("UTF-8"));
            //写表头
            /*String[] headers = {""};
            csvWriter.writeRecord(headers);*/
            //使用双中划线分隔
            //日期数据中存在单中划线，部分数据中可能存在逗号
            for (String da : data){
                String[] dd = da.split("--");
                for(String d : dd){
                    csvWriter.write(d);
                }

                csvWriter.endRecord();
            }

        } catch (IOException e) {
            throw new RuntimeException("数据读取异常");
        } finally {
            if (csvWriter != null){
                csvWriter.close();
            }
        }

        //输出csv流文件，提供给浏览器下载
        OutputStream out = null;
        FileInputStream in = null;
        File fileLoad;
        try {
            out = response.getOutputStream();
            byte[] b = new byte[10240];
            fileLoad = new File(tempFile.getCanonicalPath());
            response.reset();
            response.setContentType("application/csv");
            String fileName = new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.setHeader("content-disposition", "attachment; filename="+fileName+".csv");
            in = new FileInputStream(fileLoad);
            int n;
            //为了保证excel打开csv不出现中文乱码
            out.write(new byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });
            while ((n = in.read(b)) != -1) {
                //每次写入out1024字节
                out.write(b, 0, n);
            }
        } catch (IOException e) {
            log.error("写入数据失败,{}",e);
            throw new RuntimeException("写入数据失败");
        } finally {
            try {
                if (in != null){
                    in.close();
                }
            } catch (IOException e) {
                log.debug("下载失败,{}",e);
            }
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                log.debug("下载失败,{}",e);
            }
        }
        //删除临时文件
        deleteFile(tempFile);
    }

    /**
     * 上传csv文件
     * @param file
     * @return
     */
    public static List<Map<String,Object>> uploadCsv(MultipartFile file){

        List<Map<String,Object>> result = Lists.newArrayList();
        String[] firstField = null;

        InputStreamReader ir = null;
        BufferedReader br = null;

        try {
            // 判断文件流的编码格式
            Charset charset = detectCharset(file);
            ir = new InputStreamReader(file.getInputStream(), charset);
            br = new BufferedReader(ir);
            String fields;
            int i = 0;
            while ((fields = br.readLine()) != null){
                String[] field = fields.split(",");
                if (i == 0){
                    if(field[0].startsWith("\uFEFF")){
                        field[0] = field[0].replaceFirst("\uFEFF","");
                    }
                    firstField = field;
                    i++;
                }else {
                    Map<String,Object> map = Maps.newHashMap();
                    for (int j = 0; j < field.length; j++){
                        map.put(firstField[j],field[j]);
                    }
                    result.add(map);
                }
            }
        }catch (IOException e){
            throw new RuntimeException("上传失败");
        }finally {
            try {
                if (br != null){
                    br.close();
                }
                if (ir != null){
                    ir.close();
                }
            }catch (IOException e){
                log.debug("上传失败");
            }
        }
        return result;
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
}
