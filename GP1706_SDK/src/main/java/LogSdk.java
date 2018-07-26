package main.java;


import sun.util.logging.PlatformLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LogSdk {
    //日志打印对象
    private static final Logger logger = Logger.getGlobal();
    //定义常量
    private static final String ver = "1.0";
    private static final String platformName = "jave_server";
    private static final String chargeSuccess = "e_cs";
    private static final String chargeRefund = "e_cr";
    private static final String sdkName = "java_sdk";
    private static final String requestUrl = "http://192.168.216.64/index.html";


    /**
     * 支付成功事件,成功返回true,失败返回false
     */


    private static boolean chargeSuccess(String mid,String oid,String flag){
        if (isEmpty(mid)||isEmpty(oid)){
            Logger.log(PlatformLogger.Level.WARNING,"mid or oid is null "+"but both is must not null");
            return false;
        }
      //umi oid 肯定不为空
        try {
            Map<String,String > data = new HashMap<String, String>();
            if(isEmpty(flag)||flag.equals("1")){
                data.put("en",chargeSuccess);
            }else if (flag.equals("2")){
                data.put("en",chargeRefund)
            }
            data.put("en",chargeSuccess);
            data.put("pl",platformName);
            data.put("sdk",sdkName);
            data.put("c_time",System.currentTimeMillis());
            data.put("ver",ver);
            data.put("u_mid",mid);
            data.put("oid",oid);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("请求事件失败")
        }
        /**
         *
         */
        public static String buildUrl(Map<String ,String> data){
            if(data)
        }
    }
    public static boolean isEmpty(String input){
        return input == null || input.trim().equals("")|| input.trim().l
    }
    public static boolean isNoEmpty(String input){
        return !isEmpty(input);
    }

    public static void main(String[] args) {

    }
}
