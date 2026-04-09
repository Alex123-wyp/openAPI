package com.yupeng.openapi.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tencent COS client
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Configuration
@ConfigurationProperties(prefix = "cos.client")
@Data
public class CosClientConfig {

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * Region
     */
    private String region;

    /**
     * Bucket name
     */
    private String bucket;

    @Bean
    public COSClient cosClient() {
        // Initialize user credentials (secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        // Set the bucket region. Refer to https://www.qcloud.com/document/product/436/6224 for COS region abbreviations.
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // Create the COS client
        return new COSClient(cred, clientConfig);
    }
}