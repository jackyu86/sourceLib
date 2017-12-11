package com.jd.scrt.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

	private static ClassLoader classLoader = PropertiesUtils.class.getClassLoader();

	/**
	 * 在class路径下读取文件
	 *
	 * @param clazzPathFile
	 * @return
	 */
	public static Properties readClazzPath(String clazzPathFile) {
		Properties properties = new Properties();
		InputStream is = classLoader.getResourceAsStream(clazzPathFile);
		try {
			properties.load(is);
		} catch (Exception cause) {
			new RuntimeException("加载文件发生异常,文件:" + clazzPathFile, cause);
		} finally {
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}

		return properties;
	}


}
