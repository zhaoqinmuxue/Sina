package org.aoli.weibo.util.net;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    private static OkHttpClient client = new OkHttpClient();

    public static void doPost(String address, RequestBody requestBody, okhttp3.Callback callback){
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void doGet(String address,okhttp3.Callback callback){
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
