/*
 * Copyright 2016 Freelander
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeez.guanpj.jreadhub.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 安卓 6.0 权限管理
 * Created by Jun on 2016/10/20.
 */
public class PermissionsChecker {
    private static Activity mContext;

    private static PermissionsChecker mPermissionChecker;

    public static int REQUEST_STORAGE_PERMISSION = 100;

    public static String[] photosPermissions = new String[]{
            Manifest.permission.CAMERA
    };

    public static String[] storagePermissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };

    private static List<String> closePermissions = new ArrayList<>();

    public static PermissionsChecker getInstance(Context context) {
        if (mPermissionChecker == null) {
            mPermissionChecker = new PermissionsChecker(context);
        }

        return mPermissionChecker;
    }

    private PermissionsChecker(Context context) {
        mContext = (Activity) context;
    }

    // 判断权限集合
    public static boolean checkPermissions(Context context, String... permissions) {
        mContext = (Activity) context;
        closePermissions.clear();

        for (String permission : permissions) {
            if (!lacksPermission(permission)) {
                closePermissions.add(permission);
            }
        }

        if (closePermissions.size() != 0) {
            requestPermission(closePermissions.toArray(new String[closePermissions.size()]));
            return false;
        } else {
            return true;
        }

    }

    // 判断是否缺少权限
    private static boolean lacksPermission(String permission) {
        int checkSelfPermission = ContextCompat.checkSelfPermission(mContext, permission);
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
    }

    //询问是否允许开启权限
    private static void requestPermission(final String[] permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(mContext)
                    .setMessage("你需要启动权限才能使用该功能")
                    .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mContext, permission, REQUEST_STORAGE_PERMISSION);//请求开启权限
                        }
                    })
                    .setNegativeButton("拒绝", null)
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(mContext, permission, REQUEST_STORAGE_PERMISSION);
        }
    }

}
