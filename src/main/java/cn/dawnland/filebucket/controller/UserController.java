package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.ResponseData;
import cn.dawnland.filebucket.common.pojo.user.User;
import cn.dawnland.filebucket.common.pojo.user.UserSession;
import cn.dawnland.filebucket.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户名密码登录
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseData login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        user = userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        ResponseData responseData = new ResponseData();
        if(user != null){
            UserSession userSession = new UserSession();
            userSession.setId(user.getId());
            userSession.setUserName(user.getUsername());
            userSession.setTencentNumber(user.getTencentNumber());
            userSession.seteMail(user.getEmail());
            userSession.setLoginIp(request.getRemoteAddr());
            //存入userSession
            request.getSession().setAttribute("UserSession", userSession);
        }else{
            responseData.setCode("100001");
            responseData.setMessage("用户名或密码错误");
        }
        user.clean();
        user.setLastLogin(new Date());
        userService.updateNotNullUser(user);
        return responseData;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseData register(@RequestBody User user){
        ResponseData responseData = new ResponseData();
        User userTemp = new User();
        userTemp.setEmail(user.getEmail());
        userTemp = userService.findUserByParams(userTemp);
        if(userTemp != null){
            responseData.setCode("40001");
            responseData.setMessage("邮箱已绑定");
            return responseData;
        }
        try{
            user.setLastLogin(new Date());
            userService.insertUser(user);
        }catch (Exception e){
            responseData.setCode("40001");
            responseData.setMessage(e.getMessage());
            return responseData;
        }
        return responseData;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseData logout(HttpServletRequest request){
        request.getSession().setAttribute("UserSession", null);
        return new ResponseData();
    }

    @RequestMapping(value = "reset", method = RequestMethod.GET)
    public ResponseData reset(HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        UserSession userSession = (UserSession)request.getSession().getAttribute("UserSession");
        userSession.getUserName();
        return responseData;
    }
}
