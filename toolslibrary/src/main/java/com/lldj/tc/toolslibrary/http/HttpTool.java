package com.lldj.tc.toolslibrary.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.lldj.tc.toolslibrary.util.Clog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* 创建一个新的类 HttpTool，将公共的操作抽象出来
 * 为了避免调用sendRequest方法时需实例化，设置为静态方法
 * 传入msgListener对象为了方法回调
 * 因为网络请求比较耗时，一般在子线程中进行，
 * 为了获得服务器返回的数据，需要使用java的回调机制 */

public class HttpTool {
    private static int timeout = 8000;
    private static SSLContext ctx                   = null;
    private static HostnameVerifier verifier        = null;
    private static SSLSocketFactory socketFactory   = null;


    public static void sendGet(final String url, final msgListener listener) {
        Clog.e("url", url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con = null;
                try {
                    URL u = new URL(url);
                    if ("https".equals(u.getProtocol())) {// ssl证书管理在这里
                        HttpsURLConnection connHttps = (HttpsURLConnection) u.openConnection();
                        connHttps.setSSLSocketFactory(socketFactory);
                        connHttps.setHostnameVerifier(verifier);
                        con = connHttps;
                    } else {
                        con = (HttpURLConnection) u.openConnection();
                    }

                    con.setRequestMethod("GET");
                    con.setConnectTimeout(timeout);
                    con.setReadTimeout(timeout);

                    final int code = con.getResponseCode();
                    StringBuffer buffer = new StringBuffer();
                    if(code == HttpURLConnection.HTTP_OK)
                    {
                        InputStreamReader in = new InputStreamReader(con.getInputStream());
                        BufferedReader bf = new BufferedReader(in);

                        String temp;
                        while ((temp = bf.readLine()) != null) {
                            buffer.append(temp);
                            buffer.append("\n");
                        }
                        Log.w("HTTP", "buffer" + buffer.toString());

                        in.close();
                    }

                    final String ret = buffer.toString();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(code, ret);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(-1000, "failed to connect to");
                        }
                    });
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }
        }).start();
    }


    /*
     * 将参数转换为字符串后传入
     * 也可以传入键值对集合，再处理 */
    public static void sendRequestObj(final String url, final Object params, final msgListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;

                try {
                    URL _url=new URL(url);
                    httpURLConnection= (HttpURLConnection) _url.openConnection();

                    httpURLConnection.setConnectTimeout(timeout);//设置连接超时时间，单位ms
                    httpURLConnection.setReadTimeout(timeout);//设置读取超时时间，单位ms
                    httpURLConnection.setDoOutput(true);//设置是否向httpURLConnection输出，因为post请求参数要放在http正文内，所以要设置为true
                    httpURLConnection.setDoInput(true);//设置是否从httpURLConnection读入，默认是false
                    httpURLConnection.setUseCaches(false);//POST请求不能用缓存，设置为false

                    //如果不设置此项，传送序列化对象时，当WEB服务默认的不是这种类型时，会抛出java.io.EOFException错误
                    httpURLConnection.setRequestProperty("Content-type","application/x-java-serialized-object");
                    httpURLConnection.setRequestMethod("POST");   //设置请求方法是POST
                    httpURLConnection.connect();   //连接服务器
                    OutputStream os= httpURLConnection.getOutputStream();
                    ObjectOutputStream objOut = new ObjectOutputStream(os);  //构建输出流对象，以实现输出序列化的对象
                    objOut.writeObject(params);  //向对象输出流写出数据，这些数据将存到内存缓冲区中
                    objOut.flush();  //刷新对象输出流，将字节全部写入输出流中
                    objOut.close();  //关闭流对象
                    os.close();

                    final  int code = httpURLConnection.getResponseCode();
                    String result = "";

                    if (code == HttpURLConnection.HTTP_OK) {
                        InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
                        BufferedReader bf = new BufferedReader(in);
                        String recieveData = null;

                        while ((recieveData = bf.readLine()) != null) {
                            result += recieveData + "\n";
                        }
                        in.close();
                        httpURLConnection.disconnect();
                    } else {
                        Log.w("HTTP", "Connction failed" + httpURLConnection.getResponseCode());

                    }

                    final String ret = result;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(code, ret);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(-1000, "failed to connect to");
                        }
                    });
                }finally {
                    if (httpURLConnection != null) { httpURLConnection.disconnect(); }
                }
            }
        }).start();
    }

    public static void sendPost(final String url, final String token, final String params, final msgListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                System.out.println("url:" + url);
                System.out.println("params:" + params);
                try {
                    URL _url = new URL(url);
                    httpURLConnection = (HttpURLConnection) _url.openConnection();
                    httpURLConnection.setConnectTimeout(timeout);//设置连接超时时间，单位ms
                    httpURLConnection.setReadTimeout(timeout);//设置读取超时时间，单位ms
                    httpURLConnection.setDoOutput(true);//设置是否向httpURLConnection输出，因为post请求参数要放在http正文内，所以要设置为true
                    httpURLConnection.setDoInput(true);//设置是否从httpURLConnection读入，默认是false
                    httpURLConnection.setUseCaches(false);//POST请求不能用缓存，设置为false
                    httpURLConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8"); //设置请求属性
                    httpURLConnection.setRequestProperty("connection", "keep-alive");
                    httpURLConnection.setRequestProperty("Authorization", token);
                    httpURLConnection.setRequestMethod("POST");
                    DataOutputStream os = new DataOutputStream( httpURLConnection.getOutputStream());
                    String content = String.valueOf(params);
                    os.writeBytes(content);
                    os.flush();  //刷新对象输出流，将字节全部写入输出流中
                    os.close();  //关闭流对象

                    final int code = httpURLConnection.getResponseCode();
                    String result = "";

                    if (code == HttpURLConnection.HTTP_OK) {
                        InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
                        BufferedReader bf = new BufferedReader(in);
                        String recieveData = null;

                        while ((recieveData = bf.readLine()) != null) {
                            result += recieveData + "\n";
                        }
                        in.close();
                        httpURLConnection.disconnect();
                    } else {
                        Log.w("HTTP", "Connction failed" + httpURLConnection.getResponseCode());

                    }

                    final String ret = result;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(code, ret);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(-1000, "failed to connect to");
                        }
                    });
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void httpPost(final String url, final Map<String, String> params, final msgListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 构建请求参数
                StringBuffer sb = new StringBuffer();
                if (params != null) {
                    for (Map.Entry<String, String> e : params.entrySet()) {
                        sb.append(e.getKey());
                        sb.append("=");
                        sb.append(e.getValue());
                        sb.append("&");
                    }
                    sb.substring(0, sb.length() - 1);
                }
                System.out.println("send_url:" + url);
                System.out.println("send_data:" + sb.toString());

                HttpURLConnection con = null;

                try {
                    URL u = new URL(url);
                    if ("https".equals(u.getProtocol())) {// ssl证书管理在这里
                        HttpsURLConnection connHttps = (HttpsURLConnection) u.openConnection();
                        connHttps.setSSLSocketFactory(socketFactory);
                        connHttps.setHostnameVerifier(verifier);
                        con = connHttps;
                    } else {
                        con = (HttpURLConnection) u.openConnection();
                    }

                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    // cookie也可以在这里设置
                    // con.setRequestProperty("Set-Cookie", "");

                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setRequestProperty("connection", "keep-alive");
                    OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
                    osw.write(sb.toString());
                    osw.flush();
                    osw.close();

                    final int code = con.getResponseCode();
                    StringBuffer buffer = new StringBuffer();
                    if(code == HttpURLConnection.HTTP_OK)
                    {
                        InputStreamReader in = new InputStreamReader(con.getInputStream());
                        BufferedReader bf = new BufferedReader(in);

                        String temp;
                        while ((temp = bf.readLine()) != null) {
                            buffer.append(temp);
                            buffer.append("\n");
                        }
                        Log.w("HTTP", "buffer" + buffer.toString());
                        in.close();
                    }
                    con.disconnect();
                    final String ret = buffer.toString();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(code, ret);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(-1000, "failed to connect to");
                        }
                    });
                }finally { if (con != null) { con.disconnect(); } }
            }
        }).start();
    }

    public static void getBitmapUrl(final String url, final bmpListener listener ) {

        new Thread(new Runnable(){
            @Override
            public void run() {
                HttpURLConnection con = null;
                try {
                    URL u = new URL(url);
                    if ("https".equals(u.getProtocol())) {// ssl证书管理在这里
                        HttpsURLConnection connHttps = (HttpsURLConnection) u.openConnection();
                        connHttps.setSSLSocketFactory(socketFactory);
                        connHttps.setHostnameVerifier(verifier);
                        con = connHttps;
                    } else {
                        con = (HttpURLConnection) u.openConnection();
                    }
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(timeout);
                    con.setReadTimeout(timeout);
                    con.connect();
                    InputStream is = con.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(bitmap);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(null);
                        }
                    });
                }finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
            }

        }).start()  ;


    }

    public interface msgListener {
        void onFinish(int code, String msg);
    }

    public interface bmpListener {
        void onFinish(Bitmap bitmap);
    }

    static {
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new HttpTool.DefaultTrustManager(null)}, new SecureRandom());
            ctx.getClientSessionContext().setSessionTimeout(15);
            ctx.getClientSessionContext().setSessionCacheSize(1000);
            socketFactory = ctx.getSocketFactory();
        } catch (Exception ignored) {

        }

        verifier = new HostnameVerifier();
    }

    /**
     * 拼接get数据
     *
     * @param url    地址
     * @param params get参数
     * @return
     */
    private static String getQueryUrl(String url, Map<String, String> params) {
        StringBuilder neoUrl = new StringBuilder(url);
        if (params != null) {
            neoUrl.append("?");
            for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                neoUrl.append(stringStringEntry.getKey()).append("=").append(stringStringEntry.getValue()).append("&");
            }
            neoUrl = new StringBuilder(neoUrl.substring(0, neoUrl.length() - 1));
        }
        return neoUrl.toString();
    }


    private static class DefaultTrustManager implements X509TrustManager {
        private DefaultTrustManager(Object o) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

}