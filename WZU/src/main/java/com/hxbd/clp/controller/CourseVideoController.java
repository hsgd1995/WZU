package com.hxbd.clp.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hxbd.clp.domain.Caption;
import com.hxbd.clp.domain.Course;
import com.hxbd.clp.domain.CourseVideo;
import com.hxbd.clp.domain.Process;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.service.CaptionService;
import com.hxbd.clp.service.CourseService;
import com.hxbd.clp.service.CourseVideoService;
import com.hxbd.clp.to.UploadStatus;
import com.hxbd.clp.utils.common.RandomUtil;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.tag.PageModel;
import com.hxbd.clp.vo.CourseVideoVO;

/**
 * 课程视频管理控制层
 * @author 恒信博大
 *
 */
@Controller
public class CourseVideoController {

	// 保存课程视频路径 C:\Program Files\web\Tomcat\webapps\HXCLVideo\courseVideo
	private static final String SAVECOURSEVIDEOPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLVideo\\courseVideo";
	// 保存字幕路径
	private static final String SAVECAPTIONPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLVideo\\caption";
	// private static final String SAVECOURSEVIDEOPATH =
	// "D:\\hxbd\\courseVideo";
	@Resource
	private CourseVideoService courseVideoService;
	@Resource
	private CourseService courseService;
	@Autowired
	private CaptionService captionService;

	/**
	 * Blob加密，暂不使用
	 * 返回课程视频
	 */
	@RequestMapping("/courseVideo/play/{videoId}")
	public void play(@PathVariable("videoId") String videoId,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		
		Date date1 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("开始时间："+sdf.format(date1));
		
		/*
		 * 在这里可以进行权限验证等操作
		 */
		//校验登录和站点
		User user = (User) httpServletRequest.getSession().getAttribute(RasConstants.USER_SESSION);
		
		String referer = httpServletRequest.getHeader("referer");
		System.out.println("referer:"+referer);
		if(user == null||referer==null||!referer.contains("d7v4rr")){
			try {
				System.out.println("重定向1");
				httpServletResponse.sendRedirect("../login");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}

		CourseVideo cv =  courseVideoService.selectById(Integer.parseInt(videoId));
		if(cv==null){
			try {
				System.out.println("重定向2");
				httpServletResponse.sendRedirect("login");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String videoName = cv.getUrl().split("courseVideo")[2];
		//创建文件对象
		File f = new File(SAVECOURSEVIDEOPATH+"\\courseVideo"+videoName);
		//获取文件名称
		String fileName = f.getName();
		//导出文件
		String agent = httpServletRequest.getHeader("User-Agent").toUpperCase();
		InputStream fis = null;
		OutputStream os = null;
		try {
		    fis = new BufferedInputStream(new FileInputStream(f.getPath()));
		    
		    
		    byte[] buffer = new byte[1024];// 缓冲区
			int length;// 记录每次读取的长度
		    
		    httpServletResponse.reset();
		    //由于火狐和其他浏览器显示名称的方式不相同，需要进行不同的编码处理
		    if(agent.indexOf("FIREFOX") != -1){//火狐浏览器
		    	httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO-8859-1"));
		    }else{//其他浏览器
		    	httpServletResponse.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")); 
		    }
		    //设置response编码 
		    httpServletResponse.setCharacterEncoding("UTF-8");
		    //httpServletResponse.addHeader("Content-Length", "" + f.length());
		    //设置输出文件类型
		    httpServletResponse.setContentType("video/mpeg4");
		    //获取response输出流
		    os = httpServletResponse.getOutputStream();
		    // 输出文件
		    while ((length = fis.read(buffer)) != -1) {
				// 4.写文件
		    	os.write(buffer, 0, length);
			}
		    os.flush();// 刷新输出流
		}catch(Exception e){
		    System.out.println(e.getMessage());
		} finally{
		    //关闭流
		    try {
		        if(fis != null){
		            fis.close();
		        }
		    } catch (IOException e) {
		        System.out.println(e.getMessage());
		    } finally{
		        try {
		            if(os != null){
		                os.flush();
		            }
		        } catch (IOException e) {
		            System.out.println(e.getMessage());
		        } finally{
		            try {
		                if(os != null){
		                    os.close();
		                }
		            } catch (IOException e) {
		                System.out.println(e.getMessage());
		            }
		        }
		    }
		}
		Date date2 = new Date();
		System.out.println("结束时间："+sdf.format(date2));
	}
	
	
	/**
	 * 返回字幕文件用于前台js解析
	 * @param captionName
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/courseVideo/getCaption/{captionName}")
	public Object testCaption(@PathVariable("captionName") String captionName) throws Exception{
		System.out.println("寻找文件");
		//下载的文件名称，转码
        String filename = new String("abc".getBytes("UTF-8"), "iso-8859-1");
        //设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        //通知浏览器以attachment方式解析
        headers.setContentDispositionFormData("attachment", filename);
        //设置响应的数据格式：二进制流数据
        headers.setContentType(APPLICATION_OCTET_STREAM);
        //创建下载文件对象
        File file = new File("C:\\Program Files\\web\\Tomcat\\webapps\\HXCLVideo\\caption" +"\\"+ captionName+".vtt");
        //响应文档
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, CREATED);
	}
	
	
	/**
	 * 保存课程视频
	 * 
	 * @param courseVideo
	 * @return
	 */
	@RequestMapping("courseVideo/saveCourseVideo")
	public @ResponseBody String saveCourseVideo(CourseVideo courseVideo, MultipartFile file, HttpSession session, @RequestParam(value="captionFile",required=false) MultipartFile[] captionFile)
			throws IOException {
		CourseVideo findCPN = courseVideoService.findByCourseIdAndParentAndName(courseVideo.getCourseId(),
				courseVideo.getParent(), courseVideo.getName());
		if (findCPN != null) {
			return "{\"status\":false}";
		} else {
			String random = RandomUtil.getRandom15();
			if (!file.isEmpty()) {
				// 第一步：上传文件 C:\\Program
				// Files\\web\\Tomcat\\webapps\\HXCLVideo\\courseVideo
				String path = "/courseVideo" + random + ".mp4";
				String absolutePath = "http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo" + random + ".mp4";
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVECOURSEVIDEOPATH + path)); // http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo465834658763789.mp4
				// 第二步：将文件部分路径保存到数据库
				courseVideo.setUrl(absolutePath);
				//返回自增id
				int id = courseVideoService.addCourseVideo(courseVideo);
				//处理字幕
				Calendar calendar = Calendar.getInstance();
				String today = calendar.get(Calendar.YEAR)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.DAY_OF_MONTH);
				String captionRandom = "";
				String captionFileName = "";
				File dest = null;
				if(captionFile!=null&&captionFile.length>0){
					for (MultipartFile multipartFile : captionFile) {
						
						captionRandom = RandomUtil.getRandom15();
						//当前日期_视频id_15位随机数
						captionFileName = today+"_"+id+"_"+captionRandom+".vtt";
						//保存文件
	                    dest = new File(SAVECAPTIONPATH+"\\"+captionFileName);
	                    /*
                         * MultipartFile提供了void transferTo(File dest)方法,
                         * 将获取到的文件以File形式传输至指定路径.
                         */
                        multipartFile.transferTo(dest);
                        //将字幕文件名和视频id保存到数据库
                        Caption caption = new Caption();
                        caption.setCaptionName(captionFileName);
                        caption.setVideoId(id);
                        captionService.add(caption);
					}
				}
				return "{\"status\":true}";
			} else {
				return "{\"status\":false}";
			}
		}
	}

	/**
	 * 这里是获取上传文件状态信息的访问接口
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/courseVideo/getStatus")
	public UploadStatus getStatus(HttpSession session) {

		return (UploadStatus) session.getAttribute("upload_status");
	}

	/**
	 * 保存视频进度
	 * 
	 * @param userId
	 * @param courseVideoId
	 * @param courseId
	 * @param currentTime
	 */
	@GetMapping("/courseVideo/saveVideoProcess")
	public void saveVideoProcess(Integer userId, Integer courseVideoId, Integer courseId, Double currentTime) {
		courseVideoService.saveVideoProcess(userId, courseVideoId, courseId, currentTime);
	}

	/**
	 * 设置视频进度
	 * 
	 * @param userId
	 * @param courseVideoId
	 * @param courseId
	 * @param currentTime
	 */
	@GetMapping("/courseVideo/setVideoProcess")
	public @ResponseBody Process setVideoProcess(Integer userId, Integer courseVideoId, Integer courseId) {
		return courseVideoService.getVideoProcess(userId, courseVideoId, courseId);
	}

	@GetMapping("/courseVideo/setVideoProcessFinish")
	@ResponseBody
	public String finishVideoProcess(Integer userId, Integer courseVideoId, Integer courseId) {
		courseVideoService.finishStudyVideo(userId, courseVideoId, courseId);
		return "{\"status\":true}";
	}

	/**
	 * 保存课程视频主标题
	 * 
	 * @param courseVideo
	 * @return
	 */
	@RequestMapping("courseVideo/saveParentName")
	public @ResponseBody String saveParentName(CourseVideo courseVideo) {
		CourseVideo data;
		Course newCourse = courseService.selectByTeacherCourseNo(String.valueOf(courseVideo.getCourseId()));
		courseVideo.setCourseId(newCourse.getId());
		try {
			data = courseVideoService.findCourseVideoByCourseIdAndParent(courseVideo.getCourseId(),
					courseVideo.getName(), 0);
			if (data == null) {
				courseVideo.setParent(0);
				if (courseVideo.getCourseId() != 0 && courseVideo.getName() != null
						&& !courseVideo.getName().equals("")) {
					courseVideoService.addCourseVideo(courseVideo);
					return "{\"status\":true}";
				}
			}
		} catch (Exception e) {
			//System.err.println(e);
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return "{\"status\":false}";
	}

	/**
	 * 更新课程视频
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("courseVideo/updateCourseVideo")
	public @ResponseBody String updateCourseVideo(CourseVideo courseVideo, MultipartFile file,@RequestParam(value="captionFile",required=false) MultipartFile[] captionFile) throws IOException {
		String random = RandomUtil.getRandom15();
		if (file != null) {
			CourseVideo c = courseVideoService.selectById(courseVideo.getId());
			String filePath = c.getUrl();
			//System.err.println(filePath); // http://www.hengmu-edu.com/HXCLImages/teacherPic/teacherPic465834658763789.jpg
			String relPath = filePath.substring(47); // http://www.hengmu-edu.com/HXCLImages/teacherPic
			//System.err.println(relPath);
			String rellPath = SAVECOURSEVIDEOPATH + relPath;
			//System.err.println(rellPath);
			File oldfile = new File(rellPath);
			// 删除旧文件
			oldfile.delete();

			// 上传文件
			String path = "/courseVideo" + random + ".mp4";
			String absolutePath = "http://www.hengmu-edu.com/HXCLVideo/courseVideo/courseVideo" + random + ".mp4";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVECOURSEVIDEOPATH + path));
			// 将文件部分路径保存到数据库
			courseVideo.setUrl(absolutePath);
			courseVideoService.modifyCourseVideo(courseVideo);
		}
		
		//处理字幕
		if(captionFile!=null&&captionFile.length>0){
			System.out.println("字幕captionFile.length:"+captionFile.length);
			File canptionFile = null;
			List<Caption> captionList = captionService.getByVideoId(courseVideo.getId());
			//删除旧文件
			for (Caption caption : captionList) {
				canptionFile = new File(SAVECAPTIONPATH+"\\"+caption.getCaptionName());
				if(canptionFile!=null){
					canptionFile.delete();
				}
			}
			// 删除数据库旧记录
			captionService.deleteByVideoId(courseVideo.getId());
			
			//上传新文件
			Calendar calendar = Calendar.getInstance();
			String today = calendar.get(Calendar.YEAR)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.DAY_OF_MONTH);
			String captionRandom = "";
			String captionFileName = "";
			File dest = null;
			for (MultipartFile multipartFile : captionFile) {
				captionRandom = RandomUtil.getRandom15();
				//当前日期_视频id_15位随机数
				captionFileName = today+"_"+courseVideo.getId()+"_"+captionRandom+".vtt";
				//保存文件
                dest = new File(SAVECAPTIONPATH+"\\"+captionFileName);
                /*
                 * MultipartFile提供了void transferTo(File dest)方法,
                 * 将获取到的文件以File形式传输至指定路径.
                 */
                multipartFile.transferTo(dest);
                System.out.println("上传字幕");
                //将字幕文件名和视频id保存到数据库
                Caption caption = new Caption();
                caption.setCaptionName(captionFileName);
                caption.setVideoId(courseVideo.getId());
                captionService.add(caption);
			}
			
		}
		
		courseVideoService.modifyCourseVideo(courseVideo);
		return "{\"status\":true}";
	}

	/**
	 * 更新课程视频主标题
	 * 
	 * @param courseVideo
	 * @return
	 */
	@RequestMapping("courseVideo/updateParentName")
	public @ResponseBody String updateParentName(CourseVideo courseVideo) {
		Course course = courseService.selectByTeacherCourseNo(String.valueOf(courseVideo.getCourseId()));
		courseVideo.setCourseId(course.getId());
		courseVideo.setParent(0);
		courseVideoService.modifyCourseVideo(courseVideo);
		//System.err.println("update:" + courseVideo);
		return "{\"status\":true}";
	}

	/**
	 * 根据ID查询课程视频
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findCourseVideoById")
	public @ResponseBody CourseVideoVO findCourseVideoById(@RequestParam("id") Integer id) {
		CourseVideoVO courseVideoVO = new CourseVideoVO();
		CourseVideo courseVideo = courseVideoService.selectById(id);
		Course couser = courseService.selectByCourseId(String.valueOf(courseVideo.getCourseId()));
		courseVideoVO.setId(courseVideo.getId());
		courseVideoVO.setCourseId(courseVideo.getCourseId());
		courseVideoVO.setCourseName(courseVideo.getName());
		courseVideoVO.setCourseNo(couser.getCourseNo());
		courseVideoVO.setUrl(courseVideo.getUrl());
		courseVideoVO.setParent(courseVideo.getParent());
		return courseVideoVO;
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById/{id}")
	public @ResponseBody CourseVideo findById(@PathVariable Integer id) {
		return courseVideoService.selectById(id);
	}

	/**
	 * 根据id批量删除课程视频
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/courseVideo/deleteCourseVideo")
	public @ResponseBody String batchDelCourseVideo(@RequestParam("ids[]") Integer[] ids) {
		//删除字幕
		for (Integer integer : ids) {
			List<Caption> captionList = captionService.getByVideoId(integer);
			//删除旧文件
			File canptionFile = null;
			for (Caption caption : captionList) {
				canptionFile = new File(SAVECAPTIONPATH+"\\"+caption.getCaptionName());
				if(canptionFile!=null){
					canptionFile.delete();
				}
			}
			// 删除数据库旧记录
			captionService.deleteByVideoId(integer);
		}
		courseVideoService.batchDelCourseVideo(ids);
		return "{\"status\":true}";
	}

	/**
	 * 分页查询课程视频
	 * 
	 * @param pageIndex
	 * @param keyword
	 * @param parentNameId
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/courseVideo/{pageIndex}/getCourseVideoPageModel")
	public @ResponseBody PageModel<CourseVideoVO> getCourseVideoPageModel(@PathVariable Integer pageIndex,
			@RequestParam("keyword") String keyword, Integer parentNameId, Integer courseId) {
		PageModel<CourseVideoVO> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Map<Integer, Course> courseMap = courseService.findAllMap();
		CourseVideo cv = new CourseVideo();
		cv.setParent(parentNameId);
		if (courseId != null && courseId != 0) {
			cv.setCourseId(courseId);
		}
		if(parentNameId!=null){
			cv.setParent(parentNameId);
		}
		return courseVideoService.findCourseVideo(cv, pageModel, courseMap);
	}

	@RequestMapping("/courseVideo/{pageIndex}/getParentNamePageModel")
	public @ResponseBody PageModel<CourseVideoVO> getParentNamePageModel(@PathVariable int pageIndex,
			@RequestParam("keyword") String keyword, String courseName, String title) {
		PageModel<CourseVideoVO> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		Map<Integer, Course> courseMap = courseService.findAllMap();
		CourseVideoVO courseVideoVO = new CourseVideoVO();
		System.err.println("参数：" + courseName +","+ title);
		if(courseName != null && !courseName.equals("")){
			courseVideoVO.setCourseName(courseName);
		}
		if(title != null && !title.equals("")){
			courseVideoVO.setParentName(title);
		}
		courseVideoVO.setParent(0); // 只查询父节点=0的
		return courseVideoService.findCourseVideoVO(courseVideoVO, pageModel, courseMap);
	}

//	// 下拉选择主标题
//	@RequestMapping("courseVideo/{courseId}/getParentName")
//	public @ResponseBody List<CourseVideo> getParentNameByCid(@PathVariable int courseId) {
//		return courseVideoService.findCourseVideoByCourseIdAndParent(courseId, 0); // 0
//																					// ==
//																					// 主标题
//	}

	// 下拉选择主标题
	@RequestMapping("courseVideo/getParentName")
	public @ResponseBody List<CourseVideo> getParentName(@RequestParam("courseNo") String courseNo) {
		return courseVideoService.findCourseVideoByCourseNo(courseNo); // 0
																					// ==
																					// 主标题
	}
}