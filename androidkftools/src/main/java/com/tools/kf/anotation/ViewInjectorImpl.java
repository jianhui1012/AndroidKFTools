package com.tools.kf.anotation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tools.kf.utils.LogHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/***
 * 注解实现类
 *
 * @version v1.0
 * @类描述：实现ViewInjector接口
 * @项目名称：DjhFrame
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

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class ViewInjectorHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static ViewInjectorImpl viewinjector = new ViewInjectorImpl();

    }

    ;

    private ViewInjectorImpl() {
    }

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
            LogHelper.LogD(ex.getMessage());
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

    private void injectObject(Object windowobj, Class<?> windowobjtype,
                              ViewFinder viewFinder) {

        Field[] fields = windowobjtype.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    ViewInject viewInject = field
                            .getAnnotation(ViewInject.class);
                    if (viewInject != null) {
                        try {
                            // 如果有父类布局id--父类布局下下找控件否则再当前页面对象布局找
                            View view = viewFinder.findViewById(
                                    viewInject.value(), viewInject.parentId());

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
                } catch (Exception e) {
                    LogHelper.LogD(e.getMessage());
                }
            }
        }
    }

}
