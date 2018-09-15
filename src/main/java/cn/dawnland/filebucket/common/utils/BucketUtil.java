package cn.dawnland.filebucket.common.utils;

import cn.dawnland.filebucket.common.config.BucketConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BucketUtil {

    private COSCredentials cred;
    private ClientConfig clientConfig;
    private COSClient cosclient;
    @Autowired
    private BucketConfig bucketConfig;

    public COSClient getCosclient() {
        // 1 初始化用户身份信息(secretId, secretKey)
        cred = new BasicCOSCredentials(bucketConfig.getSecretId(), bucketConfig.getSecretKey());
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        clientConfig = new ClientConfig(new Region(bucketConfig.getRegion()));
        // 3 生成cos客户端
        cosclient = new COSClient(cred, clientConfig);
        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        //String bucketName = "mybucket-1251668577";
        return cosclient;
    }
}
