package com.hxbd.clp.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hxbd.clp.domain.News;
import com.hxbd.clp.domain.NewsPic;
import com.hxbd.clp.service.NewsPicService;
import com.hxbd.clp.service.NewsService;
import com.hxbd.clp.utils.common.RandomUtil;
import com.hxbd.clp.utils.tag.PageModel;


/**
 * 新闻管理控制层
 * 
 * @author 恒信博大
 *
 */
@Controller
public class NewsController {
	
	//保存新闻图片路径
	private static final String SAVENEWSPICPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\newsPic";
	
	@Resource
	private NewsService newsService;
	
	@Resource
	private NewsPicService newsPicService;
	//用来存放新闻内容上传图片后的图片名
	private List<String> newContentPic = new ArrayList<String>();
	
	/**
	 * 保存新闻
	 * @param news
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="news/saveNews", method = RequestMethod.POST)
	public @ResponseBody String saveNews(News news,MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			String random = RandomUtil.getRandom15();
			// 第一步：上传文件
			String path = "/newsPic" + random  + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/newsPic/newsPic" + random + ".jpg";
			//虚拟路径，用于本地测试，需要在tomcat的serer.xml中设置虚拟路径/newcContentPic
			String picPath ="/newsPicUrl" + "/newsPic"+ random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVENEWSPICPATH + path));
			// 第二步：将文件部分路径保存到数据库
			news.setNewsPic(absolutePath);
			//news.setNewsPic(picPath);
			Integer id = newsService.addNews(news);
			NewsPic newsPic = null;
			News n = null;
			//将新闻内容里的图片路径写入数据库
			for (String contentPicUrl : newContentPic) {
				//处理用户在编写新闻时上传图片后又删除图片的情况
				//如果上传某张图片后，保存是内容中确实用到了这张图片
				if(news.getContent().contains(contentPicUrl)){
					newsPic = new NewsPic();
					n = new News();
					n.setId(id);
					newsPic.setNews(n);
					newsPic.setUrl(contentPicUrl);
					newsPicService.saveNewsPic(newsPic);
				}else{
					//上传了图片但是在新闻内容中没用到这张图片
					//删除文件夹中对应图片
					String newsContentPic = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\newContentPic";
					File oldfile = new File(newsContentPic+"\\"+contentPicUrl);
					if(oldfile!=null){
						oldfile.delete();
					}
				}
			}
			newContentPic.clear();
			
		return "{\"status\":true}";
	}else{
		return "{\"status\":false}";
		}
	}
	
	/**
	 * 新闻内容图片上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("news/uploads")
	@ResponseBody
	public String newPic(MultipartFile file) throws Exception {
		// errno 即错误代码，0 表示没有错误。
		// 如果有错误，errno != 0，可通过下文中的监听函数 fail 拿到该错误码进行自定义处理
		// data 是一个数组，返回若干图片的线上地址
		if (!file.isEmpty()) {
			String random = RandomUtil.getRandom15();
			Calendar calendar = Calendar.getInstance();
			String today = calendar.get(Calendar.YEAR)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.DAY_OF_MONTH);
			// 第一步：上传文件
			String path = "/newContentPic"+ today+"_" + random + ".jpg";
			//用于页面富文本框显示已上传的图片
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/newContentPic/newContentPic"+today+"_" + random + ".jpg";
			String picPath = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\newContentPic";
			//虚拟路径，用于本地测试，需要在tomcat的serer.xml中设置虚拟路径/newcContentPic
			String pageImaUrl = "/newContentPic"+path;
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(picPath + path));
			// 第二步：将文件部分路径保存集合
			// 去除/
			// 最终格式：newContentPic2018_8_8_193291115298182.jpg
			newContentPic.add(path.substring(1, path.length()));
			
			//String message = "{\"errno\": 0,\"data\": [\""+pageImaUrl+"\"]}";
			String message = "{\"errno\": 0,\"data\": [\""+absolutePath+"\"]}";
			return message;
		} else {
			return "{\"errno\": 1,\"data\": [\""+null+"\"]}'";
		}

	}

	/**
	 * 更新新闻
	 * 
	 * @param news
	 * @return
	 */
	@RequestMapping(value="news/updateNews", method = RequestMethod.POST)
	public @ResponseBody String updateNews(News news,MultipartFile file) throws IOException {
		if(file != null){
			//处理新闻封面
			News n = newsService.selectById(news.getId());
			String filePath = n.getNewsPic();
			//System.err.println("filePath:"+filePath);
			//String relPath = filePath.substring(44);	 	
			String relPath = "/newsPic"+filePath.split("newsPic")[2];	
			//System.err.println("relPath:"+relPath);
			String rellPath = SAVENEWSPICPATH + relPath;
			//System.err.println("rellPath"+rellPath);
			File oldfile = new File(rellPath);
			//删除旧文件
			oldfile.delete();
			
			//上传文件
			String random = RandomUtil.getRandom15();
			String path = "/newsPic" + random  + ".jpg";
			//虚拟路径
			String picPath ="/newsPicUrl" + "/newsPic"+ random + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/newsPic/newsPic" + random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVENEWSPICPATH + path));
			//将文件部分路径保存到数据库
			news.setNewsPic(absolutePath); 
			//news.setNewsPic(picPath); 
			newsService.modifyNews(news);
		}
		//处理新闻内容中图片对应的图片文件
		List<NewsPic> nList = newsPicService.getNewsPicByNewsId(news.getId());
		for (NewsPic newsPic : nList) {
			//如果新闻内容中的原图片不存在
			if(!news.getContent().contains(newsPic.getUrl())){
				//删除数据库中对应记录
				newsPicService.delectById(newsPic.getId());
				//删除文件夹中对应图片
				String picPath = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\newContentPic";
				File oldfile = new File(picPath+"\\"+newsPic.getUrl());
				if(oldfile!=null){
					oldfile.delete();
				}
			}
		}
		
		NewsPic newsPic = null;
		News n = null;
		//将新闻内容里的图片路径写入数据库
		for (String contentPicUrl : newContentPic) {
			//处理用户在编写新闻时上传图片后又删除图片的情况
			//如果上传某张图片后，保存是内容中确实用到了这张图片
			if(news.getContent().contains(contentPicUrl)){
				newsPic = new NewsPic();
				n = new News();
				n.setId(news.getId());
				newsPic.setNews(n);
				newsPic.setUrl(contentPicUrl);
				newsPicService.saveNewsPic(newsPic);
			}else{
				//上传了图片但是在新闻内容中没用到这张图片
				//删除文件夹中对应图片
				String newsContentPic = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\newContentPic";
				File oldfile = new File(newsContentPic+"\\"+contentPicUrl);
				if(oldfile!=null){
					oldfile.delete();
				}
			}
		}
		newContentPic.clear();
		
		newsService.modifyNews(news);
		return "{\"status\":true}";
	}

	
	/**
	 * 根据ID查询新闻
	 * @param id
	 * @return
	 */
	@RequestMapping("/findNewsById")
	public @ResponseBody News findNewsById(@RequestParam("id") Integer id){
		//System.err.println(id);
		return newsService.selectById(id);
	}
	
	
	/**
	 * 根据id批量删除新闻
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/news/deleteNews")
	public @ResponseBody String batchDelNews(@RequestParam("ids[]") Integer[] ids) {
		//新闻内容图片文件夹路径
		String picPath = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\newContentPic";
		
		// 删除新闻内容中对应的图片
		for (Integer picId : ids) {
			// 可能一条新闻里的内容有多个图片
			List<NewsPic> n = newsPicService.getNewsPicByNewsId(picId);
			for (NewsPic ne : n) {
				File oldfile = new File(picPath + "\\" + ne.getUrl());
				// 删除旧文件
				if (oldfile != null) {
					oldfile.delete();
				}
				newsPicService.delectById(ne.getId());
			}
			//根据新闻id查找新闻，并删除新闻封面
			News news = newsService.selectById(picId);
			//新闻封面图片名
			String picUrl = "newsPic"+news.getNewsPic().split("newsPic")[2];
			
			File picCoverFile = new File(SAVENEWSPICPATH + "\\" + picUrl);
			if(picCoverFile!=null){
				picCoverFile.delete();
			}
		}
		
		newsService.batchDelNews(ids);
		return "{\"status\":true}";
	}

	/**
	 * 根据页码分页查询，获取授课数据列表
	 * 需要传入两个参数，pageIndex和keyword，当keyword不为空时，作为sql语句模糊查询的参数
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/news/{pageIndex}/getNewsPageModel")
	public @ResponseBody PageModel<News> getPicPageModel(@PathVariable Integer pageIndex,String newsName){
		PageModel<News> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(newsName != null){
			News news = new News();
			news.setNewsName(newsName);
			return newsService.findNews(news, pageModel);
		}
		return newsService.findNews(null, pageModel);
	}
	
	/**
	 * 新闻动态 type=0
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/news/{pageIndex}/getTrends")
	public @ResponseBody PageModel<News> newsGetTrends(@PathVariable Integer pageIndex) {
		PageModel<News> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		News news = new News();
		int newsType = 0;
		news.setNewsType(newsType);
		return newsService.findNews(news, pageModel);
	}
	
	/**
	 * 媒体报道 type=1
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping("/news/{pageIndex}/getReport")
	public @ResponseBody PageModel<News> newsGetReport(@PathVariable Integer pageIndex) {
		PageModel<News> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		News news = new News();
		int newsType = 1;
		news.setNewsType(newsType);
		return newsService.findNews(news, pageModel);
	}
	
	/**
	 * 最新资讯单条新闻详情页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/latest_information/Detail/{id}")
	public String latestInformationDetail(HttpServletRequest request, @PathVariable Integer id){
		News news = newsService.selectById(id);
		request.setAttribute("news", news);
		return "latest_information_Detail";
	}
	
	/**
	 * 新闻动态单条新闻详情页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/newsTrends/Detail/{id}")
	public String newsTrendsDetail(HttpServletRequest request, @PathVariable Integer id){
		News news = newsService.selectById(id);
		request.setAttribute("news", news);
		return "newsTrends_Detail";
	}
	
	/**
	 * 媒体报道单条新闻详情页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/newsReport/Detail/{id}")
	public String newsReportDetail(HttpServletRequest request, @PathVariable Integer id){
		News news = newsService.selectById(id);
		request.setAttribute("news", news);
		return "newsReport_Detail";
	}
}
