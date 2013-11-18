package com.start.jdzchina.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.start.jdzchina.R;
import com.start.jdzchina.util.BDLocationUtil;
import com.start.jdzchina.util.BDLocationUtil.LocationSuccessListener;
import com.start.jdzchina.util.BMapUtil;
import com.start.jdzchina.widget.MyRouteMapView;

public class MapFragment extends Fragment implements OnClickListener {
	private static final int showMapView = 1;
	private static final int hideMapView = 2;
	public static final String BUS = "bus";
	public static final String DRIVER = "driver";
	public static final String WALK = "walk";
	private FragmentActivity context;
	private View view;
	private View loadingView;
	private MyRouteMapView mMapView;
	private ProgressDialog progressDialog;
	private MapController mMapController;
	private BDLocation bdLocation;
	private ImageButton mBtnPre;
	private ImageButton mBtnNext;
	private TextView popupText;
	private PopupOverlay pop;
	private int position;
	private View viewCache;
	private String type;
	private TransitOverlay transitOverlay;
	private RouteOverlay routeOverlay;
	MKRoute route = null;// 保存驾车/步行路线数据的变量，供浏览节点时使用
	int nodeIndex = 0;// 节点索引,供浏览节点时使用
	private MKDrivingRouteResult drivingRouteResult;
	private MKWalkingRouteResult walkingRouteResult;
	private MKTransitRouteResult transitRouteResult;

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
		mMapView = (MyRouteMapView) view.findViewById(R.id.mapView);
		mMapController = mMapView.getController();
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		// handler.sendEmptyMessageDelayed(1, 200);
		mBtnPre = (ImageButton) view.findViewById(R.id.pre);
		mBtnNext = (ImageButton) view.findViewById(R.id.next);
		mBtnPre.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
		// createPaopao();

	}

	@Override
	public void onClick(View v) {
		// nodeClick(v);
	}

	public void createPaopao() {

		// 泡泡点击响应回调
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
				// Toast.makeText(context, "click", 1).show();
			}
		};
		pop = new PopupOverlay(mMapView, popListener);
		mMapView.setPop(pop);

	}

	/**
	 * 节点浏览
	 * */
	public void nodeClick(View v) {
		viewCache = LayoutInflater.from(context).inflate(
				R.layout.custom_text_view, null);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);

		if (type.equals(DRIVER) || type.equals(WALK)) {
			// 驾车、步行使用的数据结构相同，因此类型为驾车或步行，节点浏览方法相同
			if (type.equals(DRIVER)) {
				route = drivingRouteResult.getPlan(0).getRoute(0);
			} else {
				route = walkingRouteResult.getPlan(0).getRoute(0);
			}

			if (nodeIndex < -1 || route == null
					|| nodeIndex >= route.getNumSteps())
				return;

			// 上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 0) {
				// 索引减
				nodeIndex--;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
			// 下一个节点
			if (mBtnNext.equals(v) && nodeIndex < (route.getNumSteps() - 1)) {
				// 索引加
				nodeIndex++;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						route.getStep(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(route.getStep(nodeIndex).getContent());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText), route
						.getStep(nodeIndex).getPoint(), 5);
			}
		} else {
			// 公交换乘使用的数据结构与其他不同，因此单独处理节点浏览
			if (nodeIndex < -1 || transitOverlay == null
					|| nodeIndex >= transitOverlay.getAllItem().size())
				return;

			// 上一个节点
			if (mBtnPre.equals(v) && nodeIndex > 1) {
				// 索引减
				nodeIndex--;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						transitOverlay.getItem(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(), 5);
			}
			// 下一个节点
			if (mBtnNext.equals(v)
					&& nodeIndex < (transitOverlay.getAllItem().size() - 2)) {
				// 索引加
				nodeIndex++;
				// 移动到指定索引的坐标
				mMapView.getController().animateTo(
						transitOverlay.getItem(nodeIndex).getPoint());
				// 弹出泡泡
				popupText.setBackgroundResource(R.drawable.custom_info_bubble);
				popupText.setText(transitOverlay.getItem(nodeIndex).getTitle());
				pop.showPopup(BMapUtil.getBitmapFromView(popupText),
						transitOverlay.getItem(nodeIndex).getPoint(), 5);
			}
		}
	}

	private void initOverlay() {
		if (type.equals(BUS)) {
			transitOverlay = new TransitOverlay(context, mMapView);
			transitOverlay.setData(transitRouteResult.getPlan(position));
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(transitOverlay);
			mMapView.refresh();
		} else {
			routeOverlay = new RouteOverlay(context, mMapView);
			if (type.equals(DRIVER)) {
				routeOverlay.setData(drivingRouteResult.getPlan(0).getRoute(
						position));
			} else if (type.equals(WALK)) {
				routeOverlay.setData(walkingRouteResult.getPlan(0).getRoute(
						position));
			}
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
		}
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		if (mMapView != null && savedInstanceState != null) {
			mMapView.onRestoreInstanceState(savedInstanceState);
		}
		super.onViewStateRestored(savedInstanceState);
	}

}
