package com.example.lib_demo;

import net.nova123.lib.http.HttpManager;
import net.nova123.lib.http.HttpResultReceiver;
import android.app.Activity;
import android.view.*;

public class BaseActivity extends Activity implements HttpResultReceiver,
		View.OnClickListener {

	@Override
	public void onHttpFailure(int requestCode, int code, String str) {
	}

	@Override
	public void onHttpSuccess(int requestCode, Object t) {
	}

	@Override
	public Object onStringMessageReceive(int requestCode, String str) {
		return null;
	}

	@Override
	public void onClick(View v) {

	}

}
