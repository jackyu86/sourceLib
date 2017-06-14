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

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.zhao.crawler.ECCrawler;
import com.zhao.crawler.Goods;

/**
 *JD 爬虫
 *
 * @author <a href="ls.zhaoxiangyu@gmail.com">zhao</>
 * @date 2015-10-20
 */
public class LoginCrawler extends ECCrawler {

	private LoginContentList goodsList;

	/**
	 *
	 *
	 * @param crawlPath
	 * @param seekFormat
	 */
	public LoginCrawler(String crawlPath, String seekFormat) {
		super(crawlPath, seekFormat);
		goodsList=new LoginContentList();
	}

	@Override
	public int getTotalPage(Page page) {
//		Element ele=page.getDoc().select("div#J_bottomPage").select("span.p-skip >em").first().select("b").first();
//		return ele==null?0:Integer.parseInt(ele.text());
		return 1;
	}

	@Override
	public void visit(Page page, Links links) {
		System.out.println("url:"+page.getUrl()+"\tlinks size:"+links.size());
		goodsList.addGoods(page);
	}
	
	public static void main(String[] args) throws Exception {

		String url ="http://www.kaixin65.com/member.php?mod=logging&action=login&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login";
	//	JDCrawler crawler=new JDCrawler("c:/nevi/test/crawler/jd/", "http://list.jd.com/list.html?cat=1319,1523,7052&page=%s&go=0&JL=6_0_0");
		LoginCrawler crawler=new LoginCrawler("c:/nevi/test/crawler/jd/", url);
		crawler.setThreads(1);//抓取启动线程数
		crawler.start(1);//层数
		
		crawler.print();

	}
	
	protected void print(){
		for(Goods g:goodsList){
			System.out.println(g);
		}
	}
}
