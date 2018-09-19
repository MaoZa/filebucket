package cn.dawnland.filebucket.common.service.impl;

import cn.dawnland.filebucket.common.mapper.FilesMapper;
import cn.dawnland.filebucket.common.pojo.files.Files;
import cn.dawnland.filebucket.common.pojo.files.UploadMsg;
import cn.dawnland.filebucket.common.pojo.user.UserSession;
import cn.dawnland.filebucket.common.service.FilesService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class FilesServiceImpl implements FilesService {

    @Autowired
    private FilesMapper filesMapper;

    @Value("${tencent.dawnlandbucket.accessKey}")
    private String accessKey;
    @Value("${tencent.dawnlandbucket.secretKey}")
    private String secretKey;
    @Value("${tencent.dawnlandbucket.bucket}")
    private String bucket;
    @Value("${tencent.dawnlandbucket.bucketName}")
    private String bucketName;
    @Value("${tencent.dawnlandbucket.path}")
    private String path;
    @Value("${tencent.dawnlandbucket.prefix}")
    private String prefix;

    @Transactional
    public UploadMsg upload(HttpServletRequest request, MultipartFile file) throws Exception{
        UserSession userSession = (UserSession) request.getSession().getAttribute("UserSession");
        if(file == null){
            return new UploadMsg(0, "文件为空", null);
        }
        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + eName;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DATE);
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = this.bucketName;

        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = null;
        try {
            localFile = File.createTempFile("temp",null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String key = "/" + this.prefix + "/" + userSession.getUserName().toLowerCase() + "/" + year + "/" + month + "/" + day + "/" + newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            Files files = new Files(userSession.getId(), oldFileName, newFileName, this.path + putObjectRequest.getKey(), localFile.length());     //创建Files对象
            if(this.insertFileInfo(files) < 1){//插入Files记录
                throw new Exception("插入文件记录错误,请重试");
            }
            return new UploadMsg(1,"上传成功",this.path + putObjectRequest.getKey());
        } catch (IOException e) {
            return new UploadMsg(-1,e.getMessage(),null);
        }finally {
            // 关闭客户端(关闭后台线程)
            cosclient.shutdown();
        }
    }

    @Override
    @Transactional
    public void deleteFiles(Files files, HttpServletRequest request) throws Exception {
        UserSession userSession = (UserSession) request.getSession().getAttribute("UserSession");
        filesMapper.deleteFiles(files.getId());
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        COSClient cosclient = new COSClient(cred, clientConfig);
        String bucketName = this.bucketName;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(files.getCreateTime());
        int year = calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        String key = "/" + this.prefix + "/" + userSession.getUserName().toLowerCase() + "/" + year + "/" + month + "/" + day + "/" + files.getBucketFileName();
        cosclient.deleteObject(bucketName, key);
        cosclient.shutdown();
    }

    @Override
    @Transactional
    public Integer insertFileInfo(Files files) {
        return filesMapper.insertFileInfo(files);
    }

    @Override
    public List<Files> findFileInfoByParams(Files files) {
        return filesMapper.findFileInfoByParams(files);
    }

    @Override
    public Files findFilesByFilesId(Long id) {
        return filesMapper.findFilesByFilesId(id);
    }

    @Override
    public Long findSumSize(Long userId) {
        return filesMapper.findSumSize(userId);
    }

}
