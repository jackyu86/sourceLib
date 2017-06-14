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
package com.zhao.crawler.jd;

import cn.edu.hfut.dmic.webcollector.model.Page;
import com.zhao.crawler.GoodsList;
import com.zhao.crawler.util.HttpURLConnectionUtil;
import com.zhao.crawler.util.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;

/**
 * 
 * 
 * @author <a href="ls.zhaoxiangyu@gmail.com">zhao</>
 * @date 2015-10-23
 */
public class LoginContentList extends GoodsList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7487110223660262262L;

	@Override
	public void addGoods(Page page) {
		WebDriver driver = null;
		try {
			System.out.println(page.getResponse().getContent());



			String selector = "root";

			//li.premium-pricecube
			driver = PageUtils.getWebDriver(page);
			List<WebElement> eles = driver.findElements(By.cssSelector(selector));

			
			if (!eles.isEmpty()) {
				for (WebElement ele : eles) {
				

					System.out.println(" ### " + ele.getText());
//					// 商品链接
//					g.setUrl(ele.findElement(By.className("p-name"))
//							.findElement(By.tagName("a"))
//							.getAttribute("href"));
//					// 评价
//					String commitStr = ele
//							.findElement(By.className("p-commit"))
//							.findElement(By.tagName("a"))
//							.getText();
//					if (Tools.notEmpty(commitStr)) {
//						commitStr ="100";
//						g.setCommit(Integer.parseInt(commitStr));
//					} else {
//						g.setCommit(-1);
//					}
		
				}
			} else {
				System.out.println("else is empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}
}
