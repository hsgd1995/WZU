package com.hxbd.clp.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hxbd.clp.domain.Record;
import com.hxbd.clp.domain.User;
import com.hxbd.clp.domain.UserInfo;
import com.hxbd.clp.service.RecordService;
import com.hxbd.clp.service.UserInfoService;
import com.hxbd.clp.service.UserService;
import com.hxbd.clp.to.mail.TextMessageSender;
import com.hxbd.clp.utils.common.CaptchaUtils;
import com.hxbd.clp.utils.common.CaptchaUtils.ComplexLevel;
import com.hxbd.clp.utils.tag.PageModel;

import com.hxbd.clp.utils.common.MD5Util;
import com.hxbd.clp.utils.common.RandomUtil;
import com.hxbd.clp.utils.common.RasConstants;
import com.hxbd.clp.utils.common.SendMsg;

@Controller
public class UserController {

	// 保存用户头像路径
	private static final String SAVEUSERPICPATH = "C:\\Program Files\\web\\Tomcat\\webapps\\HXCLImages\\userPic";

	@Autowired
	private UserService userService;
	@Autowired
	private RecordService recordService;
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/registerUser")
	public @ResponseBody String getUserSession(User user) throws UnsupportedEncodingException {
		//System.err.println(user);
		String s = "ran/*dom.01";
		String DM5Password = MD5Util.getEncryption(user.getPassword());
		String newPassword = DM5Password + s;
		user.setPassword(MD5Util.getEncryption(newPassword));
		userService.saveUser(user);
		return "{\"status\":true,\"message\":\"注册成功！\"}";
	}

	/**
	 * 校验手机号是否已被绑定
	 * 
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping("/phoneValidate")
	public @ResponseBody String phoneValidate(String phoneNumber) {
		User result = userService.phoneValidate(phoneNumber);
		//System.err.println("=-=-=-=-");
		//System.err.println(result);
		//System.err.println(result == null);
		if (result != null) {
			return "{\"status\":true}";
		}
		return "{\"status\":false}";
	}

	/**
	 * 校验身份证
	 * 
	 * @param cardId
	 * @return
	 */
	@RequestMapping("/idCardValidate")
	private @ResponseBody String idCardValidate(String cardId) {
		User result = userService.idCardValidate(cardId);
		//System.err.println(result);
		if (result != null) {
			return "{\"valid\":false}";
		}
		//System.out.println(cardId);
		return "{\"valid\":true}";
	}

	/**
	 * @Decription 发送短信验证码,保存到Session中
	 * @param 封装客户端请求
	 *            POST
	 * @return 返回状态参数
	 * @throws Exception
	 */
	@RequestMapping("/getCode/{phone}/SendCheckCode")
	public @ResponseBody String SendCheckCode(HttpServletRequest request, @PathVariable String phone) throws Exception {
		Record record = recordService.selectRecordByPhone(phone);
		//System.err.println(record == null);
		Record record2 = new Record();
		if (record == null) {
			Integer count = 1;
			record2.setPhone(phone);
			record2.setCount(count);
			recordService.savePhone(record2);
			HashMap<String, String> m = SendMsg.getMessageStatus(phone); // 应用发送短信接口
			String result = m.get("result"); // 获取到result值
			if (result.trim().equals("1")) { // 如果为1，表示成功发送
				String code = m.get("code"); // 获取发送的验证码内容
				System.err.println("发送的验证码1:" + code);
				HttpSession session = request.getSession(); // 设置session
				session.setAttribute("phone_code", code); // 将短信验证码放到session中保存
				session.setMaxInactiveInterval(60 * 5);// 保存时间 暂时设定为5分钟
				return "{\"status\":true}";
			} else {
				return "{\"status\":false}";
			}
		} else {
			if (record.getCount() < 6) {
				HashMap<String, String> m = SendMsg.getMessageStatus(phone); // 应用发送短信接口
				String result = m.get("result"); // 获取到result值
				if (result.trim().equals("1")) { // 如果为1，表示成功发送
					String code = m.get("code"); // 获取发送的验证码内容
					System.err.println("发送的验证码2:" + code);
					HttpSession session = request.getSession(); // 设置session
					session.setAttribute("phone_code", code); // 将短信验证码放到session中保存
					session.setMaxInactiveInterval(60 * 5);// 保存时间 暂时设定为5分钟
					Integer count = record.getCount();
					count += count + 1;
					// record2.setPhone(phone);
					record.setCount(count);
					System.err.println(record);
					recordService.updateRecord(record);
					return "{\"status\":true}";
				} else {
					return "{\"status\":false}";
				}
			} else {
				return "{\"message\":true}"; // 短信发送超过5次
			}
		}

	}

	@RequestMapping("/getCode")
	public void getCaptcheImage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		// 通知浏览器不要缓存
		response.setHeader("Expires", "-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pargma", "-1");
		// 制作验证码
		Object[] obj = CaptchaUtils.getCaptchaImage(150, 50, 35, 3, 0, false, true, ComplexLevel.MEDIUM);
		// 将验证码输入到session中，用来验证
		session.setAttribute("login_code", obj[1]);
		// 输出到web页面
		ImageIO.write((BufferedImage) obj[0], "jpg", response.getOutputStream());
	}

	/**
	 * 请求服务器session
	 * 
	 * @return
	 */
	@RequestMapping("/getCode/getSession")
	public @ResponseBody String getCode(HttpSession session) {
		String code = (String) session.getAttribute(RasConstants.LOGINCODE);
		if (code != null) {
			return "{\"code\":\"" + code + "\"}";
		} else {
			return "{}";
		}
	}

	// 注册页面手机号验证码
	@RequestMapping("/getCodeSession")
	public @ResponseBody String getCodeSession(HttpSession session) {
		String code = (String) session.getAttribute("phone_code");
		return code;
	}

	// 获取用户session
	@RequestMapping("/getUserSession")
	public @ResponseBody User getUserSession(HttpSession session) {
		User userSession = (User) session.getAttribute("user_session");
		//System.out.println(userSession);
		//System.out.println("-123");
		return userSession;
	}
	
	// 获取用户详情
	@RequestMapping("/mySetUp/getUserInfo")
	public  @ResponseBody UserInfo  getUserInfon(HttpSession session,Model model) {
		User userSession = (User) session.getAttribute("user_session");
		if(userSession != null){
			UserInfo userInfo = userInfoService.selectByUserId(userSession.getId());
			System.out.println("userInfo:"+userInfo);
			if(userInfo != null){
				return userInfo;
			}
		}
		return null;
	}

	// 判断用户session
	@RequestMapping("/judgeUserSession")
	public @ResponseBody String judgeUserSession(HttpSession session) {
		User userSession = (User) session.getAttribute("user_session");
		if (userSession != null) {
			return "{\"status\":true}";
		}
		return "{\"status\":false}";
	}

	// 用户登录 、、、、登录名 ，手机号，邮箱
	@RequestMapping("/userLogin")
	public @ResponseBody String validateUser(@RequestParam("loginName") String loginName,
			@RequestParam("password") String password, HttpSession session) throws UnsupportedEncodingException {
		// 验证用户名和密码
		String s = "ran/*dom.01";
		String MD5password = MD5Util.getEncryption(password);
		//System.out.println("MD5加密");
		//System.out.println(MD5password);
		String String1 = MD5password + s;
		String newPassword = MD5Util.getEncryption(String1);
		User user = userService.validateUser(loginName, newPassword);
		if (user != null) {
			session.setAttribute(RasConstants.USER_SESSION, user);
			//保存登录时间
			Date date = new Date();
			user.setLoginTime(date);
			userService.updateUser(user);
			return "{\"message\":true}";
		}
		return "{\"message\":false}";
	}

	// 用户退出
	@RequestMapping("/userOutLogin")
	public String userOutLogin(HttpSession session) {
		//保存登出时间
		User userSession = (User) session.getAttribute("user_session");
		Date date = new Date();
		userSession.setLogoutTime(date);
		userService.updateUser(userSession);
		session.invalidate();
		return "index";
	}

	@Scheduled(cron = "0 0 0 * * ? ") // 每天0点执行
	public void deleteRecord() {
		Integer record = recordService.findAllRecord();
		//System.out.println(record);
		if (record > 0) {
			recordService.deleteRecord();

		}

	}
	
	@Scheduled(cron="0 0 9 * * ?") // 每天早上9点执行检测账号登录状况
	public void mailTask() throws ParseException {
		String emails = "";
		Map<Integer,User> map = userService.findAllMap();
		for (User value : map.values()) {
			Date lastLogoutTime = value.getLogoutTime();
			if(lastLogoutTime!=null){
				Date now = new Date();
				SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				long from = lastLogoutTime.getTime();
				long to = now.getTime();
				int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
				if(days>=0){
					if(emails==""){
						emails += value.getEmail();
					}else{
						emails += ";" + value.getEmail();
					}
				}
			}
		}
		if(emails != ""){
			TextMessageSender emailSend = new TextMessageSender();
			String[] args = {emails,";",";","学习提醒","您有很多天没有学习了，"};
			emailSend.email(args);
		}
	}

	// 忘记密码
	@RequestMapping("/retrievePassword")
	public @ResponseBody String retrievePassword(HttpSession session, @RequestParam("phone") String phone)
			throws Exception {
		session.setAttribute("phoneNumber", phone);
		session.setMaxInactiveInterval(60 * 5);// 保存时间 暂时设定为5分钟
		//System.err.println(session.getAttribute("phoneNumber"));
		System.err.println("这里是发送验证码");
		// 根据手机号查询是否有这个用户
		boolean user = userService.phoneValidate1(phone);
		if (user) {
			HashMap<String, String> m = SendMsg.getMessageStatus(phone); // 应用发送短信接口
			String result = m.get("result"); // 获取到result值
			if (result.trim().equals("1")) { // 如果为1，表示成功发送
				String code = m.get("code"); // 获取发送的验证码内容
				//System.out.println("发送的验证码:" + code);
				// HttpSession session = request.getSession(); //设置session
				session.setAttribute("phone_code", code); // 将短信验证码放到session中保存
				session.setMaxInactiveInterval(60 * 5);// 保存时间 暂时设定为5分钟
				System.err.println("这里是手机号验证码");
				//System.err.println(session.getAttribute("phone_code"));
				return "{\"status\":true,\"message\":\"发送成功\"}";
			} else {
				return "{\"errorMsg\":true,\"message\":\"服务器出错,请稍候再试\"}";
			}
		} else {
			return "{\"status\":false,\"message\":\"手机号无效\"}";
		}
	}

	// 修改密码
	@RequestMapping("/editPassword")
	public @ResponseBody String editPassword(@RequestParam("password") String password,
			@RequestParam("code") String code, HttpSession session) throws UnsupportedEncodingException {
		String phone = (String) session.getAttribute("phoneNumber");
		//System.err.println("这里是修改密码");
		//System.err.println(password);
		//System.err.println(phone);
		//System.err.println(code);
		String s = "ran/*dom.01";
		String DM5Password = MD5Util.getEncryption(password);
		String newPassword = DM5Password + s;

		String o = (String) session.getAttribute("phone_code");
		//System.err.println("=====");
		//System.err.println(session.getAttribute("phone_code"));
		//System.err.println("-------");
		//System.err.println(o);
		if (code.equals(o)) {
			User user = userService.phoneValidate(phone);

			user.setPassword(MD5Util.getEncryption(newPassword));
			userService.updateUser(user);
			session.invalidate();
			return "{\"status\":true,\"message\":\"修改成功！\"}";
		} else {
			return "{\"status\":false,\"message\":\"验证码错误！\"}";
		}
	}

	/**
	 * 直播预约
	 * 
	 * @param httpSession
	 * @return
	 */
	@RequestMapping("/live/appointment")
	public @ResponseBody String appointment(HttpSession httpSession) {
		User userSession = (User) httpSession.getAttribute("user_session");
		//System.err.println(userSession);
		if (userSession != null) {
			String remark = userSession.getRemark();
			//System.err.println(remark);
			if (remark != null && !remark.equals("0")) {
				return "{\"status\":300,\"message\":\"您已经预约过啦~\"}";
			} else {
				System.err.println("没预约过的进来");
				userSession.setRemark("1");
				//System.err.println(userSession.getRemark());
				userService.updateUser(userSession);
				// 发送短信到用户手机
				return "{\"status\":200,\"message\":\"预约成功,当前时间段没有直播\"}";
			}
		} else {
			return "{\"status\":400,\"message\":\"请先登录\"}";
		}
	}

	// 分页查询用户
	@RequestMapping("/user/{pageIndex}/getUserPageModel")
	public @ResponseBody PageModel<User> getUserPageModel(@PathVariable Integer pageIndex, String userName,String userIdcar,@RequestParam(value="pageSize",required=false) Integer pageSize) {
		PageModel<User> pageModel = new PageModel<>();
		pageModel.setPageIndex(pageIndex);
		if(pageSize!=null){
			pageModel.setPageSize(pageSize);
		}
		User user = new User();
		if (userName != null && !userName.equals("")) {
			user.setUsername(userName);
		}
		if (userIdcar != null && !userIdcar.equals("")) {
			user.setCardId(userIdcar);
		}
			return userService.findUser(user, pageModel);
	}

	// 根据id查询
	@RequestMapping("/findUserById")
	public @ResponseBody User findUserById(@RequestParam("id") Integer id) {
		return userService.selectById(id);
	}

	/**
	 * 保存用户
	 * 
	 * @param user
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "user/saveUser", method = RequestMethod.POST)
	public @ResponseBody String saveUser(User user, MultipartFile file) throws IOException {
		//System.err.println(user + ":sdfsdjkfgkjdsh");
		if (!file.isEmpty()) {
			String random = RandomUtil.getRandom15();
			// 第一步：上传文件
			String path = "/userPic" + random + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/userPic/userPic" + random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVEUSERPICPATH + path));
			// 第二步：将文件部分路径保存到数据库
			user.setUserPic(absolutePath);
			// 密码加密
			//System.err.println(user);
			String s = "ran/*dom.01";
			String DM5Password = MD5Util.getEncryption(user.getPassword());
			String newPassword = DM5Password + s;
			user.setPassword(MD5Util.getEncryption(newPassword));
			// 添加用户
			userService.saveUser(user);
			return "{\"status\":true}";
		} else {
			return "{\"status\":false}";
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "user/updateUser", method = RequestMethod.POST)
	public @ResponseBody String updateUser(User user, MultipartFile file) throws IOException {
		String random = RandomUtil.getRandom15();
		if (file != null) {
			User t = userService.selectById(user.getId());
			String filePath = t.getUserPic();
			//System.err.println(filePath);
			String relPath = filePath.substring(44); // http://www.hengmu-edu.com/HXCLImages/userPic/userPic465834658763789.jpg
			//System.err.println(relPath);
			String rellPath = SAVEUSERPICPATH + relPath;
			//System.err.println(rellPath);
			File oldfile = new File(rellPath);
			// 删除旧文件
			oldfile.delete();

			// 上传文件
			String path = "/userPic" + random + ".jpg";
			String absolutePath = "http://www.hengmu-edu.com/HXCLImages/userPic/userPic" + random + ".jpg";
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(SAVEUSERPICPATH + path));
			// 将文件部分路径保存到数据库
			user.setUserPic(absolutePath);
		}
		// 密码加密
		//System.err.println(user);
		String s = "ran/*dom.01";
		String DM5Password = MD5Util.getEncryption(user.getPassword());
		String newPassword = DM5Password + s;
		user.setPassword(MD5Util.getEncryption(newPassword));
		userService.updateUser(user);
		return "{\"status\":true}";
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/user/deleteUser")
	public @ResponseBody String batchDelUser(@RequestParam("ids[]") Integer[] ids) {
		// 删除用户前，将用户绑定的课程解除
		System.err.println(ids);
		userService.batchDelUser(ids);
		return "{\"status\":true}";
	}

	// 分页查询用户
	@RequestMapping("/user/getUserPageModelByIdAndName")
	public @ResponseBody PageModel<User> queryUserPageModel(@RequestParam("userId") String userId,
			@RequestParam("username") String username) {
		PageModel<User> pageModel = new PageModel<>();
		User user = new User();
		//System.out.println(userId + "," + username);
		pageModel.setPageIndex(1);
		Pattern pattern = Pattern.compile("[0-9]*");
		if (pattern.matcher(userId).matches() && userId.length() != 0) {
			user.setId(Integer.parseInt(userId));
		}
		if (username.length() != 0) {
			user.setUsername(username);
		}
		if (user.getId() == null && user.getUsername() == null) {
			return pageModel;
		}
		return userService.findUser(user, pageModel);
	}
}