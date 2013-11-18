package com.tixa.industry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.tixa.industry.R;

/**
 * @author administrator
 * @version
 * 
 */
public class RouteAdapter extends BaseAdapter {
	private Context context;
	private Object object;
	private LayoutInflater inflater;
	private MKWalkingRouteResult mkWalkingRouteResult;
	private MKTransitRouteResult mkTransitRouteResult;
	private MKDrivingRouteResult mkDrivingRouteResult;

	public RouteAdapter(Context context, Object object) {
		super();
		this.context = context;
		this.object = object;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (object instanceof MKTransitRouteResult) {
			mkTransitRouteResult = (MKTransitRouteResult) object;
			return mkTransitRouteResult.getNumPlan();
		} else if (object instanceof MKDrivingRouteResult) {
			mkDrivingRouteResult = (MKDrivingRouteResult) object;
			return mkDrivingRouteResult.getNumPlan();
		} else if (object instanceof MKWalkingRouteResult) {
			mkWalkingRouteResult = (MKWalkingRouteResult) object;
			return mkWalkingRouteResult.getNumPlan();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_route, null);
			holder.plan_num = (TextView) convertView
					.findViewById(R.id.plan_num);
			holder.change_num = (TextView) convertView
					.findViewById(R.id.change_num);
			holder.distance = (TextView) convertView
					.findViewById(R.id.distance);
			holder.route = (TextView) convertView.findViewById(R.id.route);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (mkTransitRouteResult != null) {
			holder.plan_num.setText((position + 1) + "");
			holder.change_num.setText("换乘"
					+ mkTransitRouteResult.getPlan(position).getNumLines()
					+ "次");
			holder.distance.setText("总距离："
					+ mkTransitRouteResult.getPlan(position).getDistance()
					+ "米");
			holder.route.setText(mkTransitRouteResult.getPlan(position)
					.getContent());

		} else if (mkDrivingRouteResult != null) {
			holder.plan_num.setText((position + 1) + "");
			holder.change_num.setVisibility(View.GONE);
			holder.distance.setText("总距离："
					+ mkDrivingRouteResult.getPlan(position).getDistance()
					+ "米");
			MKRoute mkRoute = mkDrivingRouteResult.getPlan(0)
					.getRoute(position);
			String temp = "";
			for (int i = 0; i < mkRoute.getNumSteps(); i++) {
				temp += mkRoute.getStep(i).getContent();
			}
			holder.route.setText(temp);

		} else if (mkWalkingRouteResult != null) {
			holder.plan_num.setText((position + 1) + "");
			holder.change_num.setVisibility(View.GONE);
			holder.distance.setText("总距离："
					+ mkWalkingRouteResult.getPlan(position).getDistance()
					+ "米");
			MKRoute mkRoute = mkWalkingRouteResult.getPlan(0)
					.getRoute(position);
			String temp = "";
			for (int i = 0; i < mkRoute.getNumSteps(); i++) {
				temp += mkRoute.getStep(i).getContent();
			}
			holder.route.setText(temp);

		}
		return convertView;
	}

	class ViewHolder {
		TextView plan_num;
		TextView change_num;
		TextView distance;
		TextView route;
	}
}
