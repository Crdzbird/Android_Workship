
package com.developer.crdzbird.friendzone_avenger;

/**
 * Created by crdzbird on 06-23-16.
 * This class is used to verify the Admin Privileges
 */

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class PreyDeviceAdmin extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        System.out.println("Device Admin enabled");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return context.getText(R.string.preferences_admin_enabled_dialog_message).toString();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        System.out.println("Device Admin disabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        System.out.println("Password was changed successfully");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {

        /*
        if (preyConfig.isLockSet()){
            PreyLogger.d("Password was entered successfully");
            preyConfig.setLock(false);
            FroyoSupport.getInstance(context).changePasswordAndLock("", false);
            final Context contexfinal=context;
            new Thread(){
                public void run() {
                    PreyWebServices.getInstance().sendNotifyActionResultPreyHttp(contexfinal, UtilJson.makeMapParam("stop","lock","stopped"));
                }
            }.start();
        }*/
    }

}
