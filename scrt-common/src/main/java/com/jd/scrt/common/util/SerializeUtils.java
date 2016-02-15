package com.jd.scrt.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * 对象序列化工具类
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * @since 1.0.6
 * 
 */
public class SerializeUtils {

	private static final Logger logger = Logger.getLogger(SerializeUtils.class);

	/**
	 * JDK对象序列化
	 * Created by wangjunlei on 2016-01-24 17:19:50.
	 * @param obj
	 * @return
	 */
	public static byte[] serialize(Object obj) throws Exception {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.error("serialize-error:", e);
		} finally {
			if(baos != null){
				baos.close();
			}
			if(oos != null){
				oos.close();
			}
		}
		return null;
	}

	/**
	 * JDK对象反序列化
	 * Created by wangjunlei on 2016-01-24 17:19:50.
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			logger.error("unserialize-error:", e);
		} finally{
			if(bais != null){
				bais.close();
			}
			if(ois != null){
				ois.close();
			}
		}
		return null;
	}

}
