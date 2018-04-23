package com.pwq.spider.chsi.common.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yuyangjun
 * @desc
 * @date 2018/3/21 下午4:31
 */
@Getter
@Setter
public class CookieInfo {
    private static final long serialVersionUID = 4147219031540970562L;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("httpOnly")
    private String httpOnly;
    @JsonProperty("name")
    private String name;
    @JsonProperty("path")
    private String path;
    @JsonProperty("secure")
    private String secure;
    @JsonProperty("value")
    private String value;
    @JsonProperty("expires")
    private String expires;
    public String getExpires() {
        return expires;
    }
}
