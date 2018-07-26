package com.qianfeng.etl.util;

import com.qianfeng.etl.util.ip.IPSeeker;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * ip的解析工具类，最终该会调用父类ipSeeker
 * 如果ip为Null，则返回null
 * 如果ip是国外的ip，则直接显示国家即可。
 * 如果ip是国内，则直接显示国家  省   市
 */
public class IpParserUtil extends IPSeeker {
    private static final Logger logger = Logger.getLogger(IpParserUtil.class);
   private RegionInfo info = new RegionInfo();
    /**
     * 解析ip的方法,返回地域信息
     * @param ip
     * @return
     */
    public RegionInfo parserIp(String ip){
        //判断IP是否为空
        if(StringUtils.isEmpty(ip)||StringUtils.isEmpty(ip.trim())){
            return info;
        }
        try{
            String country = super.getCountry(ip);
            if("局域网".equals(country)){
                info.setCountry("中国");
                info.setProvince("北京");
                info .setCity("昌平区");
            }else if (country!=null&&StringUtils.isNotEmpty(country)){
                info.setCountry("中国");
                int index = country.indexOf("省");
                if(index>0){
                    info.setProvince(country.substring(0,index+1));
                    int index2 = country.indexOf("市");
                    if (index2>0){
                        info.setCity(country.substring(index+1,Math.min(index2+1,country.length())));
                    }
                }else {
                    String flag = country.substring(0,2);
                    switch (flag){
                        case "内蒙":
                            info.setProvince("内蒙古");
                            country=country.substring(3);
                            index=country.indexOf("市");
                            if (index>0){
                                info.setCity(country.substring(0,Math.min(index+1,country.length())));
                            }
                            break;
                        case "广西":
                        case "西藏":
                        case "新疆":
                        case "宁夏":
                            info.setProvince(flag);
                            country = country.substring(2);
                            index = country.indexOf("市");
                            if(index>0){
                                info .setCity(country.substring(0,Math.min(index+1,country.length())));
                            }
                            break;
                        case "北京":
                        case "上海":
                        case "重庆":
                        case "天津":
                            info.setProvince(flag+"市");
                            country= country.substring(3);
                            index=country.indexOf("区");
                            if(index>0){
                                char ch = country.charAt(index-1);
                                if(ch !='小'&&ch!='校'&&ch!='军'){
                                    info.setCity(country.substring(0,Math.min(index+1,country.length())));

                                }
                            }
                            index = country.indexOf("县");
                            if (index>0){
                                info.setCity(country.substring(0,Math.min(index+1,country.length())));
                            }
                            break;
                        case  "香港":
                        case "澳门":
                        case "台湾":
                            info.setProvince(flag+"特别行政区");
                            break;
                        default:
                            break;
                    }
                }
            }
        }catch (Exception e){
            logger.warn("解析IP工具方法异常" ,e);
        }
        return info;
        }


    /**
     * 封装地域信息
     */
    public static class RegionInfo{
        private final String DEFAULT_VALUE = "unknown";
        private String country = DEFAULT_VALUE;
        private String province = DEFAULT_VALUE;
        private String city = DEFAULT_VALUE;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public RegionInfo(String country, String province, String city) {
            this.country = country;
            this.province = province;
            this.city = city;
        }

        public RegionInfo() {
        }

        @Override
        public String toString() {
            return "RegionInfo{" +
                    "DEFAULT_VALUE='" + DEFAULT_VALUE + '\'' +
                    ", country='" + country + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }
}
