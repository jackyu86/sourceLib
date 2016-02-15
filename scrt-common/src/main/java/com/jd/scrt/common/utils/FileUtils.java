package com.jd.scrt.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ---------------------------------------------------------------
 * @copyright Copyright 2014-2016  JR.JD.COM  All Rights Reserved
 * -----------------------------------------------------------------
 * 项目名称:  scrt
 * <p/>
 * 类名称:    com.jd.scrt.common.utils.FileUtils
 * 功    能:  文件操作工具类
 * -----------------------------------------------------------------
 * 创建人:    Yay
 * 创建时间:   2016/2/3 19:36
 * 版本号:    1.0
 * <p/>
 * 修改人:    Yay
 * 修改时间:   2016/2/3 19:36
 * 版本号:    1.0
 * <p/>
 * 复审人：
 * 复审时间：
 * -------------------------------------------------------------
 */
public class FileUtils extends org.apache.commons.io.FileUtils {


    /**
     * 写文件
     * @param folderPath 文件夹路径
     * @param fileName   文件名
     * @param data       写入的数据
     * @param separator  分隔符
     * @param encoding   编码方式
     */
    public static void writeFile(String folderPath, String fileName, List<String[]> data, String separator, String encoding) throws Exception {

        FileOutputStream out = null;
        OutputStreamWriter streamWriter = null;
        BufferedWriter writer = null;

        File file = null;
        StringBuilder builder = null;
        String path = "";
        try {
            file = new File(folderPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            if (folderPath.lastIndexOf('/') == folderPath.length() || folderPath.lastIndexOf('\\') == folderPath.length()) {
                builder = new StringBuilder(folderPath).append(fileName);
            } else {
                builder = new StringBuilder(folderPath).append(File.separator).append(fileName);
            }

            path = builder.toString();

            out = new FileOutputStream(path);

            streamWriter = new OutputStreamWriter(out, encoding);

            writer = new BufferedWriter(streamWriter);

            for (String[] line : data) {
                writer.write(assembleString(line, separator));
                writer.newLine();
            }
            writer.flush();

        } finally {

            file = null;
            builder = null;
            path = null;

            if (out != null) {
                out.close();
            }
            if (streamWriter != null) {
                streamWriter.close();
            }
            if (writer != null) {
                writer.close();
            }
        }

    }

    /**
     * 读文件
     * @param filePath  文件路径
     * @param rows      第几行开始读 从1开始
     * @param separator 分隔符
     * @param encoding  编码格式
     * @return List<String[]>
     */
    public static List<String[]> readFile(String filePath, int rows, String separator, String encoding) throws Exception {

        FileInputStream in = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;

        String line = "";
        List<String[]> list = null;
        StringBuilder builder = null;
        String regex = "";

        try {

            builder = new StringBuilder("\\").append(separator);

            regex = builder.toString();

            list = new ArrayList<String[]>();

            in = new FileInputStream(new File(filePath));

            streamReader = new InputStreamReader(in, encoding);

            reader = new BufferedReader(streamReader);

            if (reader.ready()) {

                int count = 1;
                while ((line = reader.readLine()) != null) {

                    if (count >= rows) {
                        list.add(regexString(line, regex));
                    }
                    count++;
                }
            }

        } finally {

            if (in != null) {
                in.close();
            }
            if (streamReader != null) {
                streamReader.close();
            }
            if (reader != null) {
                reader.close();
            }
            line = null;
            builder = null;
            regex = null;
        }

        return list;
    }

    public static String assembleString(String[] line, String separator) {

        StringBuilder builder = new StringBuilder();

        for (String item : line) {
            builder.append(item).append(separator);
        }
        builder.deleteCharAt(builder.lastIndexOf(separator));
        return builder.toString();
    }

    public static String[] regexString(String line, String regex) {

        return line.split(regex);
    }


}
