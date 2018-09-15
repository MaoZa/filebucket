package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.entity.ResponseData;
import cn.dawnland.filebucket.common.pojo.entity.user.User;
import cn.dawnland.filebucket.common.pojo.entity.user.UserSession;
import cn.dawnland.filebucket.common.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
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
        user.setLastLogin(new Date());
        userService.updateNotNullUser(user);
        ResponseData responseData = new ResponseData();
        if(user != null){
            UserSession userSession = new UserSession();
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
        return responseData;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseData register(@RequestBody User user){
        try{
            user.setLastLogin(new Date());
            userService.insertUser(user);
        }catch (Exception e){
            ResponseData responseData = new ResponseData();
            responseData.setCode("40001");
            return responseData;
        }
        return new ResponseData();
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseData logout(HttpServletRequest request){
        request.getSession().setAttribute("UserSession", null);
        return new ResponseData();
    }
}
