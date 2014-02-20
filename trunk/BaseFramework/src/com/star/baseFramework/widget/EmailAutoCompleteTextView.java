package com.star.baseFramework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: EmailAutoCompleteTextView
 * @Description:
 * @author Comsys-Administrator
 * @date 2013-10-29 下午03:30:07
 */
public class EmailAutoCompleteTextView extends AutoCompleteTextView {
	private static final String TAG = "EmailAutoCompleteTextView";

	private String[] emailSuffixes = new String[] { "@qq.com", "@163.com",
			"@126.com", "@189.cn", "@sina.com", "@sohu.com", "@gmail.com",
			"@hotmail.com", "@yahoo.com", "@sina.cn" };

	public EmailAutoCompleteTextView(Context context) {
		this(context, null);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.autoCompleteTextViewStyle);
	}

	public EmailAutoCompleteTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public void setAdapterString(String[] emailSuffixes) {
		if (emailSuffixes != null && emailSuffixes.length > 0) {
			this.emailSuffixes = emailSuffixes;
		}
	}

	private void init(final Context context) {
		Log.i(TAG, "init");
		// emailSuffixes可通过setAdapterString方法更改
		this.setAdapter(new EmailAutoCompleteAdapter(context,
				android.R.layout.simple_list_item_1, emailSuffixes));
		// 输入1个字符即开启自动完成
		this.setThreshold(1);

		this.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String currentText = EmailAutoCompleteTextView.this.getText()
						.toString();
				if (hasFocus) {
					// 获得焦点时,重启自动完成
					if (!currentText.equals("")) {
						performFiltering(currentText, 0);
					}
				} else {
					// 丢失焦点时,可判断邮件格式是否正确
					if (!checkEmailFormat(currentText)) {
						Toast toast = Toast.makeText(context, "邮件地址格式不正确",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.TOP, 0, 50);
						toast.show();
					}
				}
			}
		});
	}

	/**
	 * 检查邮件地址格式是否正确
	 */
	public boolean checkEmailFormat(String email) {
		// TODO
		if (email != null
				&& email.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void replaceText(CharSequence text) {
		// 默认为,使用Adapter中的文本来替换已输入文本
		// 更改为,将已输入部分与后缀合并
		String currentText = this.getText().toString();
		int index = currentText.indexOf("@");
		if (index != -1) {
			currentText = currentText.substring(0, index);
		}
		super.replaceText(currentText + text);
	}

	@Override
	protected void performFiltering(CharSequence text, int keyCode) {
		String currentText = text.toString();
		int index = currentText.indexOf("@");
		if (index == -1) {
			if (currentText.matches("^[a-zA-Z0-9_]+$")) {
				super.performFiltering("@", keyCode);
			} else
				this.dismissDropDown();// 当用户中途输入非法字符时，关闭下拉提示框

		} else {
			super.performFiltering(currentText.substring(index), keyCode);
		}
	}

	private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {

		public EmailAutoCompleteAdapter(Context context, int resource,
				String[] objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						android.R.layout.simple_list_item_1, parent, false);
			}
			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			String currentText = EmailAutoCompleteTextView.this.getText()
					.toString();
			int index = currentText.indexOf("@");
			if (index != -1) {
				currentText = currentText.substring(0, index);
			}
			// 将用户输入的文本与adapter中的email后缀拼接后，在下拉框中显示
			tv.setText(currentText + getItem(position));
			return convertView;
		}
	}

}
