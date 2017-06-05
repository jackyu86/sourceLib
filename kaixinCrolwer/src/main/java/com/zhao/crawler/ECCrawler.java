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
		String cookie = "S3rD_2132_noticeTitle=1; S3rD_2132_saltkey=YrWLr6qH; S3rD_2132_lastvisit=1496395967; S3rD_2132_st_p=0%7C1496399567%7Cae6fef01ecb54ab29b4c65528c8416eb; S3rD_2132_visitedfid=44; S3rD_2132_viewid=tid_164; jiathis_rdc=%7B%22http%3A//kaixin65.com/forum.php%3Fmod%3Dviewthread%26tid%3D164ighlight%3D%25C2%25ED%25BC%25D2%25B1%25A4%22%3A%220%7C1496398586073%22%7D; S3rD_2132_smile=1D1; Hm_lvt_63b8f8f4895a04769ec9d88bfd02f566=1496359442,1496382108,1496387415,1496398411; Hm_lpvt_63b8f8f4895a04769ec9d88bfd02f566=1496398588; S3rD_2132_sendmail=1; S3rD_2132_nofocus_forum=1; S3rD_2132_adclose_5=1; S3rD_2132_sid=q2V2Co; S3rD_2132_lastact=1496399611%09misc.php%09seccode; S3rD_2132_seccode=202.db2f2d4b7f6c3a4fab";
		requestConfig.setCookie(cookie);
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
