package com.chen.mvp.App;

import android.app.Application;

import com.chen.mvp.api.RetrofitService;
import com.chen.mvp.injector.components.AppComponent;
import com.chen.mvp.injector.components.DaggerAppComponent;
import com.chen.mvp.injector.modules.AppModule;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * Created by long on 17-3-24.
 */

public class App extends Application {

    public static App app;
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = (App) getApplicationContext();
        _initInjector();
        _initConfig();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    /**
     * 初始化注射器
     */
    private void _initInjector() {
        // 这里不做注入操作，只提供一些全局单例数据
        sAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }

    /**
     * 初始化配置
     */
    private void _initConfig() {
        Logger.init("LogTAG");
        //初始化网络配置
        RetrofitService.init();
    }

}
