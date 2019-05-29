package com.lldj.tc.toolslibrary.retrofit;

/**
 * description  : 说明  （2XX 都是成功的；5XX是服务器错误，客户端可提示：哎呀，断网之类的）
 */
public class RetrofitConfig {
    public final static int HTTP_CONNECT_TIMEOUT = 1000 * 10;
    public final static int HTTP_READ_TIMEOUT = 1000 * 10;
    public static final String TEST_HOST_URL_1024 = "http://101.89.91.206:1024/api/v1/lldj/";
    public static final String TEST_HOST_URL_9999 = "http://101.89.91.206:9999/api/v1/lldj/";
    //断网的code 随便写的
    public static final int CODE_NO_INTERNER = 10000;

    //用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
    public static final int CODE_REQUEST_ERROR = 400;
    //表示用户没有权限（令牌、用户名、密码错误）。
    public static final int CODE_NO_PERMISSION = 401;
    //用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
    public static final int CODE_NO_EXIT = 404;
    //用户请求的http method 错误
    public static final int CODE_HTTP_METHOD_ERROR = 405;
    //当创建一个对象时，发生一个验证错误。
    public static final int CODE_VERIFICATION_ERROR = 422;

    public static final int CODE_ORDER_ERROR = 424;


}
