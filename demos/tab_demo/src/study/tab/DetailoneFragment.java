package study.tab;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailoneFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = new View(getActivity());
		Bundle bundle = getArguments();
		String tag = bundle.getString("tag");
		if (tag.equals("simple")) {
			view.setBackgroundColor(Color.RED);
		} else {
			view.setBackgroundColor(Color.BLUE);
		}
		return view;
	}
}
