//package com.alison.Utils;
//
//import com.google.common.collect.Maps;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//public class ExcelUtils {
//
//    private final static String excel2003L = ".xls"; // 2003- 版本的excel
//    private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel
//
//    /**
//     * 导出数据
//     *
//     * @param list     表头map
//     * @param dataList 表格数据
//     */
//    public static void exportXlsx(OutputStream outputStream, String sheetName, List<String> list, List<Map<String, Object>> dataList) {
//
////        Workbook workbook = exportXlsx(sheetName, list, dataList);
//
//        try {
////            workbook.write(outputStream);
//        } catch (Exception e) {
//            log.error("导出数据失败", e);
//        } finally {
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    log.error("导出数据失败", e);
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 导出数据
//     *
//     * @param dataList
//     */
//    public static Workbook exportXlsx(String sheetName, List<String> list, List<Map<String, Object>> dataList) {
//
//        Workbook workbook = new XSSFWorkbook();
//
//        Sheet sheet = workbook.createSheet(sheetName);
//
//        DataFormat fmt = workbook.createDataFormat();
//        CellStyle textStyle = workbook.createCellStyle();
//        textStyle.setDataFormat(fmt.getFormat("@"));
//
//
//        int rowIndex = 0, columnIndex = 0;
//
//        //表头
//        Row row = sheet.createRow(rowIndex++);
//        for (String fieldName : list) {
//            sheet.setDefaultColumnStyle(columnIndex, textStyle);
//            Cell cell = row.createCell(columnIndex++);
//            cell.setCellValue(fieldName);
//        }
//
//        //内容
//        if (dataList != null && !dataList.isEmpty()) {
//            for (Map<String, Object> map : dataList) {
//                row = sheet.createRow(rowIndex++);
//                columnIndex = 0;
//                for (String key : list) {
//                    Cell cell = row.createCell(columnIndex++);
//                    setCellValue(cell, map.get(key));
//                }
//            }
//        }
//
//        return workbook;
//    }
//
//    private static void setCellValue(Cell cell, Object obj) {
//        if (obj == null) {
//            return;
//        }
//        if (obj instanceof String) {
//            cell.setCellValue((String) obj);
//        } else if (obj instanceof Double) {
//            cell.setCellValue((Double) obj);
//        } else {
//            cell.setCellValue(obj.toString());
//        }
//    }
//
////    public static File multipartFileToFile(MultipartFile file) {
////
////        File toFile = null;
////        if (file.equals("") || file.getSize() <= 0) {
////            file = null;
////        } else {
////            InputStream ins = null;
////            try {
////                ins = file.getInputStream();
////                toFile = new File(file.getOriginalFilename());
////                inputStreamToFile(ins, toFile);
////                ins.close();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////
////        }
////        return toFile;
////    }
////
////    //获取流文件
////    private static void inputStreamToFile(InputStream ins, File file) {
////        try {
////            OutputStream os = new FileOutputStream(file);
////            int bytesRead = 0;
////            byte[] buffer = new byte[8192];
////            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
////                os.write(buffer, 0, bytesRead);
////            }
////            os.close();
////            ins.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    public static List<Map<String, Object>> uploadExcel(MultipartFile file) {
//        try {
//            return parseExcel(file.getInputStream(), file.getOriginalFilename());
//        } catch (Exception e) {
//            log.error("上传数据失败", e);
//        }
//        return null;
//    }
//
//    public static List<Map<String, Object>> parseExcel(InputStream in, String fileName) throws Exception {
//        // 根据文件名来创建Excel工作薄
//        Workbook work = getWorkbook(in, fileName);
//        if (null == work) {
//            throw new Exception("创建Excel工作薄为空！");
//        }
//        Sheet sheet = null;
//        Row row = null;
//        Cell cell = null;
//        // 返回数据
//        List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
//        //字段映射关系
//        Map<String, String> mapping = Maps.newHashMap();
//        // 遍历Excel中所有的sheet
//        for (int i = 0; i < work.getNumberOfSheets(); i++) {
//            sheet = work.getSheetAt(i);
//            if (sheet == null)
//                continue;
//
//            // 取第一行标题
//            row = sheet.getRow(0);
//            String title[] = null;
//            if (row != null) {
//                title = new String[row.getLastCellNum()];
//
//                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
//                    cell = row.getCell(y);
//                    title[y] = (String) getCellValue(cell);
//                    mapping.put((String) getCellValue(cell), (String) getCellValue(cell));
//                }
//
//            } else
//                continue;
////            log.info(JSON.toJSONString(title));
//
//            // 遍历当前sheet中的所有行
//            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
//                row = sheet.getRow(j);
//                if (row != null) {
//                    Map<String, Object> m = new HashMap<String, Object>();
//                    // 遍历所有的列
//                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
//                        cell = row.getCell(y);
//                        String key = title[y];
//                        // log.info(JSON.toJSONString(key));
//                        m.put(mapping.get(key), getCellValue(cell));
//                    }
//                    ls.add(m);
//                }
//            }
//
//        }
//        work.close();
//        return ls;
//    }
//
//    /**
//     * 根据文件后缀，自适应上传文件的版本
//     *
//     * @param inStr,fileName
//     */
//    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
//        Workbook wb = null;
//        String fileType = fileName.substring(fileName.lastIndexOf("."));
//        if (excel2003L.equals(fileType)) {
//            wb = new HSSFWorkbook(inStr); // 2003-
//        } else if (excel2007U.equals(fileType)) {
//            wb = new XSSFWorkbook(inStr); // 2007+
//        } else {
//            throw new Exception("解析的文件格式有误！");
//        }
//        return wb;
//    }
//
//    /**
//     * 描述：对表格中数值进行格式化
//     *
//     * @param cell
//     * @return
//     */
//    public static Object getCellValue(Cell cell) {
//        cell.setCellType(CellType.STRING);
//        Object value = cell.getRichStringCellValue().getString();
//
////        switch (cell.getCellType()) {
////            case Cell.CELL_TYPE_STRING:
////                value = cell.getRichStringCellValue().getString();
////                break;
////            case Cell.CELL_TYPE_NUMERIC:
////                Boolean isDate = DateUtil.isCellDateFormatted(cell);
////                if (isDate) {
////                    value = DateFormat.getDateTimeInstance().format(cell.getDateCellValue());
////                }else{
////                    value = cell.getNumericCellValue();
////                }
////                break;
////            case Cell.CELL_TYPE_BOOLEAN:
////                value = cell.getBooleanCellValue();
////                break;
////            default:
////                value = cell.getRichStringCellValue().getString();
////                break;
////        }
//        return value;
//    }
//
//}
