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
package com.zhao.crawler.demo;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.zhao.crawler.util.PageUtils;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 *
 *
 * @author <a href="ls.zhaoxiangyu@gmail.com">zhao</>
 * @date 2015-10-22
 */
public class DemoJSCrawler extends DeepCrawler {

	public DemoJSCrawler(String crawlPath) {
        super(crawlPath);
    }

    @Override
    public Links visitAndGetNextLinks(Page page) {
       //HtmlUnitDriver
//    	handleByHtmlUnitDriver(page);
        //PhantomJsDriver
    	handleByPhantomJsDriver(page);
        return null;
    }
    
    /**
     * webcollector自带获取html driver测试
     *
     * @param page
     */
    protected void handleByHtmlUnitDriver(Page page){
    	 /*HtmlUnitDriver可以抽取JS生成的数据*/
      HtmlUnitDriver driver=PageUtils.getDriver(page,BrowserVersion.CHROME);
      /*HtmlUnitDriver也可以像Jsoup一样用CSS SELECTOR抽取数据
        关于HtmlUnitDriver的文档请查阅selenium相关文档*/
      print(driver);
    }
    
    /**
     * phantomjs driver测试
     *
     * @param page
     */
    protected void handleByPhantomJsDriver(Page page){
    	 WebDriver driver=PageUtils.getWebDriver(page);
    	 print(driver);
    	 driver.quit();
    }
    
    protected void print(WebDriver driver){
    	List<WebElement> divInfos = driver.findElements(By.cssSelector("li.gl-item"));
        for(WebElement divInfo:divInfos){
      	  WebElement price=divInfo.findElement(By.className("J_price"));
            System.out.println(price+":"+price.getText());
        }
    }
    public static void main(String[] args) throws Exception{
        DemoJSCrawler crawler=new DemoJSCrawler("D:/test/crawler/jd/");
        crawler.addSeed("http://list.jd.com/list.html?cat=1319,1523,7052&page=1&go=0&JL=6_0_0");
        crawler.start(1);
    }

}
