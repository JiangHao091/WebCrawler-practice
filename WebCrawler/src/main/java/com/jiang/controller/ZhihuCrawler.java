package com.jiang.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jiang.util.StringUtil;

/**
 * 知乎网站爬虫
 * @author JH
 *
 */
public class ZhihuCrawler {

	/**
	 * 爬取某个问题下所有所有回答包含的图片
	 */
	public static void Get() {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		try{
			HtmlPage page=webClient.getPage("https://www.zhihu.com/"); // 解析获取页面
			File txt = new File("e:\\WebCrawler/html.txt");
			FileOutputStream os = new FileOutputStream(txt);
			System.out.println(page.asXml());
			List<DomElement> imgs = page.getElementsByTagName("noscript");
			for(DomElement img : imgs){
				img = (DomElement) img.getElementsByTagName("img").get(0);
				URL url = null;
				if(StringUtil.isNotEmpty(img.getAttribute("data-original"))) {
					url = new URL(img.getAttribute("data-original"));
				}else {
					url = new URL(img.getAttribute("src"));
				}
	            // 打开网络输入流
	            DataInputStream dis = new DataInputStream(url.openStream());
	            File file = new File("e:\\WebCrawler/"+System.currentTimeMillis() + ".jpg");
	            // 建立一个新的文件
	            FileOutputStream fos = new FileOutputStream(file);
	            byte[] buffer = new byte[1024];
	            int length;
	            // 开始填充数据
	            while ((length = dis.read(buffer)) > 0) {
	                fos.write(buffer, 0, length);
	            }
	            dis.close();
	            fos.close();
			}
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
