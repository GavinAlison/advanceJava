package com.alison.Utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * excel文件导入导出工具类
 *
 * @author 梁慧琴
 * @date 2019/6/26
 */
@Slf4j
public class ExcelUtil {

//    private static Sheet initSheet;


    /**
     * 监听类，主要处理从Excel获取到的每行数据
     */
//    public static class ExcelListener extends AnalysisEventListener {
//
//        private List<Object> data = Lists.newArrayList();
//
//        @Override
//        public void invoke(Object object, AnalysisContext context) {
//
//            data.add(object);
//        }
//
//        @Override
//        public void doAfterAllAnalysed(AnalysisContext context) {
//
//            log.debug("解析excel文件完毕，读取行数：{}", data.size());
//        }
//
//        public List<Object> getData() {
//            return data;
//        }
//
//        public void clear(){
//            data.clear();
//        }
//    }
//    public static List<Object> loadExcel(String path) {
//        return loadExcel(new File(path), false);
//    }
//
//    public static List<Object> loadExcel(String path, Boolean head) {
//        return loadExcel(new File(path), head);
//    }
//
//    public static List<Object> loadExcel(File file) {
//        return loadExcel(file, false);
//    }

//    public static List<Object> loadExcel(File file, Boolean head) {
//        ExcelListener listener = new ExcelListener();
//        try {
//
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//            ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
//            excelReader.read(new Sheet(1, head ? 0 : 1));
//
//        } catch (FileNotFoundException e) {
////            log.error(e.getMessage(), e);
//
//            return Collections.emptyList();
//        }
//        return listener.getData();
//    }

//    public static List<Object> loadExcel(InputStream inputStream){
//        return loadExcel(inputStream,false);
//    }

//    public static List<Object> loadExcel(InputStream inputStream, Boolean head) {
//        ExcelListener listener = new ExcelListener();
//        try {
//
//            ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
//            excelReader.read(new Sheet(1, head ? 0 : 1));
//
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//
//            return Collections.emptyList();
//        }
//        return listener.getData();
//    }

//    public static List<Object> loadExcel(BufferedInputStream inputStream, Boolean head) {
//        ExcelListener listener = new ExcelListener();
//        try {
//
//            ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
//            excelReader.read(new Sheet(1, head ? 0 : 1));
//
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//
//            return Collections.emptyList();
//        }
//        return listener.getData();
//    }

//    public static List<Object> loadExcel(BufferedInputStream inputStream) {
//
//        return jrx.anyest.engine.base.utils.file.ExcelUtil.loadExcel(inputStream,false);
//    }


//    public static<R extends BaseRowModel> List<ExcelSheet<R>> loadExcelAllSheet(InputStream inputStream, Boolean head, Class<R> modelClass) {
//
//        List<ExcelSheet<R>> excelSheetList = new ArrayList<>();
//
//        ExcelListener listener = new ExcelListener();
//        try {
//            ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
//
//            List<Sheet> sheetList = excelReader.getSheets();
//
//
//            for (Sheet sheet : sheetList) {
//
//                if (CollectionUtils.isNotEmpty(listener.getData())){
//                    listener.clear();
//                }
//
//                excelReader.read(new Sheet(sheet.getSheetNo(), head ? 0 : 1, modelClass));
//
//                List<R> resultDataList = Lists.newArrayList();
//                listener.getData().forEach(value -> resultDataList.add(modelClass.cast(value)));
//
//                ExcelSheet<R> excelSheet = new ExcelSheet<>();
//                excelSheet.setFieldList(resultDataList);
//                excelSheet.setSheetNumber(sheet.getSheetNo());
//                excelSheet.setSheetName(sheet.getSheetName());
//
//                excelSheetList.add(excelSheet);
//            }
//
//            return excelSheetList;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return Collections.emptyList();
//        }
//    }

//    public static<R extends BaseRowModel> List<R> loadExcel(InputStream inputStream, Boolean head, Class<R> modelClass) {
//        ExcelListener listener = new ExcelListener();
//        try {
//            ExcelReader excelReader = new ExcelReader(inputStream, null, listener);
//            excelReader.read(new Sheet(1, head ? 0 : 1, modelClass));
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return Collections.emptyList();
//        }
//
//        List<R> resultDataList = Lists.newArrayList();
//        listener.getData().forEach(value -> resultDataList.add(modelClass.cast(value)));
//        return resultDataList;
//    }

//    public static<R extends BaseRowModel> List<R> loadExcel(File file, Boolean head, Class<R> modelClass) {
//        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))){
//            return loadExcel(inputStream,head,modelClass);
//        }catch (Exception e){
//            log.error(e.getMessage(), e);
//            return Collections.emptyList();
//        }
//    }

//    public static void writeExcel(OutputStream os, List<List<String>> data){
//        try{
//            ExcelWriter writer = new ExcelWriter(os, ExcelTypeEnum.XLSX,false);
//            writer.write0(data,new Sheet(0,0));
//            writer.finish();
//        }catch(Exception e){
//            log.error(e.getMessage(), e);
//        }
//
//    }

//    public static void writeExcelModel(OutputStream os,List<? extends BaseRowModel> data,Class<? extends BaseRowModel> clazz,String sheetName){
//        try{
//            ExcelWriter writer = new ExcelWriter(os, ExcelTypeEnum.XLSX,true);
//            Sheet sheet = new Sheet(1,0,clazz,sheetName,null);
//            writer.write(data,sheet);
//            writer.finish();
//        }catch(Exception e){
//            log.error(e.getMessage(), e);
//        }
//    }

}

