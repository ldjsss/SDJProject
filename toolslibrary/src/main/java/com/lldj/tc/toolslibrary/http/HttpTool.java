package com.lldj.tc.toolslibrary.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* 创建一个新的类 HttpTool，将公共的操作抽象出来
 * 为了避免调用sendRequest方法时需实例化，设置为静态方法
 * 传入msgListener对象为了方法回调
 * 因为网络请求比较耗时，一般在子线程中进行，
 * 为了获得服务器返回的数据，需要使用java的回调机制 */

public class HttpTool {
    private static int timeout = 8000;
    public static void sendGet(final String url, final msgListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL _url = new URL(url);
                    connection = (HttpURLConnection) _url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(timeout);
                    connection.setReadTimeout(timeout);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();

                    String line = "";
                    final int code = connection.getResponseCode();
                    if(HttpURLConnection.HTTP_OK == code){
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }else{}
                    final String ret = line;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(code, ret);
                        }
                    });
                    in.close();  //关闭创建的流
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    /*
     * 将参数转换为字符串后传入
     * 也可以传入键值对集合，再处理 */
    public static void sendRequestObj(final String url, final DataPost params){
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

                    //传送的内容是可序列化的
                    //如果不设置此项，传送序列化对象时，当WEB服务默认的不是这种类型时，会抛出java.io.EOFException错误
                    httpURLConnection.setRequestProperty("Content-type","application/x-java-serialized-object");
                    httpURLConnection.setRequestMethod("POST");   //设置请求方法是POST
                    httpURLConnection.connect();   //连接服务器

                    //getOutputStream会隐含调用connect()，所以不用写上述的httpURLConnection.connect()也行。
                    //得到httpURLConnection的输出流
                    OutputStream os= httpURLConnection.getOutputStream();
                    ObjectOutputStream objOut = new ObjectOutputStream(os);  //构建输出流对象，以实现输出序列化的对象

        //            //dataPost类是自定义的数据交互对象，只有两个成员变量
        //            dataPost data= new dataPost("Tom",null);
                    objOut.writeObject(params);  //向对象输出流写出数据，这些数据将存到内存缓冲区中
                    objOut.flush();  //刷新对象输出流，将字节全部写入输出流中
                    objOut.close();  //关闭流对象
                    os.close();

                    //将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端，并获取访问状态
                    if(HttpURLConnection.HTTP_OK==httpURLConnection.getResponseCode()){
                        //得到httpURLConnection的输入流，这里面包含服务器返回来的java对象
                        InputStream in=httpURLConnection.getInputStream();
                        //构建对象输入流，使用readObject()方法取出输入流中的java对象
                        ObjectInputStream inObj=new ObjectInputStream(in);

                        in.close();  //关闭创建的流
                        inObj.close();
                    }else{
                        Log.w("HTTP","Connction failed"+httpURLConnection.getResponseCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (httpURLConnection != null) { httpURLConnection.disconnect(); }
                }
            }
        }).start();
    }

    public static void sendPost(final String url, final JSONObject params, final msgListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;

                try {
                    URL _url = new URL(url);
                    httpURLConnection = (HttpURLConnection) _url.openConnection();

                    httpURLConnection.setConnectTimeout(timeout);//设置连接超时时间，单位ms
                    httpURLConnection.setReadTimeout(timeout);//设置读取超时时间，单位ms
                    httpURLConnection.setDoOutput(true);//设置是否向httpURLConnection输出，因为post请求参数要放在http正文内，所以要设置为true
                    httpURLConnection.setDoInput(true);//设置是否从httpURLConnection读入，默认是false
                    httpURLConnection.setUseCaches(false);//POST请求不能用缓存，设置为false
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.connect();   //连接服务器
                    OutputStream os = httpURLConnection.getOutputStream();
                    ObjectOutputStream objOut = new ObjectOutputStream(os);  //构建输出流对象，以实现输出序列化的对象
                    String content = String.valueOf(String.valueOf(params));
                    objOut.writeBytes(content);
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
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            }
        }).start();
    }

    public interface msgListener {
        void onFinish(int code, String msg);
    }
}