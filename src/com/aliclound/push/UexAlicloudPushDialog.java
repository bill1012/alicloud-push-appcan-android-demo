package com.aliclound.push;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class UexAlicloudPushDialog {

	private Context mContext;
	private UexAlicloudPush mAliPush;

	public UexAlicloudPushDialog(Context ctx, UexAlicloudPush UexAlicloudPush) {
		mContext = ctx;
		mAliPush = UexAlicloudPush;
	}

	public void show(String title,String value,final String methodName) {
		AlertDialog.Builder dia = new AlertDialog.Builder(mContext);
		dia.setTitle(null);
		dia.setMessage(title);
		final EditText input = new EditText(mContext);
		if (value != null) {
			input.setText(value);
		}
		dia.setView(input);
		dia.setCancelable(false);
		dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String ret = input.getText().toString();
				mAliPush.dialogCallBack(methodName,ret);
			}
		});
		dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mAliPush.dialogCallBack(methodName,null);
			}
		});
		dia.create();
		dia.show();
	}

}
