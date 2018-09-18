package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.ResponseData;
import cn.dawnland.filebucket.common.pojo.files.Files;
import cn.dawnland.filebucket.common.pojo.files.UploadMsg;
import cn.dawnland.filebucket.common.pojo.user.UserSession;
import cn.dawnland.filebucket.common.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件控制器
 */
@Controller
@RequestMapping(value = "/files")
public class FilesController {

    @Autowired
    private FilesService filesService;

    /**
     * 上传到腾讯云服务器（https://cloud.tencent.com/document/product/436/10199）
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public UploadMsg Upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){
        try {
            return filesService.upload(request, file);
        } catch (Exception e) {
            UploadMsg uploadMsg = new UploadMsg(-1, "插入文件记录异常", "");
            return uploadMsg;
        }
    }

    @RequestMapping(value = "/findFiles", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData findFiles(HttpServletRequest request, HttpServletResponse response){
        UserSession userSession = (UserSession)request.getSession().getAttribute("UserSession");
        Files files = new Files();
        files.setUserId(userSession.getId());
        List<Files> filesList = filesService.findFileInfoByParams(files) ;
        Map data = new HashMap<>();
        data.put("filesList", filesList);
        return new ResponseData(data);
    }

}