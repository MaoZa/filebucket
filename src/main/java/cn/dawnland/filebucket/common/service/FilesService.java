package cn.dawnland.filebucket.common.service;

import cn.dawnland.filebucket.common.pojo.files.Files;
import cn.dawnland.filebucket.common.pojo.files.UploadMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
public interface FilesService {

    /**
     * 上传文件
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    UploadMsg upload(HttpServletRequest request, MultipartFile file) throws Exception;

    /**
     * 插入文件记录
     * @param files
     * @return
     */
    Integer insertFileInfo(Files files);

    /**
     * 查询用户文件列表
     * @param files
     * @return
     */
    List<Files> findFileInfoByParams(Files files);

}
