package com.tools.kf.view;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tools.kf.utils.LogHelper;
import com.tools.kf.view.anotation.ContentView;
import com.tools.kf.view.anotation.Event;
import com.tools.kf.view.anotation.MethodInject;
import com.tools.kf.view.anotation.ViewInject;
import com.tools.kf.view.anotation.ViewInjector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashSet;

/***
 * 注解实现类
 *
 * @version v1.0
 * @类描述：实现ViewInjector接口
 * @项目名称：androidkftools
 * @包名： com.quick.djhframe.view.anotation
 * @类名称：ViewInjectorImpl
 * @创建人：djh
 * @创建时间：Dec 20, 20152:15:56 PM
 * @修改人：djh
 * @修改时间：Dec 20, 20152:15:56 PM
 * @修改备注：
 * @bug [nothing]
 * @Copyright go3c
 * @mail 2393101658@qq.com
 * @see [nothing]
 */
public class ViewInjectorImpl implements ViewInjector {


    private static final HashSet<Class<?>> IGNORED = new HashSet<Class<?>>();

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(android.app.Fragment.class);
        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class ViewInjectorHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static ViewInjectorImpl viewinjector = new ViewInjectorImpl();

    }

    /**
     * 防止new实例
     */
    private ViewInjectorImpl() {

    }

    /**
     * 返回ViewInjectorImpl实例
     *
     * @return
     */
    public static ViewInjectorImpl getInsatnce() {
        return ViewInjectorHolder.viewinjector;
    }

    @Override
    public void inject(View view) {

    }

    @Override
    public void inject(Activity activity) {
        // 获取Activity的ContentView的注解--获取当前运行对象的类
        Class<?> windowobjtype = activity.getClass();
        try {
            ContentView contentView = findContentView(windowobjtype);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    // 反射--参数（方法名与参数）
                    Method setContentViewMethod = windowobjtype.getMethod(
                            "setContentView", int.class);
                    // 执行setContentView 方法
                    setContentViewMethod.invoke(activity, viewId);

                }
            }
        } catch (Throwable ex) {
            //LogHelper.LogD(ex.getMessage()==null?"为捕捉到异常":ex.getMessage());
        }

        injectObject(activity, windowobjtype, new ViewFinder(activity));
    }

    /**
     * 递归找父类元注解contentview
     *
     * @param windowobjtype
     * @return
     * @throws
     * @描述:
     * @方法名: findContentView
     * @返回类型 ContentView
     * @创建人 djh
     * @创建时间 Dec 20, 20152:12:15 PM
     * @修改人 djh
     * @修改时间 Dec 20, 20152:12:15 PM
     * @修改备注
     */
    private ContentView findContentView(Class<?> windowobjtype) {
        if (windowobjtype == null) {
            return null;
        }
        ContentView contentview = windowobjtype
                .getAnnotation(ContentView.class);
        // 递归找父类元注解contentview
        if (contentview == null) {
            contentview = findContentView(windowobjtype.getSuperclass());
        }
        return contentview;
    }

    @Override
    public void inject(Object windowobj, View view) {
        injectObject(windowobj, windowobj.getClass(), new ViewFinder(view));
    }

    @Override
    public View inject(Object fragment, LayoutInflater inflater,
                       ViewGroup container) {

        // inject ContentView
        View view = null;
        Class<?> handlerType = fragment.getClass();
        try {
            ContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    view = inflater.inflate(viewId, container, false);
                }
            }
        } catch (Throwable ex) {
            LogHelper.LogD(ex.getMessage());
        }

        // inject res & event
        injectObject(fragment, handlerType, new ViewFinder(view));
        return view;
    }


    /***
     * 对Activity中的控件注入 事件注入
     *
     * @param windowobj
     * @param windowobjtype
     * @param viewFinder
     */
    private void injectObject(Object windowobj, Class<?> windowobjtype,
                              ViewFinder viewFinder) {
        if (windowobjtype == null || IGNORED.contains(windowobjtype)) {
            return;
        }
        Field[] fields = windowobjtype.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {

                Class<?> fieldType = field.getType();
                if (
                /* 不注入静态字段 */     Modifier.isStatic(field.getModifiers()) ||
                /* 不注入final字段 */    Modifier.isFinal(field.getModifiers()) ||
                /* 不注入基本类型字段 */  fieldType.isPrimitive() ||
                /* 不注入数组类型字段 */  fieldType.isArray()) {
                    continue;
                }
                ViewInject viewInject = field
                        .getAnnotation(ViewInject.class);
                if (viewInject != null) {
                    try {
                        // 如果有父类布局id--父类布局下下找控件否则再当前页面对象布局找
                        View view = viewFinder.findViewById(
                                viewInject.value(),viewInject.parentId());

                        if (view != null) {
                            field.setAccessible(true);
                            // 绑定控件--控件类型变量指向对象
                            field.set(windowobj, view);
                        } else {
                            throw new RuntimeException("Invalid id("
                                    + viewInject.value()
                                    + ") for @ViewInject!");
                        }
                    } catch (Throwable ex) {
                        LogHelper.LogD(ex.getMessage());
                    }
                }
            }
        }

        //所有方法
        Method[] methods = windowobjtype.getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {

                if (Modifier.isStatic(method.getModifiers())
                        || !Modifier.isPrivate(method.getModifiers())) {
                    continue;
                }

                //获取方法上的注解即要绑定的控件id集合
                Event event = method.getAnnotation(Event.class);
                if (event != null) {
                    //获取事件注解id集合
                    int[] ids = event.value();
                    int[] parentIds = event.parentId();
                    int parentIdsLen = parentIds == null ? 0 : parentIds.length;
                    try {
                        for (int i = 0; i < ids.length; i++) {
                            int id = ids[i];
                            if (id > 0) {
                                ViewInfo info = new ViewInfo();
                                info.value = id;
                                info.parentId = parentIdsLen > i ? parentIds[i] : 0;
                                method.setAccessible(true);
                                EventListenerManager.addEventMethod(viewFinder, info, event, windowobj, method);
                            }
                        }
                    } catch (Exception e) {
                        LogHelper.LogD(e.getMessage());
                    }

                }

            }
        }

        injectObject(windowobj, windowobjtype.getSuperclass(), viewFinder);

    }

}
