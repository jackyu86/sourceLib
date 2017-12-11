/*
 * Copyright (C) 2015 zhao
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.zhao.crawler.util;

import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 * @author <a href="ls.zhaoxiangyu@gmail.com">zhao</>
 * @date 2015-10-23
 */
public class Tools {
	
	/**
	 * 判断字符窜是否等于null、"","  ","null"
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return StringUtils.isBlank(str)||"null".equals(str);
	}
	
	/**
	 * 判断字符窜是否不等于null、"","  ","null"
	 * 
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str){
		return !StringUtils.isBlank(str)&&!"null".equals(str);
	}
	
}
