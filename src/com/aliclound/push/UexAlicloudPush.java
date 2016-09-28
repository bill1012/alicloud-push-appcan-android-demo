package com.aliclound.push;

import org.zywx.wbpalmstar.engine.EBrowserView;
import org.zywx.wbpalmstar.engine.universalex.EUExBase;
import org.zywx.wbpalmstar.engine.universalex.EUExCallback;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;

/**
 * 
 * @author billJiang 
 * QQ:475572229
 *
 */
public class UexAlicloudPush extends EUExBase {

	static final String func_on_getDeviceId = "uexAlicloudPush.onGetDeviceId";
	static final String func_on_bindAccount="uexAlicloudPush.onBindAccount";
	static final String func_on_unbindAccount="uexAlicloudPush.onUnbindAccount";
	static final String func_on_addTag="uexAlicloudPush.onAddTag";
	static final String func_on_removeTag="uexAlicloudPush.onRemoveTag";
	
	static final String TAG = "UexAlicloudPush";

	static final CloudPushService pushService = AlibabaSDK
			.getService(CloudPushService.class);

	public UexAlicloudPush(Context context, EBrowserView view) {
		super(context, view);
	}

	// get DeviceId
	public void getDeviceId(String[] param) {
		try {
			String deviceId = pushService.getDeviceId();
			jsCallback(func_on_getDeviceId, 0, EUExCallback.F_C_TEXT, deviceId);
		} catch (Exception e) {
			Toast.makeText(mContext, "getDeviceId调用失败!", Toast.LENGTH_LONG)
					.show();
		}
	}

	// bindAccount
	public void bindAccount(String[] param) {
		if (param.length < 1)
			return;
		pushService.bindAccount(param[0]);
		jsCallback(func_on_bindAccount, 0, EUExCallback.F_C_TEXT, param[0]);
	}

	// unbindAccount
	public void unbindAccount(String[] param) {
		pushService.unbindAccount();
		jsCallback(func_on_unbindAccount, 0, EUExCallback.F_C_TEXT, null);
	}

	// addTag
	public void addTag(String[] param) {
		if (param.length < 1)
			return;
		final String tag=param[0];
		pushService.addTag(tag, new CommonCallback() {

			@Override
			public void onFailed(String arg0, String arg1) {
				Log.d(TAG, "@用户增加标签失败，原因：" + arg1);
				jsCallback(func_on_addTag, 1, EUExCallback.F_C_TEXT, arg1);
			}

			@Override
			public void onSuccess() {
				Log.d(TAG, "@用户增加标签成功");
				jsCallback(func_on_addTag, 0, EUExCallback.F_C_TEXT, tag);
			}

		});
	}

	// removeTag
	public void removeTag(String[] param) {
		if (param.length < 1)
			return;
		final String tag=param[0];
		pushService.removeTag(tag, new CommonCallback() {

			@Override
			public void onFailed(String arg0, String arg1) {
				Log.d(TAG, "@用户删除标签失败，原因：" + arg1);
				jsCallback(func_on_removeTag, 1, EUExCallback.F_C_TEXT, arg1);
			}

			@Override
			public void onSuccess() {
				Log.d(TAG, "@用户删除标签成功");
				jsCallback(func_on_removeTag, 0, EUExCallback.F_C_TEXT, tag);
			}

		});
	}

	// bindAccount-Dialog
	public void bindAccountDialog(String[] param) {
		if (param.length < 1) {
			return;
		}
		String title = param[0];
		String value = null;
		if (param.length > 1)
			value = param[1];
		new UexAlicloudPushDialog(mContext, this).show(title, value,
				"bindAccount");
	}

	// addTag-Dialog
	public void addTagDialog(String[] param) {
		if (param.length < 1) {
			return;
		}
		String title = param[0];
		String value = null;
		if (param.length > 1)
			value = param[1];
		new UexAlicloudPushDialog(mContext, this).show(title, value, "addTag");
	}

	// removeTag-Dialog
	public void removeTagDialog(String[] param) {
		if (param.length < 1) {
			return;
		}
		String title = param[0];
		String value = null;
		if (param.length > 1)
			value = param[1];
		new UexAlicloudPushDialog(mContext, this).show(title, value,
				"removeTag");
	}

	public void dialogCallBack(String methodName, String retValue) {
		switch (methodName) {
		case "bindAccount":
			if (retValue != null)
				this.bindAccount(new String[] { retValue });
			break;
		case "addTag":
			if(retValue!=null)
				this.addTag(new String[]{retValue});
			break;
		case "removeTag":
			   if(retValue!=null)
				   this.removeTag(new String[]{retValue});
			break;
		}
	}

	@Override
	protected boolean clean() {
		// TODO Auto-generated method stub
		return true;
	}

}
