package study.xml;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import study.model.Mp3Info;

public class MP3ListContentHandler extends DefaultHandler {
	private List<Mp3Info> mp3Infos = null;
	private Mp3Info mp3info = null;
	private String tagName = null;

	public MP3ListContentHandler(List<Mp3Info> mp3Infos) {
		this.mp3Infos = mp3Infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String temp = new String(ch, start, length);
		if (tagName.equals("mp3.name")) {
			mp3info.setMp3Name(temp);
		} else if (tagName.equals("mp3.size")) {
			mp3info.setMp3Size(temp);
		} else if (tagName.equals("lrc.name")) {
			mp3info.setLrcName(temp);
		} else if (tagName.equals("lrc.size")) {
			mp3info.setLrcSize(temp);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (localName.equals("resource")) {
			mp3info = new Mp3Info();
			mp3info.setId(attributes.getValue("id"));
		}
		this.tagName = localName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equals("resource")) {
			mp3Infos.add(mp3info);
			// System.out.println(mp3info.toString());
		}
		this.tagName = "";
	}

}
