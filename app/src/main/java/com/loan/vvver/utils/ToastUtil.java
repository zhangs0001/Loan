package com.loan.vvver.utils;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;


/**
 *
 */

public class ToastUtil {
    /**
     * 系统默认样式toast
     */
    protected static Toast commontoast = null;
    // public static AppMsg msgToast;
    private static int oldMsgId;
    private static long oneTime = 0;
    private static long twoTime = 0;

    private static final int TYPE_APP = 0;
    private static final int TYPE_MSG = 1;
    private static int type = TYPE_APP;
    private static final int LENGTH = 2500;
    /**
     * 自定义样式toast
     */
    private static Toast toast;         //屏幕中间toast提示
    private static Toast operateToast; //操作成功提示，带√
    private static Toast noticeToast;  //带！提示

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            if (operateToast != null) {
                operateToast.cancel();
                operateToast = null;
            }
            if (noticeToast != null) {
                noticeToast.cancel();
                noticeToast = null;
            }
        }
    };




    /**
     * 系统样式toast提示，控制LENGTH时间间隔，不重复提示
     *
     * @param activity
     * @param id
     */
    public static void showToast(Activity activity, int id) {
        if (null == activity) {
            return;
        }

        if (TYPE_APP == type && null == commontoast) {
            displayToast(activity, id);
            oneTime = System.currentTimeMillis();
            oldMsgId = id;
        } else {
            twoTime = System.currentTimeMillis();
            if (oldMsgId == id) {
                if (twoTime - oneTime > LENGTH) {
                    display();
                    oneTime = twoTime;
                }
            } else {
                oldMsgId = id;
                setTextDisplay(id);
                oneTime = twoTime;
            }

        }

    }

    public static void showToast(Activity activity, String msg) {
        if (null == activity || TextUtils.isEmpty(msg)) {
            return;
        }
        if (TYPE_APP == type && null == commontoast) {
            displayToast(activity, msg);
            oneTime = System.currentTimeMillis();
            oldMsgId = msg.hashCode();
        } else {
            twoTime = System.currentTimeMillis();
            if (oldMsgId == msg.hashCode()) {
                if (twoTime - oneTime > LENGTH) {
                    display();
                    oneTime = twoTime;
                }
            } else {
                oldMsgId = msg.hashCode();
                setTextDisplay(msg);
                oneTime = twoTime;
            }
        }
    }


    private static void displayToast(Activity activity, int id) {
        if (TYPE_APP == type) {
            commontoast = Toast.makeText(activity, id, Toast.LENGTH_LONG);
            commontoast.show();
        }
    }

    private static void display() {
        if (TYPE_APP == type) {
            if (null != commontoast) {
                commontoast.show();
            }
        }
    }

    private static void setTextDisplay(int resId) {
        if (TYPE_APP == type) {
            if (null != commontoast) {
                commontoast.setText(resId);
                commontoast.show();
            }

        }
    }

    private static void setTextDisplay(String msg) {
        if (TYPE_APP == type) {
            if (null != commontoast) {
                commontoast.setText(msg);
                commontoast.show();
            }

        }
    }

    private static void displayToast(Activity activity, String msg) {
        if (TYPE_APP == type) {
            commontoast = Toast.makeText(activity, msg, Toast.LENGTH_LONG);
            commontoast.show();
        }
    }

    public static void cancelAll() {
        if (commontoast != null) {
            commontoast.cancel();
            commontoast = null;
        }
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        if (operateToast != null) {
            operateToast.cancel();
            operateToast = null;
        }
        if (noticeToast != null) {
            noticeToast.cancel();
            noticeToast = null;
        }
    }

    public static void showAppToast(String msg) {
//        Toast.makeText(BaseApplication.context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showAppToast(int resId) {
//        Toast.makeText(BaseApplication.context, resId, Toast.LENGTH_LONG).show();
    }
}
