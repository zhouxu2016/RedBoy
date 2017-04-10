package utils;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class MyUtils {
    /**
     * 将int类型的ip转换成String类型的ip比如：192.168.0.123
     * @param ip
     * @return
     */
    public static String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }

    /**
     * 根据ip得到它的路由器ip，比如可以由192.168.0.123得到192.168.0.1
     * @param localIp
     * @return
     */
    public static String getRouterIp(String localIp){
        String routerIp=null;
        if(localIp!=null){
            routerIp = localIp.substring(0, localIp.lastIndexOf("."));
            routerIp=routerIp+".1";
        }
        return routerIp;
    }




}
