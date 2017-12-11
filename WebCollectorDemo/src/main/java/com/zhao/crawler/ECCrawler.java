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
