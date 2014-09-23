package com.example.tuwenhunpai;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView mTextView = null;
	SpannableString msp = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTextView = (TextView) findViewById(R.id.myTextView);

		// ����һ�� SpannableString����
		msp = new SpannableString(
				"������������Сһ������ǰ��ɫ����ɫ��������б���б���»���ɾ����x1x2�绰�ʼ���վ���Ų��ŵ�ͼX���ۺ�/bot");

		// ��������(default,default-bold,monospace,serif,sans-serif)
		msp.setSpan(new TypefaceSpan("monospace"), 0, 2,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new TypefaceSpan("serif"), 2, 4,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// ���������С������ֵ,��λ�����أ�
		msp.setSpan(new AbsoluteSizeSpan(20), 4, 6,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		msp.setSpan(new AbsoluteSizeSpan(20, true), 6, 8,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �ڶ�������boolean
													// dip�����Ϊtrue����ʾǰ��������С��λΪdip������Ϊ���أ�ͬ�ϡ�

		// ���������С�����ֵ,��λ�����أ� ������ʾΪĬ�������С�Ķ��ٱ�
		msp.setSpan(new RelativeSizeSpan(0.5f), 8, 10,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 0.5f��ʾĬ�������С��һ��
		msp.setSpan(new RelativeSizeSpan(2.0f), 10, 12,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f��ʾĬ�������С������
		// ��������ǰ��ɫ
		msp.setSpan(new ForegroundColorSpan(Color.MAGENTA), 12, 15,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ����ǰ��ɫΪ���ɫ
		// �������屳��ɫ
		// msp.setSpan(new BackgroundColorSpan(Color.CYAN), 15, 18,
		// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ���ñ���ɫΪ��ɫ
		// ����������ʽ���������壬б�壬��б��
		msp.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), 18, 20,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ����
		msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 20, 22,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ����
		msp.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 22, 24,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // б��
		msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 24,
				27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ��б��

		// �����»���
		msp.setSpan(new UnderlineSpan(), 27, 30,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// ����ɾ����
		msp.setSpan(new StrikethroughSpan(), 30, 33,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// �������±�
		msp.setSpan(new SubscriptSpan(), 34, 35,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �±�
		msp.setSpan(new SuperscriptSpan(), 36, 37,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �ϱ�

		// �������ӣ���Ҫ����setMovementMethod����������Ӧ��
		msp.setSpan(new URLSpan("tel:4155551212"), 37, 39,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �绰
		msp.setSpan(new URLSpan("mailto:webmaster@google.com"), 39, 41,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // �ʼ�
		msp.setSpan(new URLSpan("http://www.baidu.com"), 41, 43,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ����
		msp.setSpan(new URLSpan("sms:4155551212"), 43, 45,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ���� ʹ��sms:����smsto:
		msp.setSpan(new URLSpan("mms:4155551212"), 45, 47,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ���� ʹ��mms:����mmsto:
		msp.setSpan(new URLSpan("geo:38.899533,-77.036476"), 47, 49,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ��ͼ
		// ���������С�����ֵ,��λ�����أ� ������ʾΪĬ��������ȵĶ��ٱ�
		msp.setSpan(new ScaleXSpan(2.0f), 49, 51,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 2.0f��ʾĬ��������ȵ���������X�᷽��Ŵ�ΪĬ����������������߶Ȳ���

		// �������壨���ΰ����������ƣ������С��������ʽ��������ɫ��������ɫ��
		ColorStateList csllink = null;
		ColorStateList csl = null;
		XmlResourceParser xppcolor = getResources().getXml(R.color.color);
		try {
			csl = ColorStateList.createFromXml(getResources(), xppcolor);
		} catch (XmlPullParserException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		XmlResourceParser xpplinkcolor = getResources().getXml(
				R.color.linkcolor);
		try {
			csllink = ColorStateList
					.createFromXml(getResources(), xpplinkcolor);
		} catch (XmlPullParserException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		msp.setSpan(new TextAppearanceSpan("monospace",
				android.graphics.Typeface.BOLD_ITALIC, 30, csl, csllink), 51,
				53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		// ������Ŀ����
		msp.setSpan(new BulletSpan(
				android.text.style.BulletSpan.STANDARD_GAP_WIDTH, Color.GREEN),
				0, msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ��һ��������ʾ��Ŀ����ռ�õĿ��ȣ��ڶ�������Ϊ��Ŀ���ŵ���ɫ

		// ����ͼƬ
		Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		msp.setSpan(new ImageSpan(drawable), 53, 57,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		mTextView.setText(msp);
		mTextView.setMovementMethod(LinkMovementMethod.getInstance());
	}
}