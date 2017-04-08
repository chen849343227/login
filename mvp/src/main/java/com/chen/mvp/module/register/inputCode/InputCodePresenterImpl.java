package com.chen.mvp.module.register.inputCode;

import com.chen.mvp.api.RetrofitService;
import com.chen.mvp.bean.BaseInfo;
import com.chen.mvp.utils.MobilePhone;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by long on 17-4-7.
 */

public class InputCodePresenterImpl implements IInputCodePresenter{

    private IInputCodeView mView;

    public InputCodePresenterImpl(IInputCodeView mView){
        this.mView = mView;
    }

    @Override
    public void getData(String user, String pass) {

    }

    @Override
    public void getData(String phone) {
        if (!MobilePhone.isMobileNO(phone)) {
            mView.showAnimation("");
            return;
        }
        mView.showLoading();
        RetrofitService
                .getCheckCode(phone)
                .subscribe(new Observer<BaseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseInfo info) {
                        Logger.e(info.toString());
                        mView.loadInfo(info);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showToast(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void checkCode(String code) {
        if(code.length()!=6){
            mView.showToast("请输入正确位数的验证码");
            return;
        }
        mView.showLoading();
        RetrofitService
                .verifyCheckCode(code)
                .subscribe(new Observer<BaseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseInfo info) {
                        Logger.e(info.toString());
                        mView.loadInfo(info);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
