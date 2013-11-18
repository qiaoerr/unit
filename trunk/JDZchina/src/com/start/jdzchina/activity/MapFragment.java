package com.start.jdzchina.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.start.jdzchina.R;
import com.start.jdzchina.util.BDLocationUtil;
import com.start.jdzchina.util.BDLocationUtil.LocationSuccessListener;

public class MapFragment extends Fragment {
	private Context context;
	private View view;
	private static final int showMapView = 1;
	private static final int hideMapView = 2;
	private View loadingView;
	private MapView mMapView;
	private ProgressDialog progressDialog;
	private MapController mMapController;
	private BDLocation bdLocation;

	/*private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// loadingView.setVisibility(View.GONE);
		};
	};*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.map_layout, null);
		initData();
		initView();
		return view;
	}

	private void initData() {
		context = getActivity();
		progressDialog = ProgressDialog.show(context, "", "定位中...", true, true);
		BDLocationUtil.getLocation(context, new LocationSuccessListener() {

			@Override
			public void dealWithLocationData(BDLocation location) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
				if (location == null) {
					Toast.makeText(context, "定位失败...", Toast.LENGTH_SHORT)
							.show();
				} else {
					bdLocation = location;
				}
			}
		});
	}

	private void initView() {
		loadingView = view.findViewById(R.id.loadingView);
		mMapView = (MapView) view.findViewById(R.id.mapView);
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		// handler.sendEmptyMessageDelayed(1, 200);

	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		// mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// mMapView.destroy();
		mMapView = null;
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
