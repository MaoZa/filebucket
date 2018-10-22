package cn.dawnland.filebucket.interceptor;

import cn.dawnland.filebucket.common.pojo.user.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${excludeUrl.array}")
    String excludeUrl[];
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI().substring(request.getContextPath().length());
        if("/error".equals(requestUrl)){
            logger.info("登录拦截器：错误页面-准许");
            return true;
        }
        System.out.println("请求uri：" + requestUrl);
        if (excludeUrl != null && excludeUrl.length != 0) {
            for (String url : excludeUrl) {
                if(requestUrl.equals("/") || "".equals(requestUrl)){
                    logger.info("登录拦截器：首页请求-准许");
                    return true;
                }
                if (requestUrl.startsWith(url)) {
                    logger.info("登录拦截器：登录或注册请求-准许");
                    return true;
                }
            }
        }
        UserSession userSession = (UserSession)request.getSession().getAttribute("UserSession");
        if(userSession == null){
            logger.info("登录拦截器：未登录请求-拒绝");
//            ResponseData responseData = new ResponseData();
//            responseData.setCode("40001");
//            responseData.setMessage("未登录用户");
//            response.setHeader("Content-Type", "application/json");
//            response.getWriter().println(Jackson.toJsonString(responseData));
            response.sendRedirect("login");
            return false;
        }else if(userSession.getLoginIp() != request.getRemoteAddr()){
            logger.info("登录失效: 需重新登录-拒绝");
        }
        logger.info("登录拦截器：已登录请求-准许");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
