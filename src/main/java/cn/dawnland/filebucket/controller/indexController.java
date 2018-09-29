package cn.dawnland.filebucket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("register")
    public String register(){
        return "register";
    }

    @RequestMapping("table")
    public String table(){
        return "table";
    }

    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }
}
