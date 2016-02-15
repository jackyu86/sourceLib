package com.jd.scrt.common.utils;
/*---------------------------------------------------------------
@copyright                                                                
Copyright 2014-2016  JR.JD.COM  All Rights Reserved                                                                                  
-----------------------------------------------------------------
项目名称:  scrt-supper                                                                                                                                              
                                                                                                                      
类名称:    com.jd.scrt.common.utils.${}                                                                         
功    能:  DAO for xxxxx                                                                    
-----------------------------------------------------------------
创建人：   wangjunlei1                                                                               
创建时间： 2016/1/28 19:57
版本号：   1.0

修改人：   wangjunlei1 
修改时间： 2016/1/28 19:57
版本号：   1.0
修改原因： 

复审人：                                                 
复审时间：                                                                            				   
-------------------------------------------------------------*/


import au.com.bytecode.opencsv.CSVWriter;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;


public class ExportUtil {
    /**
     * @param fileName 文件名称
     * @param headName 头名称
     * @param writer CSVWriter
     * @param response HttpServletResponse
     * @throws IOException
     */
    public static void beforeExportCSV(String fileName, String[] headName, CSVWriter writer, HttpServletResponse response) throws IOException {
        String csvFileName = fileName + "_" + DateUtils.formatDate(new Date(), DateUtils.FORMAT_DATE) + ".csv";
        response.setHeader("Content-type", "text/csv;charset=GBK");
//        response.setContentType("application/csv;charset=gbk");
        response.setCharacterEncoding("GBK");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", csvFileName));
        writer.writeNext(headName);
    }

    public static void afterExportCSV(CSVWriter writer, OutputStreamWriter responseWriter) throws IOException {
        if (null != writer) {
            writer.close();
        }
        if (null != responseWriter) {
            responseWriter.close();
        }
    }

    private String trimNull(Object obj) {
        String str = String.valueOf(obj);
        if (str == null || str.matches("\\s*") || "null".equalsIgnoreCase(str)) {
            return "";
        }
        return str.trim();
    }
}
