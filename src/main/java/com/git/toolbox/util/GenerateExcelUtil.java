package com.git.toolbox.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by poan on 2017/07/27.
 * 生成报表工具类
 */
public class GenerateExcelUtil {


    public static void main(String[] args) throws IOException {
        List<String> title = Collections.singletonList("test");
        ImmutableMap<String, List> map = ImmutableMap.<String, List>builder()
                .put("姓名", ImmutableList.builder().add("驱蚊器柔柔弱弱").add("啊啊啊时代")
                        .add("啊啊啊时代").add("啊啊啊时代").add("啊啊啊时代")
                        .add("阿萨德").add("企鹅").add("企鹅").add("企鹅")
                        .build())
                .put("部门", ImmutableList.builder().add("  ").add("  ")
                        .add("  ").add("  ").add("  ")
                        .add("undefined").add("  ").add("  ").add("  ")
                        .build())
                .build();
        HSSFWorkbook testExcel = generateExcel("testExcel", map);
        testExcel.write(new FileOutputStream("D:\\testExcel.xls"));
    }

    public static HSSFWorkbook generateExcel(String title, Map<String, List> excelMap) {

        List<String> rowList = new ArrayList<>();
        List<List> colList = new ArrayList<>();

        // 由于写入excel时，需要逐行写入；但是逻辑逐列写入较为方便，这里需要行列转换一下
        for (Map.Entry<String, List> entry : excelMap.entrySet()) {
            String key = entry.getKey();
            rowList.add(key);
            List value = entry.getValue();
            colList.add(value);
        }

        List<List> transferList = new ArrayList<>(rowList.size());

        int loop = 0;
        // 矩阵转换
        int size = colList.get(0).size();
        while (loop <= size) {
            List<Object> middleList = new ArrayList<>();
            for (List list : colList) {
                for (int i = loop; i < list.size(); i++) {
                    Object o = list.get(i);
                    middleList.add(o);
                    break;
                }
            }
            transferList.add(middleList);
            loop++;
        }

        colList = transferList;

        HSSFWorkbook workbook = new HSSFWorkbook();                        // 创建工作簿对象
        HSSFSheet sheet = workbook.createSheet(title);                    // 创建工作表

        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle = rowm.createCell(0);

        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
        HSSFCellStyle style = getStyle(workbook);                    //单元格样式对象
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowList.size() - 1)));
        cellTiltle.setCellStyle(columnTopStyle);
        cellTiltle.setCellValue(title);

        // 定义所需列数
        int columnNum = rowList.size();
        HSSFRow rowRowName = sheet.createRow(1);                // 在索引2的位置创建行(最顶端的行开始的第二行)

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(rowList.get(n));
            cellRowName.setCellValue(text);                                    //设置列头单元格的值
            cellRowName.setCellStyle(columnTopStyle);                        //设置列头单元格样式
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将查询出的数据设置到sheet对应的单元格中
        for (int i = 0; i < colList.size(); i++) {

            List obj = colList.get(i);//遍历每个对象
            HSSFRow row = sheet.createRow(i + 2);//创建所需的行数
            for (int j = 0; j < obj.size(); j++) {
                HSSFCell cell = null;   //设置单元格的数据类型
                cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                if (StringUtils.isNotEmpty(obj.get(j).toString())) {
                    if (obj.get(j) instanceof Date) {
                        Date currentTime = new Date();
                        String dateString = formatter.format(currentTime);
                        cell.setCellValue(dateString);
                    } else {
                        cell.setCellValue(obj.get(j).toString());                        //设置单元格的值
                    }

                }
                cell.setCellStyle(style);                                    //设置单元格样式
            }
        }

        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0) {
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }
        return workbook;
    }

    private static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /*
   * 列数据信息单元格样式
   */
    private static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

}
