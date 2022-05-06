package com.tgl.access.configuration;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * MongodbOptProp
 *
 * @author tgl
 * @since 2022-04-30
 */
@Configuration
@PropertySource(value = "classpath:mongodb.properties", encoding = "UTF8")
@Getter
public class MongodbOptProp {
    private Integer minConnectionPerHost = 0;
    private Integer maxConnectionPerHost = 100;
    private Integer threadsAllowedToBlockForConnectionMultiplier = 5;
    private Integer serverSelectionTimeout = 30000;
    private Integer maxWaitTime = 120000;
    private Integer maxConnectionIdleTime = 0;
    private Integer maxConnectionLifeTime = 0;
    private Integer connectTimeout = 10000;
    private Integer socketTimeout = 0;
    private Boolean socketKeepAlive = false;
    private Boolean sslEnabled = false;
    private Boolean sslInvalidHostNameAllowed = false;
    private Boolean alwaysUseMBeans = false;
    private Integer heartbeatFrequency = 10000;
    private Integer minHeartbeatFrequency = 500;
    private Integer heartbeatConnectTimeout = 20000;
    private Integer heartbeatSocketTimeout = 20000;
    private Integer localThreshold = 15;
}
