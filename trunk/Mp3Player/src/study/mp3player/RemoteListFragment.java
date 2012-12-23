package study.mp3player;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import study.download.HttpDownLoader;
import study.model.Mp3Info;
import study.mp3player.service.DownloadService;
import study.xml.MP3ListContentHandler;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RemoteListFragment extends ListFragment {
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	private List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
	private String[] remoteMusicList = null;
	private Handler handler = new Handler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Mp3Info mp3Info = mp3Infos.get(position);
		Intent intent = new Intent(getActivity(), DownloadService.class);
		// Bundle bundle = new Bundle();
		// bundle.putSerializable("mp3info", mp3Info);
		intent.putExtra("mp3info", mp3Info);
		getActivity().startService(intent);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1,
				new String[] { "Please update the remote music list" }));
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(0, UPDATE, 1, R.string.update);
		menu.add(0, ABOUT, 2, R.string.about);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == UPDATE) {
			updateMp3List();
		} else {
			Toast.makeText(getActivity(), "Thank you for support",
					Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, remoteMusicList));

		}
	};

	private void updateMp3List() {
		new Thread() {
			@Override
			public void run() {
				try {
					String ip = getActivity().getResources().getString(
							R.string.server_ip);
					HttpDownLoader downXml = new HttpDownLoader("http://" + ip
							+ ":8080/mp3/resources.xml");
					String xml = downXml.downLoader();
					SAXParserFactory factory = SAXParserFactory.newInstance();
					XMLReader xmlReader = factory.newSAXParser().getXMLReader();
					xmlReader.setContentHandler(new MP3ListContentHandler(
							mp3Infos));
					xmlReader.parse(new InputSource(new StringReader(xml)));
					StringBuffer sb = new StringBuffer();
					for (Iterator<Mp3Info> iterator = mp3Infos.iterator(); iterator
							.hasNext();) {
						Mp3Info mp3Info = iterator.next();
						sb.append(mp3Info.getMp3Name() + "==");
					}
					remoteMusicList = sb.toString().split("==");
					handler.post(runnable);
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
