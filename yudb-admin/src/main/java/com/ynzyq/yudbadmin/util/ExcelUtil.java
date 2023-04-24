package com.ynzyq.yudbadmin.util;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author xinchen
 * @date 2022/5/23 10:37
 * @description:
 */
public class ExcelUtil {
    public static OutputStream getOutPutStream(String fileName, HttpServletResponse httpServletResponse) {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream outputStream = null;
        try {
            outputStream = httpServletResponse.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为白色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //边框
//        headWriteCellStyle.setBorderBottom(BorderStyle.NONE);
//        headWriteCellStyle.setBorderLeft(BorderStyle.NONE);
//        headWriteCellStyle.setBorderRight(BorderStyle.NONE);
//        headWriteCellStyle.setBorderTop(BorderStyle.NONE);
        //自动换行
//        headWriteCellStyle.setWrapped(true);
        WriteFont headWriteFont = new WriteFont();
//        headWriteFont.setBold(true);
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short)12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
//        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
//        // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        //边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.NONE);
        contentWriteCellStyle.setBorderLeft(BorderStyle.NONE);
        contentWriteCellStyle.setBorderRight(BorderStyle.NONE);
        contentWriteCellStyle.setBorderTop(BorderStyle.NONE);
//        //自动换行
//        contentWriteCellStyle.setWrapped(true);
//        //文字
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)12);
        contentWriteFont.setFontName("宋体");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

}
