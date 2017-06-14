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
package com.zhao.crawler;

import java.util.concurrent.atomic.AtomicInteger;

import cn.edu.hfut.dmic.webcollector.crawler.DeepCrawler;
import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.net.RequestConfig;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

/**
 *电商平台爬虫
 *
 * @author <a href="ls.zhaoxiangyu@gmail.com">zhao</>
 * @date 2015-10-20
 */
public abstract class ECCrawler extends DeepCrawler {
	
	private String seedFormat;//种子格式化 
	protected RegexRule regexRule;
	
	public RegexRule getRegexRule() {
		return regexRule;
	}
	public void setRegexRule(RegexRule regexRule) {
		this.regexRule = regexRule;
	}
	public void addRegex(String urlRegex) {
		this.regexRule.addRule(urlRegex);
	}
	public ECCrawler(String crawlPath,String seedFormat ){
		super(crawlPath);
		this.seedFormat=seedFormat;
		this.regexRule=new RegexRule();
	}
	
	/*用一个自增id来生成唯一文件名*/
    AtomicInteger id=new AtomicInteger(0);
	
	@Override
	public Links visitAndGetNextLinks(Page page) {
		Links nextLinks = new Links();
		String conteType = page.getResponse().getContentType();
		if (conteType != null && conteType.contains("text/html")) {
			org.jsoup.nodes.Document doc = page.getDoc();
			if (doc != null)
				nextLinks.addAllFromDocument(page.getDoc(), regexRule);
		}
		try {
			visit(page, nextLinks);
		} catch (Exception ex) {
			LOG.info("Exception", ex);
		}
		return nextLinks;
	}
	@Override
	public void start(int depth) throws Exception {
		addContent();
		super.start(depth);
	}
	/**
	 * add seed
	 *
	 * @throws Exception
	 */
	private void addSeed() throws Exception{
		int totalPage=getTotalPage(getPage(getSeed(seedFormat, 1)));
		for(int page=1;page<=totalPage;page++){
			this.addSeed(getSeed(seedFormat, page));
		}
	}

	private void addContent() throws Exception{
		int totalPage=getTotalPage(getPage(seedFormat));
		for(int page=1;page<=totalPage;page++){
			this.addSeed(getContent(seedFormat, page));
		}
	}
	
	/**
	 * 根据url获取Page实例
	 *
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private Page getPage(String url) throws Exception {
		HttpRequest httpRequest = new HttpRequest(url);
		
		RequestConfig requestConfig=new RequestConfig();

		requestConfig.setHeader("Host", "kaixin65.com");
		requestConfig.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
		requestConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		requestConfig.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		requestConfig.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
		requestConfig.setHeader("Connection", "keep-alive");
		requestConfig.setHeader("Cookie", "yunsuo_session_verify=0b1ebbb8c7cefb1719cb8d690a1e202c; S3rD_2132_saltkey=nSll7Tsi; S3rD_2132_lastvisit=1497347758; S3rD_2132_adclose_5=1; S3rD_2132_adclose_2=1; S3rD_2132_nofocus_forum=1; S3rD_2132_seccode=127.9c68176f8c812d3f51; S3rD_2132_ulastactivity=21c4Jn3YPaag4FCvh7ZB8ZomqrKXE3AVCTwgprVr1Z8Z7wK1jkav; S3rD_2132_auth=9108nyuzYEcn4Iw%2FEB8M04skeBSjebAYq5MxaHF5yySTC3m1exjqGHRVToMbHUIhhVTSPD5xyT2yxELjKazwOWup; S3rD_2132_lastcheckfeed=4088%7C1497352880; S3rD_2132_lip=210.77.180.14%2C1497351064; S3rD_2132_lastact=1497352885%09forum.php%09; S3rD_2132_nofavfid=1; S3rD_2132_study_nge_extstyle=2; S3rD_2132_study_nge_extstyle_default=2; S3rD_2132_sid=ANNnLS; S3rD_2132_noticeTitle=1; Hm_lvt_63b8f8f4895a04769ec9d88bfd02f566=1496398890,1496659462,1496816549,1497351027; Hm_lpvt_63b8f8f4895a04769ec9d88bfd02f566=1497352846; tjpctrl=1497354646614");

		httpRequest.setRequestConfig(requestConfig);
		HttpResponse response = httpRequest.getResponse();
		Page page = new Page();
		page.setUrl(url);
		page.setHtml(response.getHtmlByCharsetDetect());
		page.setResponse(response);
		return page;
	}
	
	/**
	 *获取查询商品总页数
	 *
	 * @return
	 */
	public abstract int getTotalPage(Page page);
	
	/**
	 * 获取seed url
	 *
	 * @param seedFormat
	 * @param page
	 * @return
	 */
	public String getSeed(String seedFormat,Object ... page){
		return String.format(seedFormat, page);
	}


	public String getContent(String seedFormat,Object ... page){
		return seedFormat;
	}
	public abstract void visit(Page page, Links links);
}
