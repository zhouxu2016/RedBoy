package utils;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 网络请求的工具类
 */
public class NetworkRequestUtils {


    /** Get请求
     * @param url
     * @param requestCallBack
     */
    public static void sendGet(String url,RequestCallBack<String> requestCallBack) {

        HttpUtils httpUtils = new HttpUtils();

        httpUtils.send(HttpRequest.HttpMethod.GET,url,requestCallBack);

    }



    /**  Post请求
     * @param url
     * @param params
     * @param requestCallBack
     */
    public static void sendPost(String url, RequestParams params,RequestCallBack<String> requestCallBack) {

        HttpUtils httpUtils = new HttpUtils();

        httpUtils.send(HttpRequest.HttpMethod.POST,url,params,requestCallBack);
    }

}
