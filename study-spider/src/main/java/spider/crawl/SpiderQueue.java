package spider.crawl;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class SpiderQueue {

	private static Set<String> visitedUrl = new HashSet<String>();

	/**
	 * 添加到访问过的 URL 队列中
	 */
	public void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	/**
	 * 移除访问过的 URL
	 */
	public void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	/**
	 * 获得已经访问的 URL 数目
	 */
	public int getVisitedUrlNum() {
		return visitedUrl.size();
	}
	
	/**
	  * 待访问的url集合，即unVisited表
	  */
	 private static Queue<String> unVisitedUrl = new LinkedBlockingQueue<String>();
	 
	 /**
	  * 获得UnVisited队列
	  */
	 public Queue<String> getUnVisitedUrl() {
	  return unVisitedUrl;
	 }
	 /**
	  * 未访问的unVisitedUrl出队列
	  */
	 public String unVisitedUrlDeQueue() {
	  return unVisitedUrl.poll();
	 }
	 /**
	  * 保证添加url到unVisitedUrl的时候每个 URL只被访问一次
	  */
	 public void addUnvisitedUrl(String url) {
	  if (url != null && !url.trim().equals("") && !visitedUrl.contains(url)
	    && !unVisitedUrl.contains(url))
	   unVisitedUrl.add(url);
	 }
	 /**
	  * 判断未访问的 URL队列中是否为空
	  */
	 public boolean unVisitedUrlsEmpty() {
	  return unVisitedUrl.isEmpty();
	 }
}


