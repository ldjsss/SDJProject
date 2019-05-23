package com.lldj.tc.toolslibrary.view;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lldj.tc.toolslibrary.R;


public class ToastUtils {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    public static Toast toast;
    public static TextView textView;
    public static ImageView pic;
    private static Handler handler = new Handler();

    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
            toast = null;
        }
    };

    private static void toast(Context ctx, CharSequence msg, int duration) {
        handler.removeCallbacks(run);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.toast_default, null);
        textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(msg);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 2000;
                break;
            default:
                break;
        }
        if (null != toast) {
            textView.setText(msg);
        } else {
            if (duration == 1000) {
                toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
            }

            // toast = new Toast(ctx);
            // toast.setView(view);
            // toast.setDuration(duration);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }

    private static void toast2(Context ctx, CharSequence msg, int duration) {
        handler.removeCallbacks(run);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.toast_default, null);
        textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(msg);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 2000;
                break;
            default:
                break;
        }
        if (null != toast) {
            textView.setText(msg);
        } else {
            // toast = android.widget.Toast.makeText(ctx, msg, duration);
            toast = new Toast(ctx);
            toast.setView(view);
            if (duration == 1000) {
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast.setDuration(Toast.LENGTH_LONG);
            }

            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }

    private static void toast_pic(Context ctx, int resId, CharSequence msg, int duration) {
        handler.removeCallbacks(run);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.toast_pic, null);
        textView = (TextView) view.findViewById(R.id.toast_text);
        pic = (ImageView) view.findViewById(R.id.icon_iv);
        textView.setText(msg);
        pic.setImageResource(resId);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 2000;
                break;
            default:
                break;
        }
        if (null != toast) {
            textView.setText(msg);
            pic.setImageResource(resId);
            toast.setView(view);
        } else {
            toast = new Toast(ctx);
            toast.setView(view);
            if (duration == 1000) {
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast.setDuration(Toast.LENGTH_LONG);
            }

            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }

    /**
     * 弹出Toast
     *
     * @param ctx      弹出Toast的上下文
     * @param msg      弹出Toast的内容
     * @param duration 弹出Toast的持续时间
     */
    public static void show(Context ctx, CharSequence msg, int duration) throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        if (0 > duration) {
            duration = LENGTH_SHORT;
        }
        toast(ctx, msg, duration);
    }

    /**
     * @param ctx
     * @param msg
     * @param duration
     * @throws NullPointerException
     * @Description 在中间显示的tost
     * @author wlj
     * @date 2015-8-28 下午12:05:26
     */
    public static void show_middle(Context ctx, CharSequence msg, int duration) {
        try {
            if (null != ctx) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast2(ctx, msg, duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void show_middle_pic(Context ctx, int resId, CharSequence msg, int duration) {
        try {
            if (null != ctx && !"".equals(ctx)) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast_pic(ctx, resId, msg, duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void show_middle(Context ctx, int resId, int duration) {
        try {
            if (null != ctx && !"".equals(ctx)) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast2(ctx, ctx.getResources().getString(resId), duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    /**
     * 弹出Toast
     *
     * @param ctx      弹出Toast的上下文
     * @param resId    弹出Toast的内容的资源ID
     * @param duration 弹出Toast的持续时间
     */
    public static void show(Context ctx, int resId, int duration) throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        if (0 > duration) {
            duration = LENGTH_SHORT;
        }
        toast(ctx, ctx.getResources().getString(resId), duration);
    }
}
