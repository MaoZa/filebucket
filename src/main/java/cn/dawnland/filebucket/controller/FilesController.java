package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.ResponseData;
import cn.dawnland.filebucket.common.pojo.files.Files;
import cn.dawnland.filebucket.common.pojo.files.UploadMsg;
import cn.dawnland.filebucket.common.pojo.user.UserSession;
import cn.dawnland.filebucket.common.service.FilesService;
import cn.dawnland.filebucket.common.utils.FilesSizeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String Upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, Model model){
        UserSession userSession = (UserSession)request.getSession().getAttribute("UserSession");
        Long sumSize = filesService.findSumSize(userSession.getId());
        sumSize = sumSize == null ? 0 : sumSize;
        Map data = new HashMap();
        if(sumSize <= userMaxSize){
            if(userMaxSize - sumSize >= file.getSize()){
                try {
                    UploadMsg uploadMsg = filesService.upload(request, file);
//                    data.put("uploadMsg", uploadMsg);
//                    data.put("residualSpace", userMaxSize - sumSize - file.getSize());
//                    return new ResponseData(data);
                    model.addAttribute("title", "成功");
                    model.addAttribute("msg", "上传成功");
                    return "returnPage";
                } catch (Exception e) {
                    UploadMsg uploadMsg = new UploadMsg(-1, "插入文件记录异常", "");
//                    data.put("uploadMsg", uploadMsg);
//                    data.put("residualSpace", userMaxSize - sumSize - file.getSize());
//                    return new ResponseData(data);
                    model.addAttribute("title", "失败");
                    model.addAttribute("msg", "插入文件记录异常");
                    return "returnPage";
                }
            }
        }
//        ResponseData responseData = new ResponseData();
//        responseData.setCode("40001");
//        responseData.setMessage("用户可用储存空间不足。当前剩余空间：" + (userMaxSize - sumSize) + "，文件大小：" + file.getSize());
        model.addAttribute("title", "失败");
        model.addAttribute("msg", "用户可用储存空间不足。当前剩余空间：" + (userMaxSize - sumSize) + "，文件大小：" + file.getSize());
        return "returnPage";
    }

    @RequestMapping(value = "/findFiles", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData findFiles(Integer pageNum,
                                  Integer pageSize,
                                  HttpServletRequest request, HttpServletResponse response, Model model){
        UserSession userSession = (UserSession)request.getSession().getAttribute("UserSession");
        Files files = new Files();
        files.setUserId(userSession.getId());
        PageHelper.startPage(pageNum, pageSize);
        List<Files> filesList = filesService.findFileInfoByParams(files) ;
        PageInfo pageInfo = new PageInfo(filesList);
        Map data = new HashMap<>();
        data.put("filesList", pageInfo);
        return new ResponseData(data);
    }

    @RequestMapping(value = "/delete/{filesId}")
    public String deletedFiles(@PathVariable Long filesId, HttpServletRequest request, HttpServletResponse responser, Model model){
        UserSession userSession = (UserSession) request.getSession().getAttribute("UserSession");
        ResponseData responseData = new ResponseData();
        Files files = filesService.findFilesByFilesId(filesId);
        if (files != null) {
            if(!files.getUserId().equals(userSession.getId())){
                model.addAttribute("title", "错误");
                model.addAttribute("msg", "删除文件出错，该文件不属于你");
                return "returnPage";
            }
            try {
                filesService.deleteFiles(files, request);
                model.addAttribute("title", "成功");
                model.addAttribute("msg", "删除成功");
            } catch (Exception e) {
                model.addAttribute("title", "错误");
                model.addAttribute("msg", "系统异常，请稍后重试");
            }

            return "returnPage";
        }
        model.addAttribute("title", "错误");
        model.addAttribute("msg", "文件不存在");
        return "returnPage";
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