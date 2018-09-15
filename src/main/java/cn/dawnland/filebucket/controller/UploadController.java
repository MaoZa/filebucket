package cn.dawnland.filebucket.controller;

import cn.dawnland.filebucket.common.pojo.entity.ResponseData;
import cn.dawnland.filebucket.common.utils.BucketUtil;
import com.qcloud.cos.COSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private BucketUtil bucketUtil;

    @RequestMapping(value = "/hello")
    public ResponseData hello(){
        COSClient cosClient = bucketUtil.getCosclient();
        System.out.println(cosClient);
        Map data = new HashMap();
        data.put("Cosclient", cosClient);
        return new ResponseData(data);
    }

}
