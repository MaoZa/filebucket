package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.ResponseData;
import cn.dawnland.filebucket.common.pojo.user.User;
import cn.dawnland.filebucket.common.pojo.user.UserSession;
import cn.dawnland.filebucket.common.service.EmailCodeService;
import cn.dawnland.filebucket.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private EmailCodeService emailCodeService;
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
    public String login(User user, HttpServletRequest request, HttpServletResponse response, Model model){
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
            model.addAttribute("title", "错误");
            model.addAttribute("msg", "用户名或密码错误");
            return "returnPage";
        }
        user.clean();
        user.setLastLogin(new Date());
        userService.updateNotNullUser(user);
        return "redirect:/files/findFiles";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseData register(User user){
        ResponseData responseData = new ResponseData();
        if(user.isNotNull()){
            responseData.setCode("40001");
            responseData.setMessage("必填信息不完整");
            return responseData;
        }
        User userTemp = new User();
        userTemp.setEmail(user.getEmail());
        userTemp = userService.findUserByParams(userTemp);
        if(userTemp != null){
            responseData.setCode("40001");
            responseData.setMessage("邮箱已绑定");
            return responseData;
        }

        Integer emailCode = emailCodeService.updateCodeStatusByEmailAndEmailCode(user.getEmail(), user.getEmailCode());
        if (emailCode > 0) {
            try{
                user.setLastLogin(new Date());
                userService.insertUser(user);
            }catch (Exception e){
                responseData.setCode("40001");
                responseData.setMessage(e.getMessage());
            }
        }else{
            responseData.setCode("40001");
            responseData.setMessage("验证码无效");
        }
        return responseData;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseData logout(HttpServletRequest request){
        request.getSession().setAttribute("UserSession", null);
        return new ResponseData();
    }

    @RequestMapping(value = "reset", method = RequestMethod.GET)
    public ResponseData reset(@RequestParam("email") String email,
                              @RequestParam("emailCode") String emailCode,
                              HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        Integer flag = emailCodeService.updateCodeStatusByEmailAndEmailCode(email, emailCode);
        String val = "";
        if(flag > 0){
            //生成随机密码
            Random random = new Random();
            //length为几位密码
            Integer length = 9;
            for(int i = 0; i < length; i++) {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                //输出字母还是数字
                if( "char".equalsIgnoreCase(charOrNum) ) {
                    //输出是大写字母还是小写字母
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char)(random.nextInt(26) + temp);
                } else if( "num".equalsIgnoreCase(charOrNum) ) {
                    val += String.valueOf(random.nextInt(10));
                }
            }

            //更新邮箱用户密码
            Long userId = userService.findUserIdByEmail(email);
            if(userId == null){
                responseData.setCode("40001");
                responseData.setMessage("邮箱未注册");
                return responseData;
            }
            User user = new User();
            user.setId(userId);
            user.setEmail(email);
            user.setPassword(val);
            userService.updateNotNullUser(user);
        }else{
            responseData.setCode("40001");
            responseData.setMessage("验证码无效");
            return responseData;
        }
        Map data = new HashMap();
        data.put("newPassword", val);
        responseData.setData(data);
        return responseData;
    }
}
