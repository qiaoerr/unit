package com.example.fragmentdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 显示界面的Fragment，加载了display_fragment布局。
 * 
 * @author guolin
 */
public class DisplayFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.display_fragment, container, false);
		return view;
	}
}
