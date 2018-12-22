package com.hxbd.clp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxbd.clp.domain.Caption;
import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.domain.basedata.Awards;
import com.hxbd.clp.domain.basedata.Teacher;
import com.hxbd.clp.domain.basedata.base.Project;
import com.hxbd.clp.domain.basedata.base.ProjectPersonnel;
import com.hxbd.clp.domain.basedata.base.ProjectSubsidy;
import com.hxbd.clp.domain.bus.base.Annex;
import com.hxbd.clp.domain.bus.base.ProjectInto;
import com.hxbd.clp.service.AnnexService;
import com.hxbd.clp.service.AwardsService;
import com.hxbd.clp.service.ProjectIntoService;
import com.hxbd.clp.service.ProjectPersonnelService;
import com.hxbd.clp.service.ProjectService;
import com.hxbd.clp.service.ProjectSubsidyService;
import com.hxbd.clp.service.TeacherService;
import com.hxbd.clp.utils.common.RandomUtil;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.tag.PageModel;

/**
 * 申请进驻基地
 * @author Administrator
 *
 */
@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ProjectPersonnelService projectPersonnelService;
	@Autowired
	private ProjectSubsidyService projectSubsidyService;
	@Autowired
	private AwardsService awardsService;
	@Autowired
	private AnnexService annexService;
	
	@Autowired
	private ProjectIntoService projectIntoService;
	
	private String SAVECAPTIONPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\WZUAnnex\\annex";
	@GetMapping("project/list")
	@ResponseBody
	public PageModel<Project> list(Project base,Integer pageSize,Integer pageIndex,String keyword){
		
		if(!"gw001".equals(keyword)){
			keyword=keyword.substring(0, keyword.length()-1)+(Integer.parseInt(keyword.substring(keyword.length()-2))-1);
		}
		if(pageSize==null){
			pageSize = RasConstants.PAGE_DEFAULT_SIZE;
		}
		if(pageIndex==null){
			pageIndex = 1;
		}
		return projectService.getByParam(base,pageSize,pageIndex,keyword);
	}
	
	/**
	 * 查看审批
	 * @param id
	 * @return
	 */
	@GetMapping("project/lookReviewModal")
	@ResponseBody
	public Project lookReviewModal(Integer id){
		return projectService.findById(id);
	}
	
	
	
	@GetMapping("project/findById")
	@ResponseBody
	public Project findById(Integer id){
		return projectService.findById(id);
	}
	
	@RequestMapping(value="/project/update",method=RequestMethod.POST)
	public @ResponseBody String update(Project base){
		projectService.update(base);
		return "{\"status\":true,\"message\":\"更新成功\"}";
	}
	
	@RequestMapping(value="/project/add",method=RequestMethod.POST)
	public @ResponseBody String add(Project base,@RequestParam("teacherArray") Object teacherArray,@RequestParam("personalArray") Object personalArray,
			@RequestParam("projectSubsidyArray") Object projectSubsidyArray,@RequestParam("projectAwards") Object projectAwards,
			@RequestParam(value="annex",required=false) MultipartFile[] annex,HttpServletRequest request) throws Exception{
		System.out.println("teacherArray--"+teacherArray);
		System.out.println("personalArray--"+personalArray);
		System.out.println("projectSubsidyArray--"+projectSubsidyArray);
		System.out.println("projectAwards--"+projectAwards);
		
		int id = projectService.add(base);
		
		
		Gson gson = new Gson();
		//指导教师
		List<Teacher> teacherList = new ArrayList<>();
		JsonArray teacherJsonArray = (JsonArray) new JsonParser().parse(teacherArray.toString());
		for (JsonElement jsonElement : teacherJsonArray) {
			Teacher teacher= gson.fromJson(jsonElement, Teacher.class);
			teacher.setProjectId(id);
			//teacherList.add(teacher);
			teacherService.addTeacher(teacher);
		}
		//企业项目员工信息
		List<ProjectPersonnel> projectPersonnelList = new ArrayList<>();
		JsonArray projectPersonnelJsonArray = (JsonArray) new JsonParser().parse(personalArray.toString());
		for (JsonElement jsonElement : projectPersonnelJsonArray) {
			ProjectPersonnel p = gson.fromJson(jsonElement, ProjectPersonnel.class);
			//projectPersonnelList.add(p);
			p.setProjectId(id);
			projectPersonnelService.add(p);
		}
		//资金收入
		List<ProjectSubsidy> projectSubsidyList = new ArrayList<>();
		JsonArray projectSubsidyJsonArray = (JsonArray) new JsonParser().parse(projectSubsidyArray.toString());
		for (JsonElement jsonElement : projectSubsidyJsonArray) {
			ProjectSubsidy ps = gson.fromJson(jsonElement, ProjectSubsidy.class);
			ps.setProjectId(id);
			//projectSubsidyList.add(ps);
			projectSubsidyService.add(ps);
		}
		//项目获奖
		List<Awards> awardsList = new ArrayList<>();
		JsonArray awardsJsonArray = (JsonArray) new JsonParser().parse(projectAwards.toString());
		for (JsonElement jsonElement : awardsJsonArray) {
			Awards aw = gson.fromJson(jsonElement, Awards.class);
			//awardsList.add(aw);
			aw.setProjectId(id);
			awardsService.add(aw);
		}
		String captionRandom = "";
		String captionFileName = "";
		Calendar calendar = Calendar.getInstance();
		

		String today = calendar.get(Calendar.YEAR)+"_"+(calendar.get(Calendar.MONTH)+1)+"_"+calendar.get(Calendar.DAY_OF_MONTH);
		if(annex!=null&&annex.length>0){
			File folder= new File(SAVECAPTIONPATH);
			if(!folder.exists()){
				folder.mkdirs();
			}
			File dest = null;
			for (MultipartFile multipartFile : annex) {
				System.out.println("文件名"+multipartFile.getOriginalFilename());
				captionRandom = RandomUtil.getRandom15();
				//当前日期_视频id_15位随机数
				captionFileName = today+"_"+id+"_"+captionRandom+multipartFile.getOriginalFilename();
				//保存文件
                dest = new File(SAVECAPTIONPATH+"\\"+captionFileName);
                /*
                 * MultipartFile提供了void transferTo(File dest)方法,
                 * 将获取到的文件以File形式传输至指定路径.
                 */
                multipartFile.transferTo(dest);
                Annex an = new Annex();
                an.setName(captionFileName);
                an.setProjectId(id);
                annexService.add(an);
			}
		}
		
		ProjectInto projectInto = new ProjectInto();
		projectInto.setJobId("gw001");
		projectInto.setProjectId(id);
		Manager manager = (Manager) request.getSession().getAttribute(RasConstants.MANAGER_SESSION);
		if(manager!=null){
			projectInto.setManagerId(manager.getId());
		}
		projectIntoService.add(projectInto);
		
		return "{\"status\":true,\"message\":\"添加成功\"}";
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/project/del")
	@ResponseBody
	public String batchDisable(@RequestParam("ids[]") Integer[] ids){
		projectService.del(ids);
		return "{\"status\":true}";
	}
	
	@RequestMapping("project/downLoad")
	public ResponseEntity<byte[]> download2(HttpServletRequest request,Integer id)  {
		try{
		//需要压缩的文件
		List<Annex> annexList =  annexService.findByProjectId(id);
		
		//压缩后的文件
		String resourcesName = "附件.zip";
		
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(SAVECAPTIONPATH+"\\"+resourcesName));
		InputStream input = null;
		
		for (Annex annex : annexList) {
			String name = SAVECAPTIONPATH+"\\"+annex.getName();
			input = new FileInputStream(new File(name));  
            zipOut.putNextEntry(new ZipEntry(annex.getName()));  
            int temp = 0;  
            while((temp = input.read()) != -1){  
                zipOut.write(temp);  
            }  
            input.close();
		}
		zipOut.close();
		File file = new File(SAVECAPTIONPATH+"\\"+resourcesName);
		HttpHeaders headers = new HttpHeaders();
		String filename = new String(resourcesName.getBytes("utf-8"),"iso-8859-1");
		headers.setContentDispositionFormData("attachment", filename);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 学生申请进驻基地页面
	 * @return
	 */
	@GetMapping("/main/busStudentProjectInto")
	public String openBusStudentProjectInto(String keyword,Model model){
		switch (keyword) {
		case "gw001":
			model.addAttribute("projectTitle","学生申请进驻");
			break;
		case "gw002":
			model.addAttribute("projectTitle","教师审核");
			break;
		case "gw003":
			model.addAttribute("projectTitle","管理员审核");
			break;
		case "gw004":
			model.addAttribute("projectTitle","评委审核");
			break;

		default:
			model.addAttribute("projectTitle","");
			break;
		}
		System.out.println("keyword-->"+keyword);
		model.addAttribute("keyword",keyword);
		return "busStudentProjectInto";
	}
	//审批
	@PostMapping("project/review")
	@ResponseBody
	public Object review(ProjectInto projectInto,HttpServletRequest request){
		Manager manager = (Manager) request.getSession().getAttribute(RasConstants.MANAGER_SESSION);
		if(manager!=null){
			projectInto.setManagerId(manager.getId());
		}
		projectIntoService.add(projectInto);
		return "{\"status\":true}";
	}
	
}
