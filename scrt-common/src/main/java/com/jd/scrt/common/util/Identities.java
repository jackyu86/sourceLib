package com.jd.scrt.common.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * @since 1.0.6
 * 
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 根据参照生成UUID
	 * @return
	 */
	public static String uuid(String name){
		return UUID.nameUUIDFromBytes(name.getBytes()).toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 根据参照生成UUID, 中间无-分割.
	 * @return
	 */
	public static String uuid32(String name){
		return UUID.nameUUIDFromBytes(name.getBytes()).toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

}
