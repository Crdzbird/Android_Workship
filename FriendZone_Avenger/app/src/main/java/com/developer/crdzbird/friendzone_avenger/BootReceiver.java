package com.developer.crdzbird.friendzone_avenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by crdzbird on 06-23-16.
 * This class is used to receive device boot event.
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		Intent msgIntent = new Intent(context, IntentServiceClass.class);
		context.startService(msgIntent);
		
		AlarmManagerTXTShield alarmManager=new AlarmManagerTXTShield();
		alarmManager.setAlarm(context);
	}

}
