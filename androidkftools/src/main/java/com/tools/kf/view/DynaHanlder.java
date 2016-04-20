package com.tools.kf.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理中间类
 * Created by LikeGo on 2016/3/30.
 */
public class DynaHanlder implements InvocationHandler {

    private Object object = null;
    private Method method = null;

    public DynaHanlder(Object object, Method method) {
        super();
        this.object = object;
        this.method = method;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.method.invoke(object,args);
    }
}