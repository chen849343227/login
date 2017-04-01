package com.chen.test.Utils;

import android.util.Log;

import com.chen.test.Entity.BaseEntity;
import com.chen.test.common.ICallbackListener;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by long on 17-3-22.
 */

public class HttpUtils {

    private static HttpUtils utils;

    Gson gson = new Gson();

    /**
     * 单例的Http对象
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (utils == null) {
            utils = new HttpUtils();
        }
        return utils;
    }

    /**
     * 请求网络
     *
     * @param url  地址
     * @param user 用户名
     * @param pass 密码
     */
    public void requestNet(String url, String user, String pass, final ICallbackListener<BaseEntity> listener) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("user", user)
                .add("pass", pass)
                .build();
        final Request request = new Request
                .Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("json",json);
                if(json!=null){
                    BaseEntity entity = gson.fromJson(json, BaseEntity.class);
                    if(entity.getCode()==0){
                        listener.onSuccess(entity);
                    }else {
                        listener.onFail(entity.getMsg());
                    }
                }
            }
        });
    }
}
