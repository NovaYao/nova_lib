package com.example.lib_demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.nova123.lib.http.HttpManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HttpQueryPhoneNumActivity extends BaseActivity {

	private static final int QUERY_PHONE = 10;

	private EditText phoneEdit;

	private Button queryBtn;

	private TextView infoText;

	HttpManager httpManager = HttpManager.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_http_phone_number);

		phoneEdit = (EditText) findViewById(R.id.edit_phone);
		queryBtn = (Button) findViewById(R.id.query);
		infoText = (TextView) findViewById(R.id.text_info);

		queryBtn.setOnClickListener(this);

	}

	@Override
	public Object onStringMessageReceive(int requestCode, String str) {
		if (requestCode == QUERY_PHONE) {
			String str2 = getOperator(str) + " - " + getProvince(str);
			return str2;
		}
		return null;
	}

	private String getProvince(String str) {
		Pattern pattern = Pattern.compile("province:'.\\S'");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			String str2 = matcher.group();
			return getValue(str2);
		}
		return "";
	}

	private String getOperator(String str) {
		Pattern pattern = Pattern.compile("catName:'.\\S*'");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			String str2 = matcher.group();
			return getValue(str2);
		}
		return "";
	}

	private String getValue(String str) {
		Pattern pattern = Pattern.compile("'.*'");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			String str2 = matcher.group();
			str2 = str2.substring(1, str2.length() - 1);
			return str2;
		}
		return "";
	}

	@Override
	public void onHttpSuccess(int requestCode, Object obj) {
		infoText.setText("归属地 " + obj.toString());
	}

	@Override
	public void onClick(View v) {
		if (v == queryBtn) {
			String str = phoneEdit.getText().toString();
			if (!TextUtils.isEmpty(str)) {
				httpManager.exeGet(QUERY_PHONE,
						"http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="
								+ str, this);
			} else {
				Toast.makeText(this, "请输入手机号码!", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
