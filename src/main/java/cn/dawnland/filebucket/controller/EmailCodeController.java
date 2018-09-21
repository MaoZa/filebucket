package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.ResponseData;
import cn.dawnland.filebucket.common.pojo.emailcode.EmailCode;
import cn.dawnland.filebucket.common.service.EmailCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(value = "email")
public class EmailCodeController {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailCodeService emailCodeService;

    @RequestMapping(value = "sendcode", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData sendCode(@RequestParam("email")String email, HttpServletRequest httpServletRequest, HttpServletResponse response){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        /** --------------生成验证码开始---------------- */
        char[] codeArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        StringBuffer codeString = new StringBuffer();
        for(int i = 0; i < 6; i++){
            int randomInt = random.nextInt(codeArray.length - 1);
            codeString.append(codeArray[randomInt]);
        }
        /** --------------生成验证码结束---------------- */
        /** --------------数据库记录开始---------------- */
        EmailCode emailCode = new EmailCode();
        emailCode.setCode(codeString.toString());
        emailCode.setEmail(email);
        Long nowDate = new Date().getTime();
        emailCode.setCreateTime(new Date(nowDate));
        emailCode.setFailureTime(new Date(nowDate + 600000));
        try {
            emailCodeService.insertCode(emailCode);
        } catch (Exception e) {
            ResponseData responseData = new ResponseData();
            responseData.setCode("30001");
            responseData.setMessage("验证码发送失败请稍后重试");
            return responseData;
        }
        /** --------------数据库记录结束---------------- */

        //发送邮件
        simpleMailMessage.setFrom("cap_sub@dawnland.cn");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("验证码");
        simpleMailMessage.setText(codeString.toString());
        javaMailSender.send(simpleMailMessage);
        Map data = new HashMap();
        data.put("code", codeString);
        return new ResponseData(data);

    }

}
