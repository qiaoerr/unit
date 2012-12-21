package study.mp3player;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import study.download.HttpDownLoader;
import study.model.Mp3Info;
import study.xml.MP3ListContentHandler;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

public class Mp3ListActivity extends ListActivity {
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	private List<Mp3Info> mp3Infos = null;
	private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	private Handler handler = new Handler();
	private SimpleAdapter simpleAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mp3_list);
		// updateMp3List();
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			Mp3ListActivity.this.setListAdapter(simpleAdapter);
		}
	};

	private void updateMp3List() {
		new Thread() {
			@Override
			public void run() {
				try {
					HttpDownLoader downXml = new HttpDownLoader(
							"http://10.31.1.30:8080/mp3/resources.xml");
					String xml = downXml.downLoader();
					SAXParserFactory factory = SAXParserFactory.newInstance();
					XMLReader xmlReader = factory.newSAXParser().getXMLReader();
					mp3Infos = new ArrayList<Mp3Info>();
					xmlReader.setContentHandler(new MP3ListContentHandler(
							mp3Infos));
					xmlReader.parse(new InputSource(new StringReader(xml)));
					for (Iterator<Mp3Info> iterator = mp3Infos.iterator(); iterator
							.hasNext();) {
						HashMap<String, String> map = new HashMap<String, String>();
						Mp3Info mp3Info = iterator.next();
						map.put("mp3Name", mp3Info.getMp3Name());
						map.put("mp3Size", mp3Info.getMp3Size());
						list.add(map);
					}
					simpleAdapter = new SimpleAdapter(Mp3ListActivity.this,
							list, R.layout.listitem, new String[] { "mp3Name",
									"mp3Size" }, new int[] { R.id.text1,
									R.id.text2 });
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, UPDATE, 1, R.string.update);
		menu.add(0, ABOUT, 2, R.string.about);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == UPDATE) {
			updateMp3List();
		} else {

		}
		return super.onOptionsItemSelected(item);
	}
}
