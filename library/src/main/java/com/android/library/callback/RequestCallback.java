package com.android.library.callback;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.android.library.constant.RequestConstant;

import java.lang.ref.WeakReference;

/**
 * 请求回调
 * Created by liyanan on 16/4/22.
 */
public abstract class RequestCallback<T> extends CallbackImpl<T> {

    private WeakReference<Activity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;
    private int type = RequestConstant.REQUEST_FROM_DEFAULT;

    public RequestCallback() {

    }

    public RequestCallback(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
        type = RequestConstant.REQUEST_FROM_ACTIVITY;
    }

    public RequestCallback(Fragment fragment) {
        fragmentWeakReference = new WeakReference<>(fragment);
        type = RequestConstant.REQUEST_FROM_FRAGMENT;
    }

    /**
     * 预处理
     * 如果activity或fragment被销毁,不会继续执行onSuccess和onError
     * 默认为true
     *
     * @return
     */
    @Override
    public boolean prepareProcess() {
        if (type == RequestConstant.REQUEST_FROM_ACTIVITY) {
            //Activity 请求
            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return false;
            }

        } else if (type == RequestConstant.REQUEST_FROM_FRAGMENT) {
            //Fragment请求
            Fragment fragment = fragmentWeakReference.get();
            if (fragment == null || fragment.isDetached() || fragment.getActivity() == null || fragment.getActivity().isFinishing()) {
                return false;
            }
        }
        return true;
    }
}
