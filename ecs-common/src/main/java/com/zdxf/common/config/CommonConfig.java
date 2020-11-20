package com.zdxf.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Admin
 */
@Configuration
@Data
public class CommonConfig {

    /**
     * 图片域名
     */
    public static String imageURL;

    /**
     * 图片域名赋值
     *
     * @param url 域名地址
     */
    @Value("${server.IMAGE_URL}")
    public void setImageURL(String url) {
        imageURL = url;
    }

    /**
     * 菜单类型
     */
    public static Map<Integer, String> MENU_TYPE_LIST = new HashMap<Integer, String>() {
        {
            put(1, "目录");
            put(2, "菜单");
            put(3, "按钮");
        }
    };
}
