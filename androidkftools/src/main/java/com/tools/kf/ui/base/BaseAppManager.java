package com.tools.kf.ui.base;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/25 0025.
 */
public class BaseAppManager {

    private static final String TAG = BaseAppManager.class.getSimpleName();
    private static BaseAppManager instance;
    private static List<Activity> activitylist = new LinkedList<>();

    public static BaseAppManager getInstance() {
        if (instance == null) {
            synchronized (BaseAppManager.class) {
                if (instance == null) {
                    instance = new BaseAppManager();
                }
            }
        }
        return instance;
    }

    private BaseAppManager() {
    }

    public int getSize() {
        return activitylist.size();
    }

    public synchronized Activity getForwardActivity() {
        return getSize() > 0 ? activitylist.get(getSize() - 1) : null;
    }

    public synchronized void clear() {
        for (int i = activitylist.size() - 1; i > -1; i--) {
            Activity activity = activitylist.get(i);
            removeActivity(activity);
            activity.finish();
            i = activitylist.size();
        }
    }

    public synchronized void removeActivity(Activity activity) {
        if (activitylist.contains(activity))
            activitylist.remove(activity);
    }

    public synchronized void addActivity(Activity activity) {
        if (activity != null)
            activitylist.add(activity);
    }
}
