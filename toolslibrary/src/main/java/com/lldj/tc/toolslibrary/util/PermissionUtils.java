package com.lldj.tc.toolslibrary.util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.lldj.tc.toolslibrary.R;
import com.lldj.tc.toolslibrary.view.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wang on 2017/4/20.
 */

public class PermissionUtils {
    public static PermissionUtils instance;

    public static PermissionUtils getInstance() {
        if (null == instance) {
            instance = new PermissionUtils();
        }

        return instance;
    }

    private static final String TAG = PermissionUtils.class.getSimpleName();
    private boolean isFirst = true;
    //麦克风权限
    public static final int CODE_RECORD_AUDIO = 0;
    public static final int CODE_GET_ACCOUNTS = 1;
    //读取手机
    public static final int CODE_READ_PHONE_STATE = 2;
    //打电话
    public static final int CODE_CALL_PHONE = 3;
    //照相机
    public static final int CODE_CAMERA = 4;
    public static final int CODE_ACCESS_FINE_LOCATION = 5;
    public static final int CODE_ACCESS_COARSE_LOCATION = 6;
    public static final int CODE_READ_EXTERNAL_STORAGE = 7;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = 8;
    //多个权限申请回调
    public static final int CODE_MULTI_PERMISSION = 100;

    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);

        void onPermissionRefused();
    }

    /**
     * Requests permission.
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static void requestPermission(final Activity activity, final int requestCode, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }

        Clog.i(TAG, "requestPermission requestCode:" + requestCode);
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Clog.w(TAG, "requestPermission illegal requestCode:" + requestCode);
            return;
        }

        final String requestPermission = requestPermissions[requestCode];

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以在这个地方，低于23就什么都不做，
        // 个人建议try{}catch(){}单独处理，提示用户开启权限。
//        if (Build.VERSION.SDK_INT < 23) {
//            return;
//        }

        int checkSelfPermission;
        try {
            checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        } catch (Exception e) {
            Clog.e(TAG, "RuntimeException:" + e.getMessage());
            String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions_exception);
            if (Build.VERSION.SDK_INT < 23) {
                openSettingActivity(activity, permissionsHint[requestCode], permissionGrant);
            } else {
                ToastUtils.show_middle_pic(activity, R.mipmap.cancle_icon, permissionsHint[requestCode], ToastUtils.LENGTH_SHORT);
            }
            return;
        }

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            Clog.i(TAG, "ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED");
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            //如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don’t ask again 选项，此方法将返回 false。
            // 如果设备规范禁止应用具有该权限，此方法也会返回 false。
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
//                shouldShowRationale(activity, requestCode, requestPermission);
//            } else {
            Clog.d(TAG, "requestCameraPermission else");
            ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
//            }

        } else {
            Clog.d(TAG, "ActivityCompat.checkSelfPermission == PackageManager.PERMISSION_GRANTED" + "  opened:" + requestPermissions[requestCode]);
            permissionGrant.onPermissionGranted(requestCode);
        }
    }

    //多个权限申请的返回结果
    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionGrant permissionGrant) {
        if (activity == null) {
            return;
        }
        //TODO
        Clog.e(TAG, "onRequestPermissionsResult permissions length:" + permissions.length);
        Map<String, Integer> perms = new HashMap<>();
        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            Clog.e(TAG, "permissions: [i]:" + i + ", permissions[i]" + permissions[i] + ",grantResults[i]:" + grantResults[i]);
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }
        if (notGranted.size() == 0) {
            permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            String mNotGrantedperm = "";
            String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions_exception);
            for (int i = 0; i < notGranted.size(); i++) {
                switch (notGranted.get(i)) {
                    case PERMISSION_RECORD_AUDIO:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[0];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[0];
                        }
                        break;
                    case PERMISSION_GET_ACCOUNTS:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[1];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[1];
                        }
                        break;
                    case PERMISSION_READ_PHONE_STATE:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[2];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[2];
                        }
                        break;
                    case PERMISSION_CALL_PHONE:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[3];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[3];
                        }
                        break;
                    case PERMISSION_CAMERA:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[4];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[4];
                        }
                        break;
                    case PERMISSION_ACCESS_FINE_LOCATION:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[5];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[5];
                        }
                        break;
                    case PERMISSION_ACCESS_COARSE_LOCATION:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[6];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[6];
                        }
                        break;
                    case PERMISSION_READ_EXTERNAL_STORAGE:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[7];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[7];
                        }
                        break;
                    case PERMISSION_WRITE_EXTERNAL_STORAGE:
                        if ("".equals(mNotGrantedperm)) {
                            mNotGrantedperm = permissionsHint[8];
                        } else {
                            mNotGrantedperm = mNotGrantedperm + "\n" + permissionsHint[8];
                        }
                        break;
                }
            }
            openSettingActivity(activity, mNotGrantedperm, permissionGrant);
        }

    }


    //一次申请多个权限
    public static void requestMultiPermissions(final Activity activity, String[] resuestPermissions, PermissionGrant grant) {
        final List<String> permissionsList = getNoGrantedPermission(activity, resuestPermissions, false);
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, resuestPermissions, true);
        if (permissionsList == null || shouldRationalePermissionsList == null) {
            return;
        }
        Log.d(TAG, "requestMultiPermissions permissionsList:" + permissionsList.size() + ",shouldRationalePermissionsList:" + shouldRationalePermissionsList.size());
        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
        } else if (shouldRationalePermissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]),
                    CODE_MULTI_PERMISSION);
        } else {
            grant.onPermissionGranted(CODE_MULTI_PERMISSION);
        }
    }

    //显示弹框
    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //TODO
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
//        DialogUtils.getInstance().showSelectDialog(activity, permissionsHint[requestCode], activity.getString(R.string.str_qu_xiao),activity.getString(R.string.confirm), new DialogUtils.DialogClickListener() {
//            @Override
//            public void confirm() {
//                ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//        });

    }


    /**
     * @param activity
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(final Activity activity, final int requestCode, @NonNull String[] permissions,
                                                @NonNull int[] grantResults, PermissionGrant permissionGrant) {

        if (activity == null) {
            return;
        }
        Log.d(TAG, "requestPermissionsResult requestCode:" + requestCode);

        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults, permissionGrant);
            return;
        }

        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            Log.w(TAG, "requestPermissionsResult illegal requestCode:" + requestCode);
            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i(TAG, "onRequestPermissionsResult requestCode:" + requestCode + ",permissions:" + permissions.toString()
                + ",grantResults:" + grantResults.toString() + ",length:" + grantResults.length);

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onRequestPermissionsResult PERMISSION_GRANTED");
            //TODO success, do something, can use callback
            permissionGrant.onPermissionGranted(requestCode);

        } else {
            //TODO hint user this permission function
            Log.i(TAG, "onRequestPermissionsResult PERMISSION NOT GRANTED");
            //TODO
            String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
            openSettingActivity(activity, permissionsHint[requestCode], permissionGrant);
        }

    }

    static Dialog mDialog = null;

    //打开系统设置权限页面
    public static void openSettingActivity(final Activity activity, String message, final PermissionGrant permissionGrant) {
        if (null != mDialog && mDialog.isShowing()) {
            return;
        }
//        mDialog = DialogUtils.getInstance().showSelectDialog(activity, message, activity.getString(R.string.str_qu_xiao),activity.getString(R.string.confirm), new DialogUtils.DialogClickListener() {
//            @Override
//            public void confirm() {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Log.d(TAG, "getPackageName(): " + activity.getPackageName());
//                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
//                intent.setData(uri);
//                activity.startActivity(intent);
//            }
//
//            @Override
//            public void cancel() {
//                permissionGrant.onPermissionRefused();
//
//            }
//        });

    }


    //获取没有权限的数组列表
    public static ArrayList<String> getNoGrantedPermission(Activity activity, String[] requestpermissions, boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<>();
        for (int i = 0; i < requestpermissions.length; i++) {
            String requestPermission = requestpermissions[i];
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "RuntimeException:" + e.getMessage());
                return null;
            }

            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "getNoGrantedPermission ActivityCompat.checkSelfPermission != PackageManager.PERMISSION_GRANTED:" + requestPermission);
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                    Log.d(TAG, "shouldShowRequestPermissionRationale if");
                    if (isShouldRationale) {
                        permissions.add(requestPermission);
                    }

                } else {

                    if (!isShouldRationale) {
                        permissions.add(requestPermission);
                    }
                    Log.d(TAG, "shouldShowRequestPermissionRationale else");
                }

            }
        }

        return permissions;
    }

}

