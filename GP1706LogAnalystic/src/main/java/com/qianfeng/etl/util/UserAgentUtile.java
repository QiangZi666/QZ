package com.qianfeng.etl.util;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserAgentUtile {
    /**
     * 浏览器代理对象的解析
     */

    private static final Logger logger = Logger.getLogger(UserAgentInfo.class);

    UserAgentInfo info = new UserAgentInfo();

    //获取uasparser
    private static UASparser uaSparser = null;

    static {
        try {
            uaSparser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            logger.error("获取uasparser对象失败", e);
        }
    }

    /**
     * 解析浏览器的代理对象
     */


    public static UserAgentInfo parserUserAgent(String userAgent) throws IOException {
        UserAgentInfo ui = null;
        if (StringUtils.isEmpty(userAgent)) {
            return ui;
        }
        //使用uasparser获取对象代理信息

        cz.mallat.uasparser.UserAgentInfo info = uaSparser.parse(userAgent);
        if (info != null) {
            ui = new UserAgentInfo();
            ui.setBrowserName(info.getUaFamily());
            ui.setBrowserVersion(info.getBrowserVersionInfo());
            ui.setOsName(info.getOsFamily());
            ui.setOsVersion(info.getOsName());
        }
return ui;
    }

    /**
     * 封装浏览器相关属性
     */
    public static class UserAgentInfo {
        private String browserName;
        private String browserVersion;
        private String osName;
        private String osVersion;

        public String getBrowserName() {
            return browserName;
        }

        public void setBrowserName(String browserName) {
            this.browserName = browserName;
        }

        public String getBrowserVersion() {
            return browserVersion;
        }

        public void setBrowserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
        }

        public String getOsName() {
            return osName;
        }

        public void setOsName(String osName) {
            this.osName = osName;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public UserAgentInfo() {
        }

        public UserAgentInfo(String browserName, String browserVersion, String osName, String osVersion) {
            this.browserName = browserName;
            this.browserVersion = browserVersion;
            this.osName = osName;
            this.osVersion = osVersion;
        }

        @Override
        public String toString() {
            return "UserAgentUtile{" +
                    "browserName='" + browserName + '\'' +
                    ", browserVersion='" + browserVersion + '\'' +
                    ", osName='" + osName + '\'' +
                    ", osVersion='" + osVersion + '\'' +
                    '}';
        }
    }
}