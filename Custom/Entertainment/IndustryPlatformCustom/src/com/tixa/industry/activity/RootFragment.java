package com.tixa.industry.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tixa.industry.R;

public class RootFragment extends Fragment {
	private String modularName;
	private String typeID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tab_root, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Bundle args = getArguments();
		modularName = getArguments().getString("modularName");
		typeID = getArguments().getString("typeID");
		String rootClass = args.getString("root");
		initFragment(rootClass);

	}

	public boolean popBackStack() {
		return getChildFragmentManager().popBackStackImmediate();
	}

	public void clearBackStack() {
		getChildFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	private void initFragment(String rootClass) {

		FragmentManager fragmentManager = getChildFragmentManager();
		if (fragmentManager.findFragmentById(R.id.fragment) != null) {
			return;
		}

		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		Fragment fragment = null;

		fragment = Fragment.instantiate(getActivity(), rootClass);

		Bundle args = new Bundle();
		args.putString("modularName", modularName);
		args.putString("typeID", typeID);
		args.putBoolean("isNav", true);
		fragment.setArguments(args);
		fragmentTransaction.replace(R.id.fragment, fragment);
		fragmentTransaction.commitAllowingStateLoss();

	}

	// @Override
	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// Toast.makeText(getActivity(), data.getStringExtra("result") + ":root",
	// 1).show();
	// }

}
