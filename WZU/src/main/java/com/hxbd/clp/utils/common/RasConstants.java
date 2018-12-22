package com.hxbd.clp.utils.common;


public class RasConstants {

	//--------//
	//权限控制
	public static final String SYSROLE = "sys_role";//角色表
	public static final String SYSAUTHORITY = "sys_authority";//权限表
	public static final String SYSROLEAUTHORITY = "sys_role_authority";//角色权限对应表
	//public static final String SYSROLEUSER = "sys_role_user";//角色用户对应表
	public static final String SYSROLEMANAGER = "sys_role_manager";//角色管理员对应表
	public static final String MANAGERROLEAUTHORITY_SESSION = "manager_role_authority";
	
	
	public static final String BASE = "base_base_info";//双创基地信息
	public static final String BASEINTO = "bus_base_into";//基地进驻入口
	public static final String PROJECT = "base_project_info";//B0-在驻企业项目情况
	public static final String TEACHERTABLE = "base_teacher"; // 教师表
	public static final String PROJECTPERSONNEL = "base_project_personnel"; // 企业员工表
	public static final String PROJECTSUBSIDY = "base_project_subsidy"; // 资金收入
	public static final String AWARDS = "base_awards"; // 资金收入
	public static final String ANNEX = "base_annex"; // 资金收入
	public static final String PROJECTINTO = "base_project_into"; // 项目进驻审批表
	
	
	//--------//
	
	
	// 数据库表常量
	public static final String USERTABLE = "user_table";// 用户信息表
	public static final String PICTABLE = "pic_table";// 图片表
	
	//课程相关的表
	public static final String COURSETABLE = "course_table";// 课程表
	public static final String COURSETYPETABLE = "course_type_table";  //课程类型表
	public static final String COURSEDETAILSTABLE = "course_details_table";  //课程详情表
	public static final String TIMETABLE = "time_table"; // 课程时间表
	public static final String COURSEANDTEACHERTABLE = "course_and_teacher_table";	//课程和教师关联表
	public static final String COURSEANDUSERTABLE = "course_and_user_table";	//课程和用户关联表
	public static final String NOTICETABLE = "notice_table";	//课程公告表
	public static final String COURSEVIDEOTABLE = "course_video_table";	//课程视频表
	public static final String CQTABLE = "cq_table";  //测试答题表(选择题)
	public static final String JPTABLE = "jp_table"; //测试答题表（判断题）
	public static final String SATABLE = "shortanswer_table";//简答题表
	public static final String COMPLETIONTABLE = "completion_table"; //测试答题表（填空题）
	public static final String VIDEOPROCESS = "video_process";  //视频进度
	public static final String CAPTION = "caption";//字幕表
	public static final String TEACHERCONTENTPIC = "teacher_content_pic";//教师详细介绍图片表
	public static final String USERACHIEVEMENT = "user_achievement"	;//用户成绩表
	
	//新闻相关的表
	public static final String NEWSTABLE = "news_table"; //新闻表
	public static final String NEWSPICTABLE = "news_pic_table";// 新闻图片表
	
	public static final String MANAGERTABLE = "sys_manager"; // 管理员用户表
	public static final String RECORDTABLE = "record_table";  //手机号验证次数表
	public static final String CHANNELTABLE = "channel_table"; //直播频道管理
	
	//消息表
	public static final String MESSAGETABLE = "message";//消息表 
	public static final String MESSAGEANDUSERTABLE = "message_user_table";//消息表 
	public static final String SYSTEMMESSAGE = "system_message";//系统消息表
	

	
	// 登录
	public static final String LOGIN = "index";

	// 用户的session对象
	public static final String USER_SESSION = "user_session";
	// 管理员的session对象
	public static final String MANAGER_SESSION = "manager_session";
	// 手机号验证码session对象
	public static final String PHONECODE= "phone_code";
	//登录页面验证码session对象
	public static final String LOGINCODE = "login_code";

	//用户信息表
	public static final String PERSONAL_SETTINGS = "personal_settings";
	
	// 默认每页6条数据
	public static final int PAGE_DEFAULT_SIZE = 8;
	
	//以下是直播平台相关配置 
	//appKey
	public static final String APPKEY = "c8028e956d0d1eaf83c50ba21aad2608";
	
	//appSecret
	public static final String APPSECRET = "ecd1d1404164";
	
	
}
