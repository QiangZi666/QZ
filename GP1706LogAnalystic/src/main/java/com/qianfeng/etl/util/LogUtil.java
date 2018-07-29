package com.qianfeng.etl.util;

import com.qianfeng.common.EventLogsConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每一行的日志解析工具
 */
public class LogUtil {
    private static final Logger logger = Logger.getLogger(LogUtil.class);

    /**
     * 日志解析
     *
     * 192.168.216.111^A1532576375.965^A192.168.216.111^A
     * /index.html?ver=1.0&u_mid=123&en=e_cr&c_time=1532576375614&
     * ip:192.168.216.111
     * s_time:1532576375.965
     * ver:1.0
     *
     * @return
     */

    public static Map<String, String> handleLog(String logText) throws IOException {
        Map<String, String> info = new ConcurrentHashMap<String, String>();
        //判断logText是否为空
        if (StringUtils.isNotBlank(logText)) {
            String[] fields = logText.split(EventLogsConstant.LOG_SEPARTOR);
            if (fields.length ==4) {
                //将字段存储到info中
                info.put(EventLogsConstant.LOG_COLUMN_NAME_IP, fields[0]);
                info.put(EventLogsConstant.LOG_COLUMN_NAME_SERVER_TIME, fields[1].replaceAll("\\.", ""));
                //判断是否有参数列表
                int index = fields[3].indexOf("?");                if (index > 0) {
                    //获取参数列表
                    String requestBody = fields[3].substring(index + 1);
                    //处理参数
                    handlePaeam(requestBody, info);
                    //解析ip
                    handleIP(info);
                    //解析userAgent
                    handleUserAgent(info);
                }

            }

        }
        return info;
    }

    //处理参数列表
    private static void handlePaeam(String requestBody, Map<String, String> info) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(requestBody) && !info.isEmpty()) {
            //拆分
            String params[] = requestBody.split("&");
            for (String param : params) {
                int index = param.indexOf("=");
                if (index > 0) {
                    //拆分param
                    String[] kvs = param.split("=");
                    //info
                    String k = kvs[0];
                    String v = URLDecoder.decode(kvs[1], "UTF-8");
                    if (StringUtils.isNotBlank(k)) {
                        info.put(k, v);
                    }
                }
            }
        }
    }

    /**
     * 解析ip后存储到info中
     */
    private static void handleIP(Map<String, String> info) {
        String ip = info.get(EventLogsConstant.LOG_COLUMN_NAME_IP);
        if (StringUtils.isNotEmpty(ip)) {
            IpParserUtil.RegionInfo regionInfo = new IpParserUtil().parserIp(ip);
            if (regionInfo != null) {
                info.put(EventLogsConstant.LOG_COLUMN_NAME_COUNTRY, regionInfo.getCountry());
                info.put(EventLogsConstant.LOG_COLUMN_NAME_PROVINCE, regionInfo.getProvince());
                info.put(EventLogsConstant.LOG_COLUMN_NAME_CITY, regionInfo.getCity());
            }
        }
    }

    /**
     * 解析userAgent
     */
    private static void handleUserAgent(Map<String, String> info) throws IOException {
        //获取userAgent的字段
        String userAgent = info.get(EventLogsConstant.LOG_COLUMN_NAME_USERAGENT);
        if (StringUtils.isNotEmpty(userAgent)) {
            UserAgentUtile.UserAgentInfo userAgentInfo = UserAgentUtile.
                    parserUserAgent(userAgent);
            if (userAgentInfo != null) {
                info.put(EventLogsConstant.LOG_COLUMN_NAME_BROWSER_NAME, userAgentInfo.getBrowserName());
                info.put(EventLogsConstant.LOG_COLUMN_NAME_BROWSER_VERSION, userAgentInfo.getBrowserVersion());
                info.put(EventLogsConstant.LOG_COLUMN_NAME_OS_NAME, userAgentInfo.getOsName());
                info.put(EventLogsConstant.LOG_COLUMN_NAME_OS_VERSION, userAgentInfo.getOsVersion());

            }
        }
    }


}
