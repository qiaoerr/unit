package com.tixa.industry.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKRoute;
import com.tixa.industry.R;
import com.tixa.industry.util.BMapUtil;
import com.tixa.industry.widgetCustom.MyRouteMapView;
import com.tixa.industry.widgetCustom.TopBar;
import com.tixa.industry.widgetCustom.TopBar.TopBarListener;

/**
 * @author administrator
 * @version
 * 
 */
public class RouteMapActivity extends Activity implements OnClickListener {

	private Context context;
	private TopBar topbar;
	private MyRouteMapView mMapView;
	private ImageButton mBtnPre;
	private ImageButton mBtnNext;
	private View viewCache;
	private TextView popupText;
	private PopupOverlay pop;
	private int position;
	private String type;
	private TransitOverlay transitOverlay;
	private RouteOverlay routeOverlay;
	MKRoute route = null;// 保存驾车/步行路线数据的变量，供浏览节点时使用
	int nodeIndex = 0;// 节点索引,供浏览节点时使用

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.layout_route_map);
		initData();
		initView();
	}

	private void initData() {
		position = getIntent().getIntExtra("position", 0);
		type = getIntent().getStringExtra("type");

		System.out.println("position: " + position);
		System.out.println("type: " + type);

	}

	private void initView() {
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.setShowConfig("线路详情", R.drawable.top_back, 0);
		topbar.setTopBarListener(new TopBarListener() {

			@Override
			public void btnRightOnClick() {

			}

			@Override
			public void btnLeftOnClick() {
				finish();

			}
		});

		// 初始化地图
		mMapView = (MyRouteMapView) findViewById(R.id.mapView);
		mMapView.setBuiltInZoomControls(false);
		mMapView.getController().setZoom(12);
		mMapView.getController().enableClick(true);
		mBtnPre = (ImageButton) findViewById(R.id.pre);
		mBtnNext = (ImageButton) findViewById(R.id.next);
		mBtnPre.setOnClickListener(this);
		mBtnNext.setOnClickListener(this);
		createPaopao();
		if (type.equals(RouteListActivity.TRANSIT)) {
			transitOverlay = new TransitOverlay(this, mMapView);
			transitOverlay.setData(RouteListActivity.mkTransitRouteResult
					.getPlan(position));
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(transitOverlay);
			mMapView.refresh();
		} else {
			routeOverlay = new RouteOverlay(this, mMapView);
			if (type.equals(RouteListActivity.DRIVER)) {
				routeOverlay.setData(RouteListActivity.mkDrivingRouteResult
						.getPlan(0).getRoute(position));
			} else if (type.equals(RouteListActivity.WALK)) {
				routeOverlay.setData(RouteListActivity.mkWalkingRouteResult
						.getPlan(0).getRoute(position));
			}
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
		}
	}

	@Override
	public void onClick(View v) {
		nodeClick(v);
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
		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);

		if (type.equals(RouteListActivity.DRIVER)
				|| type.equals(RouteListActivity.WALK)) {
			// 驾车、步行使用的数据结构相同，因此类型为驾车或步行，节点浏览方法相同
			if (type.equals(RouteListActivity.DRIVER)) {
				route = RouteListActivity.mkDrivingRouteResult.getPlan(0)
						.getRoute(0);
			} else {
				route = RouteListActivity.mkWalkingRouteResult.getPlan(0)
						.getRoute(0);
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

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}
}
