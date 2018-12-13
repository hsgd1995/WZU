package com.hxbd.clp.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.hxbd.clp.domain.Manager;
import com.hxbd.clp.utils.common.RasConstants;
/**
 * 判断用户权限的拦截器
 * 
 * @author 恒信博大
 *
 */
public class AuthorizedInterceptor implements HandlerInterceptor {
    /**
     * 定义不需要拦截的请求URI
     */
    private static final String[] IGNORE_URI = { "/index", "/login","/editpwd","/backstage","/backLogin",
    		"backManagerLogin","/main","/register","/phoneValidate","/idCardValidate","/getCodeSession","/userLogin",
    		"catalog","getUserSession","userOutLogin","getCode","retrieve_password","retrievePassword","editPassword"
            ,"live","learning_process","about_us","curriculum","curriculum/*",
            "catalog","details","introduce","curriculum_video","ability_test","forum","forum_content",
            "report","trends","judgeUserSession","findCourseLimitEight","findTeacherLimitfour","collection_course","my_set_up",
            //最新资讯
            "latest_information","newsTrends","newsReport",
            //课程列表页面
            "list_of_catalogues","list_of_literature","list_of_economics","list_of_law",
            //消息页面
            "message","managerMessage","sysMessage"
            };
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 默认用户没有登录 
        boolean flag = false;
        // 获得请求的ServletPath
        String servletPath = request.getServletPath(); 
        //System.out.println("请求路径："+servletPath);
        
       /* if(servletPath.contains("video_details")){
        	System.out.println("视频详情页");
        }*/
        
        // 判断请求是否需要拦截
        for (String s : IGNORE_URI) {
            if (servletPath.contains(s)) {
            	//System.out.println("true:"+s);
                flag = true;
                break;
            }
        }
        // 拦截请求
        if (!flag) {
            // 1.获取session中的用户
            Manager manager = (Manager) request.getSession().getAttribute(RasConstants.MANAGER_SESSION);
            // 2.判断用户是否已经登录
            if (manager == null) {
                // 用户没有登录，跳转到登录页面
                /*response.sendRedirect(request.getContextPath() + "/backstage" );*/
                request.getRequestDispatcher("/backstage").forward(request, response);
                return flag;
            } else {
                flag = true;
            }
        }
        return flag;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}