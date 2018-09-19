package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.ResponseData;
import cn.dawnland.filebucket.common.pojo.files.Files;
import cn.dawnland.filebucket.common.pojo.files.UploadMsg;
import cn.dawnland.filebucket.common.pojo.user.UserSession;
import cn.dawnland.filebucket.common.service.FilesService;
import cn.dawnland.filebucket.common.utils.FilesSizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @Value("${tencent.dawnlandbucket.userMaxSize}")
    private Long userMaxSize;

    /**
     * 上传到腾讯云服务器（https://cloud.tencent.com/document/product/436/10199）
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData Upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request){
        UserSession userSession = (UserSession)request.getSession().getAttribute("UserSession");
        Long sumSize = filesService.findSumSize(userSession.getId());
        sumSize = sumSize == null ? 0 : sumSize;
        Map data = new HashMap();
        if(sumSize <= userMaxSize){
            if(userMaxSize - sumSize >= file.getSize()){
                try {
                    UploadMsg uploadMsg = filesService.upload(request, file);
                    data.put("uploadMsg", uploadMsg);
                    data.put("residualSpace", userMaxSize - sumSize - file.getSize());
                    return new ResponseData(data);
                } catch (Exception e) {
                    UploadMsg uploadMsg = new UploadMsg(-1, "插入文件记录异常", "");
                    data.put("uploadMsg", uploadMsg);
                    data.put("residualSpace", userMaxSize - sumSize - file.getSize());
                    return new ResponseData(data);
                }
            }
        }
        ResponseData responseData = new ResponseData();
        responseData.setCode("40001");
        responseData.setMessage("用户可用储存空间不足。当前剩余空间：" + (userMaxSize - sumSize) + "，文件大小：" + file.getSize());
        return responseData;
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

    @RequestMapping(value = "/delete/{filesId}")
    @ResponseBody
    public ResponseData deletedFiles(@PathVariable Long filesId, HttpServletRequest request, HttpServletResponse responser){
        UserSession userSession = (UserSession) request.getSession().getAttribute("UserSession");
        ResponseData responseData = new ResponseData();
        Files files = filesService.findFilesByFilesId(filesId);
        if (files != null) {
            if(!files.getUserId().equals(userSession.getId())){
                responseData.setCode("40001");
                responseData.setMessage("删除文件出错，该文件不属于你");
                return responseData;
            }
            try {
                filesService.deleteFiles(files, request);
                responseData.setMessage("删除成功");
            } catch (Exception e) {
                responseData.setMessage("系统异常，请稍后重试");
            }
            return responseData;
        }
        responseData.setCode("40002");
        responseData.setMessage("文件不存在");
        return responseData;
    }

    @RequestMapping(value = "/findSumSize", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData findSumSize(HttpServletRequest request){
        UserSession userSession = (UserSession) request.getSession().getAttribute("UserSession");
        Long sumSize = filesService.findSumSize(userSession.getId());
        if (sumSize == null){
            sumSize = 0L;
        }
        Map data = new HashMap();
        data.put("sumSize", FilesSizeUtil.getPrintSize(sumSize));
        return new ResponseData(data);
    }

}