package com.developer.crdzbird.friendzone_avenger;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by crdzbird on 06-23-16.
 * This class is used to recieve device admin activation.
 */

public class LockScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.lock_screen);
	}
}
