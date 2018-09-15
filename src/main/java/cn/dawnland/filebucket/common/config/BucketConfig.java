package cn.dawnland.filebucket.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BucketConfig {

    @Value("${tencent.dawnlandbucket.SecretId}")
    private String SecretId;
    @Value("${tencent.dawnlandbucket.SecretKey}")
    private String SecretKey;
    @Value("${tencent.dawnlandbucket.region}")
    private String region;

    public String getSecretId() {
        return SecretId;
    }

    public void setSecretId(String secretId) {
        SecretId = secretId;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
