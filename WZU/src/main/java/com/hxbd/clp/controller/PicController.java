package com.hxbd.clp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hxbd.clp.domain.Pic;
import com.hxbd.clp.service.PicService;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 图片管理控制层
 * 
 * @author 恒信博大
 *
 */
@Controller
public class PicController {
	
	// 保存路径
	private static final String SAVEPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLmages";
	
	@Resource
	private PicService picService;
	
	/**
	 * 保存图片
	 * 
	 * @param pic
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "pic/uploadSave", method = RequestMethod.POST)
	public @ResponseBody String uploadSave(Pic pic,MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			// 第一步：上传文件
			String path = "/" + pic.getPicName() + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVEPATH + path));
			// 第二步：将文件部分路径保存到数据库
			pic.setPath(path);
			picService.savePic(pic);
			return "{\"status\":true,\"message\":\"上传成功！\"}";
		} else {
			return "{\"status\":false,\"message\":\"请选择正确的文件后，再上传！\"}";
		}
	}
	
	/**
	 * 根据id获取图片
	 * 
	 * @param id
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/pic/{id}/get")
	public void getPicById(@PathVariable Integer id,HttpServletRequest req ,HttpServletResponse resp)
		throws IOException{
		// 1.获取图片相对路径
		Pic pic = picService.selectPicById(id);
		if(pic == null){
			return;
		}
		// 2.获取图片的保存路径
		String savePath = SAVEPATH + pic.getPath();
		//System.err.println(savePath);
		// 3.读取图片并响应到输出流
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(savePath));
		BufferedOutputStream outStream = new BufferedOutputStream(resp.getOutputStream());
		byte[] bytes = new byte[1000];
		int length;
		while((length = inputStream.read(bytes)) != -1){
			outStream.write(bytes, 0, length);
		}
		outStream.flush();
		outStream.close();
		inputStream.close();
	}
	
	@RequestMapping("/pic/getPic")
	public @ResponseBody List<Pic> getPicList(){
		return picService.picList();
	}
	
	/**
	 * 根据图片编号获取图片
	 * 
	 * @param picNo
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/pic/{picNo}/getByPicNo")
	public void getByPicNo(@PathVariable String picNo,HttpServletRequest req ,HttpServletResponse resp)
		throws IOException{
		// 1.获取图片相对路径
		Pic pic = picService.selectPicByPicNo(picNo);
		if(pic == null){
			return;
		}
		// 2.获取图片的保存路径
		String savePath = SAVEPATH + pic.getPath();
		//System.err.println(savePath);
		// 3.读取图片并响应到输出流
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(savePath));
		BufferedOutputStream outStream = new BufferedOutputStream(resp.getOutputStream());
		byte[] bytes = new byte[1000];
		int length;
		while((length = inputStream.read(bytes)) != -1){
			outStream.write(bytes, 0, length);
		}
		outStream.flush();
		outStream.close();
		inputStream.close();
	}
	
	/**
	 * 更新图片不包含文件
	 * 
	 * @param pic
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "pic/uploadUpdateNoFile", method = RequestMethod.POST)
	public @ResponseBody String uploadUpdateNoFile(Pic pic) throws IOException {
		if (pic != null) {
			// 1.获取旧文件
			Pic p =  picService.selectPicById(pic.getId());
			String filePath = SAVEPATH + p.getPath();
			File oldfile = new File(filePath);
			// 2.新路径
			String newPath = SAVEPATH + "/" + pic.getPicName() +".jpg";
			// 3.更新文件名
			File newfile = new File(newPath);
			oldfile.renameTo(newfile);
			// 4.更新图片
			picService.updatePicNoFile(pic);
			return "{\"status\":true}";
		} else {
			return "{\"status\":false}";
		}
	}
	
	/**
	 * 更新图片包含文件
	 * 
	 * @param pic
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "pic/uploadUpdateOnFile", method = RequestMethod.POST)
	public @ResponseBody String uploadUpdateOnFile(Pic pic,MultipartFile file) throws IOException {
		// 1.获取旧文件
		Pic p =  picService.selectPicById(pic.getId());
		String filePath = SAVEPATH + p.getPath();
		File oldfile = new File(filePath);
		// 2.删除旧文件
		oldfile.delete();
		// 3.保存文件
		if (!file.isEmpty()) {
			// 第一步：上传文件
			String path = "/" + pic.getPicName() + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVEPATH + path));
			// 第二步：将文件部分路径保存到数据库
			picService.updatePicNoFile(pic);
			return "{\"status\":true,\"message\":\"上传成功！\"}";
		} else {
			return "{\"status\":false,\"message\":\"请选择正确的文件后，再上传！\"}";
		}
	}
	
	@RequestMapping("/pic/{pageIndex}/getPicPageModel")
	public @ResponseBody PageModel<Pic> getPicPageModel(@PathVariable Integer pageIndex,@RequestParam("keyword") String keyword){
		PageModel<Pic> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(keyword != null){
			Pic pic = new Pic();
			pic.setPicName(keyword);
			return picService.findPicList(pic,pageModel);
		}
		return picService.findPicList(null,pageModel);
	}
	
	//删除更新图片记录
	@RequestMapping("/pic/deletePic")
	public @ResponseBody String deletePic(@RequestParam("ids[]") Integer[] ids){
		for(int i=0;i<ids.length;i++){
			// 1.获取选中
			Pic p =  picService.selectPicById(ids[i]);
			String filePath = SAVEPATH + p.getPath();
			File file = new File(filePath);
			// 2.删除文件
			file.delete();
		}
		picService.batchDeletePic(ids);
		return "{\"status\":true}";
	}
	
}
